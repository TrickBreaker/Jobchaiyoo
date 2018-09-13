package com.codeframetech.jobchaiyoo.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeframetech.jobchaiyoo.R;
import com.codeframetech.jobchaiyoo.pojo_classes.InternshipResponse;
import com.codeframetech.jobchaiyoo.adapters.Internship_Adapter;
import com.codeframetech.jobchaiyoo.constants_store_room.STORE_URL;
import com.codeframetech.jobchaiyoo.controller.AppConfig;
import com.codeframetech.jobchaiyoo.controller.NetworkCheckClass;
import com.codeframetech.jobchaiyoo.database.DatabaseOperation;
import com.codeframetech.jobchaiyoo.detail_activities.Internship_DetailActivity;
import com.codeframetech.jobchaiyoo.helpers.ItemClickListener;
import com.codeframetech.jobchaiyoo.sorting.SortClassData;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Digition on 12/14/2017.
 */

public class ClassFragment extends Fragment implements SearchView.OnQueryTextListener, ItemClickListener {
    SwipeRefreshLayout mSwipeRefreshLayout;
    Gson gson;
    //private JobResponse myJobResponse  = new JobResponse();
    private List<InternshipResponse> minternsArrayList = new ArrayList<>();
    private Internship_Adapter minternship_Adapter;
    private FloatingActionButton fabNotification;
    private CoordinatorLayout coordinate_layout;
    private AppConfig appconfig;
    private DatabaseOperation mDatabase;
    private RecyclerView mRecyclerView;
    private ImageView alert_btn;
    private TextView alert_txt;
    private TabLayout setting_tablayout;
    private long mLastClickTime=0;
    private static final long MAX_CLICK_INTERVAL=7000;
    AdView mAdview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // mSwipeRefreshLayout.setRefreshing(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.internship_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.intern_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        minternship_Adapter = new Internship_Adapter(this);
        mRecyclerView.setAdapter(minternship_Adapter);
        mAdview=view.findViewById(R.id.adView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.internship_swipeRefresh);
        fabNotification = (FloatingActionButton) view.findViewById(R.id.fab_notification);
        coordinate_layout = (CoordinatorLayout) view.findViewById(R.id.fab_layout);
        setting_tablayout = (TabLayout) view.findViewById(R.id.class_bottom_tab);
        alert_btn = (ImageView) view.findViewById(R.id.alert_btn);
        alert_txt = (TextView) view.findViewById(R.id.alert_txt);
        fabNotification.setVisibility(View.INVISIBLE);
        setting_tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setting_tablayout.addTab(setting_tablayout.newTab().setIcon(R.drawable.ic_action_sort).setText(STORE_URL.ConstantStrings.INSTITUTE_NAME));
        setting_tablayout.addTab(setting_tablayout.newTab().setIcon(R.drawable.ic_action_refresh).setText("Refresh"));
        setting_tablayout.addOnTabSelectedListener(listener());
        setting_tablayout.setSelectedTabIndicatorHeight(0);
        alert_btn.setVisibility(View.GONE);
        alert_txt.setVisibility(View.GONE);
        MobileAds.initialize(getActivity(), "ca-app-pub-6771214991849242~6925683817");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        appconfig = new AppConfig();
        mDatabase = new DatabaseOperation(getContext());
        //call asynctask here
        new ShowProgress().execute();

        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.green), ContextCompat.getColor(getContext(), R.color.orange), ContextCompat.getColor(getContext(), R.color.blue));



        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // mSwipeRefreshLayout.setRefreshing(false);
                new ShowProgress().execute();

            }
        });

        return view;
    }

    private TabLayout.OnTabSelectedListener listener() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        setting_tablayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.top_meetup_appbar_layout));
                        String sortby_name = tab.getText().toString();
                        if (sortby_name.equals(STORE_URL.ConstantStrings.INSTITUTE_NAME)) {
                            getQueryForSorting(minternsArrayList,sortby_name);
                            tab.setText(STORE_URL.ConstantStrings.SUBJECT);



                        } else {
                            tab.setText(STORE_URL.ConstantStrings.INSTITUTE_NAME);
                            getQueryForSorting(minternsArrayList,sortby_name);

                        }
                        break;
                    case 1:

                        if(SystemClock.elapsedRealtime() -mLastClickTime >MAX_CLICK_INTERVAL){
                            mSwipeRefreshLayout.setRefreshing(true);
                            new ShowProgress().execute();

                        }
                        mLastClickTime=SystemClock.elapsedRealtime();



                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:

                        String sortby_name = tab.getText().toString();
                        if (sortby_name.equals(STORE_URL.ConstantStrings.INSTITUTE_NAME)) {
                            getQueryForSorting(minternsArrayList,sortby_name);
                            tab.setText(STORE_URL.ConstantStrings.SUBJECT);


                        } else {
                            tab.setText(STORE_URL.ConstantStrings.INSTITUTE_NAME);
                            getQueryForSorting(minternsArrayList,sortby_name);

                        }
                        break;
                    case 1:

                        if(SystemClock.elapsedRealtime()-mLastClickTime > MAX_CLICK_INTERVAL){
                            mSwipeRefreshLayout.setRefreshing(true);
                            new ShowProgress().execute();

                        }
                        mLastClickTime=SystemClock.elapsedRealtime();


                }


            }
        };

    }


    @Override
    public String toString() {
        return "Class";

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.navigation, menu);
        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Institute name,  subject, address");

        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        //Do something when collapsed
                        minternship_Adapter.clearInternList();
                        minternship_Adapter.setFilter(minternsArrayList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        minternship_Adapter.setFilter(new ShowProgress().fetchDataFromDatabase());
                        return true; // Return true to expand action view
                    }
                });

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<InternshipResponse> filteredModelList = filter(minternsArrayList, newText);
        minternship_Adapter.clearInternList();
        minternship_Adapter.setFilter(filteredModelList);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public void getQueryForSorting(List<InternshipResponse> responses, String query) {
        //sorting list
        if (query.equals(STORE_URL.ConstantStrings.INSTITUTE_NAME)) {
            minternship_Adapter.setFilter(new SortClassData().returnSortedClassByInstitute(responses));
        }
        else{
            minternship_Adapter.setFilter(new SortClassData().returnSortedJobsBySubject(responses));
        }
    }

    private List<InternshipResponse> filter(List<InternshipResponse> models, String query) {
        query = query.toLowerCase();
        final List<InternshipResponse> filteredModelList = new ArrayList<>();
        for (InternshipResponse model : models) {
            final String institute_name = model.getCompany_name().toLowerCase();
            final String location = model.getLocation().toLowerCase();
            final String subject = model.getSubject_name().toLowerCase();
            if (institute_name.contains(query) || subject.contains(query) || location.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    @Override
    public void onjobItemClick(int position) {

    }

    @Override
    public void onclassItemClick(int position) {
        InternshipResponse selectedJob = minternship_Adapter.getSelectedJob(position);
        Intent intent = new Intent(getActivity(), Internship_DetailActivity.class);
        intent.putExtra(AppConfig.REFERENCE.INTERNSHIPS, selectedJob);
        getActivity().startActivity(intent);

    }

    @Override
    public void onmeetupItemClick(int position) {

    }

    //Async task to load data and display Circular Spinner progress bar
    private class ShowProgress extends AsyncTask<Void, Void, List<InternshipResponse>> {
        SpinnerFragment mSpinnerFragment;

        @Override
        protected void onPreExecute() {
            mSpinnerFragment = new SpinnerFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_class_container, mSpinnerFragment).commit();

        }


        @Override
        protected List<InternshipResponse> doInBackground(Void... params) {

            try {
                if (new NetworkCheckClass().checkConnection(getContext())) {
                    return fetchDataFromServer();
                } else {
                    return fetchDataFromDatabase();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;

        }


        @Override
        protected void onPostExecute(List<InternshipResponse> internResponse) {
            if (internResponse != null) {
                minternship_Adapter.clearInternList();
                minternsArrayList.clear();
                minternship_Adapter.notifyItemRangeRemoved(0, minternship_Adapter.getItemCount());
                for (InternshipResponse response : internResponse) {
                    minternsArrayList.add(response);
                    minternship_Adapter.addInterns(response);
                    //method to delete and add data in sqlite database
                    mDatabase.deleteClassData(response);
                    mDatabase.addClassData(response);

                    mSwipeRefreshLayout.setRefreshing(false);
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getFragmentManager().beginTransaction();
                    fragmentTransaction.remove(mSpinnerFragment).commitAllowingStateLoss();


                }

            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.remove(mSpinnerFragment).commitAllowingStateLoss();

                //Toast.makeText(getActivity(), "check your Internet connection", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar.make(coordinate_layout, "No Internet Connection", Snackbar.LENGTH_LONG);
                snackbar.show();
                alert_btn.setVisibility(View.VISIBLE);
                alert_txt.setVisibility(View.VISIBLE);
                ;

            }
        }


        //fetch data from SQlite Database

        private List<InternshipResponse> fetchDataFromDatabase() {
            //mDatabase= new DatabaseOperation(getActivity());
            try {
                List<InternshipResponse> offlineResponse= mDatabase.getClassData();
               return offlineResponse;
            } catch (Exception ex) {
                ex.printStackTrace();

            }
            return null;

        }


        /*fetch data list from server using GSON*/
        private List<InternshipResponse> fetchDataFromServer() {
            // StringRequest sr = new StringRequest(Request.Method.GET, appconfig.URL_JOBS, new Response.Listener<String>() {
            URL url = null;
            try {
                Log.d("FETCH FROM SERVER", "call rest service to get json response");
                url = new URL(appconfig.URL_INTERNSHIP);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.setConnectTimeout(4000);
                connection.setReadTimeout(4000);
                connection.connect();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                //pass buffered reader to convert json to java object using gson
                Log.d("BUFFER", bufferedReader.toString());
                return convertJsonToObject(bufferedReader);

            } catch (Exception e) {
                Log.e("FETCH FROM SERVER", "error in getting and parsing response");
            }
            return null;
        }

        private List<InternshipResponse> convertJsonToObject(BufferedReader bufferedReader) {
            //instantiate Gson
            gson = new Gson();
            Type listType = new TypeToken<ArrayList<InternshipResponse>>() {
            }.getType();

            //pass root element type to fromJson method along with input stream
            // Jobs jobsWrapper = gson.fromJson(bufferedReader, Jobs.class);
            List<InternshipResponse> joResponseList = gson.fromJson(bufferedReader, listType);
            Log.e("FETCH DATA FROM SERVER", "number of coupons from json response after gson parsing" + joResponseList.size());
            return joResponseList;
        }

    }


}

