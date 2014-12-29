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

import java.text.ParseException;
import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.batch.admin.service.QuartzService;
import org.springframework.batch.admin.web.JobLauncherDetails;
import org.springframework.batch.admin.web.util.BatchAdminLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
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

        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(JobLauncherDetails.class);
        jobDetailFactoryBean.setJobDataAsMap(jobDataMap);
        jobDetailFactoryBean.setName(jobName);
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.afterPropertiesSet();
        JobDetail jobDetail = (JobDetail) jobDetailFactoryBean.getObject();

        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setName(jobName);
        cronTriggerFactoryBean.setCronExpression(cronExpression);
        try {
            cronTriggerFactoryBean.afterPropertiesSet();
        } catch (ParseException e) {
            BatchAdminLogger.getLogger().error(e.getMessage(), e);
        }
        Trigger trigger = (Trigger) cronTriggerFactoryBean.getObject();

        quartzScheduler.setJobDetails(new JobDetail[] { jobDetail });
        quartzScheduler.setTriggers(new Trigger[] { trigger });
        try {
            quartzScheduler.afterPropertiesSet();
        } catch (Exception e) {
            BatchAdminLogger.getLogger().error(e.getMessage(), e);
        }
        quartzScheduler.start();
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
