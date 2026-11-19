package ca.hccis.scuba.dao;

import ca.hccis.scuba.jpa.entity.DivePlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Data access object which uses jdbc to get dive plans from the database for the
 * reports.
 *
 * @author Jonathan Dumaguing
 * @since 20261029
 */
public class DivePlanDAO {

    private static ResultSet rs;
    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(DivePlanDAO.class);

    public DivePlanDAO() {
        String propFileName = "application";
        ResourceBundle rb = ResourceBundle.getBundle(propFileName);
        String connectionString = rb.getString("spring.datasource.url");
        String userName = rb.getString("spring.datasource.username");
        String password = rb.getString("spring.datasource.password");

        try {
            conn = DriverManager.getConnection(connectionString, userName, password);
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    }

    /**
     * Select all of the dive plans for a dive scenario.
     *
     * @param diveScenario DRIFT, CAVE_WRECK or COUNTER_CURRENT
     * @return The dive plans for the scenario sorted by diver name
     * @author Jonathan Dumaguing
     * @since 20261029
     */
    public ArrayList<DivePlan> selectAllByDiveScenario(String diveScenario) {

        ArrayList<DivePlan> divePlans = new ArrayList<>();
        Statement stmt = null;

        //*********************************************************************
        //Use the DriverManager connection created in the constructor to get a
        //statement object.  The statement lets us run sql commands against the
        //MySql database.
        //*********************************************************************
        try {
            stmt = conn.createStatement();
            String sqlStatement = "select * from DivePlan "
                    + "where diveScenario = '" + diveScenario + "'"
                    + " ORDER BY diverName;";
            rs = stmt.executeQuery(sqlStatement);

            //*****************************************************************
            //Loop through the result set using the next method.
            //*****************************************************************
            divePlans = loadList(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
                System.out.println("There was an error closing");
            }
        }
        return divePlans;
    }

    /**
     * Load each row of the result set into a DivePlan object.
     *
     * @param rs The result set from the query
     * @return List of dive plans
     * @throws SQLException when a column can not be read
     * @author Jonathan Dumaguing
     * @since 20261029
     */
    public ArrayList<DivePlan> loadList(ResultSet rs) throws SQLException {

        ArrayList<DivePlan> divePlans = new ArrayList();

        while (rs.next()) {
            DivePlan divePlan = new DivePlan();
            divePlan.setDiveId(rs.getInt("diveId"));
            divePlan.setCreatedDateTime(rs.getString("createdDateTime"));
            divePlan.setDiverName(rs.getString("diverName"));
            divePlan.setHeightCm(rs.getDouble("heightCm"));
            divePlan.setWeightKg(rs.getDouble("weightKg"));
            divePlan.setGender(rs.getString("gender"));
            divePlan.setAgeGroup(rs.getString("ageGroup"));
            divePlan.setTargetDepthMeters(rs.getDouble("targetDepthMeters"));
            divePlan.setPlannedTimeMinutes(rs.getDouble("plannedTimeMinutes"));
            divePlan.setDiveScenario(rs.getString("diveScenario"));
            divePlan.setRecommendedTankSizeLiters(rs.getDouble("recommendedTankSizeLiters"));
            divePlans.add(divePlan);
        }
        return divePlans;
    }
}
