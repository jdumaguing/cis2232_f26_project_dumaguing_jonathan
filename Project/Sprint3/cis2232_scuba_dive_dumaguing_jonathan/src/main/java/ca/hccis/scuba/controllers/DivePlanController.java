package ca.hccis.scuba.controllers;

import ca.hccis.scuba.bo.DivePlanBO;
import ca.hccis.scuba.jpa.entity.DivePlan;
import ca.hccis.scuba.repositories.DivePlanRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Controller for the dive plans.  Provides the list, add, edit and delete using
 * Spring Data JPA and Spring validation.
 *
 * @author Jonathan Dumaguing
 * @since 20261119
 */
@Controller
@RequestMapping("/diveplan")
public class DivePlanController {

    private static final Logger logger = LoggerFactory.getLogger(DivePlanController.class);

    private final DivePlanRepository _dpr;

    public DivePlanController(DivePlanRepository dpr) {
        _dpr = dpr;
    }

    /**
     * Show the list of all the dive plans.  Any message sent by the add, edit or
     * delete is shown at the top of the page.
     *
     * @param model Spring MVC model
     * @return The list view
     * @author Jonathan Dumaguing
     * @since 20261119
     */
    @RequestMapping({"", "/"})
    public String home(Model model) {
        logger.info("Running the dive plan list");
        model.addAttribute("divePlans", getAllDivePlans());
        return "diveplan/list";
    }

    /**
     * Show the form to add a new dive plan.  A new DivePlan has the defaults
     * (created date and time) already set.
     *
     * @param model Spring MVC model
     * @return The add view
     * @author Jonathan Dumaguing
     * @since 20261119
     */
    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("divePlan", new DivePlan());
        addDropdownLists(model);
        return "diveplan/add";
    }

    /**
     * Show the form to edit an existing dive plan.  The same view is used for add
     * and edit.
     *
     * @param id The dive id of the dive plan to edit
     * @param model Spring MVC model
     * @param redirectAttributes Used to send a message to the list when the dive plan is not found
     * @return The add/edit view, or back to the list when the dive plan does not exist
     * @author Jonathan Dumaguing
     * @since 20261119
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {

        Optional<DivePlan> found = _dpr.findById(id);

        if (found.isEmpty()) {
            redirectAttributes.addFlashAttribute("messageError", "No dive plan found with dive id " + id + ".");
            return "redirect:/diveplan";
        }

        model.addAttribute("divePlan", found.get());
        addDropdownLists(model);
        return "diveplan/add";
    }

    /**
     * Submit for both add and edit.  Spring validation checks the dive plan first.
     * When it is valid the tank size is calculated, the dive plan is saved and the
     * user is sent to the list with a message showing the calculated tank size.
     *
     * @param model Spring MVC model
     * @param divePlan The dive plan from the form
     * @param bindingResult The result of the validation
     * @param redirectAttributes Used to send the success message to the list
     * @return Back to the form when there are errors, otherwise to the list
     * @author Jonathan Dumaguing
     * @since 20261119
     */
    @PostMapping("/submit")
    public String submit(Model model, @Valid @ModelAttribute("divePlan") DivePlan divePlan,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //Validation failed so send the user back to the form to fix the errors.
        if (bindingResult.hasErrors()) {
            logger.info("Dive plan validation failed");
            addDropdownLists(model);
            return "diveplan/add";
        }

        boolean isNew = divePlan.getDiveId() == null;

        //Calculate the tank size using the business object before saving.
        try {
            divePlan.setRecommendedTankSizeLiters(DivePlanBO.calculateTankSize(divePlan));
        } catch (IllegalArgumentException e) {
            model.addAttribute("messageError", "Could not calculate the tank size: " + e.getMessage());
            addDropdownLists(model);
            return "diveplan/add";
        }

        DivePlan saved = _dpr.save(divePlan);

        String message = String.format(Locale.US,
                "Dive plan %s for %s (dive id %d, %s, %.1f m, %.1f min).  Recommended tank size: %.2f L",
                isNew ? "added" : "updated",
                saved.getDiverName(), saved.getDiveId(), saved.getDiveScenario(),
                saved.getTargetDepthMeters(), saved.getPlannedTimeMinutes(),
                saved.getRecommendedTankSizeLiters());

        redirectAttributes.addFlashAttribute("messageSuccess", message);
        return "redirect:/diveplan";
    }

    /**
     * Delete a dive plan and go back to the list.
     *
     * @param id The dive id of the dive plan to delete
     * @param redirectAttributes Used to send the result message to the list
     * @return Back to the list
     * @author Jonathan Dumaguing
     * @since 20261119
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {

        if (_dpr.existsById(id)) {
            _dpr.deleteById(id);
            redirectAttributes.addFlashAttribute("messageSuccess", "Deleted dive plan with dive id " + id + ".");
        } else {
            redirectAttributes.addFlashAttribute("messageError", "No dive plan found with dive id " + id + ".");
        }
        return "redirect:/diveplan";
    }

    /**
     * Put the allowed values in the model so the form can show them in dropdowns.
     *
     * @param model Spring MVC model
     * @author Jonathan Dumaguing
     * @since 20261119
     */
    private void addDropdownLists(Model model) {
        model.addAttribute("genders", DivePlanBO.GENDERS);
        model.addAttribute("ageGroups", DivePlanBO.AGE_GROUPS);
        model.addAttribute("scenarios", DivePlanBO.DIVE_SCENARIOS);
    }

    /**
     * Get all of the dive plans from the database.
     *
     * @return List of the dive plans
     * @author Jonathan Dumaguing
     * @since 20261119
     */
    private List<DivePlan> getAllDivePlans() {
        List<DivePlan> divePlans = new ArrayList<>();
        _dpr.findAll().forEach(divePlans::add);
        return divePlans;
    }
}
