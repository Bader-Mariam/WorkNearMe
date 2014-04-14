package utility;

import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.LatLng;
import com.worknearme.Controller;
import com.worknearme.Job;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import web.KijijiParser;
import web.RSSReader;

/**
 *
 * @author Dan, Bader
 *
 * This is a utility class for performing various actions involving the Job
 * class
 *
 */
public class JobUtility {

    /**
     * Takes a url to a Kijiji RSS feed for job listings (general or specific)
     * and extracts the title, link, and address, geocodes the addresses and
     * puts the information in a Job object
     *
     * @param url A url to a Kijiji jobs listing RSS feed
     * @return An array of Job objects based on the jobs in the input RSS feed
     */
    public static Job[] createJobArray(String url) {
        Job[] jobs = null;
        try {
            RSSReader rss = RSSReader.getInstance();
            KijijiParser kparse = KijijiParser.getInstance();

            //Gets an array of Jobs based on the input url
            jobs = rss.getKijijiJobs(url);

            //Gets an array of just the links to the actual Kijiji job listings
            String[] urls = new String[jobs.length];
            int l = 0;
            for (Job j : jobs) {
                urls[l] = j.getUrl();
                l++;
            }

            //Parse the links for addresses, then parse the address for a postal code
            String[] codes = PostalCodeUtility.parseCanadaPostalCode(kparse.getAddress(urls));
            //geocode the postal code into a geocoderesponse object
            GeocodeResponse[] geocodes = PostalCodeUtility.geocode(codes);

            //Set the lat and long from the geocoding into the job object
            for (int i = 0; i < jobs.length; i++) {
                //If the geocode was not returned null and has status code OK
                if (geocodes[i] != null && geocodes[i].getStatus().toString().equals("OK")) {
                    LatLng latlng = geocodes[i].getResults().get(0).getGeometry().getLocation();
                    jobs[i].setLat(latlng.getLat());
                    jobs[i].setLng(latlng.getLng());
                    //Otherwise assume no location
                } else {
                    jobs[i].setLat(BigDecimal.ZERO);
                    jobs[i].setLng(BigDecimal.ZERO);
                }
            }

            return jobs;
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobs;
    }
}
