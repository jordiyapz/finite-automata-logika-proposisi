import java.util.Stack;

class IStack<E> extends Stack<E> {

    private static final long serialVersionUID = 1224463164541339166L;

    IStack() { super(); }

    public IStack<E> clone() {
        IStack<E> dump = new IStack<>();
        IStack<E> clone = new IStack<>();
        while (!this.isEmpty()) {
            dump.push(this.pop());
        }
        while (!dump.isEmpty()) {
            E val = (E) dump.pop();
            this.push(val);
            clone.push(val);
        }
        return clone;
    }

    public void print() {
        IStack<E> dump = new IStack<>();
        while (!this.isEmpty())
            dump.push(this.pop());
        while (!dump.isEmpty()) {
            E temp = dump.pop();
            if (temp.getClass().getSimpleName() == "Term") {
                System.out.print(((Term)temp).val);
            } else if (temp.getClass().getSimpleName() == "Nonterm") {
                System.out.print(((Nonterm)temp).val);
            } else {
                System.out.print(temp);
            }
            System.out.print(" -> ");
            this.push((E)temp);
        }
        System.out.println();
    }
}