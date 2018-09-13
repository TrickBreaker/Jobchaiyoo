package  com.codeframetech.jobchaiyoo.controller;


import com.codeframetech.jobchaiyoo.constants_store_room.STORE_URL;

/**
 * Created by Digition on 9/17/2017.
 */
public class AppConfig {

    public String URL_INTERNSHIP = STORE_URL.STORED_URL_INTERNSHIP;
    public String URL_JOBS = STORE_URL.STORED_URL_JOBS;
    public String URL_MEETUPS = STORE_URL.STORED_URL_MEETUPS;
    public String URL_JOBS_NOTIFICATION = STORE_URL.STORED_URL_JOBS_NOTIFICATION;

    public void checkLink(String selected_company) {

        switch (selected_company) {
            case STORE_URL.LEAPFROG_ACADEMY.STORED_LEAPFROG_ACADEMY:
                this.URL_JOBS = STORE_URL.LEAPFROG_ACADEMY.STORED_URL_JOBS;
                this.URL_INTERNSHIP = STORE_URL.LEAPFROG_ACADEMY.STORED_URL_INTERNSHIP;
                this.URL_MEETUPS = STORE_URL.LEAPFROG_ACADEMY.STORED_URL_MEETUPS;
                break;
            case STORE_URL.CODEFRAME_TECHNOLOGY.STORED_CODEFRAME_TECHNOLOGY:
                this.URL_JOBS = STORE_URL.CODEFRAME_TECHNOLOGY.STORED_URL_JOBS;
                this.URL_INTERNSHIP = STORE_URL.CODEFRAME_TECHNOLOGY.STORED_URL_INTERNSHIP;
                this.URL_MEETUPS = STORE_URL.CODEFRAME_TECHNOLOGY.STORED_URL_MEETUPS;
                break;
            case STORE_URL.BROADWAY_INFOSYS.STORED_BROADWAY_INFOSYS:
                this.URL_JOBS = STORE_URL.BROADWAY_INFOSYS.STORED_URL_JOBS;
                this.URL_INTERNSHIP = STORE_URL.BROADWAY_INFOSYS.STORED_URL_INTERNSHIP;
                this.URL_MEETUPS = STORE_URL.BROADWAY_INFOSYS.STORED_URL_MEETUPS;
                break;
            case "Yomari":
                this.URL_JOBS = "http://192.168.43.174/JobChaiyo/jobchaiyo_database.php";
                this.URL_INTERNSHIP = "http://192.168.43.174/JobChaiyo/jobchaiyo_intern_database.php";
                this.URL_MEETUPS = "http://192.168.43.174/JobChaiyo/jobchaiyo_meetup_database.php";
                break;
            case "Teaching":
                this.URL_JOBS = "http://192.168.43.174/JobChaiyo/jobchaiyo_database.php";
                break;
            case "Construction":
                this.URL_JOBS = "http://192.168.43.174/JobChaiyo/jobchaiyo_database.php";
                break;


        }
    }

    public static final class REFERENCE {
        public static final String JOBS = Config.PACKAGE_NAME + "jobs";
        public static final String INTERNSHIPS = Config.PACKAGE_NAME + "classes";
        public static final String MEETUPS = Config.PACKAGE_NAME + "events";
    }

    public static final class Config {
        public static final String PACKAGE_NAME = "com.codeframetech.jobchaiyoo.";
    }


}
