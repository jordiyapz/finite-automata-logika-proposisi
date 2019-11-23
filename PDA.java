import java.util.ArrayList;

class PDA {
    Nonterm initState;
    Nonterm[] states;
    Term[] terms;
    Union[][][][] transTab;
    private static int numOfLoops;

    private final static int MaxNumOfLoopPermitted = 10000;


    PDA (char initState, char[] states, Term[] terms, Union [][][][] transTab) {
        this.initState = new Nonterm(initState);
        this.states = Nonterm.toArr(states);
        this.terms = terms;
        this.transTab = transTab;
        numOfLoops = 0;
    }

    public boolean validate2(ArrayList<Term> input) {

        return false;
    }

    public boolean validate(ArrayList<Term> input) {
        IStack<Union> stack = new IStack<>();
        int head = 0;
        stack.push(initState);
        boolean result = traverse(input, stack, head);
        numOfLoops = 0;
        return result;
    }

    private boolean traverse(ArrayList<Term> input, IStack<Union> stack, int head) {
        int inpLength = input.size();
        Nonterm state;
        while (
            !stack.isEmpty() &&
            head < inpLength &&
            stack.size() <= inpLength &&
            ++numOfLoops <= MaxNumOfLoopPermitted
        ) {
            stack.print();
            Union temp = stack.pop();
            if (temp.isTerm()) {
                if (!input.get(head).equals(temp)) {
                    System.out.println("Terminal tidak sesuai dengan yg di stack");
                    return false;
                } else {
                    head++;
                }
            } else {
                state = (Nonterm) temp;
                int idState = getId(state);
                int idterm = getId((Term) input.get(head));
                if (idState == -1 || idterm == -1) {
                    System.out.println("idState tidak ditemukan");
                    return false;
                } else {
                    Union[][] product;
                    // for (int idterm = terms.length-1; idterm >= 0; idterm--) {
                        product = transTab[idState][idterm];
                        IStack<Union> stck = stack;
                        // if (idterm > 0) stck = stack.clone();
                        for (int i = product.length-1; i >= 0; i--) {
                            Union[] pd = product[i];
                            IStack<Union> st = stck;
                            if (i > 0) st = stck.clone();
                            for (int j = pd.length-1; j >= 0; j--) {
                                st.push(product[i][j]);
                            }
                            System.out.println("\nRead["+ head +"]: " + input.get(head).val);
                            System.out.print("["+ state.val +"," +(idterm+1)+ "," + i + "]: ");
                            if (traverse(input, st, head)) return true;
                        }
                    // }
                }
            }
        }
        if (stack.size() > inpLength) {
            System.out.println("Stack melebihi batas ukuran");
            return false;
        }
        if (stack.isEmpty() && head == inpLength) return true;
        else if (stack.isEmpty()) System.out.println("Stack habis duluan");
        else System.out.println("Head habis duluan");
        return false;
    }

    private int getId(Term x) {
        for (int i = 0; i < terms.length; i++)
            if (terms[i].val == x.val) return i;
        return -1;
    }
    private int getId(Nonterm x) {
        for (int i = 0; i < states.length; i++) {
            if (states[i].val.equals(x.val)) return i;
        }
        return -1;
    }
}