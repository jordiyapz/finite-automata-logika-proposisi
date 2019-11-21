class Term extends Union {
    int val;
    Term(int i) {
        val = i;
    }
    static Term[] toArr (int[] arr) {
        Term[] arrOut = new Term[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrOut[i] = new Term(arr[i]);
        }
        return arrOut;
    }
}