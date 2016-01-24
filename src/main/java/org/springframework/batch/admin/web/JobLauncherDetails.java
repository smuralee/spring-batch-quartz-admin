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

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.admin.web.domain.BatchJobDataStore;
import org.springframework.batch.admin.web.util.AppContext;
import org.springframework.batch.admin.web.util.BatchAdminLogger;
import org.springframework.batch.admin.web.util.Constants;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

/**
 * @author Suraj Muraleedharan
 *
 */
public class JobLauncherDetails implements Job {

    /**
     * Job service instance
     */
    private JobService jobService;

    /* (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {

        jobService = (JobService) AppContext.getApplicationContext().getBean(Constants.JOB_SERVICE_BEAN);

        String jobName = context.getJobDetail().getKey().getName();

        BatchJobDataStore batchJobDataStore = (BatchJobDataStore) AppContext.getApplicationContext().getBean(Constants.JOB_DATASTORE_BEAN);
        Map<String, Map<String, Object>> jobDataMapStore = batchJobDataStore.getJobDataMapStore();
        Map<String, Object> jobDataMap = jobDataMapStore.get(jobName);
        JobParameters jobParameters = getJobParametersFromJobMap(jobDataMap);

        try {
            jobService.launch(jobName, jobParameters);
        } catch (NoSuchJobException e) {
            BatchAdminLogger.getLogger().error(e.getMessage(), e);
        } catch (JobExecutionAlreadyRunningException e) {
            BatchAdminLogger.getLogger().error(e.getMessage(), e);
        } catch (JobRestartException e) {
            BatchAdminLogger.getLogger().error(e.getMessage(), e);
        } catch (JobInstanceAlreadyCompleteException e) {
            BatchAdminLogger.getLogger().error(e.getMessage(), e);
        } catch (JobParametersInvalidException e) {
            BatchAdminLogger.getLogger().error(e.getMessage(), e);
        }

    }

    // get params from jobDataAsMap property, job-quartz.xml
    private JobParameters getJobParametersFromJobMap(Map<String, Object> jobDataMap) {

        JobParametersBuilder builder = new JobParametersBuilder();

        for (Entry<String, Object> entry : jobDataMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String && !key.equals(Constants.JOB_NAME)) {
                builder.addString(key, (String) value);
            } else if (value instanceof Float || value instanceof Double) {
                builder.addDouble(key, ((Number) value).doubleValue());
            } else if (value instanceof Integer || value instanceof Long) {
                builder.addLong(key, ((Number) value).longValue());
            } else if (value instanceof Date) {
                builder.addDate(key, (Date) value);
            } else {
                // JobDataMap contains values which are not job parameters
                // (ignoring)
            }
        }

        // Needs a unique job parameter to rerun the completed job
        builder.addDate(Constants.JOB_RUN_DATE, new Date());

        return builder.toJobParameters();

    }

}
