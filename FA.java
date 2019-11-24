class FA {
    // -1 error
    // state mulai dari 0
    int id; // indeks token yg akan dikembalikan
    String header;
    int[][] transTab = {};

    /* Contoh tabel transisi
    //     a   n   d
    // 0   1   -1  -1
    // 1   -1  2   -1
    // 2   -1  -1  3
    // 3   -1  -1  -1
    */

    int[] finalState = {};

    FA (int id, String header, int[][] transTab, int[] finalState) {
        this.id = id;
        this.header = header;
        this.transTab = transTab;
        this.finalState = finalState;
    }

    FA (int id, String header) {
        this.id = id;
        this.header = header;
        int len = header.length();
        transTab = new int[len+1][len];
        for (int i = 0; i < len+1; i++) {
            for (int j = 0; j < len; j++) {
                transTab[i][j] = -1;
            }
        }
        for (int i = 0; i < len; i++){
            transTab[i][i] = i+1;
        }
        this.finalState = new int[]{len};
    }

    public boolean check (String input) {
        char[] arrChar = input.toCharArray();
        int state = 0;
        for (int i = 0; i < arrChar.length && state < transTab.length; i++) {
            final char c = arrChar[i];
            int idChar = header.indexOf(c, 0);
            if (idChar != -1)
                state = transTab[state][idChar];
            else state = -1;
            if (state == -1)
                return false;
        }
        for (int fs : finalState) {
            if (state == fs) return true;
        }
        return false;
    }
    public int getId (String input) {
        if (this.check(input)) {
            return this.id;
        }
        return 0;
    }
}