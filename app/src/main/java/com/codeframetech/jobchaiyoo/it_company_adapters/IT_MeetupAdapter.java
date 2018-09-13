package com.codeframetech.jobchaiyoo.it_company_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Typeface;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import com.codeframetech.jobchaiyoo.R;
import com.codeframetech.jobchaiyoo.pojo_classes.MeetupResponse;
import com.codeframetech.jobchaiyoo.helpers.ItemClickListener;
import com.codeframetech.jobchaiyoo.helpers.PicassoDownloader;

import static android.content.ContentValues.TAG;

/**
 * Created by Digition on 6/14/2016.
 */
public class IT_MeetupAdapter extends RecyclerView.Adapter<IT_MeetupAdapter.Meetup_holder> {
    Context c;
    ArrayList<MeetupResponse> meetupLists;
    ItemClickListener mListener;


    public IT_MeetupAdapter(ItemClickListener listener) {
        this.meetupLists = new ArrayList<>();
        mListener = listener;
    }


    @Override
    public Meetup_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.it_company_meetups_model, parent, false);
        return new Meetup_holder(v);


    }

    @Override
    public void onBindViewHolder(Meetup_holder holder, int position) {
        MeetupResponse meetupResponse = meetupLists.get(position);
        holder.nameTxt.setText(meetupResponse.getMeetup_name());
        holder.address_txt.setText(meetupResponse.getLocation());
        holder.lastdate_txt.setText(meetupResponse.getDate_in_word());
       // holder.event_type_txt.setText(meetupResponse.getEvent_type());
        PicassoDownloader.downloadImage(holder.itemView.getContext(), meetupResponse.getImage_one(), holder.img);

    }

    @Override
    public int getItemCount() {
        return meetupLists.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // use serializable way
    /*private void openDetailActivity(String meetup_name, String location, String date, String imageurl_one, String imageurl_location, String imageurl_logo, String organizer_name, String contact_us, String about_meetup, String website,String time) {
        Intent intent = new Intent(c, MeetUps_DetailActivity.class);
        intent.putExtra("MEETUP_NAME_KEY", meetup_name);
        intent.putExtra("LOCATION_KEY", location);
        intent.putExtra("DATE_KEY", date);
        intent.putExtra("IMAGE_ONE_KEY", imageurl_one);
        intent.putExtra("IMAGE_LOCATION_KEY", imageurl_location);
        intent.putExtra("IMAGE_LOGO_KEY", imageurl_logo);
        intent.putExtra("ORGANIZER_NAME_KEY", organizer_name);
        intent.putExtra("CONTACT_US_KEY", contact_us);
        intent.putExtra("ABOUT_MEETUP_KEY", about_meetup);
        intent.putExtra("WEBSITE_KEY", website);
        intent.putExtra("TIME_KEY", time);
        c.startActivity(intent);
    }*/
    public void setFilter(List<MeetupResponse> meetupModels) {
        meetupLists = new ArrayList<>();
        for (MeetupResponse jobs : meetupModels) {
            addMeetups(jobs);
            notifyDataSetChanged();

        }
    }


    public void addMeetups(MeetupResponse meetupResponse) {
        Log.e(TAG, meetupResponse.getMeetup_name());
        meetupLists.add(meetupResponse);
        notifyDataSetChanged();
    }

    public MeetupResponse getSelectedMeetup(int position) {
        return meetupLists.get(position);
    }

    public void clearMeetupList() {
        meetupLists.clear();
        notifyDataSetChanged();
    }


    public class Meetup_holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTxt, address_txt, lastdate_txt, event_type_txt, date_lable_txt;
        ImageView img;
        Typeface my_typeface, subject_tf, company_name_typeface, date_tf;

        public Meetup_holder(View itemView) {
            super(itemView);
            subject_tf = getTypeface("fonts/weblink.ttf");
            company_name_typeface = getTypeface("fonts/ManilaSansBld.otf");
            date_tf = getTypeface("fonts/Cantarell-Regular.ttf");
            nameTxt = (TextView) itemView.findViewById(R.id.nameTxt);
            nameTxt.setTypeface(company_name_typeface);
            address_txt = (TextView) itemView.findViewById(R.id.address_text);
            address_txt.setTypeface(subject_tf);
            lastdate_txt = (TextView) itemView.findViewById(R.id.date_text);
            lastdate_txt.setTypeface(date_tf);
            img = (ImageView) itemView.findViewById(R.id.company_image);
            date_lable_txt = (TextView) itemView.findViewById(R.id.date_lable);
//        date_lable_txt.setTypeface(date_tf);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onmeetupItemClick(getLayoutPosition());

        }


        public Typeface getTypeface(String s) {
            my_typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), s);

            return my_typeface;

        }


    }

}


