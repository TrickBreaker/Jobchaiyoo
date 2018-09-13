package com.codeframetech.jobchaiyoo.pojo_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Digition on 7/13/2017.
 */

public class InternshipResponse implements Serializable{


    public void setMorning_time(String morning_time) {
        this.morning_time = morning_time;
    }

    public void setDay_time(String day_time) {
        this.day_time = day_time;
    }

    public void setEvening_time(String evening_time) {
        this.evening_time = evening_time;
    }

    @SerializedName("company_image")

    @Expose


    public String company_image;



    @SerializedName("id")

    @Expose

    public String id;


    public String getCompany_image() {
        return company_image;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getStarting_date() {
        return starting_date;
    }

    public String getInternship_type() {
        return internship_type;
    }

    public String getSubject_image() {
        return subject_image;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public String getDuration() {
        return duration;
    }

    @SerializedName("company_name")


    @Expose
    public String company_name;
    @SerializedName("starting_date")

    @Expose
    public String starting_date;
    @SerializedName("internship_type")

    @Expose
    public String internship_type;
    @SerializedName("subject_image")

    @Expose
    public String subject_image;


    @SerializedName("subject_name")

    @Expose
    public String subject_name;
    @SerializedName("duration")

    @Expose
    public String duration;

    @SerializedName("location")

    @Expose
    public String location;
    @SerializedName("contact_us")
    @Expose
    public String contact_us;

    @SerializedName("time")
    @Expose
    public String time;

    public String getLocation() {
        return location;
    }

    public String getContact_us() {
        return contact_us;
    }

    public String getTime() {
        return time;
    }

    public void setCompany_image(String company_image) {
        this.company_image = company_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setStarting_date(String starting_date) {
        this.starting_date = starting_date;
    }

    public void setInternship_type(String internship_type) {
        this.internship_type = internship_type;
    }

    public void setSubject_image(String subject_image) {
        this.subject_image = subject_image;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContact_us(String contact_us) {
        this.contact_us = contact_us;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setIntern_logo(String intern_logo) {
        this.intern_logo = intern_logo;
    }

    public void setCourse_overview(String course_overview) {
        this.course_overview = course_overview;
    }

    public void setIntern_class(String intern_class) {
        this.intern_class = intern_class;
    }

    public String getWebsite() {
        return website;
    }

    public String getIntern_logo() {
        return intern_logo;
    }

    @SerializedName("website")

    @Expose
    public String website;
    @SerializedName("intern_logo")

    @Expose
    public String intern_logo;

    public String getCourse_overview() {
        return course_overview;
    }

    @SerializedName("course_overview")

    @Expose

    public String course_overview;
    @SerializedName("intern_class")

    @Expose

    public String intern_class;

    public String getIntern_class() {
        return intern_class;
    }
    public String getIT_company() {
        return it_company;
    }

    public void setIT_company(String it_company) {
        this.it_company = it_company;
    }

    @SerializedName("it_company")


    @Expose
    public String it_company;


    @SerializedName("morning_time")


    @Expose
    public String morning_time;



    @SerializedName("day_time")


    @Expose
    public String day_time;

    public String getMorning_time() {
        return morning_time;
    }

    public String getDay_time() {
        return day_time;
    }

    public String getEvening_time() {
        return evening_time;
    }

    @SerializedName("evening_time")


    @Expose
    public String evening_time;


    @SerializedName("facebook_link")


    @Expose
    public String facebook_link;

    public String getFacebook_link() {
        return facebook_link;
    }

    public void setFacebook_link(String facebook_link) {
        this.facebook_link = facebook_link;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    @SerializedName("contact_email")


    @Expose
    public String contact_email;

    public String getEnrol_link() {
        return enrol_link;
    }

    public void setEnrol_link(String enrol_link) {
        this.enrol_link = enrol_link;
    }

    @SerializedName("enrol_link")


    @Expose
    public String enrol_link;
}
