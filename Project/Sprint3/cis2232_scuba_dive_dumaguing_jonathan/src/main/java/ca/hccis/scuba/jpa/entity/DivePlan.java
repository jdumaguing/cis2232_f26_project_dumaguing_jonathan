package ca.hccis.scuba.jpa.entity;

import ca.hccis.scuba.util.CisUtility;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * JPA entity for the DivePlan table.  Holds the details of a scuba dive plan.
 *
 * The validation annotations are used by Spring validation when a dive plan is
 * added or updated.  The ranges are the same ones used in the console application.
 *
 * @author Jonathan Dumaguing
 * @since 20261119
 */
@Entity
@Table(name = "DivePlan")
public class DivePlan {

    /**
     * Defaults used when a new dive plan is created.  The created date and time is
     * set to now and the tank size starts at 0 until it is calculated on submit.
     *
     * @author Jonathan Dumaguing
     * @since 20261119
     */
    public DivePlan() {
        this.setCreatedDateTime(CisUtility.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
        this.setRecommendedTankSizeLiters(0.0);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diveId", nullable = false)
    private Integer diveId;

    @Size(max = 20)
    @NotNull
    @Column(name = "createdDateTime", nullable = false, length = 20)
    private String createdDateTime;

    @NotBlank(message = "Diver name is required")
    @Size(max = 50, message = "Diver name can not be longer than 50 characters")
    @Column(name = "diverName", nullable = false, length = 50)
    private String diverName;

    @NotNull(message = "Height is required")
    @DecimalMin(value = "100.0", message = "Height must be between 100 and 250 cm")
    @DecimalMax(value = "250.0", message = "Height must be between 100 and 250 cm")
    @Column(name = "heightCm")
    private Double heightCm;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "30.0", message = "Weight must be between 30 and 250 kg")
    @DecimalMax(value = "250.0", message = "Weight must be between 30 and 250 kg")
    @Column(name = "weightKg")
    private Double weightKg;

    @NotNull(message = "Gender is required (MALE or FEMALE)")
    @Pattern(regexp = "MALE|FEMALE", message = "Gender is required (MALE or FEMALE)")
    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @NotNull(message = "Age group is required")
    @Pattern(regexp = "AGE_10S|AGE_20S|AGE_30S|AGE_40S|AGE_50S_PLUS", message = "Age group is required")
    @Column(name = "ageGroup", nullable = false, length = 15)
    private String ageGroup;

    @NotNull(message = "Target depth is required")
    @DecimalMin(value = "1.0", message = "Target depth must be between 1 and 40 meters")
    @DecimalMax(value = "40.0", message = "Target depth must be between 1 and 40 meters")
    @Column(name = "targetDepthMeters")
    private Double targetDepthMeters;

    @NotNull(message = "Planned time is required")
    @DecimalMin(value = "1.0", message = "Planned time must be between 1 and 240 minutes")
    @DecimalMax(value = "240.0", message = "Planned time must be between 1 and 240 minutes")
    @Column(name = "plannedTimeMinutes")
    private Double plannedTimeMinutes;

    @NotNull(message = "Dive scenario is required")
    @Pattern(regexp = "DRIFT|CAVE_WRECK|COUNTER_CURRENT", message = "Dive scenario is required")
    @Column(name = "diveScenario", nullable = false, length = 20)
    private String diveScenario;

    @Column(name = "recommendedTankSizeLiters")
    private Double recommendedTankSizeLiters;

    public Integer getDiveId() {
        return diveId;
    }

    public void setDiveId(Integer diveId) {
        this.diveId = diveId;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getDiverName() {
        return diverName;
    }

    public void setDiverName(String diverName) {
        this.diverName = diverName;
    }

    public Double getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(Double heightCm) {
        this.heightCm = heightCm;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Double getTargetDepthMeters() {
        return targetDepthMeters;
    }

    public void setTargetDepthMeters(Double targetDepthMeters) {
        this.targetDepthMeters = targetDepthMeters;
    }

    public Double getPlannedTimeMinutes() {
        return plannedTimeMinutes;
    }

    public void setPlannedTimeMinutes(Double plannedTimeMinutes) {
        this.plannedTimeMinutes = plannedTimeMinutes;
    }

    public String getDiveScenario() {
        return diveScenario;
    }

    public void setDiveScenario(String diveScenario) {
        this.diveScenario = diveScenario;
    }

    public Double getRecommendedTankSizeLiters() {
        return recommendedTankSizeLiters;
    }

    public void setRecommendedTankSizeLiters(Double recommendedTankSizeLiters) {
        this.recommendedTankSizeLiters = recommendedTankSizeLiters;
    }

    @Override
    public String toString() {
        return "DivePlan{" +
                "diveId=" + diveId +
                ", createdDateTime='" + createdDateTime + '\'' +
                ", diverName='" + diverName + '\'' +
                ", heightCm=" + heightCm +
                ", weightKg=" + weightKg +
                ", gender='" + gender + '\'' +
                ", ageGroup='" + ageGroup + '\'' +
                ", targetDepthMeters=" + targetDepthMeters +
                ", plannedTimeMinutes=" + plannedTimeMinutes +
                ", diveScenario='" + diveScenario + '\'' +
                ", recommendedTankSizeLiters=" + recommendedTankSizeLiters +
                '}';
    }
}
