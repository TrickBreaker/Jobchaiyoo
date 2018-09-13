package com.codeframetech.jobchaiyoo.sorting;


import com.codeframetech.jobchaiyoo.pojo_classes.JobResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Digition on 12/26/2017.
 */

public class SortJobs{


    public List<JobResponse> returnSortedJobsByName(List<JobResponse> input) {
        List<JobResponse> sortedList = new ArrayList<>(input);
        Collections.sort(sortedList,new SortJobsByName());
        return sortedList;
}
    public List<JobResponse> returnSortedJobsByPlace(List<JobResponse> input) {
        List<JobResponse> sortedList = new ArrayList<>(input);
        Collections.sort(sortedList,new SortJobsByPlace());
        return sortedList;
    }



    public  class SortJobsByName implements Comparator<JobResponse> {


        @Override
        public int compare(JobResponse jobResponse1, JobResponse jobResponse2) {
            return jobResponse1.getName().compareTo(jobResponse2.getName());
        }
    }

    public class SortJobsByPlace implements Comparator<JobResponse> {


        @Override
        public int compare(JobResponse jobResponse1, JobResponse jobResponse2) {
            return jobResponse1.getAddress().compareTo(jobResponse2.getAddress());
        }
    }
}
