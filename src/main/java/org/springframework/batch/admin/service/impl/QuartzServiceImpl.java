/*
 * Copyright 2016 Suraj Muraleedharan.
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
package org.springframework.batch.admin.service.impl;

import static com.cronutils.model.CronType.QUARTZ;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.batch.admin.service.QuartzService;
import org.springframework.batch.admin.web.JobLauncherDetails;
import org.springframework.batch.admin.web.domain.BatchJobDataStore;
import org.springframework.batch.admin.web.util.AppContext;
import org.springframework.batch.admin.web.util.BatchAdminLogger;
import org.springframework.batch.admin.web.util.Constants;
import org.springframework.batch.admin.web.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;

/**
 * Implementation of the {@link QuartzService}
 *
 * @author Suraj Muraleedharan
 *
 */
public class QuartzServiceImpl implements QuartzService {

    /**
     * Scheduler instance
     */
    private final SchedulerFactoryBean quartzScheduler;

    /**
     * Parameterized constructor
     *
     * @param quartzScheduler
     */
    @Autowired
    public QuartzServiceImpl(SchedulerFactoryBean quartzScheduler) {
        super();
        this.quartzScheduler = quartzScheduler;
    }

    /**
     *
     * @param jobName
     * @param cronExpression
     * @param jobDataMap
     */
    public void scheduleBatchJob(String jobName, String cronExpression, Map<String, Object> jobDataMap) {

        JobKey jobKey = new JobKey(jobName, Constants.QUARTZ_GROUP);
        JobDetail jobDetail = JobBuilder.newJob(JobLauncherDetails.class).withIdentity(jobName, Constants.QUARTZ_GROUP).build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(Util.getTriggerName(jobName), Constants.QUARTZ_GROUP).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

        // Storing the details
        BatchJobDataStore batchJobDataStore = (BatchJobDataStore) AppContext.getApplicationContext().getBean(Constants.JOB_DATASTORE_BEAN);
        Map<String, Map<String, Object>> jobDataMapStore = batchJobDataStore.getJobDataMapStore();
        jobDataMapStore.put(jobName, jobDataMap);

        try {

            // Delete job, if existing
            if (quartzScheduler.getScheduler().checkExists(jobKey)) {
                quartzScheduler.getScheduler().deleteJob(jobKey);
            }

            // Schedule job
            quartzScheduler.getScheduler().scheduleJob(jobDetail, trigger);

            BatchAdminLogger.getLogger().info("Job is scheduled");
        } catch (SchedulerException e) {
            BatchAdminLogger.getLogger().error(e.getMessage(), e);
        }
    }

    /**
     *
     * @param jobName
     * @return String
     */
    public String getScheduledJobDescription(String jobName) {
        String message = Constants.JOB_IS_NOT_SCHEDULED;
        JobKey jobKey = new JobKey(jobName, Constants.QUARTZ_GROUP);
        try {
            JobDetail jobDetail = quartzScheduler.getScheduler().getJobDetail(jobKey);
            if (null != jobDetail) {
                List<? extends Trigger> triggersOfJob = quartzScheduler.getScheduler().getTriggersOfJob(jobKey);
                if (null != triggersOfJob && !triggersOfJob.isEmpty()) {
                    CronTrigger trigger = (CronTrigger) triggersOfJob.get(0);
                    String cronExpression = trigger.getCronExpression();
                    CronDescriptor descriptor = CronDescriptor.instance(Locale.US);
                    CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ));
                    message = descriptor.describe(parser.parse(cronExpression));
                }

            }
        } catch (SchedulerException e) {
            BatchAdminLogger.getLogger().error(e.getMessage(), e);
        }
        return message;
    }

    /**
     *
     * @param jobName
     * @return String
     */
    public String getScheduledJobStatus(String jobName) {
        String returnVal = Constants.JOB_IS_NOT_SCHEDULED;
        JobKey jobKey = new JobKey(jobName, Constants.QUARTZ_GROUP);

        try {
            JobDetail jobDetail = quartzScheduler.getScheduler().getJobDetail(jobKey);
            if (null != jobDetail) {

                returnVal = Constants.JOB_IS_SCHEDULED;
            }
        } catch (SchedulerException e) {
            BatchAdminLogger.getLogger().error(e.getMessage(), e);
        }

        return returnVal;
    }

    /**
     *
     * @param jobName
     */
    public void unScheduleBatchJob(String jobName) {
        // Delete job, if existing
        JobKey jobKey = new JobKey(jobName, Constants.QUARTZ_GROUP);
        try {
            JobDetail jobDetail = quartzScheduler.getScheduler().getJobDetail(jobKey);
            if (null != jobDetail) {
                quartzScheduler.getScheduler().deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            BatchAdminLogger.getLogger().error(e.getMessage(), e);
        }
    }

}
