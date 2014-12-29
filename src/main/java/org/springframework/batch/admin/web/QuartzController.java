/*
 * Copyright 2009-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.batch.admin.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronExpression;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.admin.service.QuartzService;
import org.springframework.batch.admin.web.domain.QuartzScheduleRequest;
import org.springframework.batch.admin.web.util.Constants;
import org.springframework.batch.admin.web.util.Util;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

@Controller
public class QuartzController {

    /**
     * Quartz service instance
     */
    private final QuartzService quartzService;

    /**
     * Job service instance
     */
    private final JobService jobService;

    /**
     * Timezone
     */
    private TimeZone timeZone = TimeZone.getDefault();

    /**
     * Extensions
     */
    private Collection<String> extensions = new HashSet<String>();

    /**
     * Job parameters extractor
     */
    private JobParametersExtractor jobParametersExtractor = new JobParametersExtractor();

    /**
     * A collection of extensions that may be appended to request urls aimed at
     * this controller.
     * 
     * @param extensions
     *            the extensions (e.g. [rss, xml, atom])
     */
    public void setExtensions(Collection<String> extensions) {
        this.extensions = new LinkedHashSet<String>(extensions);
    }

    @Autowired
    public QuartzController(JobService jobService, QuartzService quartzService) {
        super();
        this.jobService = jobService;
        this.quartzService = quartzService;
        extensions.addAll(Arrays.asList(".html", ".json", ".rss"));
    }

    /**
     * @param timeZone
     *            the timeZone to set
     */
    @Autowired(required = false)
    @Qualifier("userTimeZone")
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * <p>
     * Displays the list of registered jobs on the Quartz menu page
     * </p>
     * 
     * @param model
     * @param startJob
     * @param pageSize
     */
    @RequestMapping(value = "/quartz", method = RequestMethod.GET)
    public void quartzJobs(ModelMap model, @RequestParam(defaultValue = "0") int startJob, @RequestParam(defaultValue = "20") int pageSize) {
        int total = jobService.countJobs();
        TableUtils.addPagination(model, total, startJob, pageSize, "QuartzJob");
        Collection<String> names = jobService.listJobs(startJob, pageSize);
        List<JobInfo> quartzJobs = new ArrayList<JobInfo>();
        for (String name : names) {
            int count = 0;
            try {
                count = jobService.countJobExecutionsForJob(name);
            } catch (NoSuchJobException e) {
                // shouldn't happen
            }
            boolean launchable = jobService.isLaunchable(name);
            boolean incrementable = jobService.isIncrementable(name);
            quartzJobs.add(new JobInfo(name, count, null, launchable, incrementable));
        }
        model.addAttribute("quartzJobs", quartzJobs);
    }

    /**
     * <p>
     * Displays the details page for each of the quartz jobs
     * </p>
     * 
     * @param model
     * @param quartzJobName
     * @param errors
     * @param startJobInstance
     * @param pageSize
     * @return String
     */
    @RequestMapping(value = "/quartz/{quartzJobName}", method = RequestMethod.GET)
    public String quartzJobDetails(ModelMap model, @ModelAttribute("quartzJobName") String quartzJobName, Errors errors, @RequestParam(defaultValue = "0") int startJobInstance,
            @RequestParam(defaultValue = "20") int pageSize) {

        boolean launchable = jobService.isLaunchable(quartzJobName);

        try {

            Collection<JobInstance> result = jobService.listJobInstances(quartzJobName, startJobInstance, pageSize);
            Collection<JobInstanceInfo> jobInstances = new ArrayList<JobInstanceInfo>();
            model.addAttribute("quartzJobParameters", jobParametersExtractor.fromJobParameters(jobService.getLastJobParameters(quartzJobName)));

            for (JobInstance jobInstance : result) {
                Collection<JobExecution> jobExecutions = jobService.getJobExecutionsForJobInstance(quartzJobName, jobInstance.getId());
                jobInstances.add(new JobInstanceInfo(jobInstance, jobExecutions, timeZone));
            }

            model.addAttribute("quartzJobInstances", jobInstances);
            int total = jobService.countJobInstances(quartzJobName);
            TableUtils.addPagination(model, total, startJobInstance, pageSize, "QuartzJobInstance");
            int count = jobService.countJobExecutionsForJob(quartzJobName);
            model.addAttribute("quartzJobInfo", new JobInfo(quartzJobName, count, launchable, jobService.isIncrementable(quartzJobName)));

        } catch (NoSuchJobException e) {
            errors.reject("no.such.job", new Object[] { quartzJobName }, "There is no such job (" + HtmlUtils.htmlEscape(quartzJobName) + ")");
        }

        return "quartz/job";
    }

    /**
     * <p>
     * Schedules the job using the quartz
     * </p>
     * 
     * @param model
     * @param quartzJobName
     * @param quartzScheduleRequest
     * @param errors
     * @param origin
     * @return String
     */
    @RequestMapping(value = "/quartz/{quartzJobName}", method = RequestMethod.POST)
    public String scheduleQuartzJob(ModelMap model, @ModelAttribute("quartzJobName") String quartzJobName, @ModelAttribute("quartzScheduleRequest") QuartzScheduleRequest quartzScheduleRequest,
            Errors errors, @RequestParam(defaultValue = "execution") String origin) {

        // Setting the job name
        quartzScheduleRequest.setQuartzJobName(quartzJobName);

        // Validate the cron expression
        if (!CronExpression.isValidExpression(quartzScheduleRequest.getCronExpression())) {
            errors.reject("invalid.cron.expression", "Please enter a valid cron expression.i.e. * * * * * ? ");
        }

        // Validate the job parameters
        if (StringUtils.isNotBlank(quartzScheduleRequest.getQuartzJobParameters()) && (!Util.isValidRegExp(Constants.JOB_PARAMETERS_REGEX, quartzScheduleRequest.getQuartzJobParameters()))) {
            errors.reject("invalid.job.parameters", "Invalid Job Parameters (use comma or new-line separator)");
        }

        if (!errors.hasErrors()) {
            
            // Fetching the parameters
            String params = quartzScheduleRequest.getQuartzJobParameters();
            Map<String,Object> jobDataMap = Util.extractJobDataMap(quartzJobName, params);
            
            // Scheduling the batch job
            quartzService.scheduleBatchJob(quartzJobName, quartzScheduleRequest.getCronExpression(), jobDataMap);
            
        }

        // Scheduling the job using Quartz
        if (!"quartzJob".equals(origin)) {
            // if the origin is not specified we are probably not a UI client
            return "jobs/execution";
        }
        else {
            // In the UI we show the same page again...
            return quartzJobDetails(model, quartzJobName, errors, 0, 20);
        }

        // Not a redirect because normally it is requested by an Ajax call so
        // there's less of a pressing need for one (the browser history won't
        // contain the request).
    }
    
}
