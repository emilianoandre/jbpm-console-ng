/*
 * Copyright 2012 JBoss by Red Hat.
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
package org.jbpm.console.ng.pr.admin.backend.server;

import javax.annotation.PostConstruct;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.bus.server.annotations.Service;

import org.jbpm.console.ng.pr.admin.service.ProcessServiceAdminEntryPoint;
import org.jbpm.services.api.ProcessService;


@Service
@ApplicationScoped
public class ProcessServiceAdminEntryPointImpl implements ProcessServiceAdminEntryPoint {

    @Inject
    private ProcessService processService;

    @PostConstruct
    public void init(){
    }

    @Override
    public void generateMockInstances(String deploymentId, String processId, int amountOfInstances) {
        for (int i = 0; i < amountOfInstances; i++) {
            processService.startProcess( deploymentId, processId );

        }
    }
    
    

   

}
