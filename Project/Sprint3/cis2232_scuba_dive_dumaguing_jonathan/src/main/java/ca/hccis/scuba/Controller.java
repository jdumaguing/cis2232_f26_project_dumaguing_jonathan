package ca.hccis.scuba;

import ca.hccis.scuba.bo.DiveBO;
import ca.hccis.scuba.entity.Dive;
import ca.hccis.scuba.threads.ConsoleRunner;
import ca.hccis.scuba.threads.JOptionPaneRunner;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Starts the Scuba Dive Air Consumption and Tank Planner in two separate threads.
 * One thread works completely in the console and the other works completely
 * using JOptionPane.
 *
 * The dive map, the file and the accumulator are shared by both threads, so every
 * method which touches them is synchronized.  This stops the two threads from
 * changing the data or writing the file at the same time.
 *
 * @author Jonathan Dumaguing
 * @since 20261015
 */
public class Controller {

    public static final String FOLDER_NAME = "c:\\cis2232";
    public static final String FILE_NAME = "data_dumaguing_jonathan.json";
    public static final String PATH_NAME = FOLDER_NAME + "\\" + FILE_NAME;

    public static final int FIRST_DIVE_ID = 1000;

    private static HashMap<Integer, Dive> diveMap = new HashMap<>();
    private static Gson gson = new Gson();

    //Static accumulators shared by both threads.
    private static int totalDivesAdded = 0;
    private static double totalTankSizeLiters = 0;

    public static void main(String[] args) {

        initialize();

        System.out.println("Scuba Dive Planner - Multi-threading");
        long start = System.currentTimeMillis();

        ConsoleRunner consoleRunner = new ConsoleRunner();
        consoleRunner.start();

        Thread jOptionPaneRunner = new Thread(new JOptionPaneRunner());
        jOptionPaneRunner.start();

        try {
            consoleRunner.join();
            jOptionPaneRunner.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long duration = System.currentTimeMillis() - start;
        System.out.println("Both threads finished.  Duration: " + duration + " ms");
        System.out.println(getAccumulatorMessage());
        System.exit(0);
    }

    /**
     * Save a dive plan, update the accumulators and return a message showing the
     * combined totals from both threads.  A diveId of 0 means it is a new dive
     * so the next diveId is generated here, inside the lock, so both threads can
     * never get the same id.
     *
     * @param dive The dive plan to save
     * @param threadName Name of the thread which added the dive
     * @return Message with the recommended tank size and the accumulated totals
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    public static synchronized String addDive(Dive dive, String threadName) {

        readAll();

        if (dive.getDiveId() == 0) {
            dive.setDiveId(getNextDiveId());
        }

        double tankSize = DiveBO.calculate(dive);

        diveMap.put(dive.getDiveId(), dive);
        writeAll();

        totalDivesAdded++;
        totalTankSizeLiters += tankSize;

        return "Dive plan saved by " + threadName + " with dive id " + dive.getDiveId() + "." + System.lineSeparator()
                + String.format("Recommended tank size: %.2f L", tankSize) + System.lineSeparator()
                + getAccumulatorMessage();
    }

    /**
     * Save changes to an existing dive plan.
     *
     * @param dive The edited dive plan
     * @return Message with the new recommended tank size
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    public static synchronized String updateDive(Dive dive) {
        readAll();
        diveMap.put(dive.getDiveId(), dive);
        writeAll();
        return String.format("Dive plan updated.  Recommended tank size: %.2f L", DiveBO.calculate(dive));
    }

    /**
     * Build a message showing the totals accumulated by both threads.
     *
     * @return The accumulator message
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    public static synchronized String getAccumulatorMessage() {
        return "--Combined totals from both threads--" + System.lineSeparator()
                + "Dive plans added this session: " + totalDivesAdded + System.lineSeparator()
                + String.format("Total recommended tank size: %.2f L", totalTankSizeLiters) + System.lineSeparator()
                + "Dive plans in file: " + diveMap.size();
    }

    /**
     * Build the text for all dive plans using the latest from the file.
     *
     * @return All dive plans, or a message when there are none
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    public static synchronized String getAllDivesText() {

        readAll();

        if (diveMap.isEmpty()) {
            return "There are no dive plans to display.";
        }

        StringBuilder output = new StringBuilder("--All Dive Plans (" + diveMap.size() + ")--");
        for (Dive current : diveMap.values()) {
            output.append(System.lineSeparator())
                    .append(current.toString())
                    .append(String.format(", tank=%.2fL", DiveBO.calculate(current)));
        }
        return output.toString();
    }

    /**
     * Get a dive plan using the diveId.
     *
     * @param diveId The dive id to look for
     * @return The matching dive plan or null when not found
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    public static synchronized Dive getDive(int diveId) {
        readAll();
        return diveMap.get(diveId);
    }

    /**
     * Find a dive plan using the diver name.  The comparison ignores case.
     *
     * @param diverName Name of the diver being looked for
     * @return The matching dive plan, or null when the diver was not found
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    public static synchronized Dive findByDiverName(String diverName) {
        readAll();
        for (Dive current : diveMap.values()) {
            if (current.getDiverName() != null
                    && current.getDiverName().trim().equalsIgnoreCase(diverName.trim())) {
                return current;
            }
        }
        return null;
    }

    /**
     * Determine the next diveId to use for a new dive plan.
     *
     * @return The highest diveId currently in use plus one
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    private static int getNextDiveId() {
        int maxDiveId = FIRST_DIVE_ID;
        for (Dive current : diveMap.values()) {
            if (current.getDiveId() > maxDiveId) {
                maxDiveId = current.getDiveId();
            }
        }
        return maxDiveId + 1;
    }

    /**
     * Write each dive plan in the map to the file, one json string per line.
     *
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    private static synchronized void writeAll() {
        try {
            FileWriter writer = new FileWriter(PATH_NAME, false);
            for (Dive current : diveMap.values()) {
                writer.append(gson.toJson(current));
                writer.append(System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read each line from the file and load the dive plans into the map.  This
     * picks up the dive plans added by the other thread.
     *
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    private static synchronized void readAll() {

        if (!Files.exists(Paths.get(PATH_NAME))) {
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(PATH_NAME));
            String line = reader.readLine();
            while (line != null) {
                if (!line.trim().isEmpty()) {
                    Dive diveFromJson = gson.fromJson(line, Dive.class);
                    diveMap.put(diveFromJson.getDiveId(), diveFromJson);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the cis2232 folder when it does not already exist.
     *
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    private static void createFolderIfMissing() throws IOException {
        Path folder = Paths.get(FOLDER_NAME);
        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
            System.out.println("Created folder " + FOLDER_NAME);
        }
    }

    /**
     * Make sure the folder exists, then load the data saved the last time the
     * program was run.  If there is no file yet, create it with sample dive plans.
     *
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    private static synchronized void initialize() {
        try {
            createFolderIfMissing();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (Files.exists(Paths.get(PATH_NAME))) {
            System.out.println("Dive plans exist.");
            readAll();
        } else {
            diveMap.put(1001, new Dive(1001, "Jonathan Dumaguing", 170.0, 72.0,
                    "MALE", "AGE_30S", 18.0, 40.0, "DRIFT"));
            diveMap.put(1002, new Dive(1002, "Sean Doyle", 180.0, 85.0,
                    "MALE", "AGE_20S", 30.0, 25.0, "CAVE_WRECK"));
            diveMap.put(1003, new Dive(1003, "Jake Murphy", 175.0, 78.0,
                    "MALE", "AGE_40S", 12.0, 50.0, "COUNTER_CURRENT"));
            diveMap.put(1004, new Dive(1004, "Maria Santos", 160.0, 55.0,
                    "FEMALE", "AGE_20S", 15.0, 45.0, "DRIFT"));
            diveMap.put(1005, new Dive(1005, "Anna Reid", 165.0, 62.0,
                    "FEMALE", "AGE_50S_PLUS", 22.0, 30.0, "CAVE_WRECK"));
            writeAll();
        }
    }
}
