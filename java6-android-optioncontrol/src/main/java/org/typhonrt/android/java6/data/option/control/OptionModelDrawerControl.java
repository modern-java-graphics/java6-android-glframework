/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.typhonrt.android.java6.data.option.control;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import org.typhonrt.java6.data.option.model.IOptionModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * OptionModelDrawerControl
 */
public final class OptionModelDrawerControl
{
   private OptionModelAdapter optionListAdapter;
   private List<IOptionModel> optionModelList;

   private AtomicBoolean      updateNotify;

   private ListView           drawerListView;
   private View               headerView;

   public OptionModelDrawerControl(Activity activity)
   {
      optionModelList = new ArrayList<IOptionModel>();
      updateNotify = new AtomicBoolean();

      DrawerLayout drawerLayout = (DrawerLayout)activity.findViewById(R.id.drawer_layout);

      drawerListView = (ListView)activity.findViewById(R.id.left_drawer);

      Resources resources = activity.getResources();

      drawerLayout.setScrimColor(resources.getColor(R.color.drawer_scrim_color));

      // Handle ActionBarDrawerToggle

      ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout,
       (Toolbar)activity.findViewById(R.id.toolbar), R.string.drawer_open, R.string.drawer_close);

      actionBarDrawerToggle.syncState();

      // Handle different Drawer States :D
      drawerLayout.setDrawerListener(actionBarDrawerToggle);

      // Setup header views
      LayoutInflater inflater = activity.getLayoutInflater();
      headerView = inflater.inflate(R.layout.drawer_headerview, null);

      // Set the adapter for the list view
      drawerListView.setAdapter(optionListAdapter = new OptionModelAdapter(activity, optionModelList, updateNotify));
   }

   public boolean isOptionModelDirty()
   {
      return updateNotify.compareAndSet(true, false);
   }

   public void setOptionModels(IOptionModel model[])
   {
      optionModelList.clear();

      int modelLength = 0;

      if (model != null)
      {
         optionModelList.addAll(Arrays.asList(model));
         modelLength = model.length;
      }

      // Set header view
      if (modelLength > 0)
      {
         drawerListView.addHeaderView(headerView, null, false);
         drawerListView.setBackgroundResource(R.drawable.drawer_dropshadow);
      }
      else
      {
         drawerListView.removeHeaderView(headerView);
         drawerListView.setBackground(null);
      }

      optionListAdapter.notifyDataSetChanged();
   }
}

