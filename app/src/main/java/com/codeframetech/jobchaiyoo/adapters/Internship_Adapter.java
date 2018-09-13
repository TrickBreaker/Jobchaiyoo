package  com.codeframetech.jobchaiyoo.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeframetech.jobchaiyoo.R;
import com.codeframetech.jobchaiyoo.pojo_classes.InternshipResponse;
import com.codeframetech.jobchaiyoo.helpers.ItemClickListener;
import com.codeframetech.jobchaiyoo.helpers.PicassoDownloader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Digition on 6/14/2016.
 */
public class Internship_Adapter extends RecyclerView.Adapter<Internship_Adapter.InternshipHolder> {
    private ArrayList<InternshipResponse> minternLists;
    private final ItemClickListener mListener;
    public static final String TAG = Internship_Adapter.class.getSimpleName();

    public Internship_Adapter(ItemClickListener listener) {
        minternLists = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public InternshipHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.internship_model, parent, false);
        return new InternshipHolder(view);
    }

    @Override
    public void onBindViewHolder(InternshipHolder intern_holder, int position) {
        InternshipResponse internResponse = minternLists.get(position);
        intern_holder.company_name_txt.setText(internResponse.getCompany_name());
        intern_holder.subject_name_txt.setText(internResponse.getSubject_name());
        intern_holder.starting_date_txt.setText(internResponse.getStarting_date());
        intern_holder.class_time_txt.setText(internResponse.getTime());

        //intern_holder.internship_type_txt.setText(internResponse.getInternship_type());
       // intern_holder.duration_txt.setText(internResponse.getDuration());
       PicassoDownloader.downloadImage(intern_holder.itemView.getContext(),internResponse.getCompany_image(),intern_holder.company_img);


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

    public  class InternshipHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.company_name)
        TextView company_name_txt;
        @BindView(R.id.starting_date)
        TextView   starting_date_txt;
        @BindView(R.id.subject_name)
        TextView  subject_name_txt;
       @BindView(R.id.class_time)
       TextView class_time_txt;
        @BindView(R.id.date_lable)
        TextView starting_date_lable;
        @BindView(R.id.company_image)
        ImageView company_img;
        InternshipHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           mListener.onclassItemClick(getLayoutPosition());
        }

        /*Typeface getTypeface(String s) {
            my_typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), s);
            return my_typeface;
        }*/

    }
}