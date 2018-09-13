package  com.codeframetech.jobchaiyoo.detail_activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeframetech.jobchaiyoo.R;
import com.codeframetech.jobchaiyoo.pojo_classes.JobResponse;
import com.codeframetech.jobchaiyoo.controller.AppConfig;
import com.codeframetech.jobchaiyoo.helpers.PicassoDownloader;
import com.codeframetech.jobchaiyoo.upload_resume.GetCVfromClient;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JobDetailActivity extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView company_title_txt, address_txt, last_date_in_word_txt, job_discription_txt, job_requirement_txt;
    ImageView img, img_cover_pic;
    TextView apply_title, qualification_txt, job_level,available_vacancy,designation_txt, day_remaining_txt, jobdiscription_label, job_requirement_label;
    WebView job_dispcription_View, job_requirement_view;
    Button job_apply_btn, send_email;
    Toolbar toolbar;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobs_content_detail);
        MobileAds.initialize(this, "ca-app-pub-6771214991849242~6925683817");
        //Job apply button onclick method
        job_apply_btn = findViewById(R.id.direct_mail);
        send_email = (Button) findViewById(R.id.via_email);


        // Find the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //method to configure all the views
        configViews();
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //jobTimer();
       /*Receive serializable values and set values and typeface in views*/
        Intent i = this.getIntent();
        final JobResponse jobs;
        jobs = (JobResponse) i.getSerializableExtra(AppConfig.REFERENCE.JOBS);
        viewSetter(jobs);
        Thread timer= null;
        Runnable runnableThread = new CountDownRunner(jobs);
        timer= new Thread(runnableThread);
        timer.start();

        company_title_txt.setVisibility(View.INVISIBLE);

        //send company name to GetCVfromClient activity
        job_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JobDetailActivity.this, GetCVfromClient.class);
                i.putExtra("COMPANY_NAME", jobs.getName());
                startActivity(i);
            }
        });
        send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + jobs.getCompany_email()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Applying For " + jobs.getPost());
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });


    }

    private void viewSetter(final JobResponse jobs) {
        //make this text as a presentation format
        job_dispcription_View.loadDataWithBaseURL(null, jobs.getJob_discription(), "text/html", "utf-8", null);
        job_requirement_view.loadDataWithBaseURL(null, jobs.getJob_requirement(), "text/html", "utf-8", null);
        designation_txt.setText(jobs.getPost());
        qualification_txt.setText(jobs.getQualification());
        address_txt.setText(jobs.getAddress());
        last_date_in_word_txt.setText(jobs.getDate_in_word());
        available_vacancy.setText(jobs.getAvailable_vacancy());
        job_level.setText(jobs.getJob_level());
        collapsingToolbarLayout.setTitle(jobs.getName());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);

        //using custom fonts for collapsing toolbar title
        final Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/ManilaSansBld.otf");
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);
        //custom text for job information
        final Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/weblink.ttf");
        designation_txt.setTypeface(typeface);
        qualification_txt.setTypeface(typeface);
        address_txt.setTypeface(typeface);
        last_date_in_word_txt.setTypeface(typeface);
        day_remaining_txt.setTypeface(tf);
        jobdiscription_label.setTypeface(tf);
        job_requirement_label.setTypeface(tf);
        apply_title.setTypeface(tf);
        job_level.setTypeface(typeface);
        available_vacancy.setTypeface(typeface);
        PicassoDownloader.downloadImage(this, jobs.getImageUrl(), img);
        PicassoDownloader.downloadNextImage(this, jobs.getImage_coverpic(), img_cover_pic);
        //for preventing web view to be clickable
        job_dispcription_View.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        job_requirement_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        job_dispcription_View.setLongClickable(false);
        job_dispcription_View.setHapticFeedbackEnabled(false);
        job_requirement_view.setLongClickable(false);
        job_requirement_view.setHapticFeedbackEnabled(false);

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


    //configures id of views
    private void configViews() {
        mAdView = findViewById(R.id.adView);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        company_title_txt = findViewById(R.id.company_title);
        img = findViewById(R.id.profile);
        apply_title = findViewById(R.id.apply_title);
        img_cover_pic = findViewById(R.id.cover_pic);
        address_txt = findViewById(R.id.address);
        last_date_in_word_txt = findViewById(R.id.lastdate_txt);
        //job_discription_txt = (TextView) findViewById(R.id.job_discription);
        designation_txt = findViewById(R.id.designation_txt);
        qualification_txt = findViewById(R.id.qualification_txt);
        day_remaining_txt = findViewById(R.id.day_remaining);
        jobdiscription_label = findViewById(R.id.discription_label);
        job_requirement_label = findViewById(R.id.job_requirement_label);
        job_dispcription_View = findViewById(R.id.job_discription_webview);
        job_requirement_view = findViewById(R.id.job_requirement_webview);
        available_vacancy=findViewById(R.id.vacancy_txt);
        job_level=findViewById(R.id.level_txt);


    }
    public class CountDownRunner implements  Runnable{
        JobResponse jobs;
        CountDownRunner(JobResponse jobs){
         this.jobs=jobs;
        }

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted())
            { try {
                doWork(jobs);
                Thread.sleep(1000);
            }
                catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
                catch(Exception e){

                }
            }}
        }

    private void doWork(final JobResponse jobs) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Date currentDate = new Date();
                    SimpleDateFormat timeformat = new SimpleDateFormat("dd/M/yyy hh:mm:ss");
                    String currentDeviceDate = timeformat.format(currentDate);
                    Date currentFormatedDate = timeformat.parse(currentDeviceDate);
                    Date postedDateFromDatabase = timeformat.parse(jobs.getTime_remaining());
                    long timeDifference = postedDateFromDatabase.getTime() - currentFormatedDate.getTime();
                    long secondsInMilli = 1000;
                    long minutesInMilli = secondsInMilli * 60;
                    long hoursInMilli = minutesInMilli * 60;
                    long daysInMilli = hoursInMilli * 24;
                    if(timeDifference > 0) {
                        long elapsedDays = timeDifference / daysInMilli;
                        timeDifference = timeDifference % daysInMilli;
                        long elapsedHours = timeDifference / hoursInMilli;


                        day_remaining_txt.setText(String.valueOf(elapsedDays) + " days " + String.valueOf(elapsedHours) + " hours" + " left");
                        //Log.e("TIME",String.valueOf(elapsedDays) +" " + String.valueOf(elapsedHours));
                    }
                    else{day_remaining_txt.setText("Vacancy over");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}


