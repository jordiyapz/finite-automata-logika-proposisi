abstract class Union {
    boolean isNonterm() {
        return this.getClass().getSimpleName() == "Nonterm";
    }
    boolean isTerm() {
        return this.getClass().getSimpleName() == "Term";
    }
    abstract boolean equals(Union n);
}