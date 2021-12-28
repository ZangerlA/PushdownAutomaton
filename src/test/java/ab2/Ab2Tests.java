package ab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.*;

import ab2.impl.Nachnamen.Ab2Impl;

public class Ab2Tests {

    private static Ab2Impl factory = new Ab2Impl();

    private static Set<Character> readChars = new HashSet<>(Arrays.asList('0', '1', 'a', 'b', 'c', 'd', 'e', 'f', '|'));
    private static Set<Character> writeChars = new HashSet<>(Arrays.asList('0', '1', 'a', 'b', 'c', 'd', 'e', 'f'));

    private static int pdaPunkte = 0;
    private static boolean basisTestsOK = true;

    public static PDA pda1() {
	PDA m = factory.getEmptyPDA();
	m.setNumStates(1);
	m.setInitialState(0);
	m.setAcceptingState(new HashSet<>(Arrays.asList(0)));
	m.setInputChars(readChars);
	m.setStackChars(writeChars);

	m.addTransition(0, 'a', null, 'a', 0);
	m.addTransition(0, 'a', 'a', null, 0);

	return m;
    }

    private static PDA pda2() {
	PDA m = factory.getEmptyPDA();
	m.setNumStates(1);
	m.setInitialState(0);
	m.setAcceptingState(new HashSet<>(Arrays.asList(0)));
	m.setInputChars(readChars);
	m.setStackChars(writeChars);

	m.addTransition(0, 'a', null, 'a', 0);
	m.addTransition(0, 'b', null, 'b', 0);
	m.addTransition(0, 'c', null, 'c', 0);

	m.addTransition(0, 'a', 'a', null, 0);
	m.addTransition(0, 'b', 'b', null, 0);
	m.addTransition(0, 'c', 'c', null, 0);

	return m;
    }

    public static PDA pda3() {
	PDA m = factory.getEmptyPDA();
	m.setNumStates(2);
	m.setInitialState(0);
	m.setAcceptingState(new HashSet<>(Arrays.asList(1)));
	m.setInputChars(readChars);
	m.setStackChars(writeChars);

	m.addTransition(0, 'a', null, 'a', 0);
	m.addTransition(0, 'b', null, 'b', 0);
	m.addTransition(0, 'c', null, 'c', 0);

	m.addTransition(0, 'a', null, null, 1);
	m.addTransition(0, 'b', null, null, 1);
	m.addTransition(0, 'c', null, null, 1);

	m.addTransition(1, 'a', 'a', null, 1);
	m.addTransition(1, 'b', 'b', null, 1);
	m.addTransition(1, 'c', 'c', null, 1);

	return m;
    }

    private static PDA pda4() {
	PDA m = factory.getEmptyPDA();
	m.setNumStates(2);
	m.setInitialState(0);
	m.setAcceptingState(new HashSet<>(Arrays.asList(0, 1)));
	m.setInputChars(readChars);
	m.setStackChars(writeChars);

	m.addTransition(0, 'a', null, 'a', 0);

	m.addTransition(0, 'b', 'a', null, 1);

	m.addTransition(1, 'b', 'a', null, 1);

	return m;
    }


    @Test
    public void testPDANumStates1() {
        assertThrows(IllegalArgumentException.class, () -> {
            PDA pda = factory.getEmptyPDA();
            pda.setNumStates(-1);

            basisTestsOK = false;
	});
    }

    @Test
    public void testPDANumStates2() {
        assertThrows(IllegalArgumentException.class, () -> {
            PDA pda = factory.getEmptyPDA();
            pda.setNumStates(0);

            basisTestsOK = false;
	});
    }

    @Test
    public void testPDAInitStateNoStates() {
        assertThrows(IllegalStateException.class, () -> {
            PDA pda = factory.getEmptyPDA();
            pda.setInitialState(2);

            basisTestsOK = false;
        });
    }

    @Test
    public void testPDAInitStateNotValid1() {
        assertThrows(IllegalArgumentException.class, () -> {
            PDA pda = factory.getEmptyPDA();
            pda.setNumStates(5);
            pda.setInitialState(-1);

            basisTestsOK = false;
        });
    }

    @Test
    public void testPDAInitStateNotValid2() {
        assertThrows(IllegalArgumentException.class, () -> {
            PDA pda = factory.getEmptyPDA();
            pda.setNumStates(5);
            pda.setInitialState(6);

            basisTestsOK = false;
        });
    }

    @Test
    public void testPDATransitionNoStates() {
        assertThrows(IllegalStateException.class, () -> {
            PDA pda = factory.getEmptyPDA();
            pda.setInputChars(readChars);
            pda.setStackChars(writeChars);

            pda.addTransition(0, 'a', 'a', 'b', 1);

            basisTestsOK = false;
        });
    }

    @Test
    public void testPDATransitionNoInputChars() {
        assertThrows(IllegalStateException.class, () -> {
            PDA pda = factory.getEmptyPDA();
            pda.setNumStates(5);
            pda.setStackChars(writeChars);

            pda.addTransition(0, 'a', 'a', 'b', 1);

            basisTestsOK = false;
        });
    }

    @Test
    public void testPDATransitionNoStackChars() {
        assertThrows(IllegalStateException.class, () -> {
            PDA pda = factory.getEmptyPDA();
            pda.setNumStates(5);
            pda.setInputChars(readChars);

            pda.addTransition(0, 'a', 'a', 'b', 1);

            basisTestsOK = false;
        });
    }

    @Test
    public void testPDATransitionStateNotValid() {
        assertThrows(IllegalArgumentException.class, () -> {
            PDA pda = factory.getEmptyPDA();
            pda.setNumStates(5);
            pda.setInputChars(readChars);
            pda.setStackChars(writeChars);

            pda.addTransition(0, 'a', 'a', 'b', 5);

            basisTestsOK = false;
        });
    }

    @Test
    public void testPDATransitionInputCharNotValid() {
        assertThrows(IllegalArgumentException.class, () -> {
            PDA pda = factory.getEmptyPDA();
            pda.setNumStates(5);
            pda.setInputChars(readChars);
            pda.setStackChars(writeChars);

            pda.addTransition(0, 'z', 'a', 'b', 1);

            basisTestsOK = false;
        });
    }

    @Test
    public void testPDATransitionStackReadCharNotValid() {
        assertThrows(IllegalArgumentException.class, () -> {
            PDA pda = factory.getEmptyPDA();
            pda.setNumStates(5);
            pda.setInputChars(readChars);
            pda.setStackChars(writeChars);

            pda.addTransition(0, 'a', 'z', 'a', 1);

            basisTestsOK = false;
        });
    }

    @Test
    public void testPDATransitionStackWriteCharNotValid() {
        assertThrows(IllegalArgumentException.class, () -> {
            PDA pda = factory.getEmptyPDA();
            pda.setNumStates(5);
            pda.setInputChars(readChars);
            pda.setStackChars(writeChars);

            pda.addTransition(0, 'a', 'a', 'z', 1);

            basisTestsOK = false;
        });
    }

    @Test
    public void testPDAAccepts1() {
	PDA pda = pda1();

	assertTrue(pda.accepts(""));
	assertTrue(pda.accepts("aa"));
	assertTrue(pda.accepts("aaaa"));

	assertFalse(pda.accepts("a"));
	assertFalse(pda.accepts("aaa"));

	assertFalse(pda.accepts("aab"));

	pdaPunkte += 1;
    }

    @Test
    public void testPDAAccepts2() {
	PDA pda = pda2();

	assertTrue(pda.accepts(""));
	assertTrue(pda.accepts("aa"));
	assertTrue(pda.accepts("aabb"));

	assertFalse(pda.accepts("a"));
	assertFalse(pda.accepts("aaa"));

	assertFalse(pda.accepts("aab"));

	pdaPunkte += 1;
    }

    @Test
    public void testPDAAccepts3() {
	PDA pda = pda3();

	assertTrue(pda.accepts("a"));
	assertTrue(pda.accepts("aaa"));
	assertTrue(pda.accepts("abcba"));

	assertFalse(pda.accepts("aa"));
	assertFalse(pda.accepts("abccba"));

	pdaPunkte += 1;
    }

    @Test
    public void testPDAAccepts4() {
	PDA pda = pda4();

	assertTrue(pda.accepts("ab"));
	assertTrue(pda.accepts("aabb"));

	assertFalse(pda.accepts("a"));
	assertFalse(pda.accepts("aabbab"));

	pdaPunkte += 1;
    }

    @Test
    public void testPDADet() {
	assertFalse(pda1().isDPDA());
	assertFalse(pda2().isDPDA());
	assertFalse(pda3().isDPDA());
	assertTrue(pda4().isDPDA());

	pdaPunkte += 2;
    }

    @Test
    public void testPDAUnion1() {
	PDA pda1 = pda1();
	PDA pda2 = pda2();

	PDA pda = pda1.union(pda2);

	assertTrue(pda1 != pda);
	assertTrue(pda2 != pda);

	assertTrue(pda.accepts(""));
	assertTrue(pda.accepts("aa"));
	assertTrue(pda.accepts("aaaa"));

	assertTrue(pda.accepts(""));
	assertTrue(pda.accepts("aa"));
	assertTrue(pda.accepts("aabb"));

	assertFalse(pda.accepts("c"));
	assertFalse(pda.accepts("aba"));

	pdaPunkte += 1;
    }

    @Test
    public void testPDAUnion2() {
	PDA pda1 = pda1();
	PDA pda2 = pda3();

	PDA pda = pda1.union(pda2);

	assertTrue(pda1 != pda);
	assertTrue(pda2 != pda);

	assertTrue(pda.accepts(""));
	assertTrue(pda.accepts("aa"));
	assertTrue(pda.accepts("aaaa"));

	assertTrue(pda.accepts("a"));
	assertTrue(pda.accepts("aaa"));
	assertTrue(pda.accepts("abcba"));

	assertFalse(pda.accepts("cc"));

	pdaPunkte += 1;
    }

    @Test
    public void testPDAUnion3() {
	PDA pda1 = pda1();
	PDA pda2 = pda4();

	PDA pda = pda1.union(pda2);

	assertTrue(pda1 != pda);
	assertTrue(pda2 != pda);

	assertTrue(pda.accepts(""));
	assertTrue(pda.accepts("aa"));
	assertTrue(pda.accepts("aaaa"));

	assertTrue(pda.accepts("ab"));
	assertTrue(pda.accepts("aabb"));

	assertFalse(pda.accepts("cc"));

	pdaPunkte += 1;
    }

    @Test
    public void testPDAUnion4() {
	PDA pda1 = pda3();
	PDA pda2 = pda4();

	PDA pda = pda1.union(pda2);

	assertTrue(pda1 != pda);
	assertTrue(pda2 != pda);

	assertTrue(pda.accepts("a"));
	assertTrue(pda.accepts("aaa"));
	assertTrue(pda.accepts("abcba"));

	assertTrue(pda.accepts("ab"));
	assertTrue(pda.accepts("aabb"));

	assertFalse(pda.accepts("cc"));

	pdaPunkte += 1;
    }

    @Test
    public void testPDAAppend1() {
	PDA pda1 = pda1();
	PDA pda2 = pda2();

	PDA pda = pda1.append(pda2);

	assertTrue(pda1 != pda);
	assertTrue(pda2 != pda);

	assertTrue(pda.accepts(""));
	assertTrue(pda.accepts("aa"));
	assertTrue(pda.accepts("aaaa"));
	assertTrue(pda.accepts("aa"));
	assertTrue(pda.accepts("aabb"));
	assertTrue(pda.accepts("abba"));

	assertFalse(pda.accepts("c"));
	assertFalse(pda.accepts("aab"));

	pdaPunkte += 1;
    }

    @Test
    public void testPDAAppend2() {
	PDA pda1 = pda1();
	PDA pda2 = pda3();

	PDA pda = pda1.append(pda2);

	assertTrue(pda1 != pda);
	assertTrue(pda2 != pda);

	assertTrue(pda.accepts("aaaba"));
	assertTrue(pda.accepts("a"));
	assertTrue(pda.accepts("aaa"));
	assertTrue(pda.accepts("abcba"));

	assertFalse(pda.accepts(""));
	assertFalse(pda.accepts("cc"));

	pdaPunkte += 1;
    }

    @Test
    public void testPDAAppend3() {
	PDA pda1 = pda1();
	PDA pda2 = pda4();

	PDA pda = pda1.append(pda2);

	assertTrue(pda1 != pda);
	assertTrue(pda2 != pda);

	assertTrue(pda.accepts(""));
	assertTrue(pda.accepts("aa"));
	assertTrue(pda.accepts("aaab"));
	assertTrue(pda.accepts("aaaa"));

	assertFalse(pda.accepts("cc"));

	pdaPunkte += 1;
    }

    @Test
    public void testPDAAppend4() {
	PDA pda1 = pda3();
	PDA pda2 = pda4();

	PDA pda = pda1.append(pda2);

	assertTrue(pda1 != pda);
	assertTrue(pda2 != pda);

	assertTrue(pda.accepts("aab"));
	assertTrue(pda.accepts("aaaaabb"));
	assertTrue(pda.accepts("abcba"));

	assertFalse(pda.accepts("cc"));

	pdaPunkte += 1;
    }

    /************************************************************************/
    /******************************* CFG->PDF *******************************/
    /************************************************************************/

    @Test
    public void testCFGToPDA1() {
	String cfg = "S→aSa|bSb|cSc|a|b|c";
	Set<String> set = new HashSet<>();
	set.add(cfg);

	PDA pda = factory.getPDAFromCFG('S', set);

	assertTrue(pda.accepts("a"));
	assertTrue(pda.accepts("aaa"));
	assertTrue(pda.accepts("aba"));
	assertTrue(pda.accepts("abcba"));

	assertFalse(pda.accepts(""));
	assertFalse(pda.accepts("aa"));
	assertFalse(pda.accepts("aabac"));

	pdaPunkte += 2;
    }

    @Test
    public void testCFGToPDA2() {
	String cfg1 = "S→aS|bS|T";
	String cfg2 = "T→cT|c";
	Set<String> set = new HashSet<>();
	set.add(cfg1);
	set.add(cfg2);

	PDA pda = factory.getPDAFromCFG('S', set);

	assertTrue(pda.accepts("c"));
	assertTrue(pda.accepts("ac"));
	assertTrue(pda.accepts("abc"));
	assertTrue(pda.accepts("bac"));
	assertTrue(pda.accepts("cccc"));
	assertTrue(pda.accepts("acccc"));

	assertFalse(pda.accepts(""));
	assertFalse(pda.accepts("aa"));
	assertFalse(pda.accepts("caa"));
	assertFalse(pda.accepts("bab"));

	pdaPunkte += 2;
    }

    @AfterAll
    public static void printPoints() {
	int punkte = 0;

	if (basisTestsOK) {
	    punkte += pdaPunkte;
	}
	else {
	    System.out.println("PDA-Basistests nicht erfüllt!");
	}

	System.out.println("Gesamtpunkte: " + punkte);
    }
}
