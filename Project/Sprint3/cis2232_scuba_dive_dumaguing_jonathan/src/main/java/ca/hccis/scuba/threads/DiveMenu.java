package ca.hccis.scuba.threads;

import ca.hccis.scuba.Controller;
import ca.hccis.scuba.entity.Dive;
import ca.hccis.scuba.util.InputUtility;

/**
 * The A / V / E / X menu used by both threads.  The gui flag decides if the
 * input and output use the console or JOptionPane, so the functionality is
 * exactly the same in both threads.
 *
 * @author Jonathan Dumaguing
 * @since 20261015
 */
public class DiveMenu {

    public static final String ADD = "A";
    public static final String VIEW = "V";
    public static final String EDIT = "E";
    public static final String EXIT = "X";

    public static final String MENU = "A) Add" + System.lineSeparator()
            + "V) View" + System.lineSeparator()
            + "E) Edit" + System.lineSeparator()
            + "X) eXit";

    public static final String MESSAGE_ERROR = "Error";
    public static final String MESSAGE_EXIT = "Goodbye";

    private final boolean gui;
    private final String threadName;

    /**
     * @param gui true to use JOptionPane, false to use the console
     * @param threadName Name shown in the messages for this thread
     */
    public DiveMenu(boolean gui, String threadName) {
        this.gui = gui;
        this.threadName = threadName;
    }

    /**
     * Show the menu until the user chooses X.
     *
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    public void run() {

        String menuOption;
        do {
            menuOption = InputUtility.getString("[" + threadName + "]" + System.lineSeparator() + MENU, gui)
                    .trim().toUpperCase();

            //A cancelled dialog comes back blank, treat it as exit for the JOptionPane thread.
            if (gui && menuOption.isEmpty()) {
                menuOption = EXIT;
            }

            switch (menuOption) {
                case EXIT:
                    InputUtility.showMessage(threadName + " - " + MESSAGE_EXIT, gui);
                    break;
                case ADD:
                    add();
                    break;
                case VIEW:
                    InputUtility.showMessage(Controller.getAllDivesText(), gui);
                    break;
                case EDIT:
                    edit();
                    break;
                default:
                    InputUtility.showMessage(MESSAGE_ERROR, gui);
                    break;
            }
        } while (!menuOption.equals(EXIT));
    }

    /**
     * Processing for menu option A.  The diver name is asked first so the
     * duplicate check happens before the other fields are typed in.
     *
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    private void add() {

        String diverName = getValidDiverName();

        Dive existingDive = Controller.findByDiverName(diverName);
        int diveId = 0; //0 means Controller will generate the next id

        if (existingDive != null) {
            String answer = InputUtility.getString("Warning: dive plan already exists for this diver!"
                    + System.lineSeparator() + existingDive.toString()
                    + System.lineSeparator() + "Overwrite this dive plan? (y/n)", gui);
            if (!answer.trim().equalsIgnoreCase("y")) {
                InputUtility.showMessage("Add cancelled.", gui);
                return;
            }
            diveId = existingDive.getDiveId();
        }

        Dive newDive = new Dive();
        newDive.setDiveId(diveId);
        newDive.setDiverName(diverName);
        newDive.getInformation(gui);

        //Save, update the accumulator and display the combined totals.
        InputUtility.showMessage(Controller.addDive(newDive, threadName), gui);
    }

    /**
     * Processing for menu option E.  The dive plan is picked using the diveId
     * which can be seen from the View option.
     *
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    private void edit() {

        int diveId = getValidDiveId();
        Dive editingDive = Controller.getDive(diveId);

        if (editingDive == null) {
            InputUtility.showMessage(MESSAGE_ERROR + " - No dive plan found with dive id " + diveId + ".", gui);
            return;
        }

        InputUtility.showMessage("Current values:" + System.lineSeparator() + editingDive.toString()
                + System.lineSeparator() + "Please enter the new values.", gui);

        String diverName = getValidDiverName();
        Dive nameOwner = Controller.findByDiverName(diverName);

        if (nameOwner != null && nameOwner.getDiveId() != editingDive.getDiveId()) {
            InputUtility.showMessage(MESSAGE_ERROR + " - another dive plan already uses this diver name."
                    + System.lineSeparator() + "Edit cancelled.", gui);
            return;
        }

        editingDive.setDiverName(diverName);
        editingDive.getInformation(gui);

        InputUtility.showMessage(Controller.updateDive(editingDive), gui);
    }

    /**
     * Keep asking until the diver name is not blank.
     *
     * @return A valid diver name
     */
    private String getValidDiverName() {
        String diverName = InputUtility.getString("Diver Name", gui).trim();
        while (diverName.isEmpty()) {
            InputUtility.showMessage("Diver name cannot be blank.", gui);
            diverName = InputUtility.getString("Diver Name", gui).trim();
        }
        return diverName;
    }

    /**
     * Keep asking until the user enters a whole number for the dive id.
     *
     * @return A valid dive id
     */
    private int getValidDiveId() {
        while (true) {
            String entered = InputUtility.getString("Dive ID", gui).trim();
            try {
                return Integer.parseInt(entered);
            } catch (NumberFormatException e) {
                InputUtility.showMessage("'" + entered + "' is not a valid dive id.  Please try again.", gui);
            }
        }
    }
}
