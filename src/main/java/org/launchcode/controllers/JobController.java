package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        model.addAttribute("title", "Job Detail for Id = " + id);
        model.addAttribute("job", jobData.findById(id));
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.



        if (errors.hasErrors()){
            model.addAttribute(jobForm);
        return "new-job";
    }

        String thename = jobForm.getName();

        //Employer emp = JobData.getEmployers().findById(jobForm.getEmployerId());
        Employer emp = jobData.getEmployers().findById(jobForm.getEmployerId());

        //Location loc = JobData.getLocations().findById(jobForm.getLocationId());
        Location loc = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType postype = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency corecomp = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());

        // What about the id this constructor will generate as part of the Job object
        // Won't that be diff from what was passed into

        Job newjob = new Job(thename, emp, loc, postype, corecomp );

        jobData.add(newjob);
        model.addAttribute(newjob);
        return "redirect:/job?id=" + newjob.getId();

    }
}
