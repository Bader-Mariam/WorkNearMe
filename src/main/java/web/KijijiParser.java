package web;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * A class for parsing Kijiji adds and retrieving different information
 *
 * @author Dan, Bader
 *
 */
public class KijijiParser {

    private static KijijiParser instance = null;

    private KijijiParser() {
    }

    public static synchronized KijijiParser getInstance() {
        if (instance == null) {
            instance = new KijijiParser();
        }
        return instance;
    }

    /**
     * Retrieves the address within a Kijiji page
     *
     * @param url the url to the Kijiji page
     * @return A string containing the first 100 characters after the address on
     * the kijiji page
     * @throws IOException
     */
    public String getAddress(String url) throws IOException {
        try {
            Document doc = Jsoup.connect(url).get();
            String html = doc.select(".viewad_frame_tbl td td").html();
            html = html.substring(html.indexOf("Address") + 7, html.length() - 1);
            return html;
        } catch (StringIndexOutOfBoundsException ex) {
            return "";
        }
    }

    /**
     * Retrieves the address within a Kijiji page
     *
     * @param url the url to the Kijiji page
     * @return A string containing the first 100 characters after the address on
     * the kijiji page
     * @throws IOException
     */
    public String[] getAddress(String[] urls) throws IOException {
        String[] addresses = new String[urls.length];

        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = getAddress(urls[i]);
        }

        return addresses;
    }
}
