package com.codeframetech.jobchaiyoo;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.codeframetech.jobchaiyoo.Fragments.AllJobFragment;
import com.codeframetech.jobchaiyoo.Fragments.ClassFragment;
import com.codeframetech.jobchaiyoo.Fragments.MeetUps_Fragement;
import com.codeframetech.jobchaiyoo.constants_store_room.STORE_URL;
import com.codeframetech.jobchaiyoo.controller.AppConfig;
import com.codeframetech.jobchaiyoo.controller.NetworkCheckClass;
import com.codeframetech.jobchaiyoo.database.DatabaseOperation;
import com.codeframetech.jobchaiyoo.database.Database_Initializer;
import com.codeframetech.jobchaiyoo.helpers.MyFragPagerAdapter;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.gson.Gson;
import butterknife.BindView;
import butterknife.ButterKnife;


public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.mTab_ID) TabLayout tabLayout;
    @BindView(R.id.mViewpager_ID) ViewPager vp;
    @BindView(R.id.top_bar) AppBarLayout appBarLayout;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;

    Intent intent;
    static com.codeframetech.jobchaiyoo.SpinnerReselect sr;
    String company_name;
    Gson gson;
    AppConfig appConfig;
    private static final int PAGE_LIMIT = 3;
    String[] tabTitles;
    String[] DayOfWeek;
    DatabaseOperation mDatabase;
    ProfilePictureView profileView;
    TextView profileName, profileEmail;
    String profileFullName, email, profileImageLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //toolbar.setTitleTextColor(Color.WHITE);
        spinnerCreation();
        vp.setOffscreenPageLimit(PAGE_LIMIT);
        this.addPages(vp);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(vp, true);
        tabLayout.addOnTabSelectedListener(listener(vp));


        //tabLayout.removeOnTabSelectedListener(listener(vp));
        tabLayout.setTabTextColors(
                ContextCompat.getColor(this, R.color.tab_tile_unselect),
                ContextCompat.getColor(this, R.color.tab_title_selected)
        );

        tabTitles = getResources().getStringArray(R.array.tab_titles);
        //drawer layout
        // drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Navigation View
        assert navigationView != null;
        View headerLayout = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        profileView =  headerLayout.findViewById(R.id.profile_image);
        profileName =  headerLayout.findViewById(R.id.profile_name);
        profileEmail = headerLayout.findViewById(R.id.profile_user_id);
        //String profileUserID = returnValueFromBundles(FacebookActivity.PROFILE_USER_ID);
        mDatabase=new DatabaseOperation(this);


        if (new NetworkCheckClass().checkConnection(this)) {
            profileFullName = returnValueFromBundles(FacebookActivity.PROFILE_NAME);
            email = returnValueFromBundles(FacebookActivity.PROFILE_EMAIL);
            profileImageLink = returnValueFromBundles(FacebookActivity.PROFILE_IMAGE_URL);
            profileName.setText(profileFullName);
            profileEmail.setText(email);
            profileView.setProfileId(profileImageLink);

        } else {
            fetchDataFromDatabase();

        }



        // appConfig = new AppConfig();



    }


    private void fetchDataFromDatabase() {
        Cursor cursor = mDatabase.getUserFacebookData();
        if (cursor!=null && cursor.getCount() > 0) {

            while (cursor.moveToNext()){
                profileName.setText(cursor.getString(cursor.getColumnIndex(Database_Initializer.USER_NAME)));
                profileEmail.setText(cursor.getString(cursor.getColumnIndex(Database_Initializer.USER_EMAIL)));
                profileView.setProfileId(cursor.getString(cursor.getColumnIndex(Database_Initializer.USER_PROFILE_PIC)));

            }
        }

    }




    public void getDataFromFacebook() {

    }


    private void addPages(ViewPager pager) {
        MyFragPagerAdapter adapter = new MyFragPagerAdapter(getSupportFragmentManager());
        adapter.addPage(new MeetUps_Fragement());
        adapter.addPage(new ClassFragment());
        adapter.addPage(new AllJobFragment());
        //SET ADAPTER TO PAGER
        pager.setAdapter(adapter);
        pager.getAdapter().notifyDataSetChanged();
        // pager.destroyDrawingCache();

    }


    private TabLayout.OnTabSelectedListener listener(final ViewPager pager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                toolbar.setTitle(tabTitles[tab.getPosition()]);
                switch (tab.getPosition()) {
                    case 0:
                        tintSystemBars(ContextCompat.getColor(NavigationActivity.this, R.color.eventPrimaryDark),ContextCompat.getColor(NavigationActivity.this, R.color.eventPrimaryDark),ContextCompat.getColor(NavigationActivity.this, R.color.top_meetup_appbar_layout),ContextCompat.getColor(NavigationActivity.this, R.color.top_meetup_appbar_layout));
                        // toolbar.setBackgroundColor(ContextCompat.getColor(NavigationActivity.this, R.color.top_class_appbar_layout));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(NavigationActivity.this, R.color.top_meetup_appbar_layout));
                        break;

                    case 1:
                        tintSystemBars(ContextCompat.getColor(NavigationActivity.this, R.color.classPrimaryDark),ContextCompat.getColor(NavigationActivity.this, R.color.classPrimaryDark),ContextCompat.getColor(NavigationActivity.this, R.color.top_class_appbar_layout),ContextCompat.getColor(NavigationActivity.this, R.color.top_class_appbar_layout));
                        // toolbar.setBackgroundColor(ContextCompat.getColor(NavigationActivity.this, R.color.top_meetup_appbar_layout));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(NavigationActivity.this, R.color.top_class_appbar_layout));
                        break;

                    case 2:
                        tintSystemBars(ContextCompat.getColor(NavigationActivity.this, R.color.jobPrimaryDark),ContextCompat.getColor(NavigationActivity.this, R.color.jobPrimaryDark),ContextCompat.getColor(NavigationActivity.this, R.color.top_job_appbar_layout),ContextCompat.getColor(NavigationActivity.this, R.color.top_job_appbar_layout));
                        // toolbar.setBackgroundColor(ContextCompat.getColor(NavigationActivity.this, R.color.top_job_appbar_layout));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(NavigationActivity.this, R.color.top_job_appbar_layout));
                        break;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //pager.setCurrentItem(tab.getPosition());


            }
        };

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);


    }


    //Menu option from here
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //use sharedpreference to save value of id
        //saveID(id);

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.action_logout) {
            LoginManager.getInstance().logOut();
            Intent logoutIntent = new Intent(NavigationActivity.this, FacebookActivity.class);
            startActivity(logoutIntent);
            finish();
// Handle the camera action
        } else if (id == R.id.broadway) {
            Intent intent = new Intent(NavigationActivity.this, IT_Company.class);
            Bundle extras = new Bundle();
            extras.putString("LEAPFROG", STORE_URL.BROADWAY_INFOSYS.STORED_BROADWAY_INFOSYS);
            intent.putExtras(extras);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);


        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(NavigationActivity.this, MyCityList.class);
            startActivity(intent);
        } else if (id == R.id.codeframe) {
            Intent intent = new Intent(NavigationActivity.this, IT_Company.class);
            Bundle extras = new Bundle();
            extras.putString("LEAPFROG", STORE_URL.CODEFRAME_TECHNOLOGY.STORED_CODEFRAME_TECHNOLOGY);
            intent.putExtras(extras);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_leapfrog) {
            Intent intent = new Intent(NavigationActivity.this, IT_Company.class);
            Bundle extras = new Bundle();
            extras.putString("LEAPFROG", STORE_URL.LEAPFROG_ACADEMY.STORED_LEAPFROG_ACADEMY);
            intent.putExtras(extras);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);


        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String returnValueFromBundles(String key) {
        Bundle inBundle = getIntent().getExtras();
        return String.valueOf(inBundle.get(key));

    }

    private void spinnerCreation() {
        sr = findViewById(R.id.spinner);
        ArrayAdapter<String> myadapter = new ArrayAdapter(NavigationActivity.this, R.layout.custom_spinner, getResources().getStringArray(R.array.jobitem));
        myadapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        // DayOfWeek = getResources().getStringArray(R.array.jobitem);

        //sr.setAdapter(new MyCustomAdapter(NavigationActivity.this, R.layout.row, DayOfWeek));
        sr.setAdapter(myadapter);
        //sr.setDropDownWidth(100);
        sr.setSelection(0, false);
        //sr.setSelection(myadapter.getPosition("Engineering"));

        sr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String SpinnerValue = (String) sr.getSelectedItem();
                Toast.makeText(NavigationActivity.this, sr.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                //open activity accroding to selected spinner item
                switch (SpinnerValue) {

                    case "Engineering":
                        intent = new Intent(NavigationActivity.this, EngineeringActivity.class);
                        startActivity(intent);
                        break;

                    case "Medical":
                        intent = new Intent(NavigationActivity.this, EngineeringActivity.class);
                        startActivity(intent);
                        break;

                    case "Teaching":
                        Intent intent = new Intent(NavigationActivity.this, TeachingJob.class);
                        Bundle extras = new Bundle();
                        extras.putString("TEACHING", "Teaching");
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    /*private void notificationBuilder(String company_name, Bitmap bitmap_of_company_image) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(
                bitmap_of_company_image).build();
        //Get an instance of the NotificationManager service
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //set intents and pending intents to call activity on click of "top menu" action
        Intent resultIntent = new Intent(this, IT_Company.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent piResult = PendingIntent.getActivity(this, (int) Calendar.getInstance().getTimeInMillis(), resultIntent, 0);

        //build Notification
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(company_name)
                        .setContentText("Recent Available Vacancy")
                        .setStyle(bigPictureStyle)
                        .addAction(R.drawable.ic_menu_share, "Share", piResult)
                        .addAction(R.drawable.ic_menu_send, "Show activity", PendingIntent.getActivity(getApplicationContext(), 0, getIntent(), 0, null));

        //to post your notification to the notification bar
        notificationManager.notify(0, builder.build());
    }
*/

/*    //fetch data to display as Notification
    public void fetch_notification_data() {
        StringRequest sr = new StringRequest(Request.Method.GET, appConfig.URL_JOBS_NOTIFICATION, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                Log.e("Response", response);
                gson = new Gson();
                Type listType = new TypeToken<List<JobResponse>>() {
                }.getType();
                List<JobResponse> responses = gson.fromJson(response, listType);
                for (JobResponse response1 : responses) {
                    *//*Jobs job = new Jobs();
                    job.setJobResponseResponse(response1);*//*
                    company_name = responses.get(1).getName();
                    String company_image = responses.get(1).getImageUrl();
                  *//*  Log.e("Error.Response", company_image);
                    convertToBitmap convert = new convertToBitmap();
                    convert.execute(response1);*//*


                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error.Response", error.toString());


            }
        });

        AppController.getInstance().addToRequestQueue(sr);


    }*/

   /* //save ID
    private void saveID(int id) {
        SharedPreferences sharedPref = getSharedPreferences("SAVE_ID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("ID", id);
        editor.apply();
    }

    //read ID
    private int readID() {
        SharedPreferences sharedPref = getSharedPreferences("SAVE_ID", Context.MODE_PRIVATE);
        final int MISMATCH_VALUE = 736;
        int id = sharedPref.getInt("ID", MISMATCH_VALUE);
        return id;


    }*/


    /*public class  convertToBitmap extends AsyncTask<JobResponse,JobResponse,Boolean>{



        @Override
        protected Boolean doInBackground(JobResponse... params) {
            JobResponse response= params[0];

            try {
                InputStream   stream = new URL(response.imageUrl).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
               // notificationBuilder(company_name,bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
           return null;
        }

        @Override
        protected void onProgressUpdate(JobResponse... values) {
            super.onProgressUpdate(values);
        }
    }*/


    /*public class MyCustomAdapter extends ArrayAdapter<String> {


        public MyCustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull String[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getDropDownView(position, convertView, parent);
            return getCustomView(position, convertView, parent);

        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            *//*return super.getView(position, convertView, parent);*//*
            return getCustomView(position, convertView, parent);
        }

        private View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            TextView label = (TextView) row.findViewById(R.id.weekofday);
            //label.setTextColor(ContextCompat.getColor(NavigationActivity.this,R.color.white));
            label.setText(DayOfWeek[position]);
            return row;

        }
    }*/
    /*private class ShowProgress extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            //mprogressBar.setVisibility(View.VISIBLE);

        }


        @Override
        protected Void doInBackground(Void... voids) {
            if (new NetworkCheckClass().checkConnection(getContext())) {
                fetchDataFromServer("");

            } else {
                fetchDataFromDatabase();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            mprogressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            mRecyclerView.setAdapter(meetup_adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        }
    }*/
    private void tintSystemBars(final int statusBarColor,  final int statusBarToColor,final int toolbarColor, final int toolbarToColor ) {
        // Initial colors of each system bar.

        // Desired final colors of each bar.


        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Use animation position to blend colors.
                float position = animation.getAnimatedFraction();

                // Apply blended color to the status bar.
                int blended = blendColors(statusBarColor, statusBarToColor, position);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.setStatusBarColor(blended);
                }

                // Apply blended color to the ActionBar.
                blended = blendColors(toolbarColor, toolbarToColor, position);
                ColorDrawable background = new ColorDrawable(blended);
                getSupportActionBar().setBackgroundDrawable(background);
            }
        });

        anim.setDuration(500).start();
    }

    private int blendColors(int from, int to, float ratio) {
        final float inverseRatio = 1f - ratio;

        final float r = Color.red(to) * ratio + Color.red(from) * inverseRatio;
        final float g = Color.green(to) * ratio + Color.green(from) * inverseRatio;
        final float b = Color.blue(to) * ratio + Color.blue(from) * inverseRatio;

        return Color.rgb((int) r, (int) g, (int) b);
    }


}
