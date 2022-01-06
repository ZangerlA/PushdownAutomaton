package ab2.impl.Nachnamen;

public class Transition {

    private int fromState;
    private int toState;
    private Character charReadTape;
    private Character charReadStack;
    private Character charWriteStack;

    public Transition(int fromState, Character charReadTape, Character charReadStack, Character charWriteStack, int toState) {
        this.fromState = fromState;
        this.toState = toState;
        this.charReadTape = charReadTape;
        this.charReadStack = charReadStack;
        this.charWriteStack = charWriteStack;
    }

    public int getFromState() {
        return fromState;
    }

    public void setFromState(int fromState) {
        this.fromState = fromState;
    }

    public int getToState() {
        return toState;
    }

    public void setToState(int toState) {
        this.toState = toState;
    }

    public Character getCharReadTape() {
        return charReadTape;
    }

    public void setCharReadTape(Character charReadTape) {
        this.charReadTape = charReadTape;
    }

    public Character getCharReadStack() {
        return charReadStack;
    }

    public void setCharReadStack(Character charReadStack) {
        this.charReadStack = charReadStack;
    }

    public Character getCharWriteStack() {
        return charWriteStack;
    }

    public void setCharWriteStack(Character charWriteStack) {
        this.charWriteStack = charWriteStack;
    }
}
