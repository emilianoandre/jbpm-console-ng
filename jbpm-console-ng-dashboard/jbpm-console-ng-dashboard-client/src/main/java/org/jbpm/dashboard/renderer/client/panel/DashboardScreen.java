/**
 * Copyright (C) 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.dashboard.renderer.client.panel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.IsWidget;
import org.jbpm.dashboard.renderer.client.panel.i18n.DashboardConstants;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.client.mvp.UberView;

@Dependent
@WorkbenchScreen(identifier = "DashboardScreen")
public class DashboardScreen {

    public interface View extends UberView<DashboardScreen> {
    }

    @Inject
    ProcessDashboard processDashboard;

    @Inject
    TaskDashboardPresenter taskDashboard;

    @Inject
    DashboardView view;

    @PostConstruct
    public void init() {
        this.view.init(this);
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return DashboardConstants.INSTANCE.processDashboardName();
    }

    @WorkbenchPartView
    public IsWidget getView() {
        return view;
    }

    public IsWidget getProcessDashboard() {
        return processDashboard;
    }

    public IsWidget getTaskDashboard() {
        return taskDashboard;
    }
}

