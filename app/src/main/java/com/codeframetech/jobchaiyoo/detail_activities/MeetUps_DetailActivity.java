package  com.codeframetech.jobchaiyoo.detail_activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeframetech.jobchaiyoo.R;
import com.codeframetech.jobchaiyoo.pojo_classes.MeetupResponse;
import com.codeframetech.jobchaiyoo.controller.AppConfig;
import com.codeframetech.jobchaiyoo.helpers.PicassoDownloader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;


public class MeetUps_DetailActivity extends AppCompatActivity {
    private TextView meetup_name, meetup_date, meetup_location, meetup_time, about_meetup_txt, contact_organizer_txt;
    private ImageView imageView_logo, imageView_location;
    private TextView location, contact_us, website, company_name_txt, event_details_txt;
    private WebView about_meetup_View;
    private Gson gson;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppConfig appConfig;
    private RecyclerView rv;
    MeetupResponse meetups;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meetups_details);
        MobileAds.initialize(this, "ca-app-pub-6771214991849242~6925683817");

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //configure all the views xml views
        configureViews();
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //Instantiating AppConfig class
        appConfig = new AppConfig();
        //get dataList using appConfig
        //fetchDownloads();

        /*Receive serializable values and set values and typeface in views*/
        Intent i = this.getIntent();

        meetups = (MeetupResponse) i.getSerializableExtra(AppConfig.REFERENCE.MEETUPS);
        about_meetup_View.loadDataWithBaseURL(null, meetups.getAbout_meetup(), "text/html", "UTF-8", null);
        //download image using picassoDownloader class
        PicassoDownloader.downloadImage(this, meetups.getImage_one(), imageView_logo);
        PicassoDownloader.downloadNextImage(this, meetups.getImage_location(), imageView_location);

        //setting text value to Views
        String meetup_type = meetups.getEvent_type();
        about_meetup_txt.setText("About " + meetup_type);
        meetup_name.setText(meetups.getMeetup_name());
        meetup_date.setText(meetups.getDate_in_word());
        meetup_time.setText(meetups.getTime());
        location.setText(meetups.getLocation());
        contact_us.setText(meetups.getContact_us());
        website.setText(meetups.getWebsite());
        company_name_txt.setText(meetups.getOrganizer_name());
        event_details_txt.setText(meetups.getMeetup_details());
        meetup_location.setText(meetups.getLocation());
        //setting title and gravity for Collapsing Toolbar Layout
        collapsingToolbarLayout.setTitle(meetups.getMeetup_name());
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER_VERTICAL);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);

        //custom text for Meetup information
        final Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/weblink.ttf");
        meetup_time.setTypeface(typeface);
        meetup_location.setTypeface(typeface);
        meetup_date.setTypeface(typeface);
        location.setTypeface(typeface);
        contact_us.setTypeface(typeface);
        website.setTypeface(typeface);

        //using custom fonts for collapsing toolbar title
        final Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/ManilaSansBld.otf");
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#00FFFFFF"));
        contact_organizer_txt.setTypeface(tf);
        about_meetup_txt.setTypeface(tf);
        meetup_name.setTypeface(tf);
        company_name_txt.setTypeface(tf);

        //for preventing web view to be clickable
        about_meetup_View.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        about_meetup_View.setLongClickable(false);
        about_meetup_View.setHapticFeedbackEnabled(false);
    }

    public void callPhone(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + meetups.getContact_us()));
        startActivity(intent);
    }

    public void sendmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "nepazine@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ABOUT INTERNSHIP");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
    }

    public void openfacebook(View view) {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/zitennbasnet"));
        startActivity(facebookIntent);
    }

    public void openbrowser(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(browserIntent);
    }

public void openRegisterLink(View view){
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
    startActivity(browserIntent);

}
  /*  //get data List from the server using the links (appConfig.URL_MEETUPS)
    public void fetchDownloads() {
        StringRequest sr = new StringRequest(Request.Method.GET, appConfig.URL_MEETUPS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                gson = new Gson();
                Type listType = new TypeToken<List<MeetupResponse>>() {
                }.getType();
                List<MeetupResponse> responses = gson.fromJson(response, listType);

                meetupsArrayList.clear();
                for (MeetupResponse response1 : responses) {
                    Meetups meetups = new Meetups();
                    meetups.setMeetupsResponse(response1);

                    meetupsArrayList.add(meetups);
                    meetup_adapter_details.notifyDataSetChanged();

                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error.Response", error.toString());

                Toast.makeText(MeetUps_DetailActivity.this, "check your Internet connection", Toast.LENGTH_SHORT).show();


            }
        });

        AppController.getInstance().addToRequestQueue(sr);


    }*/

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

    private void configureViews() {
       /* rv = (RecyclerView) findViewById(R.id.meetup_recycler);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        meetup_adapter_details = new Meetup_Details_adapter(this, meetupsArrayList);
        rv.setLayoutManager(mLinearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(meetup_adapter_details);*/
        mAdView = findViewById(R.id.adView);
        collapsingToolbarLayout = findViewById(R.id.internship_collapsing_toolbar);
        company_name_txt = findViewById(R.id.company_name);
        meetup_name = findViewById(R.id.meetup_name);
        meetup_location = findViewById(R.id.meetup_location);
        imageView_logo = findViewById(R.id.image_logo);
        imageView_location = findViewById(R.id.image_location);
        meetup_date = findViewById(R.id.fb_date);
        meetup_time = findViewById(R.id.time);
        about_meetup_View = findViewById(R.id.about_meetup_webview);
        location = findViewById(R.id.location_text);
        contact_us = findViewById(R.id.contact_us);
        website = findViewById(R.id.website);
        about_meetup_txt = findViewById(R.id.about_meetup);
        contact_organizer_txt = findViewById(R.id.contact_details);
        event_details_txt = findViewById(R.id.event_details);
    }

}





