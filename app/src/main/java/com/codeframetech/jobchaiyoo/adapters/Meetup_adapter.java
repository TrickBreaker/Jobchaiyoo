package  com.codeframetech.jobchaiyoo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.codeframetech.jobchaiyoo.R;
import com.codeframetech.jobchaiyoo.pojo_classes.MeetupResponse;
import com.codeframetech.jobchaiyoo.helpers.ItemClickListener;
import com.codeframetech.jobchaiyoo.helpers.PicassoDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Digition on 6/14/2016.
 */
public class Meetup_adapter extends RecyclerView.Adapter<Meetup_adapter.Meetup_holder> {
    Context c;
    ArrayList<MeetupResponse> meetupLists;
    ItemClickListener mListener;


    public Meetup_adapter(ItemClickListener listener) {
        this.meetupLists = new ArrayList<>();
        mListener = listener;
    }


    @Override
    public Meetup_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meetups_model, parent, false);
        return new Meetup_holder(v);


    }

    @Override
    public void onBindViewHolder(Meetup_holder holder, int position) {
        MeetupResponse meetupResponse = meetupLists.get(position);
        holder.nameTxt.setText(meetupResponse.getMeetup_name());
        holder.address_txt.setText(meetupResponse.getLocation());
        holder.lastdate_txt.setText(meetupResponse.getDate_in_word());
        holder.event_type_txt.setText(meetupResponse.getEvent_type());
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

    public void setFilter(List<MeetupResponse> fileteredList) {
        //meetupLists = new ArrayList<>();
        meetupLists.clear();
        meetupLists.addAll(fileteredList);
        // notifyItemChanged(meetupLists.size());
        notifyDataSetChanged();
    }


    public void addMeetups(MeetupResponse meetupResponse) {
        // Log.e(TAG, meetupResponse.getMeetup_name());
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

        TextView nameTxt, address_txt, lastdate_txt, event_type_txt;
        ImageView img;

        public Meetup_holder(View itemView) {
            super(itemView);
            event_type_txt = (TextView) itemView.findViewById(R.id.event_type);
            // subject_tf = getTypeface("fonts/weblink.ttf");
            // company_name_typeface = getTypeface("fonts/ManilaSansBld.otf");
            // date_tf = getTypeface("fonts/Cantarell-Regular.ttf");
            nameTxt = (TextView) itemView.findViewById(R.id.nameTxt);
            // nameTxt.setTypeface(company_name_typeface);
            address_txt = (TextView) itemView.findViewById(R.id.address_text);
            // address_txt.setTypeface(subject_tf);
            lastdate_txt = (TextView) itemView.findViewById(R.id.date_text);
            // lastdate_txt.setTypeface(date_tf);
            img = (ImageView) itemView.findViewById(R.id.company_image);
            // date_lable_txt.setTypeface(date_tf);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onmeetupItemClick(getLayoutPosition());

        }


       /* Typeface getTypeface(String s) {
            my_typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), s);

            return my_typeface;

        }*/


    }

}



