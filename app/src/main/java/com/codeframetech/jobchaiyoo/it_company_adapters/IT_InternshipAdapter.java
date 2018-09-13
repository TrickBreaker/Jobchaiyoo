package com.codeframetech.jobchaiyoo.it_company_adapters;

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
import com.codeframetech.jobchaiyoo.pojo_classes.InternshipResponse;
import com.codeframetech.jobchaiyoo.helpers.ItemClickListener;
import com.codeframetech.jobchaiyoo.helpers.PicassoDownloader;

/**
 * Created by Digition on 6/14/2016.
 */
public class IT_InternshipAdapter extends RecyclerView.Adapter<IT_InternshipAdapter.InternshipHolder> {
    private ArrayList<InternshipResponse> minternLists;
    private final ItemClickListener mListener;
    public static final String TAG = IT_InternshipAdapter.class.getSimpleName();

    public IT_InternshipAdapter(ItemClickListener listener) {
        minternLists = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public InternshipHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.it_company_class_model, parent, false);
        return new InternshipHolder(view);
    }

    @Override
    public void onBindViewHolder(InternshipHolder intern_holder, int position) {
        InternshipResponse internResponse = minternLists.get(position);
        intern_holder.company_name_txt.setText(internResponse.getCompany_name());
        intern_holder.subject_name_txt.setText(internResponse.getSubject_name());
        intern_holder.starting_date_txt.setText(internResponse.getStarting_date());
        intern_holder.intern_address.setText(internResponse.getLocation());
        intern_holder.intern_time.setText(internResponse.getTime());
        //intern_holder.internship_type_txt.setText(internResponse.getInternship_type());
        // intern_holder.duration_txt.setText(internResponse.getDuration());
        PicassoDownloader.downloadImage(intern_holder.itemView.getContext(), internResponse.getCompany_image(), intern_holder.company_img);


    }


    @Override
    public int getItemCount() {
        return minternLists.size();


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setFilter(List<InternshipResponse> internModels) {
        minternLists = new ArrayList<>();
        for (InternshipResponse interns : internModels) {
            addInterns(interns);
            notifyDataSetChanged();
        }
    }


    public void addInterns(InternshipResponse internResponse) {
        Log.e(TAG, internResponse.getCompany_name());
        minternLists.add(internResponse);
        notifyDataSetChanged();
    }

    public InternshipResponse getSelectedJob(int position) {
        return minternLists.get(position);
    }

    public void clearInternList() {
        minternLists.clear();
        notifyDataSetChanged();
    }

    public class InternshipHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView company_name_txt, starting_date_txt, starting_date_lable, subject_name_txt,
                internship_type_txt, duration_txt, internship_class_txt, intern_address,intern_time;
        Typeface subject_tf, company_name_typeface, date_tf;
        Typeface my_typeface;

        ImageView company_img, subject_img;
        ItemClickListener itemClickListener;

        public InternshipHolder(View itemView) {
            super(itemView);
            subject_tf = getTypeface("fonts/weblink.ttf");
            company_name_typeface = getTypeface("fonts/ManilaSansBld.otf");
            date_tf = getTypeface("fonts/Cantarell-Regular.ttf");
            intern_address = (TextView) itemView.findViewById(R.id.intern_address);
            intern_time = (TextView) itemView.findViewById(R.id.time);

            company_name_txt = (TextView) itemView.findViewById(R.id.company_name);
            company_name_txt.setTypeface(company_name_typeface);

            starting_date_txt = (TextView) itemView.findViewById(R.id.starting_date);
            starting_date_txt.setTypeface(date_tf);
            // duration_txt = (TextView) itemView.findViewById(R.id.duration);
            subject_name_txt = (TextView) itemView.findViewById(R.id.subject_name);
            subject_name_txt.setTypeface(subject_tf);
            // internship_type_txt = (TextView) itemView.findViewById(R.id.internship_type);
            internship_class_txt = (TextView) itemView.findViewById(R.id.internship_class);
            company_img = (ImageView) itemView.findViewById(R.id.company_image);
            // subject_img = (ImageView) itemView.findViewById(R.id.subject_image);
            starting_date_lable = (TextView) itemView.findViewById(R.id.date_lable);
            starting_date_lable.setTypeface(date_tf);
            internship_class_txt.setTypeface(subject_tf);
            intern_time.setTypeface(subject_tf);
            intern_address.setTypeface(subject_tf);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onclassItemClick(getLayoutPosition());
        }

        Typeface getTypeface(String s) {
            my_typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), s);
            return my_typeface;
        }

    }
}