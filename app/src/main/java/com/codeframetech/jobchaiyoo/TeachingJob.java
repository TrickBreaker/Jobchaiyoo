package com.codeframetech.jobchaiyoo;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codeframetech.jobchaiyoo.pojo_classes.TeachingResponse;
import com.codeframetech.jobchaiyoo.controller.AppConfig;
import com.codeframetech.jobchaiyoo.controller.AppController;
import com.codeframetech.jobchaiyoo.helpers.Teachings;
import com.codeframetech.jobchaiyoo.teaching_recyclerview.TeachingAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TeachingJob extends AppCompatActivity implements SearchView.OnQueryTextListener {

    Toolbar toolbar;
    AppConfig appConfig;
    Gson gson;
    RecyclerView rv;
    TeachingAdapter teachingAdapter;
    private ArrayList<Teachings> teachingArrayList = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    CoordinatorLayout mylayout;
    FloatingActionButton fabNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teaching_job);
        //initillize toolbar,coordinate layout,floatingbutton,recyclerview etc
        allInitializer();
        //receving Intent Message
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String selected_faculty = extras.getString("TEACHING");

            Log.e("school_college_name", selected_faculty);
            //instace of Appconfig class
            appConfig = new AppConfig();
            appConfig.checkLink(selected_faculty);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        teachingAdapter.setFilter(teachingArrayList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
        return true;
    }


    public void fetchDownloads() {
        StringRequest sr = new StringRequest(Request.Method.GET, appConfig.URL_JOBS, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                Log.e("Response", response);
                gson = new Gson();
                Type listType = new TypeToken<List<TeachingResponse>>() {
                }.getType();
                List<TeachingResponse> responses = gson.fromJson(response, listType);
                //sorting list albhabetically
                shortListData_Alphabetically(responses);
                teachingArrayList.clear();
                teachingAdapter.notifyItemRangeRemoved(0, teachingAdapter.getItemCount());
                for (TeachingResponse response1 : responses) {
                    Teachings teachings = new Teachings();
                    teachings.setJobResponseResponse(response1);
                    teachingArrayList.add(teachings);
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    teachingAdapter.notifyDataSetChanged();

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error.Response", error.toString());
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                //Toast.makeText(getActivity(), "check your Internet connection", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar.make(mylayout, "No Internet Connection", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fetchDownloads();
                    }
                });
                snackbar.show();


            }
        });

        AppController.getInstance().addToRequestQueue(sr);


    }

    private void shortListData_Alphabetically(List<TeachingResponse> responses) {
        Collections.sort(responses, new Comparator<TeachingResponse>() {

            @Override
            public int compare(TeachingResponse teachingResponse1, TeachingResponse teachingResponse2) {
                return teachingResponse1.getName().compareTo(teachingResponse2.getName());
            }
        });
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Teachings> filteredModelList = filter(teachingArrayList, newText);

        teachingAdapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Teachings> filter(List<Teachings> models, String query) {
        query = query.toLowerCase();
        final List<Teachings> filteredModelList = new ArrayList<>();
        for (Teachings model : models) {
            final String text = model.getTeachingResponse().getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public void allInitializer() {
        toolbar = (Toolbar) findViewById(R.id.teaching_activity_toolbar);
        toolbar.setTitle("Teaching Jobs");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        appConfig = new AppConfig();
        fabNotification = (FloatingActionButton) findViewById(R.id.fab_notification);
        fabNotification.setVisibility(View.INVISIBLE);
        //Reference to Recyclerview
        mylayout = (CoordinatorLayout) findViewById(R.id.fab_layout);
        rv = (RecyclerView) findViewById(R.id.teaching_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        teachingAdapter = new TeachingAdapter(rv, teachingArrayList, this);
        rv.setAdapter(teachingAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.teaching_Refresh);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.green), ContextCompat.getColor(this, R.color.orange), ContextCompat.getColor(this, R.color.blue));
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchDownloads();
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }


            }
        }, 0);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                fetchDownloads();

            }
        });
    }

}
