import java.util.Scanner;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputan = scanner.nextLine();
        scanner.close();
        // String inputan = "p and q or iff if s then q or p";
        // String inputan = "(q and p xor(      if q then p) iff ((q)))";

        ArrayList<Integer> arrIdToken = tokenize(splitInput(inputan));
        
        for (int id : arrIdToken) {
            System.out.printf("%d ", id);
        }
        System.out.println();

    }

    private static ArrayList<String> splitInput (String inputan) { 
        /** split inputan */
        ArrayList<String> arrKata = new ArrayList<String>();
        char[] arrChar = inputan.toCharArray();
        String kata = "";
        boolean tokened = false;
        for (int i = 0; i < arrChar.length; i++) {
            final char c = arrChar[i];
            switch (c) {
                case ' ':
                    if (tokened) continue;
                    else {
                        arrKata.add(kata);
                        kata = "";
                        tokened = true;
                    }
                    break;
                case '(':
                case ')':
                    if (!tokened && kata != "") {
                        arrKata.add(kata);
                        kata = "";           
                    }
                    arrKata.add(String.valueOf(c));
                    tokened = true;
                    break;
                default:
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

    private static ArrayList<Integer> tokenize(ArrayList<String> arrKata) {

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

        ArrayList<Integer> arrIdToken = new ArrayList<> ();
        for (String kata : arrKata) {
            boolean b = false;
            for (int i = 0; i < arrFa.length; i++) {
                final FA fa = arrFa[i];
                int idToken = fa.getId(kata);
                // System.out.println(idToken);
                if (idToken != 0) {
                    b = true;                    
                    arrIdToken.add((Integer) idToken);
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
}