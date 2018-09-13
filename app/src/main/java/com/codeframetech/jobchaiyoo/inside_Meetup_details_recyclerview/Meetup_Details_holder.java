package com.codeframetech.jobchaiyoo.inside_Meetup_details_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeframetech.jobchaiyoo.R;


/**
 * Created by Digition on 6/14/2016.
 */
public class Meetup_Details_holder extends RecyclerView.ViewHolder  {

    TextView meetup_discription;
    ImageView img;

    public Meetup_Details_holder(View itemView) {
        super(itemView);
        meetup_discription=(TextView)itemView.findViewById(R.id.meetup_discription);
        img = (ImageView) itemView.findViewById(R.id.meetup_image);
    }

}
