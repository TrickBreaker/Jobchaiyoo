package  com.codeframetech.jobchaiyoo.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codeframetech.jobchaiyoo.pojo_classes.InternshipResponse;
import com.codeframetech.jobchaiyoo.pojo_classes.JobResponse;
import com.codeframetech.jobchaiyoo.pojo_classes.MeetupResponse;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Digition on 12/8/2017.
 */

public class DatabaseOperation extends SQLiteOpenHelper {
    public DatabaseOperation(Context context) {
        super(context, Database_Initializer.DB_NAME, null, Database_Initializer.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(Database_Initializer.CREATE_MEETUP_TABLE_QUERY);
            sqLiteDatabase.execSQL(Database_Initializer.CREATE_CLASS_TABLE_QUERY);
            sqLiteDatabase.execSQL(Database_Initializer.CREATE_JOB_TABLE_QUERY);
            sqLiteDatabase.execSQL(Database_Initializer.CREATE_USER_FACEBOOK_TABLE_QUERY);

        } catch (SQLException ex) {
            Log.d(TAG, ex.getMessage());


        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(Database_Initializer.DROP_MEETUP_TABLE_QUERY);
        sqLiteDatabase.execSQL(Database_Initializer.DROP_CLASS_TABLE_QUERY);
        sqLiteDatabase.execSQL(Database_Initializer.DROP_JOB_TABLE_QUERY);
        sqLiteDatabase.execSQL(Database_Initializer.DROP_USER_FACEBOOK_DETAILS_TABLE);
        this.onCreate(sqLiteDatabase);

    }

    //ADDING MEETUP VALUES IN THE DATABASE
    public void addMeetupData(MeetupResponse meetupResponse) {

        Log.e("MEETUP DATA ", "VALUES GOT " + meetupResponse.getMeetup_name());
        Log.e("MEETUP DATA ", "VALUES GOT " + meetupResponse.getId());
        Log.e("MEETUP DATA ", "VALUES GOT " + meetupResponse.getImage_location());
        Log.e("MEETUP DATA", "VALUES GOT " + meetupResponse.getOrganizer_name());
        Log.e("MEETUP DATA ", "VALUES GOT " + meetupResponse.getImage_one());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues meetup_values = new ContentValues();
        meetup_values.put(Database_Initializer.MEETUP_ID, meetupResponse.getId());
        meetup_values.put(Database_Initializer.LOCATION, meetupResponse.getLocation());
        meetup_values.put(Database_Initializer.CONTACT_US, meetupResponse.getContact_us());
        meetup_values.put(Database_Initializer.ABOUT_MEETUP, meetupResponse.getAbout_meetup());
        meetup_values.put(Database_Initializer.ORGANIZER_NAME, meetupResponse.getOrganizer_name());
        meetup_values.put(Database_Initializer.IMGAGE_ONE, meetupResponse.getImage_one());
        meetup_values.put(Database_Initializer.MEETUP_NAME, meetupResponse.getMeetup_name());
        meetup_values.put(Database_Initializer.DATE_IN_WORD, meetupResponse.getDate_in_word());
        meetup_values.put(Database_Initializer.IMAGE_LOCATION, meetupResponse.getImage_location());
        meetup_values.put(Database_Initializer.WEBSITE, meetupResponse.getWebsite());
        meetup_values.put(Database_Initializer.TIME, meetupResponse.getTime());
        meetup_values.put(Database_Initializer.EVENT_TYPE, meetupResponse.getEvent_type());
        meetup_values.put(Database_Initializer.MEETUP_DETAILS, meetupResponse.getMeetup_details());
        //meetup_values.put(AppConfig.Database_Initializer.MEETUP_DETAILS,meetups.getMeetup_details());
        long result = db.insert(Database_Initializer.MEETUP_TABLE, null, meetup_values);
        // long result= db.insertWithOnConflict(Database_Initializer.MEETUP_TABLE,null,meetup_values,SQLiteDatabase.CONFLICT_IGNORE);

        //update each time for new data in database
        // long result= db.update(Database_Initializer.MEETUP_TABLE,meetup_values,Database_Initializer.MEETUP_ID + " = ?" ,new String[]{meetupResponse.getId()});
        db.close();
        if (result == -1) {
            Log.e("MEETUP NOT INSERTED", String.valueOf(result));

        } else {
            Log.e("MEETUP INSERTED", String.valueOf(result));
        }

        // db.insertWithOnConflict(AppConfig.Database_Initializer.TABLE_NAME,null,meetup_values,SQLiteDatabase.CONFLICT_IGNORE);


    }


    //ADDING JOBS DATA IN THE DATABASE
    public void addJobData(JobResponse jobResponse) {

        Log.e("JOB DATA ", "VALUES GOT " + jobResponse.getJob_requirement());
        Log.e("JOB DATA ", "VALUES GOT " + jobResponse.getDate_in_word());
        Log.e("JOB DATA  ", "VALUES GOT " + jobResponse.getImageUrl());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues jobs_values = new ContentValues();
        jobs_values.put(Database_Initializer.JOB_ID, jobResponse.getId());
        jobs_values.put(Database_Initializer.JOB_REQUIREMENT, jobResponse.getJob_requirement());
        jobs_values.put(Database_Initializer.JOB_DATE_IN_WORD, jobResponse.getDate_in_word());
        jobs_values.put(Database_Initializer.JOB_COMPANY_NAME, jobResponse.getName());
        jobs_values.put(Database_Initializer.QUALIFICATION, jobResponse.getQualification());
        jobs_values.put(Database_Initializer.POST, jobResponse.getPost());
        jobs_values.put(Database_Initializer.IMAGE_PROFILE_PIC, jobResponse.getImageUrl());
        jobs_values.put(Database_Initializer.IMAGE_COVER_PIC, jobResponse.getImage_coverpic());
        jobs_values.put(Database_Initializer.JOB_APPLY_LAST_DATE, jobResponse.getLastdate());
        jobs_values.put(Database_Initializer.ADDRESS, jobResponse.getAddress());
        jobs_values.put(Database_Initializer.JOB_DISCIRPTION, jobResponse.getJob_discription());
        jobs_values.put(Database_Initializer.AVAILABLE_VACANCY, jobResponse.getAvailable_vacancy());
        jobs_values.put(Database_Initializer.JOB_LEVEL, jobResponse.getJob_level());
        jobs_values.put(Database_Initializer.TIME_REMAINING, jobResponse.getTime_remaining());
        long result = db.insert(Database_Initializer.JOB_TABLE, null, jobs_values);

        // long result= db.insertWithOnConflict(Database_Initializer.JOB_TABLE,null,jobs_values,SQLiteDatabase.CONFLICT_IGNORE);
        // long result= db.update(Database_Initializer.JOB_TABLE,jobs_values,Database_Initializer.JOB_ID + " = ?" ,new String[]{jobResponse.getId()});
        db.close();
        if (result == -1) {
            Log.e("JOB NOT INSERTED", String.valueOf(result));

        } else {
            Log.e("JOB DATA INSERTED", String.valueOf(result));
        }

        // db.insertWithOnConflict(AppConfig.Database_Initializer.TABLE_NAME,null,meetup_values,SQLiteDatabase.CONFLICT_IGNORE);


    }

    //ADDING CLASS DATA IN THE DATABASE
    public void addClassData(InternshipResponse classResponse) {

        Log.e("CLASS DATA ", "VALUES GOT " + classResponse.getCompany_image());
        Log.e("CLASS DATA  ", "VALUES GOT " + classResponse.getSubject_name());
        Log.e("CLASS DATA  ", "VALUES GOT " + classResponse.getCompany_name());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues class_values = new ContentValues();
        class_values.put(Database_Initializer.CLASS_ID, classResponse.getId());
        class_values.put(Database_Initializer.COMPANY_IMAGE, classResponse.getCompany_image());
        class_values.put(Database_Initializer.CLASS_COMPANY_NAME, classResponse.getCompany_name());
        class_values.put(Database_Initializer.STARTING_DATE, classResponse.getStarting_date());
        class_values.put(Database_Initializer.SUBJECT_NAME, classResponse.getSubject_name());
        class_values.put(Database_Initializer.DURATION, classResponse.getDuration());
        class_values.put(Database_Initializer.CLASS_LOCATION, classResponse.getLocation());
        class_values.put(Database_Initializer.CLASS_CONTACT_US, classResponse.getContact_us());
        class_values.put(Database_Initializer.CLASS_TIME, classResponse.getTime());
        class_values.put(Database_Initializer.COMPANY_WEBSITE, classResponse.getWebsite());
        class_values.put(Database_Initializer.INTERN_CLASS, classResponse.getIntern_class());
        class_values.put(Database_Initializer.MORNING_TIME, classResponse.getMorning_time());
        class_values.put(Database_Initializer.DAY_TIME, classResponse.getDay_time());
        class_values.put(Database_Initializer.EVENING_TIME, classResponse.getEvening_time());
        // class_values.put(Database_Initializer.INTERN_CLASS, classResponse.getInternship_type());
        class_values.put(Database_Initializer.SUBJECT_IMAGE, classResponse.getSubject_image());
        try {
            //long result= db.insertWithOnConflict(Database_Initializer.CLASS_TABLE,null,class_values,SQLiteDatabase.CONFLICT_IGNORE);
            long result = db.insert(Database_Initializer.CLASS_TABLE, null, class_values);


            // long result= db.update(Database_Initializer.CLASS_TABLE,class_values,Database_Initializer.CLASS_ID + " = ?" ,new String[]{classResponse.getId()});
            // long result =
            if (result == -1) {
                Log.e("CLASS DATA NOT INSERTED", String.valueOf(result));

            } else {
                Log.e("CLASS DATA INSERTED", String.valueOf(result));
            }

        } catch (SQLException ex) {

        }

        db.close();


        // db.insertWithOnConflict(AppConfig.Database_Initializer.TABLE_NAME,null,meetup_values,SQLiteDatabase.CONFLICT_IGNORE);


    }

    //ADDING CLASS DATA IN THE DATABASE
    public void addUserFacebookData(String user_name, String user_email, String profile_pic) {

        Log.e("CLASS DATA ", "VALUES GOT " + user_name);
        /*Log.e("CLASS DATA  ", "VALUES GOT " + user_email);
        Log.e("CLASS DATA  ", "VALUES GOT " + profile_pic);*/
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues facebook_values = new ContentValues();
        facebook_values.put(Database_Initializer.USER_NAME, user_name);
        facebook_values.put(Database_Initializer.USER_EMAIL, user_email);
        facebook_values.put(Database_Initializer.USER_PROFILE_PIC, profile_pic);

        try {
            //long result= db.insertWithOnConflict(Database_Initializer.CLASS_TABLE,null,class_values,SQLiteDatabase.CONFLICT_IGNORE);
            long result = db.insert(Database_Initializer.USER_FACEBOOK_DETAILS_TABLE, null, facebook_values);


            // long result= db.update(Database_Initializer.CLASS_TABLE,class_values,Database_Initializer.CLASS_ID + " = ?" ,new String[]{classResponse.getId()});
            // long result =
            if (result == -1) {
                Log.e(" DATA NOT INSERTED", String.valueOf(result));

            } else {
                Log.e("FACE DATA INSERTED", String.valueOf(result));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        db.close();


        // db.insertWithOnConflict(AppConfig.Database_Initializer.TABLE_NAME,null,meetup_values,SQLiteDatabase.CONFLICT_IGNORE);


    }


    //GETTING MEETUP DATA BACK FROM THE DATABASE
    public List<MeetupResponse> getMeetupData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Database_Initializer.GET_MEETUP_QUERY, null);
        List<MeetupResponse> meetupResponseList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    MeetupResponse meetupResponse = new MeetupResponse();
                    meetupResponse.setMeetup_name(cursor.getString(cursor.getColumnIndex(Database_Initializer.MEETUP_NAME)));
                    meetupResponse.setAbout_meetup(cursor.getString(cursor.getColumnIndex(Database_Initializer.ABOUT_MEETUP)));
                    meetupResponse.setContact_us(cursor.getString(cursor.getColumnIndex(Database_Initializer.CONTACT_US)));
                    meetupResponse.setImage_one(cursor.getString(cursor.getColumnIndex(Database_Initializer.IMGAGE_ONE)));
                    meetupResponse.setDate_in_word(cursor.getString(cursor.getColumnIndex(Database_Initializer.DATE_IN_WORD)));
                    meetupResponse.setImage_location(cursor.getString(cursor.getColumnIndex(Database_Initializer.IMAGE_LOCATION)));
                    meetupResponse.setTime(cursor.getString(cursor.getColumnIndex(Database_Initializer.TIME)));
                    meetupResponse.setId(cursor.getString(cursor.getColumnIndex(Database_Initializer.MEETUP_ID)));
                    meetupResponse.setWebsite(cursor.getString(cursor.getColumnIndex(Database_Initializer.WEBSITE)));
                    meetupResponse.setOrganizer_name(cursor.getString(cursor.getColumnIndex(Database_Initializer.ORGANIZER_NAME)));
                    meetupResponse.setLocation(cursor.getString(cursor.getColumnIndex(Database_Initializer.LOCATION)));
                    meetupResponse.setEvent_type(cursor.getString(cursor.getColumnIndex(Database_Initializer.EVENT_TYPE)));
                    meetupResponse.setMeetup_details(cursor.getString(cursor.getColumnIndex(Database_Initializer.MEETUP_DETAILS)));
                    meetupResponseList.add(meetupResponse);

                }
                while (cursor.moveToNext());
            }

        }

        return meetupResponseList;


    }


    //GETTING CLASS DATA BACK FROM THE DATABASE
    public List<InternshipResponse> getClassData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Database_Initializer.GET_CLASS_QUERY, null);
        List<InternshipResponse> classResponseList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    InternshipResponse classResponse = new InternshipResponse();
                    classResponse.setId(cursor.getString(cursor.getColumnIndex(Database_Initializer.CLASS_ID)));
                    classResponse.setCompany_image(cursor.getString(cursor.getColumnIndex(Database_Initializer.COMPANY_IMAGE)));
                    classResponse.setStarting_date(cursor.getString(cursor.getColumnIndex(Database_Initializer.STARTING_DATE)));
                    classResponse.setSubject_name(cursor.getString(cursor.getColumnIndex(Database_Initializer.SUBJECT_NAME)));
                    classResponse.setDuration(cursor.getString(cursor.getColumnIndex(Database_Initializer.DURATION)));
                    classResponse.setLocation(cursor.getString(cursor.getColumnIndex(Database_Initializer.CLASS_LOCATION)));
                    classResponse.setContact_us(cursor.getString(cursor.getColumnIndex(Database_Initializer.CLASS_CONTACT_US)));
                    classResponse.setTime(cursor.getString(cursor.getColumnIndex(Database_Initializer.CLASS_TIME)));
                    classResponse.setWebsite(cursor.getString(cursor.getColumnIndex(Database_Initializer.COMPANY_WEBSITE)));
                    classResponse.setCompany_name(cursor.getString(cursor.getColumnIndex(Database_Initializer.CLASS_COMPANY_NAME)));
                    classResponse.setSubject_image(cursor.getString(cursor.getColumnIndex(Database_Initializer.SUBJECT_IMAGE)));
                    classResponse.setIntern_class(cursor.getString(cursor.getColumnIndex(Database_Initializer.INTERN_CLASS)));
                    classResponse.setMorning_time(cursor.getString(cursor.getColumnIndex(Database_Initializer.MORNING_TIME)));
                    classResponse.setDay_time(cursor.getString(cursor.getColumnIndex(Database_Initializer.DAY_TIME)));
                    classResponse.setEvening_time(cursor.getString(cursor.getColumnIndex(Database_Initializer.EVENING_TIME)));
                    classResponseList.add(classResponse);


                }
                while (cursor.moveToNext());
            }

        }

        return classResponseList;


    }

    //GETTING CLASS DATA BACK FROM THE DATABASE
    public Cursor getUserFacebookData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Database_Initializer.GET_FACEBOOK_DETAILS_QUERY, null);
        return cursor;


    }


    //GETTING JOB DATA BACK FROM THE DATABASE
    public List<JobResponse> getJobData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Database_Initializer.GET_JOB_QUERY, null);
        List<JobResponse> jobResponseList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    JobResponse jobResponse = new JobResponse();
                    jobResponse.setJob_requirement(cursor.getString(cursor.getColumnIndex(Database_Initializer.JOB_REQUIREMENT)));
                    jobResponse.setJob_discription(cursor.getString(cursor.getColumnIndex(Database_Initializer.JOB_DISCIRPTION)));
                    jobResponse.setDate_in_word(cursor.getString(cursor.getColumnIndex(Database_Initializer.JOB_DATE_IN_WORD)));
                    jobResponse.setName(cursor.getString(cursor.getColumnIndex(Database_Initializer.JOB_COMPANY_NAME)));
                    jobResponse.setQualification(cursor.getString(cursor.getColumnIndex(Database_Initializer.QUALIFICATION)));
                    jobResponse.setPost(cursor.getString(cursor.getColumnIndex(Database_Initializer.POST)));
                    jobResponse.setImageUrl(cursor.getString(cursor.getColumnIndex(Database_Initializer.IMAGE_PROFILE_PIC)));
                    jobResponse.setImage_coverpic(cursor.getString(cursor.getColumnIndex(Database_Initializer.IMAGE_COVER_PIC)));
                    jobResponse.setId(cursor.getString(cursor.getColumnIndex(Database_Initializer.JOB_ID)));
                    jobResponse.setLastdate(cursor.getString(cursor.getColumnIndex(Database_Initializer.JOB_APPLY_LAST_DATE)));
                    jobResponse.setAddress(cursor.getString(cursor.getColumnIndex(Database_Initializer.ADDRESS)));
                    jobResponse.setAvailable_vacancy(cursor.getString(cursor.getColumnIndex(Database_Initializer.AVAILABLE_VACANCY)));
                    jobResponse.setJob_level(cursor.getString(cursor.getColumnIndex(Database_Initializer.JOB_LEVEL)));
                    jobResponse.setTime_remaining(cursor.getString(cursor.getColumnIndex(Database_Initializer.TIME_REMAINING)));

                    jobResponseList.add(jobResponse);

                }
                while (cursor.moveToNext());
            }

        }


        return jobResponseList;


    }
    //methods to delete the database data

    public void deleteMeetupData(MeetupResponse meetupResponse) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Database_Initializer.MEETUP_TABLE, Database_Initializer.MEETUP_ID + " = ?", new String[]{meetupResponse.getId()});

    }

    public void deleteJobData(JobResponse jobResponse) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Database_Initializer.JOB_TABLE, Database_Initializer.JOB_ID + " = ?", new String[]{jobResponse.getId()});


    }

    public void deleteClassData(InternshipResponse internResponse) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Database_Initializer.CLASS_TABLE, Database_Initializer.CLASS_ID + " = ?", new String[]{internResponse.getId()});
    }

}
