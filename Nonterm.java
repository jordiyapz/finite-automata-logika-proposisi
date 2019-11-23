class Nonterm extends Union {
    String val;
    Nonterm(String s) {
        val = s;
    }
    Nonterm(char c) {
        val = String.valueOf(c);
    }
    boolean equals(Union u) {
        Nonterm t = (Nonterm) u;
        return t.val.equals(val);
    }
    static Nonterm[] toArr (String[] arr) {
        Nonterm[] arrOut = new Nonterm[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrOut[i] = new Nonterm(arr[i]);
        }
        return arrOut;
    }
    static Nonterm[] toArr (char[] arr) {
        Nonterm[] arrOut = new Nonterm[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrOut[i] = new Nonterm(arr[i]);
        }
        return arrOut;
    }
    static Nonterm[] toArr (String s) {
        return toArr(s.toCharArray());
    }
}