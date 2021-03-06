/*
 * Copyright 2014 JBoss by Red Hat.
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

package org.jbpm.console.ng.pr.client.util;

import org.kie.api.runtime.process.ProcessInstance;

public class LogUtils {
    
    public static enum LogType {
        TECHNICAL, BUSINESS
    }    
        
    public static enum LogOrder {
        DESC, ASC
    }
    
    public static String getInstanceStatus(int state){
        String statusStr = "Unknown";
        switch (state) {
            case ProcessInstance.STATE_ACTIVE:
                statusStr = "Active";
                break;
            case ProcessInstance.STATE_ABORTED:
                statusStr = "Aborted";
                break;
            case ProcessInstance.STATE_COMPLETED:
                statusStr = "Completed";
                break;
            case ProcessInstance.STATE_PENDING:
                statusStr = "Pending";
                break;
            case ProcessInstance.STATE_SUSPENDED:
                statusStr = "Suspended";
                break;
            default:
                break;
        }
        return statusStr;
    }
}
