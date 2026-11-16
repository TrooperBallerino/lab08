package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;

import java.util.Map;
import java.util.Objects;
import java.util.HashMap;

/**
 * DeathNote implements @see it.unibo.deathnote.api.DeathNote interface.
 */
public class DeathNoteImpl implements DeathNote {
    private static final int MIN_RULE = 1;
    private static final double CAUSE_MILLIS = 40;
    private static final double DETAILS_MILLIS = 6040;
    private final Map<String, DeathInformation> writtenNames = new HashMap<>();
    private String lastInsertedName;
    private double lastInsertedTime;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < MIN_RULE || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException(
                "The selected index is out of bound [" + MIN_RULE
                + ", " + RULES.size() + "]"
            );
        }
        return RULES.get(ruleNumber - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeName(final String name) {
        if (Objects.isNull(name)) {
            throw new NullPointerException("The name to write cannot be null"); // NOPMD
            // PMD suppressed by teacher explicit request
        }
        writtenNames.put(name, new DeathInformation());
        this.lastInsertedName = name;
        this.lastInsertedTime = System.currentTimeMillis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDeathCause(final String cause) {
        if (Objects.isNull(lastInsertedName)) {
            throw new IllegalStateException("Cannot write death cause if there's no names written");
        }
        if (System.currentTimeMillis() - lastInsertedTime > CAUSE_MILLIS) {
            return false;
        }
        this.writtenNames.get(lastInsertedName).cause = cause;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDetails(final String details) {
        if (Objects.isNull(lastInsertedName)) {
            throw new IllegalStateException("Cannot write death details if there's no names written");
        }
        if (System.currentTimeMillis() - lastInsertedTime > DETAILS_MILLIS) {
            return false;
        }
        this.writtenNames.get(lastInsertedName).details = details;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathCause(final String name) {
        if (!writtenNames.containsKey(name)) {
            throw new IllegalArgumentException(
                "Cannot return the cause of death if the name is not written"
            );
        }
        return writtenNames.get(name).cause;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathDetails(final String name) {
        if (!writtenNames.containsKey(name)) {
            throw new IllegalArgumentException(
                "Cannot return the deatails of death if the name is not written"
            );
        }
        return writtenNames.get(name).details;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNameWritten(final String name) {
        return writtenNames.containsKey(name);
    }

    private final class DeathInformation {
        private String cause = "heart attack";
        private String details;
    }
}
