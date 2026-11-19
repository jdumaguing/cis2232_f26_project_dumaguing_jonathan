package ca.hccis.scuba.threads;

/**
 * Thread which runs the dive planner completely in the console.
 *
 * @author Jonathan Dumaguing
 * @since 20261015
 */
public class ConsoleRunner extends Thread {

    @Override
    public void run() {
        new DiveMenu(false, "Console").run();
    }
}
