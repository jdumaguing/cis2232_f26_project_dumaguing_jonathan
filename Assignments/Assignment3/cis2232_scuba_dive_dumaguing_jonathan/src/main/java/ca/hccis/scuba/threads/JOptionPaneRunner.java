package ca.hccis.scuba.threads;

/**
 * Thread which runs the dive planner completely using JOptionPane.
 *
 * @author Jonathan Dumaguing
 * @since 20261015
 */
public class JOptionPaneRunner implements Runnable {

    @Override
    public void run() {
        new DiveMenu(true, "JOptionPane").run();
    }
}
