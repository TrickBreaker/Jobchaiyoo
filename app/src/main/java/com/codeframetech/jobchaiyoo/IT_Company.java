package com.codeframetech.jobchaiyoo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codeframetech.jobchaiyoo.pojo_classes.InternshipResponse;
import com.codeframetech.jobchaiyoo.pojo_classes.JobResponse;
import com.codeframetech.jobchaiyoo.pojo_classes.MeetupResponse;
import com.codeframetech.jobchaiyoo.controller.AppConfig;
import com.codeframetech.jobchaiyoo.controller.AppController;
import com.codeframetech.jobchaiyoo.detail_activities.Internship_DetailActivity;
import com.codeframetech.jobchaiyoo.detail_activities.JobDetailActivity;
import com.codeframetech.jobchaiyoo.detail_activities.MeetUps_DetailActivity;
import com.codeframetech.jobchaiyoo.helpers.Internships;
import com.codeframetech.jobchaiyoo.helpers.ItemClickListener;
import com.codeframetech.jobchaiyoo.helpers.Jobs;
import com.codeframetech.jobchaiyoo.helpers.Meetups;
import com.codeframetech.jobchaiyoo.it_company_adapters.IT_InternshipAdapter;
import com.codeframetech.jobchaiyoo.it_company_adapters.IT_JobAdapter;
import com.codeframetech.jobchaiyoo.it_company_adapters.IT_MeetupAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class IT_Company extends AppCompatActivity implements ItemClickListener {
    ArrayList<Jobs> jobsArrayList = new ArrayList<>();
    ArrayList<Internships> classArrayList = new ArrayList<>();
    ArrayList<Meetups> meetupArrayList = new ArrayList<>();
    IT_JobAdapter mjobAdapterIT;
    IT_InternshipAdapter it_classAdapter;
    IT_MeetupAdapter it_meetupAdapter;
    AppConfig appConfig;
    String company_name;
    Gson gson;
    Toolbar toolbar;
    TextView vacancy_subText, course_subText, events_subText, Title_vacancy, Title_event, Title_class;
    RecyclerView mjobRecyclerView, mclassRecyclerView, meetupRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_it__company);
        //setting Toolbar
        toolbar = (Toolbar) findViewById(R.id.teaching_activity_toolbar);
        toolbar.setTitle("LeapFrog Academy");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        // toolbar.setTitleMarginEnd(160);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Initiallizing each recycler view
        initialize_job_recyclerview();
        initialize_class_recyclerview();
        initialize_meetup_recyclerview();
        vacancy_subText = findViewById(R.id.vacancy_sub_text);
        course_subText = findViewById(R.id.course_sub_text);
        events_subText = findViewById(R.id.events_sub_text);
        Title_vacancy = findViewById(R.id.vacancy);
        Title_class = findViewById(R.id.classes);
        Title_event = findViewById(R.id.events);
        useTypeFace();

        //receving Intent Message
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String selected_company = extras.getString("LEAPFROG");

            Log.e("company_name", selected_company);
            toolbar.setTitle(selected_company);
            //instace of Appconfig class
            appConfig = new AppConfig();
            appConfig.checkLink(selected_company);

        }
        //Fetching data for each recyclerview
        fetch_jobs_Data();
        fetch_class_Data();
        fetch_meetup_Data();


    }

    public void fetch_jobs_Data() {
        StringRequest sr = new StringRequest(Request.Method.GET, appConfig.URL_JOBS, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                Log.e("Response", response);
                gson = new Gson();
                Type listType = new TypeToken<List<JobResponse>>() {
                }.getType();
                List<JobResponse> responses = gson.fromJson(response, listType);
                jobsArrayList.clear();
                mjobAdapterIT.notifyItemRangeRemoved(0, mjobAdapterIT.getItemCount());
                for (JobResponse response1 : responses) {
                    //Jobs jobs = new Jobs();
                    //jobs.setJobResponseResponse(response1);
                    //jobsArrayList.add(jobs);
                    mjobAdapterIT.addJobs(response1);
                    String job_vacancy_subText = getString(R.string.vacancy_sub_text) + " " + response1.getIT_company();
                    vacancy_subText.setText(job_vacancy_subText);
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error.Response", error.toString());


            }
        });

        AppController.getInstance().addToRequestQueue(sr);


    }


    public void fetch_class_Data() {
        StringRequest sr = new StringRequest(Request.Method.GET, appConfig.URL_INTERNSHIP, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                Log.e("Response", response);
                gson = new Gson();
                Type listType = new TypeToken<List<InternshipResponse>>() {
                }.getType();
                List<InternshipResponse> responses = gson.fromJson(response, listType);
                classArrayList.clear();
                it_classAdapter.notifyItemRangeRemoved(0, it_classAdapter.getItemCount());
                for (InternshipResponse response1 : responses) {
                    //Internships interns = new Internships();
                    //interns.setInternResponse(response1);
                    //classArrayList.add(response1);
                    it_classAdapter.addInterns(response1);
                    String class_subText = getString(R.string.class_sub_text) + " " + response1.getIT_company();
                    course_subText.setText(class_subText);

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error.Response", error.toString());


            }
        });

        AppController.getInstance().addToRequestQueue(sr);


    }

    public void fetch_meetup_Data() {
        StringRequest sr = new StringRequest(Request.Method.GET, appConfig.URL_MEETUPS, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                Log.e("Response", response);
                gson = new Gson();
                Type listType = new TypeToken<List<MeetupResponse>>() {
                }.getType();
                List<MeetupResponse> responses = gson.fromJson(response, listType);
                meetupArrayList.clear();
                it_meetupAdapter.notifyItemRangeRemoved(0, it_meetupAdapter.getItemCount());
                for (MeetupResponse response1 : responses) {
                    //  Meetups meetups = new Meetups();
                    // meetups.setMeetupsResponse(response1);
                    // meetupArrayList.add(meetups);
                    it_meetupAdapter.addMeetups(response1);
                    //change meetup name with the company name
                    String event_subText = getString(R.string.events_sub_text) + " " + response1.getIT_company();
                    events_subText.setText(event_subText);

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error.Response", error.toString());


            }
        });

        AppController.getInstance().addToRequestQueue(sr);


    }


    //initiallize job Recycler view here
    private void initialize_job_recyclerview() {
        mjobRecyclerView = (RecyclerView) findViewById(R.id.it_job_recycler);
        mjobRecyclerView.setHasFixedSize(true);
        mjobRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mjobAdapterIT = new IT_JobAdapter(this);
        mjobRecyclerView.setAdapter(mjobAdapterIT);
        mjobRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    //initiallize class Recycler view here
    private void initialize_class_recyclerview() {
        mclassRecyclerView = (RecyclerView) findViewById(R.id.it_class_recycler);
        mclassRecyclerView.setHasFixedSize(true);
        /*mRecyclerView.setItemViewCacheSize(20);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);*/
        mclassRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        it_classAdapter = new IT_InternshipAdapter(this);
        mclassRecyclerView.setAdapter(it_classAdapter);
        mclassRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    //initiallize class Recycler view here
    private void initialize_meetup_recyclerview() {
        meetupRecyclerView = (RecyclerView) findViewById(R.id.it_meetup_recycler);
        meetupRecyclerView.setHasFixedSize(true);
        /*mRecyclerView.setItemViewCacheSize(20);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);*/
        meetupRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        it_meetupAdapter = new IT_MeetupAdapter(this);
        meetupRecyclerView.setAdapter(it_meetupAdapter);
        meetupRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    //Menu option from here
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

    }


    @Override
    public void onjobItemClick(int position) {
        JobResponse selectedJob = mjobAdapterIT.getSelectedJob(position);

        Intent intent = new Intent(this, JobDetailActivity.class);
        intent.putExtra(AppConfig.REFERENCE.JOBS, selectedJob);
        this.startActivity(intent);


    }

    @Override
    public void onclassItemClick(int position) {
        InternshipResponse selectedJob = it_classAdapter.getSelectedJob(position);
        Intent intent = new Intent(this, Internship_DetailActivity.class);
        intent.putExtra(AppConfig.REFERENCE.INTERNSHIPS, selectedJob);
        this.startActivity(intent);

    }


    @Override
    public void onmeetupItemClick(int position) {
        MeetupResponse selectedMeetup = it_meetupAdapter.getSelectedMeetup(position);
        Intent intent = new Intent(this, MeetUps_DetailActivity.class);
        intent.putExtra(AppConfig.REFERENCE.MEETUPS, selectedMeetup);
        this.startActivity(intent);

    }

    private void useTypeFace() {
        final Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Regular.ttf");
        vacancy_subText.setTypeface(typeface);
        events_subText.setTypeface(typeface);
        course_subText.setTypeface(typeface);
        Title_vacancy.setTypeface(typeface);
        Title_event.setTypeface(typeface);
        Title_class.setTypeface(typeface);

    }

}
