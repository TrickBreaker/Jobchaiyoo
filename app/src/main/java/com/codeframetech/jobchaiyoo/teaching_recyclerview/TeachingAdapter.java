package com.codeframetech.jobchaiyoo.teaching_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeframetech.jobchaiyoo.R;
import com.codeframetech.jobchaiyoo.detail_activities.JobDetailActivity;
import com.codeframetech.jobchaiyoo.helpers.PicassoDownloader;
import com.codeframetech.jobchaiyoo.helpers.Teachings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Digition on 6/14/2016.
 */
public class TeachingAdapter extends RecyclerView.Adapter< com.codeframetech.jobchaiyoo.teaching_recyclerview.MyHolder> {
    Context c;
    private List<Teachings> teachingLists;
    private LayoutInflater inflater;
    List<Teachings> mStringFilterList;


    public TeachingAdapter(RecyclerView recyclerView, ArrayList<Teachings> teachingLists, Context c) {
        this.c = c;
        this.teachingLists = teachingLists;
        mStringFilterList = teachingLists;

    }

    @Override
    public  com.codeframetech.jobchaiyoo.teaching_recyclerview.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_model, parent, false);
        return new  com.codeframetech.jobchaiyoo.teaching_recyclerview.MyHolder(view);


    }

    @Override
    public void onBindViewHolder( com.codeframetech.jobchaiyoo.teaching_recyclerview.MyHolder myholder, int position) {
        final Teachings s = teachingLists.get(position);
        myholder.nameTxt.setText(s.getTeachingResponse().getName());
        myholder.qualification_txt.setText(s.getTeachingResponse().getQualification());
        myholder.post_txt.setText(s.getTeachingResponse().getPost());
        myholder.address_txt.setText(s.getTeachingResponse().getAddress());
        myholder.lastdate_txt.setText(s.getTeachingResponse().getLastdate());
        PicassoDownloader.picassoResize(c, s.getTeachingResponse().getImageUrl(), myholder.img, 100, 100);
        /*myholder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick() {
                // if (new NetworkCheckClass().checkConnection(c)) {

                openDetailActivity(s.getTeachingResponse().getName(), s.getTeachingResponse().getQualification(), s.getTeachingResponse().getPost(), s.getTeachingResponse().getAddress(), s.getTeachingResponse().getLastdate(), s.getTeachingResponse().getImageUrl(), s.getTeachingResponse().getImageUrl_coverpic(), s.getTeachingResponse().getJob_requirement(),
                        s.getTeachingResponse().getJob_discription(), s.getTeachingResponse().getDate_in_word());
                // } else {
                //Toast.makeText(c, "No Internet Connection", Toast.LENGTH_SHORT).show();
                //}
            }
        });*/

       /* } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }*/

    }

   /* @Override
    public int getItemViewType(int position) {
        return jobLists.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }*/


    @Override
    public int getItemCount() {
        /*return jobLists == null ? 0 : jobLists.size();*/
        return teachingLists.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

   /* public void setLoaded() {
        isLoading = false;
    }*/

    private void openDetailActivity(String name, String qualification, String post, String address, String lastdate, String imageUrl, String imageUrl_coverpic, String job_requirement, String job_discription, String date_in_word) {
        Intent intent = new Intent(c, JobDetailActivity.class);
        intent.putExtra("NAME_KEY", name);
        intent.putExtra("QUALIFICATION_KEY", qualification);
        intent.putExtra("POST_KEY", post);
        intent.putExtra("ADDRESS_KEY", address);
        intent.putExtra("LASTDATE_KEY", lastdate);
        intent.putExtra("IMAGEURL_KEY", imageUrl);
        intent.putExtra("IMAGEURL_COVER_PIC_KEY", imageUrl_coverpic);
        intent.putExtra("JOB_DISCRIPTION_KEY", job_discription);
        intent.putExtra("JOB_REQUIREMENT_KEY", job_requirement);
        intent.putExtra("DATE_IN_WORD_KEY", date_in_word);
        c.startActivity(intent);
    }



   /* public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }*/


    public void setFilter(List<Teachings> teachingModels) {
        teachingLists = new ArrayList<>();
        teachingLists.addAll(teachingModels);
        notifyDataSetChanged();
    }

}