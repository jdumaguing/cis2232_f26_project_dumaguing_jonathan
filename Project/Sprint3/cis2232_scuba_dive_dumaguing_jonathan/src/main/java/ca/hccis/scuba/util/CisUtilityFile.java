package ca.hccis.scuba.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Utility methods for writing reports to a file.
 *
 * @author Jonathan Dumaguing
 * @since 20261029
 */
public class CisUtilityFile {

    public static final String FOLDER_NAME = "c:\\CIS2232\\";
    public static final String DEFAULT_DATE_FORMAT = "_yyyyMMddHHmmss";

    /**
     * Write the objects to a report file in the default folder.  The file name
     * will have the current date and time added to it.
     *
     * @param fileName Base name of the file
     * @param theObjects Objects to write, one per line
     * @author Jonathan Dumaguing
     * @since 20261029
     */
    public static void writeReportToFile(String fileName, List<?> theObjects) {
        writeReportToFile(FOLDER_NAME, fileName, theObjects, DEFAULT_DATE_FORMAT);
    }

    /**
     * Write the objects to a report file in the default folder using the date
     * format provided for the date and time added to the file name.
     *
     * @param fileName Base name of the file
     * @param theObjects Objects to write, one per line
     * @param dateFormat Format of the date and time added to the file name
     * @author Jonathan Dumaguing
     * @since 20261029
     */
    public static void writeReportToFile(String fileName, List<?> theObjects, String dateFormat) {
        writeReportToFile(FOLDER_NAME, fileName, theObjects, dateFormat);
    }

    /**
     * Write the objects to a report file.  The folder is created if it does not
     * already exist.
     *
     * @param folderName Folder for the file (ending with a separator)
     * @param fileName Base name of the file
     * @param theObjects Objects to write, one per line
     * @param dateFormat Format of the date and time added to the file name
     * @return The full name of the file which was written
     * @author Jonathan Dumaguing
     * @since 20261029
     */
    public static String writeReportToFile(String folderName, String fileName, List<?> theObjects, String dateFormat) {

        String fullName = folderName + fileName + CisUtility.getCurrentDate(dateFormat) + ".txt";

        Path path = Paths.get(folderName);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            System.out.println("CisUtilityFile-Error creating directories");
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fullName, true));
            for (Object current : theObjects) {
                writer.write(current.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("CisUtilityFile exception with file: " + fullName);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("CisUtilityFile exception with file: " + fullName);
            }
        }
        return fullName;
    }
}
