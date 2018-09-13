package com.codeframetech.jobchaiyoo.detail_activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.codeframetech.jobchaiyoo.R;
import com.codeframetech.jobchaiyoo.pojo_classes.InternshipResponse;
import com.codeframetech.jobchaiyoo.controller.AppConfig;
import com.codeframetech.jobchaiyoo.helpers.PicassoDownloader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Internship_DetailActivity extends AppCompatActivity {
    private TextView company_name_txt, starting_date_txt, subject_name_txt,
            internship_type_txt, duration_txt, location_txt, website_txt, contact_us_txt;
    private ImageView company_img, subject_img, intern_img;
    private WebView course_webview;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String course_overview = "";
    private String website = "";
    private String email = "";
    private TextView course_overview_txt;
    private TextView contact_details_txt;
    private TableLayout tableLayout;
    private InternshipResponse interns;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internship_details);
        MobileAds.initialize(this, "ca-app-pub-6771214991849242~6925683817");
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        configureViews();
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //Receive
        Intent i = this.getIntent();
        interns = (InternshipResponse) i.getSerializableExtra(AppConfig.REFERENCE.INTERNSHIPS);
        website = interns.getWebsite();
        View tableRow = LayoutInflater.from(this).inflate(R.layout.table_item, null, false);
        TextView morning = tableRow.findViewById(R.id.morning_id);
        TextView day = tableRow.findViewById(R.id.day_id);
        TextView evening = tableRow.findViewById(R.id.evening_id);
        morning.setText(interns.getMorning_time());
        day.setText(interns.getDay_time());
        evening.setText(interns.getEvening_time());
        tableLayout.addView(tableRow);
        //BIND
        company_name_txt.setText(interns.getCompany_name());
        starting_date_txt.setText(interns.getStarting_date());
        //internship_type_txt.setText(internship_type);
        subject_name_txt.setText(interns.getSubject_name());
        //duration_txt.setText(duration);
        location_txt.setText(interns.getLocation());
        website_txt.setText(interns.getWebsite());
        contact_us_txt.setText(interns.getContact_us());

        collapsingToolbarLayout = findViewById(R.id.internship_collapsing_toolbar);
        collapsingToolbarLayout.setTitle(interns.getSubject_name());
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER_VERTICAL);
        //collapsingToolbarLayout.setTitle(getTitle());

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        //using custom fonts for collapsing toolbar title
        final Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/ManilaSansBld.otf");
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#00FFFFFF"));


        //make this text as a presentation format
        course_webview.loadDataWithBaseURL(null, interns.getCourse_overview(), "text/html", "utf-8", null);
        //custom text for job information
        final Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/weblink.ttf");
        company_name_txt.setTypeface(tf);
        starting_date_txt.setTypeface(typeface);
        subject_name_txt.setTypeface(tf);
        course_overview_txt.setTypeface(tf);
        contact_details_txt.setTypeface(tf);
        //3rd typeface
        // final Typeface organo = Typeface.createFromAsset(this.getAssets(), "fonts/Organo.ttf");
        // duration_txt.setTypeface(typeface);
        location_txt.setTypeface(typeface);
        contact_us_txt.setTypeface(typeface);
        website_txt.setTypeface(typeface);
        PicassoDownloader.downloadImage(this, interns.getCompany_image(), company_img);
        PicassoDownloader.downloadNextImage(this, interns.getSubject_image(), subject_img);
        //PicassoDownloader.downloadNextImage(this, intern_logo, intern_img);


    }

    public void callPhone(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + interns.getContact_us()));
        startActivity(intent);
    }

    public void sendmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
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

    public void openEnrolLink(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(browserIntent);

    }

    private void configureViews() {
        //company_title.setText("CodeFrame Pvt Ltd");
        mAdView = findViewById(R.id.adView);
        company_name_txt = findViewById(R.id.company_name);
        starting_date_txt = findViewById(R.id.starting_date);
        subject_name_txt = findViewById(R.id.subject_name);
        contact_us_txt = findViewById(R.id.contact_us);
        tableLayout = findViewById(R.id.tableLayout);
        //internship_type_txt = (TextView) findViewById(R.id.internship_type);
        //duration_txt = (TextView) findViewById(R.id.duration);
        location_txt = findViewById(R.id.location_text);
        website_txt = findViewById(R.id.website);
        // intern_img = (ImageView) findViewById(R.id.intern_logo);
        company_img = findViewById(R.id.company_image);
        subject_img = findViewById(R.id.subject_image);
        course_webview = findViewById(R.id.course_overview);
        course_overview_txt = findViewById(R.id.discription_title);
        contact_details_txt = findViewById(R.id.contact_details);

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

}
