package ab2.impl.Nachnamen;

import ab2.PDA;

import java.util.Set;

public class PushDownAutomaton implements PDA {

    private char startSymbol;
    private Set<String> rules;
    private Integer initialState;
    private Integer numStates;
    private Set<Integer> acceptingStates;
    private Set<Character> inputAlphabet;
    private Set<Character> stackAlphabet;
    private Set<Transition> transitions;

    public PushDownAutomaton() {
    }

    public PushDownAutomaton(char startSymbol, Set<String> rules) {
        this.startSymbol = startSymbol;
        this.rules = rules;
    }
    @Override
    public void setNumStates(int numStates) throws IllegalArgumentException {
        if (numStates <= 0) {
            throw new IllegalArgumentException("Number of states can't be 0 or less.");
        }
        this.numStates = numStates;
    }

    @Override
    public void setInitialState(int initialState) throws IllegalArgumentException, IllegalStateException {
        if (numStates == null) {
            throw new IllegalStateException("numStates must be set first.");
        }
        // This should be correct.
        if (!StatesAreInBoundaries(Set.of(initialState))) {
            throw new IllegalArgumentException("Number of states can't be 0 or less and the state for initialState does not exist.");
        }
        // Typo in interface ?
        if (numStates <= 0 || initialState >= numStates) {
            throw new IllegalArgumentException("Number of states can't be 0 or less and the state for initialState does not exist.");
        }
        this.initialState = initialState;
    }

    @Override
    public void setAcceptingState(Set<Integer> acceptingStates) throws IllegalArgumentException, IllegalStateException {
        if (numStates == null) {
            throw new IllegalStateException("numStates must be set first.");
        }
        if (!StatesAreInBoundaries(acceptingStates)) {
            throw new IllegalArgumentException("One of the states in acceptingStates is outside of boundaries.");
        }
        this.acceptingStates = acceptingStates;
    }

    @Override
    public void setInputChars(Set<Character> chars) {
        this.inputAlphabet = chars;
    }

    @Override
    public void setStackChars(Set<Character> chars) {
        this.stackAlphabet = chars;
    }

    @Override
    public void addTransition(int fromState, Character charReadTape, Character charReadStack, Character charWriteStack, int toState) throws IllegalArgumentException, IllegalStateException {
        if (!alphabetAndNumberOfStatesAreNotNull()) {
            throw new IllegalStateException("One or more of the following is not set: numStates, inputAlphabet, stackAlphabet.");
        }
        if (!StatesAreInBoundaries(Set.of(fromState, toState)) ||
                !inputAlphabet.contains(charReadTape) ||
                !stackAlphabet.contains(charReadStack) ||
                !stackAlphabet.contains(charWriteStack)) {
            throw new IllegalArgumentException("States or characters in this transition are not valid.");
        }
        transitions.add(new Transition(fromState, charReadTape, charReadStack, charWriteStack, toState));
    }

    @Override
    public boolean accepts(String input) throws IllegalArgumentException, IllegalStateException {
        if (!alphabetAndNumberOfStatesAreNotNull()) {
            throw new IllegalStateException("One or more of the following is not set: numStates, inputAlphabet, stackAlphabet.");
        }
        if (!inputAlphabetContainsInputString(input)) {
            throw new IllegalArgumentException("input contains illegal character");
        }
        //TODO
        return false;
    }

    @Override
    public PDA append(PDA pda) throws IllegalArgumentException, IllegalStateException {
        if (!this.alphabetAndNumberOfStatesAreNotNull()) {
            throw new IllegalStateException("One or more of the following is not set: numStates, inputAlphabet, stackAlphabet.");
        }
        if (!((PushDownAutomaton) pda).alphabetAndNumberOfStatesAreNotNull()) {
            throw new IllegalArgumentException("One or more of the following is not set: numStates, inputAlphabet, stackAlphabet.");
        }
        //TODO
        return null;
    }

    @Override
    public PDA union(PDA pda) throws IllegalArgumentException, IllegalStateException {
        if (!this.alphabetAndNumberOfStatesAreNotNull()) {
            throw new IllegalStateException("One or more of the following is not set: numStates, inputAlphabet, stackAlphabet.");
        }
        if (!((PushDownAutomaton) pda).alphabetAndNumberOfStatesAreNotNull()) {
            throw new IllegalArgumentException("One or more of the following is not set: numStates, inputAlphabet, stackAlphabet.");
        }
        //TODO
        return null;
    }

    @Override
    public boolean isDPDA() throws IllegalStateException {
        if (!alphabetAndNumberOfStatesAreNotNull()) {
            throw new IllegalStateException("One or more of the following is not set: numStates, inputAlphabet, stackAlphabet.");
        }
        //TODO
        return false;
    }

    private boolean StatesAreInBoundaries(Set<Integer> states) {
        for(Integer state : states) {
            if (state < 0 || state >= numStates) {
                return false;
            }
        }
        return true;
    }

    private boolean alphabetAndNumberOfStatesAreNotNull() {
        if (numStates == null || inputAlphabet == null || stackAlphabet == null) {
            return false;
        }
        return true;
    }

    private boolean inputAlphabetContainsInputString(String inputString) {
        for (char c : inputString.toCharArray()) {
            if (!inputAlphabet.contains(c)) {
                return false;
            }
        }
        return true;
    }
}
