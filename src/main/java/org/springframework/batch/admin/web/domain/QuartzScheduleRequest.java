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

package org.springframework.batch.admin.web.domain;

import java.io.Serializable;

/**
 * @author Suraj Muraleedharan
 *
 */
public class QuartzScheduleRequest implements Serializable {
    
    /**
     * Default version ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Stores the job name
     */
    private String quartzJobName;
    
    /**
     * Stores the cron expression
     */
    private String cronExpression;
    
    /**
     * Stores the job parameters
     */
    private String quartzJobParameters;
    
    /**
     * @return the quartzJobName
     */
    public String getQuartzJobName() {
        return quartzJobName;
    }
    /**
     * @param quartzJobName the quartzJobName to set
     */
    public void setQuartzJobName(String quartzJobName) {
        this.quartzJobName = quartzJobName;
    }
    
    /**
     * @return the cronExpression
     */
    public String getCronExpression() {
        return cronExpression;
    }
    /**
     * @param cronExpression the cronExpression to set
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
    
    /**
     * @return the quartzJobParameters
     */
    public String getQuartzJobParameters() {
        return quartzJobParameters;
    }
    /**
     * @param quartzJobParameters the quartzJobParameters to set
     */
    public void setQuartzJobParameters(String quartzJobParameters) {
        this.quartzJobParameters = quartzJobParameters;
    }
}
