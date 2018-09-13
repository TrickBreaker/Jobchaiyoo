package com.codeframetech.jobchaiyoo.pojo_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Digition on 7/11/2017.
 */

public class MeetupResponse implements Serializable {


    public String getId() {
        return id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContact_us(String contact_us) {
        this.contact_us = contact_us;
    }

    public void setAbout_meetup(String about_meetup) {
        this.about_meetup = about_meetup;
    }

    public void setOrganizer_name(String organizer_name) {
        this.organizer_name = organizer_name;
    }

    public void setImage_one(String image_one) {
        this.image_one = image_one;
    }

    public void setMeetup_name(String meetup_name) {
        this.meetup_name = meetup_name;
    }

    public void setDate_in_word(String date_in_word) {
        this.date_in_word = date_in_word;
    }

    public void setImage_logo(String image_logo) {
        this.image_logo = image_logo;
    }

    public void setImage_location(String image_location) {
        this.image_location = image_location;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public String getImage_logo() {
        return image_logo;
    }

    public String getImage_location() {
        return image_location;
    }

    public String getWebsite() {
        return website;
    }

    public String getLocation() {
        return location;
    }

    public String getContact_us() {
        return contact_us;
    }

    public String getAbout_meetup() {
        return about_meetup;
    }

    public String getOrganizer_name() {
        return organizer_name;
    }

    public String getImage_one() {
        return image_one;
    }

    public String getMeetup_name() {
        return meetup_name;
    }

    @SerializedName("id")

    @Expose
    public String id;


    @SerializedName("location")

    @Expose
    public String location;
    @SerializedName("contact_us")
    @Expose
    public String contact_us;
    @SerializedName("about_meetup")
    @Expose
    public String about_meetup;
    @SerializedName("organizer_name")
    @Expose
    public String organizer_name;
    @SerializedName("image_one")
    @Expose
    public String image_one;
    @SerializedName("meetup_name")
    @Expose
    public String meetup_name;

    public String getDate_in_word() {
        return date_in_word;
    }

    @SerializedName("date_in_word")

    @Expose
    public String date_in_word;

    @SerializedName("image_logo")

    @Expose
    public String image_logo;

    @SerializedName("image_location")

    @Expose
    public String image_location;

    @SerializedName("website")

    @Expose
    public String website;
    @SerializedName("time")
    @Expose
    public String time;

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    @SerializedName("event_type")
    @Expose
    public String event_type;

    @Expose
    public String meetup_details;
    @SerializedName("meetup_details")



    public String getMeetup_details() {
        return meetup_details;
    }

    public void setMeetup_details(String meetup_details) {
        this.meetup_details = meetup_details;

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


    public String getRegister_link() {
        return register_link;
    }

    public void setRegister_link(String register_link) {
        this.register_link = register_link;
    }

    @SerializedName("register_link")


    @Expose

    public String register_link;

}
