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
import java.util.HashMap;
import java.util.Map;

/**
 * @author Suraj Muraleedharan
 *
 */
public class BatchJobDataStore implements Serializable {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Stores the key value pair for the jobs
     */
    private Map<String, Map<String, Object>> jobDataMapStore = new HashMap<String, Map<String, Object>>();

    /**
     * @return the jobDataMapStore
     */
    public Map<String, Map<String, Object>> getJobDataMapStore() {
        return jobDataMapStore;
    }

    /**
     * @param jobDataMapStore the jobDataMapStore to set
     */
    public void setJobDataMapStore(Map<String, Map<String, Object>> jobDataMapStore) {
        this.jobDataMapStore = jobDataMapStore;
    }

    /**
     * Default constructor
     */
    public BatchJobDataStore() {
        super();
    }

    /**
     * @param jobDataMapStore
     */
    public BatchJobDataStore(Map<String, Map<String, Object>> jobDataMapStore) {
        super();
        this.jobDataMapStore = jobDataMapStore;
    }

}
