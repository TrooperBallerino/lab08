package it.unibo.mvc;

import it.unibo.mvc.api.DrawNumberController;
// import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.controller.DrawNumberControllerMultiple;
import it.unibo.mvc.model.DrawNumberImpl;
import it.unibo.mvc.view.DrawNumberStandardOutputView;
import it.unibo.mvc.view.DrawNumberSwingView;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     */
    public static void main() {
        final var model = new DrawNumberImpl();
        // final DrawNumberController app = new DrawNumberControllerImpl(model);
        final DrawNumberController app = new DrawNumberControllerMultiple(model);
        app.addView(new DrawNumberSwingView());
        app.addView(new DrawNumberStandardOutputView());
    }
}
