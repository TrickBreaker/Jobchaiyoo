package  com.codeframetech.jobchaiyoo.database;

/**
 * Created by Digition on 12/10/2017.
 */

public class Database_Initializer {
    static final String DB_NAME = "com.codeframetech.jobchaiyoo.db";
    static final int DB_VERSION = 1;
    //TABLE NAME
    static final String MEETUP_TABLE = "meetup_table";
    static final String JOB_TABLE = "job_table";
    static final String CLASS_TABLE = "class_table";
    static final String USER_FACEBOOK_DETAILS_TABLE = "user_facebook_details_table";


    //DROP TABLE QWERY
    static final String DROP_MEETUP_TABLE_QUERY = "DROP TABLE IF EXISTS " + MEETUP_TABLE;
    static final String DROP_JOB_TABLE_QUERY = "DROP TABLE IF EXISTS " + JOB_TABLE;
    static final String DROP_CLASS_TABLE_QUERY = "DROP TABLE IF EXISTS " + CLASS_TABLE;
    static final String DROP_USER_FACEBOOK_DETAILS_TABLE = "DROP TABLE IF EXISTS " + USER_FACEBOOK_DETAILS_TABLE;

    //GET DATA QWERY FROM TABLE
    static final String GET_MEETUP_QUERY = "SELECT * FROM " + MEETUP_TABLE;
    static final String GET_JOB_QUERY = "SELECT * FROM " + JOB_TABLE;
    static final String GET_CLASS_QUERY = "SELECT * FROM " + CLASS_TABLE;
    static final String GET_FACEBOOK_DETAILS_QUERY = "SELECT * FROM " + USER_FACEBOOK_DETAILS_TABLE;


    //ALL COLUMN FIELD OF MEETUP TABLE
    static final String MEETUP_ID = "id";
    static final String LOCATION = "location";
    static final String CONTACT_US = "contact_us";
    static final String ABOUT_MEETUP = "about_meetup";
    static final String ORGANIZER_NAME = "organizer_name";
    static final String IMGAGE_ONE = "image_one";
    static final String MEETUP_NAME = "meetup_name";
    static final String DATE_IN_WORD = "date_in_word";
    static final String IMAGE_LOCATION = "image_location";
    static final String WEBSITE = "website";
    static final String EVENT_TYPE = "event_type";
    static final String MEETUP_DETAILS = "meetup_details";
    static final String TIME = "time";


    //ALL COLUMN FIELD OF JOB TABLE
    static final String JOB_ID = "id";
    static final String JOB_REQUIREMENT = "job_requirement";
    //public static final String company_info = "contact_us";
    static final String JOB_DATE_IN_WORD = "date_in_word";
    static final String JOB_COMPANY_NAME = "name";
    static final String QUALIFICATION = "qualification";
    static final String POST = "post";
    static final String IMAGE_PROFILE_PIC = "imageurl";
    static final String IMAGE_COVER_PIC = "image_cover_pic";
    static final String ADDRESS = "address";
    static final String AVAILABLE_VACANCY = "available_vacancy";
    static final String JOB_LEVEL = "job_level";
    static final String TIME_REMAINING = "time_remaining";
    //public static final String MEETUP_DETAILS = "meetup_details";
    static final String JOB_APPLY_LAST_DATE = "lastdate";
    static final String JOB_DISCIRPTION = "job_discription";


    //ALL COLUMN FIELD OF CLASS TABLE
    static final String CLASS_ID = "id";
    static final String COMPANY_IMAGE = "company_image";
    static final String CLASS_COMPANY_NAME = "company_name";
    static final String STARTING_DATE = "starting_date";
    //public static final String INTERNSHIP_TYPE = "organizer_name";
    static final String SUBJECT_IMAGE = "subject_image";
    static final String SUBJECT_NAME = "subject_name";
    static final String DURATION = "duration";
    static final String CLASS_LOCATION = "location";
    static final String CLASS_CONTACT_US = "contact_us";
    static final String CLASS_TIME = "time";
    static final String COMPANY_WEBSITE = "website";
    static final String INTERN_CLASS = "intern_class";
    static final String MORNING_TIME = "morning_time";
    static final String DAY_TIME = "day_time";
    static final String EVENING_TIME = "evening_time";



    //ALL COLUMN FIELD OF USER_FACEBOOK TABLE
    static final String FACEBOOK_USER_ID = "id";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PROFILE_PIC = "profile_pic";


    //QWERY TO CREATE MEETUP TABLE
    static final String CREATE_MEETUP_TABLE_QUERY = "CREATE TABLE " + MEETUP_TABLE +
            "(" + MEETUP_ID + " PRIMARY KEY," +
            LOCATION + " VARCHAR not null," +
            CONTACT_US + " VARCHAR not null," +
            ABOUT_MEETUP + " VARCHAR not null," +
            ORGANIZER_NAME + " VARCHAR not null," +
            IMGAGE_ONE + " VARCHAR not null," +
            MEETUP_NAME + " VARCHAR not null," +
            DATE_IN_WORD + " VARCHAR not null," +
            IMAGE_LOCATION + " VARCHAR not null," +
            WEBSITE + " VARCHAR not null," +
            EVENT_TYPE + " VARCHAR not null," +
            MEETUP_DETAILS + " VARCHAR not null," +
            TIME + " VARCHAR not null)";


    //QWERY TO CREATE JOB TABLE
    static final String CREATE_JOB_TABLE_QUERY = "CREATE TABLE " + JOB_TABLE +
            "(" + JOB_ID + " PRIMARY KEY," +
            JOB_REQUIREMENT + " VARCHAR not null," +
            JOB_DATE_IN_WORD + " VARCHAR not null," +
            JOB_COMPANY_NAME + " VARCHAR not null," +
            QUALIFICATION + " VARCHAR not null," +
            POST + " VARCHAR not null," +
            IMAGE_PROFILE_PIC + " VARCHAR not null," +
            IMAGE_COVER_PIC + " VARCHAR not null," +
            ADDRESS + " VARCHAR not null," +
            JOB_DISCIRPTION + " VARCHAR not null," +
            AVAILABLE_VACANCY + " VARCHAR not null," +
            JOB_LEVEL + " VARCHAR not null," +
            TIME_REMAINING + " VARCHAR not null," +
            JOB_APPLY_LAST_DATE + " VARCHAR not null)";


    //QWERY TO CREATE FACEBOOK USER TABLE
    static final String CREATE_USER_FACEBOOK_TABLE_QUERY = "CREATE TABLE " + USER_FACEBOOK_DETAILS_TABLE +
            "(" + FACEBOOK_USER_ID + " PRIMARY KEY," +
            USER_NAME + " VARCHAR not null," +
            USER_EMAIL + " VARCHAR not null," +
            USER_PROFILE_PIC + " VARCHAR not null)";


    //QWERY TO CREATE USER_FACEBOOK_DETAIL TABLE
    static final String CREATE_CLASS_TABLE_QUERY = "CREATE TABLE " + CLASS_TABLE +
            "(" + CLASS_ID + " PRIMARY KEY," +
            COMPANY_IMAGE + " VARCHAR not null," +
            CLASS_COMPANY_NAME + " VARCHAR not null," +
            STARTING_DATE + " VARCHAR not null," +
            SUBJECT_NAME + " VARCHAR not null," +
            DURATION + " VARCHAR not null," +
            CLASS_LOCATION + " VARCHAR not null," +
            CLASS_CONTACT_US + " VARCHAR not null," +
            CLASS_TIME + " VARCHAR not null," +
            COMPANY_WEBSITE + " VARCHAR not null," +
            SUBJECT_IMAGE + " VARCHAR not null," +
            MORNING_TIME + " VARCHAR not null," +
            DAY_TIME + " VARCHAR not null," +
            EVENING_TIME + " VARCHAR not null," +
            INTERN_CLASS + " VARCHAR not null)";


}