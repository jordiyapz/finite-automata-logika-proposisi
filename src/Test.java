import java.util.ArrayList;

class Test extends Main {
    public static void main(String[] args) {
        unitTest();
    }

    private static boolean unitTest () {
        Object[][] testCase = {
            {"(p) and q",                                   true},
            {"p and q or p iff if S then q or p",           true},
            {"(q AND p xor(     if q then p) iff ((q)))",   true},
            {"p iff",                                       false},
            {"((if p and s then q) iff (r xor p))",         true},
            {"p xor q and q iff r or if not s then q",      true},
            {"pq",                                          false},
            {"not p iff",                                   false},
            {"p and q iff (p xor (if p and s then r))",     true}
        };
        ArrayList<Term> arrToken = null;
        PDA pda = createPDA();
        boolean result, finalResult = true;
        for (int i = 0; i < testCase.length; i++) {
            String inputan = (String) testCase[i][0];
            boolean testValue = (boolean) testCase[i][1];
            System.out.println("Test case " + i + ": " + inputan);
            System.out.print("Result: ");
            result = false;
            arrToken = new ArrayList<>();
            if (lexicalize(arrToken, inputan))
                result = pda.validate(arrToken);
            System.out.println((result)? "Valid":"Not valid");
            result = result == testValue;
            System.out.println((result)? "Passed": "Not Passed");
            System.out.println();
            if (!result) finalResult = false;
        }

        System.out.println("Final test result: " + ((finalResult)? "Passed": "Not Passed"));
        return finalResult;
    }
}