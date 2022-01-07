package ab2.impl.Nachnamen;

import ab2.PDA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        this.transitions = new HashSet<>();
    }

    public PushDownAutomaton(char startSymbol, Set<String> rules) {
        this.startSymbol = startSymbol;
        this.rules = rules;
        this.transitions = new HashSet<>();
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
        if (!statesAreInBoundaries(Set.of(initialState))) {
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
        if (!statesAreInBoundaries(acceptingStates)) {
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
        if (!statesAreInBoundaries(fromState == toState ? Set.of(fromState) : Set.of(fromState, toState)) ||
                !inputAlphabet.contains(charReadTape) && charReadTape != null ||
                !stackAlphabet.contains(charReadStack) && charReadStack != null ||
                !stackAlphabet.contains(charWriteStack) && charWriteStack != null) {
            throw new IllegalArgumentException("States or characters in this transition are not valid.");
        }
        this.transitions.add(new Transition(fromState, charReadTape, charReadStack, charWriteStack, toState));
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
        List<Character> stack = new ArrayList<>();
        return doStep(input, this.initialState, stack);
    }

    private boolean doStep(String input, int currentState, List<Character> stack) {
        if (currentState < 0 || currentState >= this.numStates) {
            throw new IllegalArgumentException();
        }

        if (this.acceptingStates.contains(currentState) && input.equals("") && stack.size() == 0) {
            return true;
        } else if (stack.size() != 0 && input.equals("")) {
            return false;
        }

        for (Transition transition : this.transitions) {
            if (transition.getFromState() == currentState && (transition.getCharReadTape() == input.charAt(0) || transition.getCharReadTape() == null) && ((stack.size() > 0 && transition.getCharReadStack() == stack.get(stack.size() - 1)) || transition.getCharReadStack() == null)) {
                List<Character> tempStack = new ArrayList<>(stack);
                if (transition.getCharReadStack() != null) tempStack.remove(tempStack.size() - 1);
                if (transition.getCharWriteStack() != null) tempStack.add(transition.getCharWriteStack());
                if (doStep(input.substring(1), transition.getToState(), tempStack)) {
                    return true;
                }
            }
        }
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

        PushDownAutomaton unionPDA = new PushDownAutomaton();
        unionPDA.setStatesAndAlphabetUnionPDA(this, pda);
        unionPDA.setTransitionsUnionPDA(this, pda);

        return unionPDA;
    }

    private void setStatesAndAlphabetUnionPDA(PDA pda1, PDA pda2) {
        Set<Character> inputAlphabet = new HashSet<>();
        Set<Character> stackAlphabet = new HashSet<>();
        Set<Integer> acceptingStates = new HashSet<>();

        inputAlphabet.addAll(((PushDownAutomaton)pda1).inputAlphabet);
        inputAlphabet.addAll(((PushDownAutomaton)pda2).inputAlphabet);
        stackAlphabet.addAll(((PushDownAutomaton)pda1).stackAlphabet);
        stackAlphabet.addAll(((PushDownAutomaton)pda2).stackAlphabet);
        acceptingStates.addAll(calculateAcceptingStates(0, pda1));
        acceptingStates.addAll(calculateAcceptingStates(((PushDownAutomaton)pda2).numStates, pda2));

        this.setNumStates(((PushDownAutomaton)pda1).numStates + ((PushDownAutomaton)pda2).numStates + 1);
        this.setInitialState(0);
        this.setInputChars(inputAlphabet);
        this.setStackChars(stackAlphabet);
        this.setAcceptingState(acceptingStates);
    }

    private void setTransitionsUnionPDA(PDA pda1, PDA pda2) {
        int offset = ((PushDownAutomaton)pda2).numStates;

        this.addTransition(0, null, null, null, 1);
        this.addTransition(0, null, null, null, offset + 1);

        for (Transition t : ((PushDownAutomaton)pda1).transitions) {
            this.addTransition(
                    t.getFromState() + 1,
                    t.getCharReadTape(),
                    t.getCharReadStack(),
                    t.getCharWriteStack(),
                    t.getToState() + 1
            );
        }

        for (Transition t : ((PushDownAutomaton)pda2).transitions) {
            this.addTransition(
                    t.getFromState() + offset + 1,
                    t.getCharReadTape(),
                    t.getCharReadStack(),
                    t.getCharWriteStack(),
                    t.getToState() + offset + 1
            );
        }
    }

    private Set<Integer> calculateAcceptingStates(int offset, PDA pda) {
        Set<Integer> resultStates = new HashSet<>();

        for(Integer state : ((PushDownAutomaton)pda).acceptingStates) {
            resultStates.add(state + offset + 1);
        }

        return resultStates;
    }

    @Override
    public boolean isDPDA() throws IllegalStateException {
        if (!alphabetAndNumberOfStatesAreNotNull()) {
            throw new IllegalStateException("One or more of the following is not set: numStates, inputAlphabet, stackAlphabet.");
        }
        //TODO
        return false;
    }

    private boolean statesAreInBoundaries(Set<Integer> states) {
        for (Integer state : states) {
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
