package com.codeframetech.jobchaiyoo.pojo_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Digition on 7/11/2017.
 */

public class JobResponse implements Serializable {

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
    public String getImageUrl() {
        return imageUrl;
    }

    public String getImage_coverpic() {
        return image_coverpic;
    }

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

    public String getId() {
        return id;
    }

    public void setJob_requirement(String job_requirement) {
        this.job_requirement = job_requirement;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCompany_info(String company_info) {
        this.company_info = company_info;
    }

    public void setDate_in_word(String date_in_word) {
        this.date_in_word = date_in_word;
    }

    public void setJob_discription(String job_discription) {
        this.job_discription = job_discription;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImage_coverpic(String image_coverpic) {
        this.image_coverpic = image_coverpic;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    @SerializedName("id")

    @Expose

    public String id;

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

    @SerializedName("image_cover_pic")

    @Expose
    public String image_coverpic;
    @SerializedName("address")

    @Expose
    public String address;



    @SerializedName("job_requirement")

    @Expose

    public String job_requirement;



    @SerializedName("lastdate")


    @Expose
    public String lastdate;

    public String getIT_company() {
        return it_company;
    }

    public void setIT_company(String it_company) {
        this.it_company = it_company;
    }

    @SerializedName("it_company")


    @Expose
    public String it_company;

    public String getTime_remaining() {
        return time_remaining;
    }

    public void setTime_remaining(String time_remaining) {
        this.time_remaining = time_remaining;
    }

    @SerializedName("time_remaining")


    @Expose
    public String time_remaining;


    public String getAvailable_vacancy() {
        return available_vacancy;
    }

    public void setAvailable_vacancy(String available_vacancy) {
        this.available_vacancy = available_vacancy;
    }

    public String getJob_level() {
        return job_level;
    }

    public void setJob_level(String job_level) {
        this.job_level = job_level;
    }

    @SerializedName("available_vacancy")


    @Expose
    public String available_vacancy;
    @SerializedName("job_level")


    @Expose
    public String job_level;


    public String getCompany_email() {
        return company_email;
    }

    public void setCompany_email(String company_email) {
        this.company_email = company_email;
    }

    @SerializedName("company_email")


    @Expose
    public String company_email;







}
