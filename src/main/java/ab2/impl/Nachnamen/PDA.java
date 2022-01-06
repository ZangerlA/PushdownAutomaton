package ab2.impl.Nachnamen;

import java.util.Set;

public class PDA implements ab2.PDA {
    @Override
    public void setNumStates(int numStates) throws IllegalArgumentException {

    }

    @Override
    public void setInitialState(int initialState) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public void setAcceptingState(Set<Integer> acceptingStates) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public void setInputChars(Set<Character> chars) {

    }

    @Override
    public void setStackChars(Set<Character> chars) {

    }

    @Override
    public void addTransition(int fromState, Character charReadTape, Character charReadStack, Character charWriteStack, int toState) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public boolean accepts(String input) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public ab2.PDA append(ab2.PDA pda) throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public ab2.PDA union(ab2.PDA pda) throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public boolean isDPDA() throws IllegalStateException {
        return false;
    }
}
