package web;

import com.worknearme.Job;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * <<singleton>> A class that retrieves various elements from an RSS feed
 *
 * @author Dan, Bader
 */
public class RSSReader {

    private static RSSReader instance = null;

    private RSSReader() {
    }

    public static synchronized RSSReader getInstance() {
        if (instance == null) {
            instance = new RSSReader();
        }
        return instance;
    }

    public String[] getURLs(String url) throws IOException {
        return getElement(url, "guid");
    }

    public String[] getTitles(String url) throws IOException {
        return getElement(url, "title");
    }

    public String[] getPubDates(String url) throws IOException {
        return getElement(url, "pubDate");
    }

    /**
     * Retrieves an element of an RSS feed
     *
     * @param url the url of the RSS feed
     * @param element the element to be retrieved
     * @return an array of the element requested
     * @throws IOException
     */
    public String[] getElement(String url, String element) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByTag("item");
        String[] results = new String[elements.size()];

        for (int i = 0; i < results.length; i++) {
            results[i] = elements.get(i).getElementsByTag(element).html();
            if (results[i] == null || results[i].equals("")) {
                results[i] = "NONE";
            }
        }

        return results;
    }

    /**
     * Gets the elements of a Kijiji rss feed that are required to make a Job
     * object
     *
     * @param url the link to the Kijiji RSS feed
     * @return an array of jobs of the RSS feed
     * @throws IOException
     */
    public Job[] getKijijiJobs(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByTag("item");
        Job[] results = new Job[elements.size()];

        for (int i = 0; i < results.length; i++) {
            results[i] = new Job();
            results[i].setUrl(elements.get(i).getElementsByTag("guid").html());
            results[i].setTitle(elements.get(i).getElementsByTag("title").html());
            results[i].setPubDate(elements.get(i).getElementsByTag("pubDate").html());

            if (results[i].getUrl() == null || results[i].getUrl().equals("")) {
                results[i].setUrl("NONE");
            }
            if (results[i].getTitle() == null || results[i].getTitle().equals("")) {
                results[i].setTitle("NONE");
            }
            if (results[i].getPubDate() == null || results[i].getPubDate().equals("")) {
                results[i].setPubDate("NONE");
            }
        }


        return results;
    }
}