package com.codeframetech.jobchaiyoo.inside_Meetup_details_recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeframetech.jobchaiyoo.R;
import com.codeframetech.jobchaiyoo.helpers.Meetups;
import com.codeframetech.jobchaiyoo.helpers.PicassoDownloader;

import java.util.ArrayList;

/**
 * Created by Digition on 7/13/2017.
 */
public class Meetup_Details_adapter extends RecyclerView.Adapter< com.codeframetech.jobchaiyoo.inside_Meetup_details_recyclerview.Meetup_Details_holder> {
    Context c;
    ArrayList<Meetups> meetupLists;


    public Meetup_Details_adapter(Context c, ArrayList<Meetups> meetupLists) {
        this.c = c;
        this.meetupLists = meetupLists;
    }


    @Override
    public  com.codeframetech.jobchaiyoo.inside_Meetup_details_recyclerview.Meetup_Details_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_rv_meetups_model, parent, false);

        return new  com.codeframetech.jobchaiyoo.inside_Meetup_details_recyclerview.Meetup_Details_holder(v);


    }

    @Override
    public void onBindViewHolder(Meetup_Details_holder holder, int position) {

        final Meetups s = meetupLists.get(position);
        holder.meetup_discription.setText(s.getMeetupResponse().getMeetup_name());
        PicassoDownloader.downloadImage(c, s.getMeetupResponse().getImage_one(), holder.img);
    }

    @Override
    public int getItemCount() {
        return meetupLists.size();
    }

}

