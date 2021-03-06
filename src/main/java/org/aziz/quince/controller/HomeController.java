package org.aziz.quince.controller;

import org.aziz.quince.model.Course;
import org.aziz.quince.service.CourseRepository;
import org.aziz.quince.service.features.EnvService;
import org.aziz.quince.service.features.FeatureService;
import org.aziz.quince.service.features.FeatureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private final String POD_GROUP = "GROUP";
    private final String url_jsonFile = "https://featuresservice.herokuapp.com/myfeatures";
    private final EnvService envService = new EnvService(POD_GROUP);
    // Refactoring: Extract Constant/field/variable:
    private final String envValue = envService.getEnvValue();

    private final FeatureService featureService = FeatureServiceImpl.create(url_jsonFile, envValue);

    @RequestMapping(value = "/")

    public String loadIndex(Model model) {

        String message;

        if (envValue == null) {
            model.addAttribute("pod", "No group found for this pod, default mode enabled");
        } else {
            model.addAttribute("pod", envValue);
        }
        model.addAttribute("featureService", featureService);
        if (featureService.isActive(envValue + ".db-persistence")) {
            model.addAttribute("courses", courseRepository.findAll().iterator());
        } else {
            message = "No database connected";
            model.addAttribute("message", message);
        }

        return "index";
    }

    @RequestMapping(value = "/addNewCourse", method = RequestMethod.POST)
    public String addNewCourse(@RequestParam("input_title") String input_title, @RequestParam String input_location, @RequestParam String input_desc, @RequestParam String input_category) {
        courseRepository.save(new Course(input_title, input_category, input_location, input_desc));
        return "redirect:/";
    }


    @Autowired
    private CourseRepository courseRepository;


}
