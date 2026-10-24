package ca.hccis.scuba.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * JPA entity for the DivePlan table.  Holds the details of a scuba dive plan.
 *
 * @author Jonathan Dumaguing
 * @since 20261029
 */
@Entity
@Table(name = "DivePlan")
public class DivePlan {

    @Id
    @Column(name = "diveId", nullable = false)
    private Integer diveId;

    @Size(max = 20)
    @NotNull
    @Column(name = "createdDateTime", nullable = false, length = 20)
    private String createdDateTime;

    @Size(max = 50)
    @NotNull
    @Column(name = "diverName", nullable = false, length = 50)
    private String diverName;

    @Column(name = "heightCm")
    private Double heightCm;

    @Column(name = "weightKg")
    private Double weightKg;

    @Size(max = 10)
    @NotNull
    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @Size(max = 15)
    @NotNull
    @Column(name = "ageGroup", nullable = false, length = 15)
    private String ageGroup;

    @Column(name = "targetDepthMeters")
    private Double targetDepthMeters;

    @Column(name = "plannedTimeMinutes")
    private Double plannedTimeMinutes;

    @Size(max = 20)
    @NotNull
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
