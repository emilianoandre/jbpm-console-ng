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
package org.jbpm.console.ng.pr.client.editors.instance.list.variables.dash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwtmockito.GwtMockitoTestRunner;
import org.jbpm.console.ng.df.client.list.base.DataSetEditorManager;
import org.jbpm.console.ng.gc.client.experimental.grid.base.ExtendedPagedTable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.uberfire.ext.services.shared.preferences.GridColumnPreference;
import org.uberfire.ext.services.shared.preferences.GridGlobalPreferences;
import org.uberfire.ext.services.shared.preferences.GridPreferencesStore;
import org.uberfire.ext.services.shared.preferences.MultiGridPreferencesStore;
import org.uberfire.ext.widgets.common.client.tables.ColumnMeta;
import org.uberfire.ext.widgets.common.client.tables.FilterPagedTable;
import org.uberfire.mvp.Command;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith( GwtMockitoTestRunner.class )
public class DataSetProcessInstanceWithVariablesListViewTest {

    @Mock
    protected ExtendedPagedTable currentListGrid;

    @Mock
    protected GridPreferencesStore gridPreferencesStore;

    @Mock
    protected DataSetEditorManager dataSetEditorManager;

    @Mock
    protected MultiGridPreferencesStore multiGridPreferencesStore;

    @Mock
    protected FilterPagedTable filterPagedTable;

    @Mock
    protected DataSetProcessInstanceWithVariablesListPresenter presenter;

    private ProcessInstancesWithVariableListViewExtension view;

    @Before
    public void setupMocks() {

        view = new ProcessInstancesWithVariableListViewExtension();
        view.setUpMocks(currentListGrid, filterPagedTable, dataSetEditorManager, presenter);

    }

        @Test
    public void testDataStoreNameIsSet() {
        when (gridPreferencesStore.getColumnPreferences()).thenReturn(new ArrayList<GridColumnPreference>());
        when(currentListGrid.getGridPreferencesStore()).thenReturn(gridPreferencesStore);
        doAnswer( new Answer() {
            @Override
            public Void answer( InvocationOnMock invocationOnMock ) throws Throwable {
                final List<ColumnMeta> columns = (List<ColumnMeta>) invocationOnMock.getArguments()[ 0 ];
                for ( ColumnMeta columnMeta : columns ) {
                    assertNotNull( columnMeta.getColumn().getDataStoreName() );
                }
                return null;
            }
        } ).when(currentListGrid).addColumns(anyList());


        view.initColumns( currentListGrid );

        verify( currentListGrid ).addColumns( anyList() );
    }

    @Test
    public void testInitDefaultFilters() {
        when(filterPagedTable.getMultiGridPreferencesStore()).thenReturn(multiGridPreferencesStore);

        view.initDefaultFilters(new GridGlobalPreferences( "testGrid", new ArrayList<String>(),new ArrayList<String>()), null);

        verify(filterPagedTable,times(3)).addTab((ExtendedPagedTable) any(), anyString(), (Command) any());
        verify(filterPagedTable,times(3)).saveNewTabSettings(anyString(), (HashMap) any());
    }

}
