package com.codeframetech.jobchaiyoo.sorting;

import com.codeframetech.jobchaiyoo.pojo_classes.InternshipResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Digition on 12/26/2017.
 */

public class SortClassData {


    public List<InternshipResponse> returnSortedClassByInstitute(List<InternshipResponse> input) {
        List<InternshipResponse> sortedList = new ArrayList<>(input);
        Collections.sort(sortedList,new SortJobsByInstitute());
        return sortedList;
    }

    public List<InternshipResponse> returnSortedJobsBySubject(List<InternshipResponse> input) {
        List<InternshipResponse> sortedList = new ArrayList<>(input);
        Collections.sort(sortedList,new SortClassBySubject());
        return sortedList;
    }


    public  class SortJobsByInstitute implements Comparator<InternshipResponse> {


        @Override
        public int compare(InternshipResponse classResponse1, InternshipResponse classResponse2) {
            return classResponse1.getCompany_name().compareTo(classResponse2.getCompany_name());
        }
    }

    public class SortClassBySubject implements Comparator<InternshipResponse> {


        @Override
        public int compare(InternshipResponse classResponse1, InternshipResponse classResponse2) {
            return classResponse1.getSubject_name().compareTo(classResponse2.getSubject_name());
        }
    }
}