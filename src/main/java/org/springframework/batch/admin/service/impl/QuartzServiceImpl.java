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

package org.springframework.batch.admin.service.impl;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
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
     */
    @Autowired
    public QuartzServiceImpl(SchedulerFactoryBean quartzScheduler) {
        super();
        this.quartzScheduler = quartzScheduler;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.batch.admin.service.QuartzService#scheduleBatchJob
     * (java.lang.String, java.lang.String, Map<String,Object>)
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

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.admin.service.QuartzService#
     * initializeQuartzTriggers()
     */
    public void initializeQuartzTriggers() {
        // TODO Auto-generated method stub
    }
}
