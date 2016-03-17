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
package org.springframework.batch.admin.web.util;

/**
 * @author Suraj Muraleedharan
 *
 */
public final class Constants {

    /**
     * job parameters regex
     */
    public static final String JOB_PARAMETERS_REGEX = "([\\w\\.-_\\)\\(]+=[^,\\n]*[,\\n])*([\\w\\.-_\\)\\(]+=[^,]*$)";

    /**
     * job name
     */
    public static final String JOB_NAME = "jobName";

    /**
     * job run date
     */
    public static final String JOB_RUN_DATE = "jobRunDate";

    /**
     * Quartz group
     */
    public static final String QUARTZ_GROUP = "quartzGroup";

    /**
     * Quartz trigger name suffix
     */
    public static final String TRIGGER_SUFFIX = "QuartzTrigger";

    /**
     * Bean names
     */
    public static final String JOB_SERVICE_BEAN = "jobService";
    public static final String JOB_DATASTORE_BEAN = "batchDataStore";
    
     /**
     * Messages
     */
    public static final String JOB_IS_NOT_SCHEDULED = "Not Scheduled";
    public static final String JOB_IS_SCHEDULED = "Scheduled";
    
    /**
     * Action
     */
    public static final String ACTION_SCHEDULE = "Schedule";
    public static final String ACTION_UNSCHEDULE = "Un-Schedule";
    
}
