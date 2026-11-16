package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/**
 * Command line {@link DrawNumberView} implementation.
 */
public class DrawNumberStandardOutputView implements DrawNumberView {

    private static final String START = "-- Welcome to Draw Number Game --\n";
    private static final String ATTEMPT = "Give me your best attempt (type 'q' to quit or 'r' to reset): ";
    private static final String NEW_GAME = "A new game starts...";
    private static final String QUIT_COMMAND = "q";
    private static final String RESET_COMMAND = "r";
    private DrawNumberController controller;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setController(final DrawNumberController observer) {
        this.controller = observer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        IO.println(START);
        askNewAttempt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void result(final DrawResult res) {
        switch (res) {
            case YOURS_HIGH, YOURS_LOW -> {
                plainMessage(res.getDescription());
                askNewAttempt();
            }
            case YOU_WON, YOU_LOST -> {
                plainMessage(res.getDescription());
                plainMessage(NEW_GAME);
                this.resetGame();
            }
        }
    }

    private void askNewAttempt() {
        final String rawAttempt = IO.readln(ATTEMPT);
        if (QUIT_COMMAND.equals(rawAttempt)) {
            controller.quit();
        } else if (RESET_COMMAND.equals(rawAttempt)) {
            this.resetGame();
        } else {
            try {
                controller.newAttempt(Integer.parseInt(rawAttempt));
            } catch (final NumberFormatException e) {
                IO.println("please enter an integer or a valid command");
                askNewAttempt();
            }
        }
    }

    private void plainMessage(final String message) {
        IO.println(message);
    }

    private void resetGame() {
        this.controller.resetGame();
        this.start();
    }
}
