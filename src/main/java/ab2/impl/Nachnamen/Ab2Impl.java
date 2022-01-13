package ab2.impl.Nachnamen;

import java.util.Set;

import ab2.Ab2;
import ab2.PDA;

public class Ab2Impl implements Ab2 {

    @Override
    public PDA getEmptyPDA() {
        return new PushDownAutomaton();
    }

    @Override
    public PDA getPDAFromCFG(char startSymbol, Set<String> rules) {
        return new PushDownAutomaton(startSymbol, rules);
    }
}
