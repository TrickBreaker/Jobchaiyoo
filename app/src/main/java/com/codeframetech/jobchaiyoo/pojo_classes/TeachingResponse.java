package com.codeframetech.jobchaiyoo.pojo_classes;

/**
 * Created by Digition on 12/1/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TeachingResponse {

    public String getJob_requirement() {
        return job_requirement;
    }

    public String getCompany_info() {
        return company_info;
    }

    public String getDate_in_word() {
        return date_in_word;
    }

    public String getJob_discription() {
        return job_discription;
    }

    @SerializedName("job_requirement")

    @Expose

    public String job_requirement;
    @SerializedName("company_info")

    @Expose
    public String company_info;
    @SerializedName("date_in_word")

    @Expose
    public String date_in_word;
    @SerializedName("job_discription")

    @Expose
    public String job_discription;
    @SerializedName("name")

    @Expose
    public String name;


    @SerializedName("qualification")

    @Expose
    public String qualification;
    @SerializedName("post")

    @Expose
    public String post;

    @SerializedName("imageurl")

    @Expose
    public String imageUrl;

    @SerializedName("imageurl_cover_pic")

    @Expose
    public String imageUrl_coverpic;
    @SerializedName("address")

    @Expose
    public String address;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageUrl_coverpic() {
        return imageUrl_coverpic;
    }

    @SerializedName("lastdate")


    @Expose
    public String lastdate;

    public String getName() {
        return name;
    }

    public String getQualification() {
        return qualification;
    }

    public String getPost() {
        return post;
    }


    public String getAddress() {
        return address;
    }

    public String getLastdate() {
        return lastdate;
    }


}
