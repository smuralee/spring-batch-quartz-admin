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
import java.sql.Timestamp;

/**
 * @author Suraj Muraleedharan
 *
 */
public class BatchCronDetails implements Serializable {

    /**
     * Default version ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Cron details identifier
     */
    private Integer cronInstanceID;

    /**
     * Job name
     */
    private String jobName;

    /**
     * Cron expression for the batch job
     */
    private String cronExp;

    /**
     * Timestamp for storing the added time
     */
    private Timestamp addedTime;

    /**
     * Timestamp for storing the modified time.
     */
    private Timestamp modifiedTime;

    /**
     * @return the cronInstanceID
     */
    public Integer getCronInstanceID() {
        return cronInstanceID;
    }

    /**
     * @param cronInstanceID
     *            the cronInstanceID to set
     */
    public void setCronInstanceID(Integer cronInstanceID) {
        this.cronInstanceID = cronInstanceID;
    }

    /**
     * @return the jobName
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * @param jobName
     *            the jobName to set
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @return the cronExp
     */
    public String getCronExp() {
        return cronExp;
    }

    /**
     * @param cronExp
     *            the cronExp to set
     */
    public void setCronExp(String cronExp) {
        this.cronExp = cronExp;
    }

    /**
     * @return the addedTime
     */
    public Timestamp getAddedTime() {
        return addedTime;
    }

    /**
     * @param addedTime
     *            the addedTime to set
     */
    public void setAddedTime(Timestamp addedTime) {
        this.addedTime = addedTime;
    }

    /**
     * @return the modifiedTime
     */
    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    /**
     * @param modifiedTime
     *            the modifiedTime to set
     */
    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * Default constructor
     */
    public BatchCronDetails() {
    }

    /**
     * @param cronInstanceID
     * @param jobName
     * @param cronExp
     * @param addedTime
     * @param modifiedTime
     */
    public BatchCronDetails(Integer cronInstanceID, String jobName, String cronExp, Timestamp addedTime, Timestamp modifiedTime) {
        this.cronInstanceID = cronInstanceID;
        this.jobName = jobName;
        this.cronExp = cronExp;
        this.addedTime = addedTime;
        this.modifiedTime = modifiedTime;
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
        result = prime * result + ((addedTime == null) ? 0 : addedTime.hashCode());
        result = prime * result + ((cronExp == null) ? 0 : cronExp.hashCode());
        result = prime * result + ((cronInstanceID == null) ? 0 : cronInstanceID.hashCode());
        result = prime * result + ((jobName == null) ? 0 : jobName.hashCode());
        result = prime * result + ((modifiedTime == null) ? 0 : modifiedTime.hashCode());
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
        if (!(obj instanceof BatchCronDetails)) {
            return false;
        }
        BatchCronDetails other = (BatchCronDetails) obj;
        if (addedTime == null) {
            if (other.addedTime != null) {
                return false;
            }
        } else if (!addedTime.equals(other.addedTime)) {
            return false;
        }
        if (cronExp == null) {
            if (other.cronExp != null) {
                return false;
            }
        } else if (!cronExp.equals(other.cronExp)) {
            return false;
        }
        if (cronInstanceID == null) {
            if (other.cronInstanceID != null) {
                return false;
            }
        } else if (!cronInstanceID.equals(other.cronInstanceID)) {
            return false;
        }
        if (jobName == null) {
            if (other.jobName != null) {
                return false;
            }
        } else if (!jobName.equals(other.jobName)) {
            return false;
        }
        if (modifiedTime == null) {
            if (other.modifiedTime != null) {
                return false;
            }
        } else if (!modifiedTime.equals(other.modifiedTime)) {
            return false;
        }
        return true;
    }

}
