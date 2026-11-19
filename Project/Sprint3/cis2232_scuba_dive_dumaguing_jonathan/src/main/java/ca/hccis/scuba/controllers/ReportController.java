package ca.hccis.scuba.controllers;

import ca.hccis.scuba.bo.DivePlanBO;
import ca.hccis.scuba.entity.ReportInput;
import ca.hccis.scuba.jpa.entity.DivePlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Controller to administer reports of the project.
 *
 * @author Jonathan Dumaguing
 * @since 20261029
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    /**
     * Send the user to list of reports view.
     *
     * @param model
     * @param session
     * @return To the appropriate view
     * @author Jonathan Dumaguing
     * @since 20261029
     */
    @RequestMapping("")
    public String home(Model model, HttpSession session) {
        logger.info("Running the reports controller base method");
        return "report/list";
    }

    /**
     * Method to send user to the dive scenario report.
     *
     * @param model
     * @return view for the report input
     * @author Jonathan Dumaguing
     * @since 20261029
     */
    @RequestMapping("/dive/scenario")
    public String reportDiveScenario(Model model) {
        logger.info("Running the reports controller dive scenario method");
        model.addAttribute("scenarios", DivePlanBO.DIVE_SCENARIOS);
        model.addAttribute("reportInput", new ReportInput());
        return "report/reportDiveScenario";
    }

    /**
     * Process the report - dive scenario
     *
     * @param model
     * @param reportInput Object containing inputs for the report
     * @return view to show report
     * @author Jonathan Dumaguing
     * @since 20261029
     */
    @RequestMapping("/dive/scenario/submit")
    public String reportDiveScenarioSubmit(Model model, @ModelAttribute("reportInput") ReportInput reportInput) {

        System.out.println("Dive scenario from input form:" + reportInput.getDiveScenario());

        //Get all the applicable dive plans from the database
        DivePlanBO divePlanBO = new DivePlanBO();
        ArrayList<DivePlan> theList = divePlanBO.processSelectAllByDiveScenario(reportInput.getDiveScenario());
        reportInput.setDivePlans(theList);

        //Add a message in case the report does not contain any data
        if (theList != null && theList.isEmpty()) {
            model.addAttribute("message", "No dive plans found for that scenario");
            System.out.println("No data found");
        }

        //Put objects in model so they can be used on the view (html)
        model.addAttribute("scenarios", DivePlanBO.DIVE_SCENARIOS);
        model.addAttribute("reportInput", reportInput);
        return "report/reportDiveScenario"; //Send user to another view.
    }
}
