package it.unibo.mvc;

import java.lang.reflect.InvocationTargetException;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.controller.DrawNumberControllerMultiple;
import it.unibo.mvc.model.DrawNumberImpl;
import it.unibo.mvc.api.DrawNumberView;

/**
 * Application entry-point.
 */
public final class LaunchMultipleViewApp {

    private static final int VIEWS_PER_TYPE = 3;
    private static final String VIEW_PACKET = "it.unibo.mvc.view";

    private LaunchMultipleViewApp() { }

    /**
     * Runs the application.
     * Use reflection to get all subclasses of {@see DrawNumberView} interface
     *
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main() throws
        ClassNotFoundException,
        NoSuchMethodException,
        InvocationTargetException,
        InstantiationException,
        IllegalAccessException {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerMultiple(model);
        final Class<?> stdoutView = Class.forName(VIEW_PACKET + ".DrawNumberStandardOutputView");
        final Class<?> swingView = Class.forName(VIEW_PACKET + ".DrawNumberSwingView");
        for (int i = 0; i < VIEWS_PER_TYPE; ++i) {
            app.addView((DrawNumberView) swingView.getConstructor().newInstance());
            app.addView((DrawNumberView) stdoutView.getConstructor().newInstance());
        }
    }
}
