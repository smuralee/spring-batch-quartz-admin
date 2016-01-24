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
package org.springframework.batch.admin.web.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.batch.support.PropertiesConverter;

/**
 * @author Suraj Muraleedharan
 *
 */
public final class Util {

    /**
     * <p>
     * Validates if the value is as per the regular expression
     * </p>
     *
     * @param regExp
     * @return boolean
     */
    public static boolean isValidRegExp(String regExp, String value) {

        boolean isValid = false;
        Pattern r = Pattern.compile(regExp);
        Matcher m = r.matcher(value);
        if (m.find()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * <p>
     * Returns a map with the job parameters
     * </p>
     *
     * @param jobName
     * @param params
     * @return Map<String, Object>
     */
    public static Map<String, Object> extractJobDataMap(String jobName, String params) {
        Map<String, Object> jobDataMap = new HashMap<String, Object>();
        // Adding the job name
        jobDataMap.put(Constants.JOB_NAME, jobName);

        Properties properties = PropertiesConverter.stringToProperties(params);
        for (String propertyName : properties.stringPropertyNames()) {
            jobDataMap.put(propertyName, properties.getProperty(propertyName));
        }

        return jobDataMap;
    }

    /**
     *
     * <p>
     * Returns the jobName appended by the trigger name suffix
     * </p>
     *
     * @param jobName
     * @return
     */
    public static String getTriggerName(String jobName) {
        StringBuffer sb = new StringBuffer();
        sb.append(jobName);
        sb.append(Constants.TRIGGER_SUFFIX);
        return sb.toString();
    }

}
