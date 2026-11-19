package ca.hccis.scuba.bo;

import ca.hccis.scuba.dao.DivePlanDAO;
import ca.hccis.scuba.entity.Dive;
import ca.hccis.scuba.jpa.entity.DivePlan;
import ca.hccis.scuba.util.CisUtilityFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to hold the business logic needed for the dive plans.  It is used by the
 * reports (sprint 2) and by the add and edit of dive plans (sprint 3).
 *
 * @author Jonathan Dumaguing
 * @since 20261119
 */
public class DivePlanBO {

    public static final List<String> DIVE_SCENARIOS = Arrays.asList(
            DiveBO.SCENARIO_DRIFT,
            DiveBO.SCENARIO_CAVE_WRECK,
            DiveBO.SCENARIO_COUNTER_CURRENT);

    public static final List<String> GENDERS = Arrays.asList(
            DiveBO.GENDER_MALE,
            DiveBO.GENDER_FEMALE);

    public static final List<String> AGE_GROUPS = Arrays.asList(
            DiveBO.AGE_10S,
            DiveBO.AGE_20S,
            DiveBO.AGE_30S,
            DiveBO.AGE_40S,
            DiveBO.AGE_50S_PLUS);

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
     * Calculate the recommended tank size for one dive plan using DiveBO.  The
     * result is rounded to 2 decimal places so the value saved in the database is
     * tidy.
     *
     * @param divePlan The dive plan to calculate
     * @return The recommended tank size in liters
     * @throws IllegalArgumentException when the dive plan is missing values or has invalid values
     * @author Jonathan Dumaguing
     * @since 20261119
     */
    public static double calculateTankSize(DivePlan divePlan) {

        if (divePlan == null
                || divePlan.getHeightCm() == null
                || divePlan.getWeightKg() == null
                || divePlan.getTargetDepthMeters() == null
                || divePlan.getPlannedTimeMinutes() == null) {
            throw new IllegalArgumentException("The dive plan is missing values needed for the calculation.");
        }

        //A new dive plan does not have a dive id yet so 0 is used for the calculation.
        int diveId = divePlan.getDiveId() == null ? 0 : divePlan.getDiveId();

        Dive dive = new Dive(diveId, divePlan.getDiverName(),
                divePlan.getHeightCm(), divePlan.getWeightKg(),
                divePlan.getGender(), divePlan.getAgeGroup(),
                divePlan.getTargetDepthMeters(), divePlan.getPlannedTimeMinutes(),
                divePlan.getDiveScenario());

        double tankSize = DiveBO.calculate(dive);
        return Math.round(tankSize * 100.0) / 100.0;
    }

    /**
     * Calculate the recommended tank size for each dive plan using DiveBO.
     * A row with invalid values in the database gets a tank size of 0.
     *
     * @param divePlans The dive plans to calculate
     * @author Jonathan Dumaguing
     * @since 20261119
     */
    public static void calculateTankSizes(List<DivePlan> divePlans) {
        for (DivePlan current : divePlans) {
            try {
                current.setRecommendedTankSizeLiters(calculateTankSize(current));
            } catch (IllegalArgumentException e) {
                System.out.println("Could not calculate tank size for dive id " + current.getDiveId());
                current.setRecommendedTankSizeLiters(0.0);
            }
        }
    }
}
