package com.codeframetech.jobchaiyoo.it_company_adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeframetech.jobchaiyoo.R;
import com.codeframetech.jobchaiyoo.pojo_classes.JobResponse;
import com.codeframetech.jobchaiyoo.helpers.ItemClickListener;
import com.codeframetech.jobchaiyoo.helpers.PicassoDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Digition on 6/14/2016.
 */
public class IT_JobAdapter extends RecyclerView.Adapter<IT_JobAdapter.Job_Holder> {
    private final ItemClickListener mListener;
    private List<JobResponse> mjobLists;
    //private List<Jobs> mStringFilterList;
    private static final String TAG = IT_JobAdapter.class.getSimpleName();


    public IT_JobAdapter(ItemClickListener listener) {
        mjobLists = new ArrayList<>();
        mListener = listener;
        //mStringFilterList = jobLists;
    }

    @Override
    public Job_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.it_company_job_model, parent, false);
        return new Job_Holder(view);

    }

    @Override
    public void onBindViewHolder(Job_Holder jobholder, int position) {
        JobResponse currentResponse = mjobLists.get(position);
        jobholder.nameTxt.setText(currentResponse.getName());
        // jobholder.address_txt.setText(currentResponse.getAddress());
        jobholder.qualification_txt.setText(currentResponse.getQualification());
        //jobholder.post_txt.setText(currentResponse.getQualification());
        jobholder.lastdate_txt.setText(currentResponse.getLastdate());
        PicassoDownloader.downloadImage(jobholder.itemView.getContext(), currentResponse.getImageUrl(), jobholder.img);

    }


    @Override
    public int getItemCount() {
        return mjobLists.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    //use serializble way

   /* public void setFilter(List<JobResponse> jobModels) {

        mjobLists = new ArrayList<>();
        for(JobResponse jobs: jobModels){
            addJobs(jobs);
            notifyDataSetChanged();

        }

    }*/


    public void addJobs(JobResponse jobResponse) {
        Log.e(TAG, jobResponse.getName());
        mjobLists.add(jobResponse);
        notifyDataSetChanged();
    }

    public JobResponse getSelectedJob(int position) {
        return mjobLists.get(position);
    }

    public void clearJobList() {
        mjobLists.clear();
        notifyDataSetChanged();
    }


    public class Job_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTxt, qualification_txt, post_txt, address_txt,
                lastdate_txt, date_lable_txt;
        ImageView img;
        Typeface my_typeface, subject_tf, company_name_typeface, date_tf;


        public Job_Holder(View itemView) {

            super(itemView);
            subject_tf = getTypeface("fonts/weblink.ttf");
            company_name_typeface = getTypeface("fonts/ManilaSansBld.otf");
            date_tf = getTypeface("fonts/Cantarell-Regular.ttf");

            nameTxt = (TextView) itemView.findViewById(R.id.nameTxt);
            nameTxt.setTypeface(company_name_typeface);
            qualification_txt = (TextView) itemView.findViewById(R.id.qualification_text);
            qualification_txt.setTypeface(subject_tf);
            post_txt = (TextView) itemView.findViewById(R.id.job_title_text);
            // post_txt.setTypeface(subject_tf);
            address_txt = (TextView) itemView.findViewById(R.id.address_text);
            //address_txt.setTypeface(subject_tf);
            lastdate_txt = (TextView) itemView.findViewById(R.id.date_text);
            lastdate_txt.setTypeface(date_tf);
            date_lable_txt = (TextView) itemView.findViewById(R.id.date_lable);
          date_lable_txt.setTypeface(date_tf);
            img = (ImageView) itemView.findViewById(R.id.company_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onjobItemClick(getLayoutPosition());
        }



        public Typeface getTypeface(String s) {
            my_typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), s);

            return my_typeface;

        }


    }

}