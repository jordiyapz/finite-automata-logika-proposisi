import java.util.ArrayList;

class PDA {
    Nonterm initState;
    Nonterm[] states;
    Term[] terms;
    Union[][][] transTab;
    private static int numOfLoops;
    private final static int maxNumOfLoopPermitted = 100;
    PDA (char initState, char[] states, Term[] terms, Union [][][] transTab) {
        this.initState = new Nonterm(initState);
        this.states = Nonterm.toArr(states);
        this.terms = terms;
        this.transTab = transTab;
        numOfLoops = 0;
    }

    boolean validate (ArrayList<Term> input) {
        IStack<Union> stack = new IStack<>();
        stack.push(initState);
        int head = 0;
        return traverse(input, stack, head);
        // return false;
    }

    private boolean traverse(ArrayList<Term> input, IStack<Union> stack, int head) {
        numOfLoops++;
        if (numOfLoops <= maxNumOfLoopPermitted) {
            int inpLength = input.size();
            Nonterm state;
            while (!stack.isEmpty() && head < inpLength && numOfLoops <= maxNumOfLoopPermitted) {
                System.out.println("input["+head+"]: " + input.get(head).val);
                System.out.print("Stack: ");
                stack.print();
                Union temp = stack.pop();
                if (temp.isNonterm()) {
                    System.out.println("Yang dibaca Nonterm");
                    state = (Nonterm) temp;
                    int iNonterm = getId(state);
                    int iTerm = getId(input.get(head));
                    if (iTerm != -1 && iNonterm != -1) { // Kalau keduanya ketemu
                        Union[] product = transTab[iNonterm][iTerm];
                        for (int i = 0; i< product.length; i++) {
                            if (product[i].isNonterm())
                                System.out.println("Prod[" + i + "]: " + ((Nonterm)product[i]).val);
                            else
                                System.out.println("Prod[" + i + "]: " + ((Term)product[i]).val);
                        }
                        if (product.length == 0) {
                            System.out.println("Produk tidak ada di table");
                            return false;
                        }
                        for (int i = 1; i < product.length; i++) {
                            System.out.println("Cloning ke-" + String.valueOf(i));
                            IStack<Union> clone = stack.clone();
                            /** Jalanin kodingan clone */
                            if (product[i].isNonterm()) {
                                Nonterm[] nterm = Nonterm.toArr(((Nonterm)product[0]).val);
                                for (int j = nterm.length - 1; j >= 0; j--) {
                                    clone.push(nterm[j]);
                                }
                            } else {
                                clone.push(product[i]);
                            }

                            boolean result = traverse(input, clone, head);
                            if (result) {
                                System.out.println("BERHASIL!!!");
                                return result;
                            }
                        }
                        /** Jalanin kodingan awal */
                        if (product[0].isNonterm()) {
                            Nonterm[] nterm = Nonterm.toArr(((Nonterm)product[0]).val);
                            for (int j = nterm.length - 1; j >= 0; j--) {
                                stack.push(nterm[j]);
                            }
                        } else {
                            System.out.println("Nilai prod[" + 0 + "]: " + ((Term)product[0]).val);
                            stack.push(product[0]);
                        }
                    } else {
                        System.out.println("Indeks tidak ditemukan");
                        return false;
                    }
                } else {
                    if (input.get(head).val != ((Term) temp).val)
                        return false;
                    else {
                        System.out.println("Headnya maju");
                        head++;
                    }
                }
                System.out.println();
            }
            if (stack.isEmpty() && head == inpLength)
                return true;
            else if (stack.isEmpty()) {
                System.out.println("Gagal karena head blum diakhir");
            } else {
                System.out.println("Gagal karena stack belum kosong");
            }

        }
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