package com.codeframetech.jobchaiyoo.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.codeframetech.jobchaiyoo.R;
import com.codeframetech.jobchaiyoo.pojo_classes.JobResponse;
import com.codeframetech.jobchaiyoo.adapters.Job_Adapter;
import com.codeframetech.jobchaiyoo.constants_store_room.STORE_URL;
import com.codeframetech.jobchaiyoo.controller.AppConfig;
import com.codeframetech.jobchaiyoo.controller.NetworkCheckClass;
import com.codeframetech.jobchaiyoo.database.DatabaseOperation;
import com.codeframetech.jobchaiyoo.detail_activities.JobDetailActivity;
import com.codeframetech.jobchaiyoo.helpers.ItemClickListener;
import com.codeframetech.jobchaiyoo.sorting.SortJobs;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Digition on 3/2/2017.
 */


public class AllJobFragment extends Fragment implements SearchView.OnQueryTextListener, ItemClickListener {
    SwipeRefreshLayout mSwipeRefreshLayout;
    Gson gson;
    private List<JobResponse> mjobList = new ArrayList<>();
    private Job_Adapter mjobAdapter;
    private CoordinatorLayout coordinate_layout;
    private AppConfig appconfig;
    private DatabaseOperation mDatabase;
    private TabLayout setting_tablayout;
    private BottomSheetBehavior behavior;
    private ImageButton cancel_search;
    View bottomSheet;
    private SearchView mSearchView;
    Spinner job_spinner, company_spinner, education_spinner, location_spinner;
    private RecyclerView mRecyclerView;
    private long mLastClickTime = 0;
    private static final long MAX_CLICK_INTERVAL = 5000;
    InterstitialAd mInterstitialAd;
    AdView mAdView;
    private SpinnerFragmentForJob mSpinnerFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.alljob_fragment, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
        mRecyclerView = view.findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mjobAdapter = new Job_Adapter(this);
        mRecyclerView.setAdapter(mjobAdapter);
        mAdView = view.findViewById(R.id.adView);
        MobileAds.initialize(getActivity(), "ca-app-pub-6771214991849242~6925683817");

        job_spinner = (Spinner) view.findViewById(R.id.jobspinner);
        company_spinner = (Spinner) view.findViewById(R.id.company_spinner);
        education_spinner = (Spinner) view.findViewById(R.id.education_spinner);
        location_spinner = (Spinner) view.findViewById(R.id.location_spinner);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swifeRefresh);
        final FloatingActionButton fabNotification = (FloatingActionButton) view.findViewById(R.id.fab_notification);
        coordinate_layout = (CoordinatorLayout) view.findViewById(R.id.fab_layout);
        setting_tablayout = (TabLayout) view.findViewById(R.id.job_bottom_tab);
        cancel_search = (ImageButton) view.findViewById(R.id.cancel);
        bottomSheet = view.findViewById(R.id.bottom_sheet);
        fabNotification.setVisibility(View.INVISIBLE);
        setting_tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setting_tablayout.addTab(setting_tablayout.newTab().setIcon(R.drawable.ic_action_sort).setText(STORE_URL.ConstantStrings.NAME));
        setting_tablayout.addTab(setting_tablayout.newTab().setIcon(R.drawable.ic_action_refresh).setText("Refresh"));
        //setting_tablayout.addTab(setting_tablayout.newTab().setIcon(R.drawable.ic_action_settings).setText("Advance Search"));
        setting_tablayout.addOnTabSelectedListener(listener());
        setting_tablayout.setSelectedTabIndicatorHeight(0);
        spinnerCreation();
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.green), ContextCompat.getColor(getContext(), R.color.orange), ContextCompat.getColor(getContext(), R.color.blue));

        //fetch the data
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-6771214991849242/9015347459");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        appconfig = new AppConfig();
        mDatabase = new DatabaseOperation(getActivity());
        //new ShowProgress().execute();
        behavior = BottomSheetBehavior.from(bottomSheet);


        cancel_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


            }

        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getJobListObservable();
            }
        });

        //using rxjava2 with androidNetworking
        getJobListObservable();


        return view;


    }


    private Observable<List<JobResponse>> fetchDataFromDatabase() {

        try {

            return Observable.just(mDatabase.getJobData());
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return null;

    }


    //tablayout listner method
    private TabLayout.OnTabSelectedListener listener() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        String sortby_name = tab.getText().toString();
                        if (sortby_name.equals(STORE_URL.ConstantStrings.NAME)) {
                            getQueryForSorting(mjobList, sortby_name);
                            tab.setText(STORE_URL.ConstantStrings.PLACE);
                        } else {
                            tab.setText(STORE_URL.ConstantStrings.NAME);
                            getQueryForSorting(mjobList, sortby_name);
                        }
                        break;
                    case 1:
                        if (SystemClock.elapsedRealtime() - mLastClickTime > MAX_CLICK_INTERVAL) {
                            mSwipeRefreshLayout.setRefreshing(true);
                            getJobListObservable();

                        }
                        mLastClickTime = SystemClock.elapsedRealtime();


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
                            tab.setText(STORE_URL.ConstantStrings.PLACE);
                            getQueryForSorting(mjobList, sortby_name);
                        } else {
                            tab.setText(STORE_URL.ConstantStrings.NAME);
                            getQueryForSorting(mjobList, sortby_name);
                        }
                        break;
                    case 1:
                        if (SystemClock.elapsedRealtime() - mLastClickTime > MAX_CLICK_INTERVAL) {
                            mSwipeRefreshLayout.setRefreshing(true);
                            getJobListObservable();

                        }
                        mLastClickTime = SystemClock.elapsedRealtime();


                }


            }
        };

    }


    @Override
    public String toString() {
        return "All Jobs";

    }


    //Menu option from here
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.setting_btn) {
            //hide inputKeyboard
            hideSoftKeyboard();


            if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                mSearchView.clearFocus();
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);


            } else {

                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }


        }

        //use sharedpreference to save value of id

        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.navigation, menu);
        inflater.inflate(R.menu.notification_menu, menu);
        final MenuItem item = menu.findItem(R.id.search);
        final MenuItem settingg = menu.findItem(R.id.setting_btn);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setQueryHint("company name, post, address, qualification.");
        mSearchView.setOnQueryTextListener(this);


        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        settingg.setVisible(false);
                        mjobAdapter.clearJobList();
                        mjobAdapter.setFilter(mjobList);
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        // mjobAdapter.setFilter(new ShowProgress().fetchDataFromDatabase());
                        settingg.setVisible(true);
                        setting_tablayout.setVisibility(View.INVISIBLE);
                        return true; // Return true to expand action view
                    }
                });

    }

    @Override
    public boolean onQueryTextChange(String newText) {

        final List<JobResponse> filteredModelList = filter(mjobList, newText);
        mjobAdapter.clearJobList();
        mjobAdapter.setFilter(filteredModelList);

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //method to hide InputkeyBoard when bottomsheet opens up.
    public void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    private List<JobResponse> filter(List<JobResponse> models, String query) {
        query = query.toLowerCase();
        final List<JobResponse> filteredModelList = new ArrayList<>();
        for (JobResponse model : models) {
            final String company_name = model.getName().toLowerCase();
            final String post = model.getPost().toLowerCase();
            final String qualification = model.getQualification().toLowerCase();
            final String address = model.getAddress().toLowerCase();
            if (company_name.contains(query) || post.contains(query) || qualification.contains(query) || address.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public void getQueryForSorting(List<JobResponse> responses, String query) {
        //sorting list
        if (query.equals(STORE_URL.ConstantStrings.NAME)) {
            mjobAdapter.setFilter(new SortJobs().returnSortedJobsByName(responses));
        } else {
            mjobAdapter.setFilter(new SortJobs().returnSortedJobsByPlace(responses));
        }
    }


    @Override
    public void onjobItemClick(final int position) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            JobResponse selectedJob = mjobAdapter.getSelectedJob(position);
            startActivity(new Intent(getActivity(), JobDetailActivity.class).putExtra(AppConfig.REFERENCE.JOBS, selectedJob));
        }

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                JobResponse selectedJob = mjobAdapter.getSelectedJob(position);
                startActivity(new Intent(getActivity(), JobDetailActivity.class).putExtra(AppConfig.REFERENCE.JOBS, selectedJob));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());


            }

        });


    }

    @Override
    public void onclassItemClick(int position) {

    }

    @Override
    public void onmeetupItemClick(int position) {

    }

    //fetching joblist from server using Rxjava and Android Networking
    private void getJobListObservable() {

        //displaying progress bar before data is fetched
        mSpinnerFragment = new SpinnerFragmentForJob();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_job_container, mSpinnerFragment).commit();
        Observable<List<JobResponse>> jobList = null;

        if (new NetworkCheckClass().checkConnection(getContext())) {
            jobList = Rx2AndroidNetworking.get(appconfig.URL_JOBS)
                    .build()
                    .getObjectListObservable(JobResponse.class);
        } else {
            jobList = fetchDataFromDatabase();
        }


        assert jobList != null;
        jobList.flatMap(new Function<List<JobResponse>, Observable<JobResponse>>() {
            @Override
            public Observable<JobResponse> apply(List<JobResponse> jobResponses) {
                return Observable.fromIterable(jobResponses);
            }

        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JobResponse>() {
                    @Override
                    public void onComplete() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        android.support.v4.app.FragmentTransaction fragmentTransaction =
                                getFragmentManager().beginTransaction();
                        fragmentTransaction.remove(mSpinnerFragment).commitAllowingStateLoss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        android.support.v4.app.FragmentTransaction fragmentTransaction =
                                getFragmentManager().beginTransaction();
                        fragmentTransaction.remove(mSpinnerFragment).commitAllowingStateLoss();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                        mjobList.clear();
                        mjobAdapter.clearJobList();
                        mjobAdapter.notifyItemRangeRemoved(0, mjobAdapter.getItemCount());
                    }

                    @Override
                    public void onNext(JobResponse jobResponse) {

                        if (jobResponse != null) {

                            mjobList.add(jobResponse);
                            mjobAdapter.addJobs(jobResponse);
                            mDatabase.deleteJobData(jobResponse);
                            mDatabase.addJobData(jobResponse);
                            //mjobAdapter.notifyDataSetChanged();
                            mSwipeRefreshLayout.setRefreshing(false);
                            android.support.v4.app.FragmentTransaction fragmentTransaction =
                                    getFragmentManager().beginTransaction();
                            fragmentTransaction.remove(mSpinnerFragment).commitAllowingStateLoss();


                        }


                    }
                });


    }


    private void spinnerCreation() {

        ArrayAdapter<String> job_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.jobitem));
        ArrayAdapter<String> company_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.company_list));
        ArrayAdapter<String> education_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.education_list));
        ArrayAdapter<String> location_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.place_list));
        // ArrayAdapter adapter= ArrayAdapter.createFromResource(getContext(),R.array.jobitem,R.layout.custom_spinner);
        // adapter.setDropDownViewResource(R.layout.row);
        // DayOfWeek = getResources().getStringArray(R.array.jobitem);
        //sr.setAdapter(new MyCustomAdapter(NavigationActivity.this, R.layout.row, DayOfWeek));
        education_spinner.setAdapter(education_adapter);
        job_spinner.setAdapter(job_adapter);
        location_spinner.setAdapter(location_adapter);
        company_spinner.setAdapter(company_adapter);
        // sr.setDropDownWidth(500);
        company_spinner.setSelection(0, false);
        job_spinner.setSelection(0, false);
        location_spinner.setSelection(0, false);
        education_spinner.setSelection(0, false);
        hideSoftKeyboard();
        // sr.setSelection(adapter.getPosition("Engineering"));
        location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String location_Query = location_spinner.getSelectedItem().toString();
                final List<JobResponse> filterList = filter(mjobList, location_Query);
                mjobAdapter.clearJobList();
                mjobAdapter.setFilter(filterList);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        company_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String company_name_Query = company_spinner.getSelectedItem().toString();
                final List<JobResponse> filteredModelList = filter(mjobList, company_name_Query);
                mjobAdapter.clearJobList();
                mjobAdapter.setFilter(filteredModelList);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        education_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String education_Query = education_spinner.getSelectedItem().toString();
                final List<JobResponse> filteredList = filter(mjobList, education_Query);
                mjobAdapter.clearJobList();
                mjobAdapter.setFilter(filteredList);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        job_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String education_Query = job_spinner.getSelectedItem().toString();
                final List<JobResponse> filteredList = filter(mjobList, education_Query);
                //mjobAdapter.clearJobList();
                mjobAdapter.setFilter(filteredList);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


    }


}

