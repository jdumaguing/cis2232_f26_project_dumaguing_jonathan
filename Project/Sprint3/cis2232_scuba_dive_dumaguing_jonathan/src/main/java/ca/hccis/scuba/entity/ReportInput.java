package ca.hccis.scuba.entity;

import ca.hccis.scuba.jpa.entity.DivePlan;

import java.util.ArrayList;

/**
 * Entity class to hold the attributes of the reports.  It holds what the user
 * entered on the report form and the dive plans found for the report.
 *
 * @author Jonathan Dumaguing
 * @since 20261029
 */
public class ReportInput {

    private String diveScenario;
    private ArrayList<DivePlan> divePlans;

    public String getDiveScenario() {
        return diveScenario;
    }

    public void setDiveScenario(String diveScenario) {
        this.diveScenario = diveScenario;
    }

    public ArrayList<DivePlan> getDivePlans() {
        return divePlans;
    }

    public void setDivePlans(ArrayList<DivePlan> divePlans) {
        this.divePlans = divePlans;
    }
}
