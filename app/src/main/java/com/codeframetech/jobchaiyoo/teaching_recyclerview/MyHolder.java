package com.codeframetech.jobchaiyoo.teaching_recyclerview;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeframetech.jobchaiyoo.R;
import com.codeframetech.jobchaiyoo.helpers.ItemClickListener;

/**
 * Created by Digition on 6/14/2016.
 */
public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView nameTxt, qualification_txt, post_txt, address_txt,
            lastdate_txt, date_lable_txt;
    ImageView img;
    ItemClickListener itemClickListener;
    Typeface my_typeface, subject_tf, company_name_typeface, date_tf;


    public MyHolder(View itemView) {
        super(itemView);
       // subject_tf = getTypeface("fonts/weblink.ttf");
       // company_name_typeface = getTypeface("fonts/ManilaSansBld.otf");
       // date_tf = getTypeface("fonts/Cantarell-Regular.ttf");

        nameTxt = (TextView) itemView.findViewById(R.id.nameTxt);
       // nameTxt.setTypeface(company_name_typeface);
        qualification_txt = (TextView) itemView.findViewById(R.id.qualification_text);
       // qualification_txt.setTypeface(subject_tf);
        post_txt = (TextView) itemView.findViewById(R.id.job_title_text);
        // post_txt.setTypeface(subject_tf);
        address_txt = (TextView) itemView.findViewById(R.id.address_text);
        //address_txt.setTypeface(subject_tf);
        lastdate_txt = (TextView) itemView.findViewById(R.id.date_text);
      //  lastdate_txt.setTypeface(date_tf);
      //  date_lable_txt = (TextView) itemView.findViewById(R.id.date_lable);
     //   date_lable_txt.setTypeface(date_tf);
        img = (ImageView) itemView.findViewById(R.id.company_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

   /* public Typeface getTypeface(String s) {
        my_typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), s);

        return my_typeface;

    }*/


}
