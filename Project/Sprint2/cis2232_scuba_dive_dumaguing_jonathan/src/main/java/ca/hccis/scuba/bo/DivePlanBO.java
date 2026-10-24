package ca.hccis.scuba.bo;

import ca.hccis.scuba.dao.DivePlanDAO;
import ca.hccis.scuba.entity.Dive;
import ca.hccis.scuba.jpa.entity.DivePlan;
import ca.hccis.scuba.util.CisUtilityFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to hold the business logic needed for the dive plan reports.
 *
 * @author Jonathan Dumaguing
 * @since 20261029
 */
public class DivePlanBO {

    public static final List<String> DIVE_SCENARIOS = Arrays.asList(
            DiveBO.SCENARIO_DRIFT,
            DiveBO.SCENARIO_CAVE_WRECK,
            DiveBO.SCENARIO_COUNTER_CURRENT);

    public static final String REPORT_FILE_NAME = "DivePlansForScenario";
    public static final String REPORT_DATE_FORMAT = "yyyyMMddHHmm";

    public ArrayList<DivePlan> processSelectAllByDiveScenario(String diveScenario) {

        //*********************************************************************
        // This could be done using the repository but there will be times when
        // jdbc will be useful.  For the reports, the requirements state that you
        // are to use jdbc to obtain the data for the report.
        //*********************************************************************

        //Only the three known scenarios are allowed to go to the database.
        if (diveScenario == null || !DIVE_SCENARIOS.contains(diveScenario)) {
            return new ArrayList<>();
        }

        DivePlanDAO divePlanDAO = new DivePlanDAO();
        ArrayList<DivePlan> divePlans = divePlanDAO.selectAllByDiveScenario(diveScenario);

        //Use the calculation from the business object so the tank size is always current.
        calculateTankSizes(divePlans);

        //Also write the report to a file
        CisUtilityFile.writeReportToFile(REPORT_FILE_NAME, divePlans, REPORT_DATE_FORMAT);

        return divePlans;
    }

    /**
     * Calculate the recommended tank size for each dive plan using DiveBO.
     * A row with invalid values in the database gets a tank size of 0.
     *
     * @param divePlans The dive plans to calculate
     * @author Jonathan Dumaguing
     * @since 20261029
     */
    public static void calculateTankSizes(List<DivePlan> divePlans) {
        for (DivePlan current : divePlans) {
            try {
                Dive dive = new Dive(current.getDiveId(), current.getDiverName(),
                        current.getHeightCm(), current.getWeightKg(),
                        current.getGender(), current.getAgeGroup(),
                        current.getTargetDepthMeters(), current.getPlannedTimeMinutes(),
                        current.getDiveScenario());
                current.setRecommendedTankSizeLiters(DiveBO.calculate(dive));
            } catch (IllegalArgumentException e) {
                System.out.println("Could not calculate tank size for dive id " + current.getDiveId());
                current.setRecommendedTankSizeLiters(0.0);
            }
        }
    }
}
