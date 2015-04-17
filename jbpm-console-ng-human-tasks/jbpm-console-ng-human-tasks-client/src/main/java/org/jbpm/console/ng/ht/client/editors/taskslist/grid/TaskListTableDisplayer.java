/*
 * Copyright 2015 JBoss by Red Hat.
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
package org.jbpm.console.ng.ht.client.editors.taskslist.grid;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import org.dashbuilder.dataset.DataSetLookupConstraints;
import org.dashbuilder.displayer.DisplayerAttributeGroupDef;
import org.dashbuilder.displayer.DisplayerConstraints;
import org.jboss.errai.security.shared.api.identity.User;
import org.jbpm.console.ng.gc.client.displayer.BaseTableDisplayer;
import org.jbpm.console.ng.gc.client.displayer.TableSettings;
import org.jbpm.console.ng.gc.client.displayer.TableSettingsBuilder;
import org.jbpm.console.ng.ht.client.editors.quicknewtask.QuickNewTaskPopup;
import org.jbpm.console.ng.ht.client.i18n.Constants;
import org.jbpm.console.ng.ht.model.events.TaskSelectionEvent;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.mvp.PlaceStatus;
import org.uberfire.ext.widgets.common.client.tables.PagedTable;

import static org.dashbuilder.dataset.filter.FilterFactory.*;

/**
 * Task list table displayer
 */
@Dependent
public class TaskListTableDisplayer extends BaseTableDisplayer {

    public static final String COLUMN_ID = "taskId";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCR = "description";
    public static final String COLUMN_OWNER = "actualOwner";
    public static final String COLUMN_CREATION = "createdOn";
    public static final String COLUMN_STATUS = "status";

    @Inject
    protected User identity;

    @Inject
    protected PlaceManager placeManager;

    @Inject
    protected Event<TaskSelectionEvent> taskSelectedEvent;

    @Inject
    private QuickNewTaskPopup quickNewTaskPopup;

    private final Constants constants = GWT.create(Constants.class);

    protected int selectedRow = -1;

    @PostConstruct
    protected void init() {
        buildDefaultTables();
        loadUserTables();
    }

    protected void buildDefaultTables() {

        // All
        super.addTableSettings("All", false,
                (TableSettings) TableSettingsBuilder.init()
                        .dataset("jbpmHumanTasks")
                        .column(COLUMN_ID).format(constants.Id())
                        .column(COLUMN_NAME).format(constants.Task())
                        .column(COLUMN_OWNER).format("Owner")
                        .column(COLUMN_CREATION).format("Created on", "MMM dd E, yyyy")
                        .column(COLUMN_STATUS).format(constants.Status())
                        .column(COLUMN_DESCR).format(constants.Description())
                        .tableWidth(1000)
                        .buildSettings());

        // Active
        super.addTableSettings("Active", false,
                (TableSettings) TableSettingsBuilder.init()
                        .dataset("jbpmHumanTasks")
                        .filter(COLUMN_OWNER, equalsTo(identity.getIdentifier()))
                        .column(COLUMN_ID).format(constants.Id())
                        .column(COLUMN_NAME).format(constants.Task())
                        .column(COLUMN_OWNER).format("Owner")
                        .column(COLUMN_CREATION).format("Created on", "MMM dd E, yyyy")
                        .column(COLUMN_STATUS).format(constants.Status()).expression("value.toUpperCase()")
                        .column(COLUMN_DESCR).format(constants.Description())
                        .tableWidth(1000)
                        .buildSettings());
    }

    // TODO: load & register tableSettings defined by user
    protected void loadUserTables() {

        // Mock up example
        super.addTableSettings("Custom", true,
                (TableSettings) TableSettingsBuilder.init()
                        .dataset("jbpmHumanTasks")
                        .filter(COLUMN_CREATION, timeFrame("begin[year march] till now +1day"))
                        .filter(COLUMN_OWNER, equalsTo(identity.getIdentifier()))
                        .column(COLUMN_ID).format(constants.Id())
                        .column(COLUMN_NAME).format(constants.Task())
                        .column(COLUMN_OWNER).format("Owner")
                        .column(COLUMN_CREATION).format("Created on", "MMM dd E, yyyy")
                        .column(COLUMN_STATUS).format(constants.Status())
                        .expression("value.toUpperCase().substring(0,3)")
                        .tableWidth(800)
                        .buildSettings());
    }

    // TableDisplayer overrides

    @Override
    public DisplayerConstraints createDisplayerConstraints() {

        DataSetLookupConstraints lookupConstraints = new DataSetLookupConstraints()
                .setGroupAllowed(false)
                .setMaxColumns(-1)
                .setMinColumns(1)
                .setExtraColumnsAllowed(true)
                .setColumnsTitle("Columns");

        return new DisplayerConstraints(lookupConstraints)
                .supportsAttribute(DisplayerAttributeGroupDef.GENERAL_GROUP)
                .supportsAttribute(DisplayerAttributeGroupDef.COLUMNS_GROUP)
                .supportsAttribute(DisplayerAttributeGroupDef.FILTER_GROUP)
                .supportsAttribute(DisplayerAttributeGroupDef.REFRESH_GROUP)
                .supportsAttribute(DisplayerAttributeGroupDef.TABLE_GROUP);
    }

    @Override
    protected PagedTable<Integer> createTable() {
        PagedTable<Integer> pagedTable = super.createTable();
        Button newTaskButton = new Button();
        newTaskButton.setTitle(constants.New_Task());
        newTaskButton.setIcon( IconType.PLUS_SIGN );
        newTaskButton.setTitle( Constants.INSTANCE.New_Task() );
        newTaskButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                quickNewTaskPopup.show();
            }
        });
        pagedTable.getLeftToolbar().add(newTaskButton);
        return pagedTable;
    }

    @Override
    protected void onCellSelected(String columnId, boolean selectable, int rowIndex) {
        super.onCellSelected(columnId, selectable, rowIndex);

        String missingColumn = missingColumn(COLUMN_ID, COLUMN_NAME, COLUMN_STATUS);
        if (missingColumn != null) {
            GWT.log("A mandatory data set column is missing: " + missingColumn);
            return;
        }

        Long taskId = Long.parseLong(dataSet.getValueAt(rowIndex, COLUMN_ID).toString());
        String taskName = (String) dataSet.getValueAt(rowIndex, COLUMN_NAME);
        String taskStatus = (String) dataSet.getValueAt(rowIndex, COLUMN_STATUS);

        // TODO: read from data set
        boolean logOnly = false;
        boolean forAdmin = false;
        //boolean logOnly = Boolean.parseBoolean((String) dataSet.getValueAt(rowIndex, COLUMN_LOG_ONLY));
        //boolean forAdmin = Boolean.parseBoolean((String) dataSet.getValueAt(rowIndex, COLUMN_FOR_ADMIN));
        PlaceStatus status = placeManager.getStatus("Task Details Multi");
        if (taskStatus.equals("Completed") && logOnly) logOnly = true;

        boolean close = false;
        if (selectedRow == -1) {
            selectedRow = rowIndex;
            //listGrid.setRowStyles(selectedStyles);
            //listGrid.redraw();
        } else if (rowIndex != selectedRow) {
            selectedRow = rowIndex;
            //listGrid.setRowStyles(selectedStyles);
            //listGrid.redraw();
        } else {
            close = true;
        }

        if (status == PlaceStatus.CLOSE) {
            placeManager.goTo("Task Details Multi");
            taskSelectedEvent.fire(new TaskSelectionEvent(taskId, taskName, forAdmin, logOnly));
        } else if (status == PlaceStatus.OPEN && !close) {
            taskSelectedEvent.fire(new TaskSelectionEvent(taskId, taskName, forAdmin, logOnly));
        } else if (status == PlaceStatus.OPEN && close) {
            placeManager.closePlace("Task Details Multi");
        }
    }
}
