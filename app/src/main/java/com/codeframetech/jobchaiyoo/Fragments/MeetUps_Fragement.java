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
import com.codeframetech.jobchaiyoo.pojo_classes.MeetupResponse;
import com.codeframetech.jobchaiyoo.adapters.Meetup_adapter;
import com.codeframetech.jobchaiyoo.constants_store_room.STORE_URL;
import com.codeframetech.jobchaiyoo.controller.AppConfig;
import com.codeframetech.jobchaiyoo.controller.NetworkCheckClass;
import com.codeframetech.jobchaiyoo.database.DatabaseOperation;
import com.codeframetech.jobchaiyoo.detail_activities.MeetUps_DetailActivity;
import com.codeframetech.jobchaiyoo.helpers.ItemClickListener;
import com.codeframetech.jobchaiyoo.sorting.SortEventsData;
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
 * Created by Digition on 3/2/2017.
 */


public class MeetUps_Fragement extends Fragment implements SearchView.OnQueryTextListener, ItemClickListener {
    Gson gson;
    private ArrayList<MeetupResponse> meetupsArrayList = new ArrayList<>();
    private Meetup_adapter meetup_adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton fabNotification;
    private CoordinatorLayout mylayout;
    private AppConfig appConfig;
    private DatabaseOperation mDatabase;
    private RecyclerView mRecyclerView;
    private ImageView alert_btn;
    private TextView alert_txt;
    private long mLastClickTime=0;
    private static final long MAX_CLICK_INTERVAL=5000;
    private TabLayout setting_tablayout;
    AdView mAdView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.meetup_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.meetup_recycler_view);
        mSwipeRefreshLayout = view.findViewById(R.id.meetup_swipeRefresh);
        mylayout = view.findViewById(R.id.meetup_fragment);
        alert_btn =  view.findViewById(R.id.alert_btn);
        alert_txt = view.findViewById(R.id.alert_txt);
        meetup_adapter = new Meetup_adapter(this);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setAdapter(meetup_adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdView = view.findViewById(R.id.adView);
        setting_tablayout = view.findViewById(R.id.meetup_bottom_tab);
        fabNotification = view.findViewById(R.id.fab_notification);
        fabNotification.setVisibility(View.INVISIBLE);
        setting_tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setting_tablayout.addTab(setting_tablayout.newTab().setIcon(R.drawable.ic_action_sort).setText("Sort by Name"));
        setting_tablayout.addTab(setting_tablayout.newTab().setIcon(R.drawable.ic_action_refresh).setText("Refresh"));
        setting_tablayout.addOnTabSelectedListener(listener());
        setting_tablayout.setSelectedTabIndicatorHeight(0);
        alert_btn.setVisibility(View.GONE);
        alert_txt.setVisibility(View.GONE);
        MobileAds.initialize(getActivity(), "ca-app-pub-6771214991849242~6925683817");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        appConfig = new AppConfig();
        mDatabase = new DatabaseOperation(getActivity());
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
                        String sortby_name = tab.getText().toString();
                        if (sortby_name.equals(STORE_URL.ConstantStrings.NAME)) {
                            getQueryForSorting(meetupsArrayList, sortby_name);
                            tab.setText(STORE_URL.ConstantStrings.PLACE);


                        } else {
                            tab.setText(STORE_URL.ConstantStrings.NAME);
                            getQueryForSorting(meetupsArrayList, sortby_name);

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
                        if (sortby_name.equals(STORE_URL.ConstantStrings.NAME)) {
                            getQueryForSorting(meetupsArrayList, sortby_name);
                            tab.setText(STORE_URL.ConstantStrings.PLACE);


                        } else {
                            tab.setText(STORE_URL.ConstantStrings.NAME);
                            getQueryForSorting(meetupsArrayList, sortby_name);
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
        };

    }


    @Override
    public String toString() {
        return "Events";

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
        searchView.setQueryHint("Events name, location, date, org");
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        meetup_adapter.clearMeetupList();
                        meetup_adapter.setFilter(meetupsArrayList);
                        return true;
                        // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        meetup_adapter.setFilter(new ShowProgress().fetchDataFromDatabase());
                        return true;
                        // Return true to expand action view
                    }
                });

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<MeetupResponse> filteredModelList = filter(meetupsArrayList, newText);
        meetup_adapter.clearMeetupList();
        meetup_adapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //Returns the  List of searched data
    private List<MeetupResponse> filter(List<MeetupResponse> models, String query) {
        query = query.toLowerCase();
        final List<MeetupResponse> filteredModelList = new ArrayList<>();
        for (MeetupResponse model : models) {
            final String event_name = model.getMeetup_name().toLowerCase();
            final String date = model.getDate_in_word().toLowerCase();
            final String location = model.getLocation().toLowerCase();
            final String organizer = model.getOrganizer_name().toLowerCase();
            if (event_name.contains(query) || date.contains(query) || location.contains(query) || organizer.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    //

    public void getQueryForSorting(List<MeetupResponse> responses, String query) {
        //sorting list
        if (query.equals(STORE_URL.ConstantStrings.NAME)) {
            meetup_adapter.setFilter(new SortEventsData().returnSortedEventsByName(responses));
        } else {
            meetup_adapter.setFilter(new SortEventsData().returnSortedEventsByLocation(responses));
        }
    }



    @Override
    public void onjobItemClick(int position) {

    }

    @Override
    public void onclassItemClick(int position) {

    }

    @Override
    public void onmeetupItemClick(int position) {
        MeetupResponse selectedMeetup = meetup_adapter.getSelectedMeetup(position);
        Intent intent = new Intent(getActivity(), MeetUps_DetailActivity.class);
        intent.putExtra(AppConfig.REFERENCE.MEETUPS, selectedMeetup);
        getActivity().startActivity(intent);

    }


    //Async task to load data and display Circular Spinner progress bar
    private class ShowProgress extends AsyncTask<Void, Void, List<MeetupResponse>> {
        SpinnerFragment mSpinnerFragment;

        @Override
        protected void onPreExecute() {


            mSpinnerFragment = new SpinnerFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, mSpinnerFragment).commit();

        }


        @Override
        protected List<MeetupResponse> doInBackground(Void... params) {

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
        protected void onPostExecute(List<MeetupResponse> meetupResponse) {
            if (meetupResponse != null) {
                meetup_adapter.clearMeetupList();
                meetupsArrayList.clear();
                meetup_adapter.notifyItemRangeRemoved(0, meetup_adapter.getItemCount());
                for (MeetupResponse response : meetupResponse) {
                    meetupsArrayList.add(response);
                    meetup_adapter.addMeetups(response);
                    mDatabase.deleteMeetupData(response);
                    mDatabase.addMeetupData(response);
                    //mjobAdapter.notifyDataSetChanged();
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
                Snackbar snackbar = Snackbar.make(mylayout, "No Internet Connection", Snackbar.LENGTH_LONG);
                snackbar.show();
                alert_btn.setVisibility(View.VISIBLE);
                alert_txt.setVisibility(View.VISIBLE);

            }
        }


        //fetch data from SQlite Database

        private List<MeetupResponse> fetchDataFromDatabase() {
            //mDatabase= new DatabaseOperation(getActivity());
            try {
                // getQueryForSorting(offlineResponse, 0);
                return mDatabase.getMeetupData();

            } catch (Exception ex) {
                ex.printStackTrace();

            }
            return null;

        }


        /*fetch data list from server using GSON*/
        private List<MeetupResponse> fetchDataFromServer() {

            // StringRequest sr = new StringRequest(Request.Method.GET, appconfig.URL_JOBS, new Response.Listener<String>() {
            URL url = null;
            try {
                Log.d("FETCH FROM SERVER", "call rest service to get json response");
                url = new URL(appConfig.URL_MEETUPS);
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

        private List<MeetupResponse> convertJsonToObject(BufferedReader bufferedReader) {
            //instantiate Gson
            gson = new Gson();
            Type listType = new TypeToken<ArrayList<MeetupResponse>>() {
            }.getType();

            //pass root element type to fromJson method along with input stream
            // Jobs jobsWrapper = gson.fromJson(bufferedReader, Jobs.class);
            List<MeetupResponse> joResponseList = gson.fromJson(bufferedReader, listType);
            Log.e("FETCH DATA FROM SERVER", "number of coupons from json response after gson parsing" + joResponseList.size());
            return joResponseList;
        }

    }


}

