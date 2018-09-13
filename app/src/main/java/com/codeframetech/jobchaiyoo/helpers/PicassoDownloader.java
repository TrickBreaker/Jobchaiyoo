package com.codeframetech.jobchaiyoo.helpers;

import android.content.Context;
import android.widget.ImageView;

import com.codeframetech.jobchaiyoo.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Digition on 6/14/2016.
 */
public class PicassoDownloader {

    public static void downloadImage(Context c, String imageUrl, ImageView img) {
        if (imageUrl != null && imageUrl.length() > 0) {
            Picasso.get().load(imageUrl).placeholder(R.drawable.placeholder).into(img);

        } else {

            Picasso.get().load(R.drawable.placeholder).into(img);
        }
    }
    public static void downloadNextImage(Context c, String imageUrl_coverpic, ImageView cover_image) {
        if (imageUrl_coverpic != null && imageUrl_coverpic.length() > 0) {
            Picasso.get().load(imageUrl_coverpic).placeholder(R.drawable.placeholder).into(cover_image);

        } else {

            Picasso.get().load(R.drawable.mycover).into(cover_image);
        }
    }
    public static void downloadNext_Image(Context c, String imageUrl_coverpic, ImageView cover_image) {
        if (imageUrl_coverpic != null && imageUrl_coverpic.length() > 0) {
            Picasso.get().load(imageUrl_coverpic).placeholder(R.drawable.mycover).into(cover_image);

        } else {

            Picasso.get().load(R.drawable.mycover).into(cover_image);
        }
    }
    public static void picassoResize(Context c,String imageurl, ImageView imageView,int height,int width)
    {
        if (imageurl != null && imageurl.length() > 0) {
            Picasso.get().load(imageurl).resize(height,width).centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(imageView);
        }

    }
}
