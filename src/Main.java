import java.util.Scanner;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        System.out.print("Input: ");
        Scanner scanner = new Scanner(System.in);
        String inputan = scanner.nextLine();
        scanner.close();
        ArrayList<Term> arrToken = new ArrayList<>();
        if (lexicalize(arrToken, inputan)) {
            System.out.print("Lexic: ");
            print(arrToken);
            PDA pda = createPDA();
            System.out.println("Result : " + ((pda.validate(arrToken))? "Valid":"Not valid"));
        } else {
            System.out.println("Wrong input");
        }
    }

    protected static ArrayList<String> splitInput (String inputan) {
        /** split inputan */
        ArrayList<String> arrKata = new ArrayList<String>();
        inputan = inputan.toLowerCase();
        char[] arrChar = inputan.toCharArray();
        String kata = "";
        boolean start = true;
        boolean tokened = false;
        for (int i = 0; i < arrChar.length; i++) {
            final char c = arrChar[i];
            switch (c) {
                case ' ':
                    if (tokened) continue;
                    else if (!start) {
                        arrKata.add(kata);
                        kata = "";
                        tokened = true;
                    }
                    break;
                case '(':
                case ')':
                    start = false;
                    if (!tokened && kata != "") {
                        arrKata.add(kata);
                        kata = "";
                    }
                    arrKata.add(String.valueOf(c));
                    tokened = true;
                    break;
                default:
                    start = false;
                    tokened = false;
                    kata += c;
            }
        }
        if (!tokened) {
            arrKata.add(kata);
        }

        return arrKata;
    }
    protected static boolean lexicalize(ArrayList<Term> arrToken, String inputan) {

        FA[] arrFa = {
            new FA (1, "pqrs", new int[][]{{1, 1, 1, 1},{-1, -1, -1}}, new int[]{1}),
            new FA (2, "not"),
            new FA (3, "and"),
            new FA (4, "or"),
            new FA (5, "xor"),
            new FA (6, "if"),
            new FA (7, "then"),
            new FA (8, "if", new int[][]{{1,-1},{-1, 2},{-1,3},{-1,-1}}, new int[]{3}),
            new FA (9, "("),
            new FA (10, ")")
        };

        ArrayList<String> arrKata = splitInput(inputan);

        for (String kata : arrKata) {
            boolean b = false;
            for (int i = 0; i < arrFa.length; i++) {
                final FA fa = arrFa[i];
                int idToken = fa.getId(kata);
                // System.out.println(idToken);
                if (idToken != 0) {
                    b = true;
                    arrToken.add(new Term(idToken));
                    break;
                }
            }
            if (!b) {
                return false;
            }
        }
        return true;
    }
    protected static void print(ArrayList<Term> arr) {
        for (Term obj : arr) {
            System.out.print(obj.val + " ");
        }
        System.out.println();
    }

    protected static PDA createPDA () {
        char[] arrC = {'S', 'A', 'B'};
        Nonterm[] nt = Nonterm.toArr(arrC);
        /** Tabel Transisi:
                1       2       3   4   5   6           7   8   9           10
            S   A       A                   A                   A|910
            A   ABA|1   ABA|2A              ABA|6A7A            ABA|9A10
            B                   3   4   5                   8
        */
        Term[] t = Term.toArr(new int[]{0,1,2,3,4,5,6,7,8,9,10});
        Union[][][][] transTab = {
            { {{nt[1]}}, {{nt[1]}}, {}, {}, {}, {{nt[1]}}, {}, {}, {{nt[1]},{t[9],t[10]}}, {} },
            { {{nt[1],nt[2],nt[1]},{t[1]}}, {{nt[1],nt[2],nt[1]},{t[2],nt[1]}}, {}, {}, {}, {{nt[1],nt[2],nt[1]},{t[6],nt[1],t[7],nt[1]}}, {}, {}, {{nt[1],nt[2],nt[1]},{t[9],nt[1],t[10]}}, {} },
            { {}, {}, {{t[3]}}, {{t[4]}}, {{t[5]}}, {}, {}, {{t[8]}}, {}, {} }
        };

        Term[] terms = new Term[10];
        for (int i = 0; i < terms.length; i++) {
            terms[i] = t[i+1];
        }
        return new PDA(arrC[0], arrC, terms, transTab);
    }
}