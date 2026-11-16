package it.unibo.deathnote;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;



class TestDeathNote {
    private static final String NAME_1 = "Giovanni Casini";
    private static final String NAME_2 = "Pippo Fantini";
    private DeathNote deathNote;

    @BeforeEach
    void setUp() {
        this.deathNote = new DeathNoteImpl();
    }

    @Test
    void testGetRuleMin() {
        try {
            deathNote.getRule(0);
            fail("Get the rule number 0 should be possible, "
                    + "but it should throw a IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            for (int i = 1; i <= DeathNote.RULES.size(); ++i) {
                assertNotEquals(deathNote.getRule(i), null);
            }
        }
    }

    @Test
    @SuppressFBWarnings(
        value = { // List of bugs to be suppressed
            "BC_UNCONFIRMED_CAST_OF_RETURN_VALUE",
            "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE"
        }, // String with the reasons for them to be suppressed
        justification = "Catching NullPointerException is a explicit request of teacher"
    )
    void testWriteName() {
        try {
            deathNote.writeName(null);
            fail("When null pointer passed to writeName method "
                + "it should throw NullPointerException");
        } catch (final NullPointerException e) { //NOPMD
            // PMD suppressed by teacher request
            assertFalse(deathNote.isNameWritten(NAME_1));
            deathNote.writeName(NAME_1);
            assertTrue(deathNote.isNameWritten(NAME_1));
            assertFalse(deathNote.isNameWritten(NAME_2));
        }
    }

    @Test
    void testDeathCause() throws InterruptedException {
        try {
            deathNote.writeDeathCause("Nobody should die");
            fail("If there aren't names written in the Death Note "
                + "it should throw an IllegalStateException");
        } catch (final IllegalStateException e) {
            final String customDeathCause = "karting accident";
            deathNote.writeName(NAME_1);
            assertEquals("heart attack", deathNote.getDeathCause(NAME_1));
            deathNote.writeName(NAME_2);
            deathNote.writeDeathCause(customDeathCause);
            assertEquals(customDeathCause, deathNote.getDeathCause(NAME_2));
            Thread.sleep(100);
            assertFalse(deathNote.writeDeathCause("car accident"));
        }
    }

    @Test
    void testDeathDetails() throws InterruptedException {
        try {
            deathNote.writeDetails("I have no enemies");
            fail("If there aren't names written in the Death Note "
                + "it should throw an IllegalStateException");
        } catch (final IllegalStateException e) {
            final String customDeathDetails = "ran too long";
            final long timeOver = 6100;
            deathNote.writeName(NAME_1);
            assertNull(deathNote.getDeathDetails(NAME_1));
            assertTrue(deathNote.writeDetails(customDeathDetails));
            assertEquals(customDeathDetails, deathNote.getDeathDetails(NAME_1));
            deathNote.writeName(NAME_2);
            Thread.sleep(timeOver);
            assertFalse(deathNote.writeDetails(customDeathDetails));
        }
    }
}
