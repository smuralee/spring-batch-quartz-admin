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
package org.springframework.batch.admin.service;

import java.util.Map;

/**
 * Interface for general purpose monitoring of the Quartz scheduling of spring batch
 *
 * @author Suraj Muraleedharan
 *
 */
public interface QuartzService {

    /**
     * <p>
     * Schedules the batch job as per the cron expression
     * </p>
     *
     * @param jobName
     * @param cronExpression
     * @param jobDataMap
     */
    void scheduleBatchJob(String jobName, String cronExpression, Map<String, Object> jobDataMap);

    /**
     * <p>
     * Returns the schedule description, if the job is scheduled. If no job is found, returns
     * default string - "This job is not scheduled"
     * </p>
     *
     * @param jobName
     * @return
     */
    String getScheduledJobDescription(String jobName);

    /**
     * <p>
     * Returns "Scheduled", if the job is scheduled
     * </p>
     *
     * @param jobName
     * @return boolean
     */
    String getScheduledJobStatus(String jobName);

    /**
     * <p>
     * Un-schedules the batch job from the quartz controller
     * </p>
     *
     * @param jobName
     */
    void unScheduleBatchJob(String jobName);

}
