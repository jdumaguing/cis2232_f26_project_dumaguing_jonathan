package ca.hccis.scuba.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Base controller which control general functionality in the app.
 *
 * @author Jonathan Dumaguing
 * @since 20261029
 */
@Controller
public class BaseController {

    /**
     * Send the user to the welcome view
     *
     * @return The welcome view
     * @author Jonathan Dumaguing
     * @since 20261029
     */
    @RequestMapping("/")
    public String home() {
        return "index";
    }
}
