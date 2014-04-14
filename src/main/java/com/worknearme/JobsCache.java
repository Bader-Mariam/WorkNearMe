package com.worknearme;

import java.io.IOException;
import utility.JobUtility;

/**
 *
 * @author Dan, Bader
 *
 * <<singleton>>
 *
 * This is the cache of the current jobs retrieved from Kijiji. It stores a JSON
 * stringified object to be given out for incoming requests at all times.
 */
public class JobsCache {

    private JobList finalJobs = new JobList();
    private static JobsCache instance = null;

    private JobsCache() {
    }

    public static synchronized JobsCache getInstance() {
        if (instance == null) {
            instance = new JobsCache();
        }
        return instance;
    }

    public void updateJobs() throws IOException {
        JobList workingJobs = new JobList(); //New list to stringify later

        //Create an array of each job category and set in the JobList
        workingJobs.setProgramming(JobUtility.createJobArray("http://winnipeg.kijiji.ca/f-SearchAdRss?CatId=54&Location=1700192"));
        workingJobs.setAdmin(JobUtility.createJobArray("http://manitoba.kijiji.ca/f-SearchAdRss?CatId=58&Location=1700192&isProvinceSearch=true"));
        workingJobs.setDesign(JobUtility.createJobArray("http://manitoba.kijiji.ca/f-SearchAdRss?CatId=152&Location=1700192&isProvinceSearch=true"));
        workingJobs.setFoodService(JobUtility.createJobArray("http://manitoba.kijiji.ca/f-SearchAdRss?CatId=60&Location=1700192&isProvinceSearch=true"));
        workingJobs.setMedia(JobUtility.createJobArray("http://manitoba.kijiji.ca/f-SearchAdRss?CatId=55&Location=1700192&isProvinceSearch=true"));
        workingJobs.setRetail(JobUtility.createJobArray("http://manitoba.kijiji.ca/f-SearchAdRss?CatId=61&Location=1700192&isProvinceSearch=true"));

        //Stringify the list to a JSON object
        synchronized (this) {
            finalJobs = workingJobs;
        }
    }

    public JobList getJsonJobList() {
        return finalJobs;
    }
}
