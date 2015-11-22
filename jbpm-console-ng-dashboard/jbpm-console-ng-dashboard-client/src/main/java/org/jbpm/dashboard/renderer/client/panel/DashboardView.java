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

import javax.enterprise.context.Dependent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.TabPane;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
public class DashboardView extends Composite implements DashboardScreen.View {

    interface Binder extends UiBinder<Widget, DashboardView> {}
    private static Binder uiBinder = GWT.create(Binder.class);

    @UiField
    TabPane processesPane;

    @UiField
    TabPane tasksPane;

    DashboardScreen presenter;

    public void init(DashboardScreen presenter) {
        initWidget(uiBinder.createAndBindUi(this));
        this.presenter = checkNotNull( "presenter", presenter );
        processesPane.add(presenter.getProcessDashboard());
        tasksPane.add(presenter.getTaskDashboard());
    }
}