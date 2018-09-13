package com.codeframetech.jobchaiyoo.sorting;


import com.codeframetech.jobchaiyoo.pojo_classes.MeetupResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Digition on 12/26/2017.
 */




public class SortEventsData{

    public List<MeetupResponse> returnSortedEventsByName(List<MeetupResponse> input) {
        List<MeetupResponse> sortedList = new ArrayList<>(input);
        Collections.sort(sortedList,new SortEventsByName());
        return sortedList;
    }

    public List<MeetupResponse> returnSortedEventsByLocation(List<MeetupResponse> input) {
        List<MeetupResponse> sortedList = new ArrayList<>(input);
        Collections.sort(sortedList,new SortClassByLocation());
        return sortedList;
    }


    public  class SortEventsByName implements Comparator<MeetupResponse> {


        @Override
        public int compare(MeetupResponse eventResponse1, MeetupResponse eventResponse2) {
            return eventResponse1.getMeetup_name().compareTo(eventResponse2.getMeetup_name());
        }
    }

    public class SortClassByLocation implements Comparator<MeetupResponse> {


        @Override
        public int compare(MeetupResponse eventResponse1, MeetupResponse eventResponse2) {
            return eventResponse1.getLocation().compareTo(eventResponse2.getLocation());
        }
    }

}