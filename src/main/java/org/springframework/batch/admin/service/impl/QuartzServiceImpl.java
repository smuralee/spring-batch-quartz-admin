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

import org.springframework.batch.admin.service.QuartzService;
import org.springframework.batch.admin.web.util.BatchAdminLogger;
import org.springframework.batch.core.JobParameters;

/**
 * Implementation of the {@link QuartzService}
 * 
 * @author Suraj Muraleedharan
 *
 */
public class QuartzServiceImpl implements QuartzService {

    /* (non-Javadoc)
     * @see org.springframework.batch.admin.service.QuartzService#scheduleAndLaunchBatchJob(java.lang.String, java.lang.String, org.springframework.batch.core.JobParameters)
     */
    public void scheduleAndLaunchBatchJob(String jobName, String cronExpression, JobParameters jobParameters) {
        
        BatchAdminLogger.getLogger().info("The quartz batch job is scheduled"); 
    }
    
}
