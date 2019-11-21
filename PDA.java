class PDA {
    /**
     * Tabel Transisi:
            1		2		3		4		5		6		7		8		9		10
        S	HI|1	CH		-		-		-		KL		-		-		FJ		-
        A	1		-		-		-		-		-		-		-		-		-
        B	-		-		3		4		5		-		-		8		-		-
        C	-		2		-		-		-		-		-		-		-		-
        D	-		-		-		-		-		6		-		-		-		-
        E	-		-		-		-		-		-		7		-		-		-
        F	-		-		-		-		-		-		-		-		9		-
        G	-		-		-		-		-		-		-		-		-		10
        H	HI|1	CH		-		-		-		KL		-		-		FJ		-
        I	BH		-		-		-		-		-		-		-		-		-
        J	HG		HG		-		-		-		HG		-		-		HG		-
        K	-		-		-		-		-		DH		-		-		-		-
        L	-		-		-		-		-		-		EH		-		-		-
     */    
    Nonterm initState;
    Nonterm[] states;
    Term[] terms;
    Union[][][] transTab;
    PDA (char initState, char[] states, Term[] terms, Union [][][] transTab) {
        this.initState = new Nonterm(initState);
        this.states = Nonterm.toArr(states);
        this.terms = terms;
        this.transTab = transTab;        
    }
    
    boolean validate (String input) {
        IStack<Union> stack = new IStack<>();
        stack.push(initState);
        Nonterm state;
        Union temp;
        while (!stack.isEmpty()) {
            temp = stack.pop();
            System.out.println(temp.getClass().getSimpleName());
        }
        return false;
    }
    
}