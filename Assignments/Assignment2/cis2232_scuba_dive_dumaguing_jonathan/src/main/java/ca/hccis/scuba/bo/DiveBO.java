package ca.hccis.scuba.bo;

import ca.hccis.scuba.entity.Dive;

/**
 * Business object for the Scuba Dive Air Consumption &amp; Tank Planner.
 * Holds the calculation which determines the recommended tank size for a dive.
 *
 * Calculation (from the project readme):
 * <ol>
 *   <li>bsa = sqrt(heightCm x weightKg / 3600)  (Mosteller formula)</li>
 *   <li>baselineSAC = bsa x genderFactor x ageMultiplier</li>
 *   <li>ata = targetDepthMeters / 10 + 1</li>
 *   <li>effectiveSAC = baselineSAC x scenarioMultiplier</li>
 *   <li>totalAirNeededLiters = effectiveSAC x ata x plannedTimeMinutes</li>
 *   <li>recommendedTankSizeLiters = totalAirNeededLiters / 150</li>
 * </ol>
 *
 * This class was developed following a test driven development approach.
 *
 * @author Jonathan Dumaguing
 * @since 20260930
 */
public class DiveBO {

    //Mosteller formula divisor
    public static final double BSA_DIVISOR = 3600.0;

    //Gender factors
    public static final String GENDER_MALE = "MALE";
    public static final String GENDER_FEMALE = "FEMALE";
    public static final double GENDER_FACTOR_MALE = 9.8;
    public static final double GENDER_FACTOR_FEMALE = 9.0;

    //Age group multipliers
    public static final String AGE_10S = "AGE_10S";
    public static final String AGE_20S = "AGE_20S";
    public static final String AGE_30S = "AGE_30S";
    public static final String AGE_40S = "AGE_40S";
    public static final String AGE_50S_PLUS = "AGE_50S_PLUS";
    public static final double AGE_MULTIPLIER_10S = 1.10;
    public static final double AGE_MULTIPLIER_20S = 1.00;
    public static final double AGE_MULTIPLIER_30S = 0.98;
    public static final double AGE_MULTIPLIER_40S = 1.05;
    public static final double AGE_MULTIPLIER_50S_PLUS = 1.15;

    //Dive scenario multipliers
    public static final String SCENARIO_DRIFT = "DRIFT";
    public static final String SCENARIO_CAVE_WRECK = "CAVE_WRECK";
    public static final String SCENARIO_COUNTER_CURRENT = "COUNTER_CURRENT";
    public static final double SCENARIO_MULTIPLIER_DRIFT = 1.0;
    public static final double SCENARIO_MULTIPLIER_CAVE_WRECK = 1.25;
    public static final double SCENARIO_MULTIPLIER_COUNTER_CURRENT = 1.5;

    //Pressure values
    public static final double METERS_PER_ATMOSPHERE = 10.0;
    public static final double SURFACE_ATMOSPHERE = 1.0;

    //Standard 200 bar tank less a 50 bar safety reserve
    public static final double USABLE_TANK_PRESSURE_BAR = 150.0;

    /**
     * Calculate the recommended tank size (liters) needed for the dive.
     *
     * @param dive The dive plan to calculate
     * @return Recommended tank size in liters
     * @throws IllegalArgumentException when the dive is null or contains invalid values
     * @author Jonathan Dumaguing
     * @since 20260930
     */
    public static double calculate(Dive dive) {

        if (dive == null) {
            throw new IllegalArgumentException("Dive cannot be null.");
        }
        if (dive.getHeightCm() <= 0 || dive.getWeightKg() <= 0) {
            throw new IllegalArgumentException("Height and weight must be greater than zero.");
        }
        if (dive.getTargetDepthMeters() < 0 || dive.getPlannedTimeMinutes() < 0) {
            throw new IllegalArgumentException("Depth and planned time cannot be negative.");
        }

        double bsa = Math.sqrt(dive.getHeightCm() * dive.getWeightKg() / BSA_DIVISOR);
        double baselineSac = bsa * getGenderFactor(dive.getGender()) * getAgeMultiplier(dive.getAgeGroup());

        double ata = dive.getTargetDepthMeters() / METERS_PER_ATMOSPHERE + SURFACE_ATMOSPHERE;
        double effectiveSac = baselineSac * getScenarioMultiplier(dive.getDiveScenario());
        double totalAirNeededLiters = effectiveSac * ata * dive.getPlannedTimeMinutes();

        return totalAirNeededLiters / USABLE_TANK_PRESSURE_BAR;
    }

    /**
     * Get the gender factor used for the baseline SAC rate.
     *
     * @param gender MALE or FEMALE
     * @return The gender factor
     * @throws IllegalArgumentException when the gender is not valid
     * @author Jonathan Dumaguing
     * @since 20260930
     */
    public static double getGenderFactor(String gender) {
        if (GENDER_MALE.equals(gender)) {
            return GENDER_FACTOR_MALE;
        } else if (GENDER_FEMALE.equals(gender)) {
            return GENDER_FACTOR_FEMALE;
        }
        throw new IllegalArgumentException("Invalid gender: " + gender);
    }

    /**
     * Get the age multiplier used for the baseline SAC rate.
     *
     * @param ageGroup AGE_10S, AGE_20S, AGE_30S, AGE_40S or AGE_50S_PLUS
     * @return The age multiplier
     * @throws IllegalArgumentException when the age group is not valid
     * @author Jonathan Dumaguing
     * @since 20260930
     */
    public static double getAgeMultiplier(String ageGroup) {
        if (ageGroup == null) {
            throw new IllegalArgumentException("Age group cannot be null.");
        }
        switch (ageGroup) {
            case AGE_10S:
                return AGE_MULTIPLIER_10S;
            case AGE_20S:
                return AGE_MULTIPLIER_20S;
            case AGE_30S:
                return AGE_MULTIPLIER_30S;
            case AGE_40S:
                return AGE_MULTIPLIER_40S;
            case AGE_50S_PLUS:
                return AGE_MULTIPLIER_50S_PLUS;
            default:
                throw new IllegalArgumentException("Invalid age group: " + ageGroup);
        }
    }

    /**
     * Get the multiplier for the dive scenario.
     *
     * @param diveScenario DRIFT, CAVE_WRECK or COUNTER_CURRENT
     * @return The scenario multiplier
     * @throws IllegalArgumentException when the scenario is not valid
     * @author Jonathan Dumaguing
     * @since 20260930
     */
    public static double getScenarioMultiplier(String diveScenario) {
        if (diveScenario == null) {
            throw new IllegalArgumentException("Dive scenario cannot be null.");
        }
        switch (diveScenario) {
            case SCENARIO_DRIFT:
                return SCENARIO_MULTIPLIER_DRIFT;
            case SCENARIO_CAVE_WRECK:
                return SCENARIO_MULTIPLIER_CAVE_WRECK;
            case SCENARIO_COUNTER_CURRENT:
                return SCENARIO_MULTIPLIER_COUNTER_CURRENT;
            default:
                throw new IllegalArgumentException("Invalid dive scenario: " + diveScenario);
        }
    }
}
