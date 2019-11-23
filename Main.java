import java.util.Scanner;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        String inputan = getInput(0);

        ArrayList<Term> arrToken = tokenize("p xor q and q iff r or if not s then q");
        print(arrToken);
        PDA pda = createPDA2();
        System.out.println(pda.validate(arrToken));
    }

    private static String getInput(int mode) {
        String inputan;
        switch (mode) {
            case 0: return "p and q";
            case 1: return "p and q or p iff if s then q or p";
            case 2: return "(q and p xor(     if q then p) iff ((q)))";
            case 3: return "p iff";
            case 4: return "((if (q) then q) iff (r xor p))";
            default:
                Scanner scanner = new Scanner(System.in);
                inputan = scanner.nextLine();
                scanner.close();
                break;
        }
        return inputan;
    }
    private static ArrayList<String> splitInput (String inputan) {
        /** split inputan */
        ArrayList<String> arrKata = new ArrayList<String>();
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
        /*
        for (String s : arrKata) {
            System.out.print(s + " ");
        }
        System.out.println();
        */

        return arrKata;
    }
    private static ArrayList<Term> tokenize(String inputan) {

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

        ArrayList<Term> arrIdToken = new ArrayList<> ();
        ArrayList<String> arrKata = splitInput(inputan);

        for (String kata : arrKata) {
            boolean b = false;
            for (int i = 0; i < arrFa.length; i++) {
                final FA fa = arrFa[i];
                int idToken = fa.getId(kata);
                // System.out.println(idToken);
                if (idToken != 0) {
                    b = true;
                    arrIdToken.add(new Term(idToken));
                    break;
                }
            }
            if (!b) {
                System.out.println("error ");
                break;
            }
        }
        return arrIdToken;
    }
    private static void print(ArrayList<Term> arr) {
        for (Term obj : arr) {
            System.out.print(obj.val + " ");
        }
        System.out.println();
    }
    private static PDA createPDA () {
        char[] arrC = new char[13];
        arrC[0] = 'S';
        char temp = 'A';
        for (int i = 1; i <= 12; i++) {
            arrC[i] = temp++;
        }
        /**
         * Tabel Transisi:
                1		2		3		4		5		6		7		8		9		10		EOS
            S	HI|1	CH		-		-		-		KL		-		-		FJ		-		EOS
            A	1		-		-		-		-		-		-		-		-		-		-
            B	-		-		3		4		5		-		-		8		-		-		-
            C	-		2		-		-		-		-		-		-		-		-		-
            D	-		-		-		-		-		6		-		-		-		-		-
            E	-		-		-		-		-		-		7		-		-		-		-
            F	-		-		-		-		-		-		-		-		9		-		-
            G	-		-		-		-		-		-		-		-		-		10		-
            H	HI|1	CH		-		-		-		KL		-		-		FJ		-		-
            I	-		-		BH		BH		BH		-		-		BH		-		-		-
            J	HG		HG		-		-		-		HG		-		-		HG		-		-
            K	-		-		-		-		-		DH		-		-		-		-		-
            L	-		-		-		-		-		-		EH		-		-		-		-
        */
        Term[] ta = Term.toArr(new int[]{0,1,2,3,4,5,6,7,8,9,10});
        Nonterm[] nta = Nonterm.toArr(new String[] {
            "HI", "CH", "KL", "FJ", "BH", "HG", "DH", "EH"
        });
        Union[][][] transTab = {
            {{nta[0],ta[1]},    {nta[1]},   {},         {},         {},         {nta[2]},   {},         {},         {nta[3]},   {}          },
            {{ta[1]},           {},         {},         {},         {},         {},         {},         {},         {},         {}          },
            {{},                {},         {ta[3]},    {ta[4]},    {ta[5]},    {},         {},         {ta[8]},    {},         {}          },
            {{},                {ta[2]},    {},         {},         {},         {},         {},         {},         {},         {}          },
            {{},                {},         {},         {},         {},         {ta[6]},    {},         {},         {},         {}          },
            {{},                {},         {},         {},         {},         {},         {ta[7]},    {},         {},         {}          },
            {{},                {},         {},         {},         {},         {},         {},         {},         {ta[9]},    {}          },
            {{},                {},         {},         {},         {},         {},         {},         {},         {},         {ta[10]}    },
            {{nta[0],ta[1]},    {nta[1]},   {},         {},         {},         {nta[2]},   {},         {},         {nta[3]},   {}          },
            {{},                {},         {nta[4]},   {nta[4]},   {nta[4]},   {},         {},         {nta[4]},   {},         {}          },
            {{nta[5]},          {nta[5]},   {},         {},         {},         {nta[5]},   {},         {},         {nta[5]},   {}          },
            {{},                {},         {},         {},         {},         {nta[6]},   {},         {},         {},         {}          },
            {{},                {},         {},         {},         {},         {},         {nta[7]},   {},         {},         {}          }
        };
        Term[] terms = new Term[10];
        for (int i = 0; i < terms.length; i++) {
            terms[i] = ta[i+1];
        }
        // terms = {1,2,3,4,5,6,7,8,9,10}
        return new PDA(arrC[0], arrC, terms, transTab);
    }
    private static PDA createPDA2 () {
        char[] arrC = {'S', 'A', 'B'};
        Nonterm[] nt = Nonterm.toArr(arrC);
        /**
            Tabel Transisi:
                1       2       3   4   5   6           7   8   9           10
            S   A       A                   A                   A
            A   ABA|1   ABA|2A              ABA|6A7A            ABA|9A10
            B                   3   4   5                   8
        */
        Term[] t = Term.toArr(new int[]{0,1,2,3,4,5,6,7,8,9,10});
        Union[][][][] transTab = {
            { {{nt[1]}}, {{nt[1]}}, {}, {}, {}, {{nt[1]}}, {}, {}, {{nt[1]}}, {} },
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