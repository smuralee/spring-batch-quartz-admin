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

    /**
     * Default constructor
     */
    public QuartzScheduleRequest() {
    }

    /**
     * @param quartzJobName
     * @param cronExpression
     * @param quartzJobParameters
     */
    public QuartzScheduleRequest(String quartzJobName, String cronExpression, String quartzJobParameters) {
        this.quartzJobName = quartzJobName;
        this.cronExpression = cronExpression;
        this.quartzJobParameters = quartzJobParameters;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cronExpression == null) ? 0 : cronExpression.hashCode());
        result = prime * result + ((quartzJobName == null) ? 0 : quartzJobName.hashCode());
        result = prime * result + ((quartzJobParameters == null) ? 0 : quartzJobParameters.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof QuartzScheduleRequest)) {
            return false;
        }
        QuartzScheduleRequest other = (QuartzScheduleRequest) obj;
        if (cronExpression == null) {
            if (other.cronExpression != null) {
                return false;
            }
        } else if (!cronExpression.equals(other.cronExpression)) {
            return false;
        }
        if (quartzJobName == null) {
            if (other.quartzJobName != null) {
                return false;
            }
        } else if (!quartzJobName.equals(other.quartzJobName)) {
            return false;
        }
        if (quartzJobParameters == null) {
            if (other.quartzJobParameters != null) {
                return false;
            }
        } else if (!quartzJobParameters.equals(other.quartzJobParameters)) {
            return false;
        }
        return true;
    }

}
