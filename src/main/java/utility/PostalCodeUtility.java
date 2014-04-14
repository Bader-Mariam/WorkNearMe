package utility;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A utility classy for parsing strings to retrieve postal codes
 *
 * @author Dan, Bader
 */
public class PostalCodeUtility {

    /**
     * Retrieves the postal code for the specified country
     *
     * @param phrase the string to parse for postal code
     * @param country the country of the postal code to search for
     * @return the postal code if it exists, else empty string
     */
    public static String parsePostalCode(String phrase, String country) {
        if (country.equals("Canada")) {
            return parseCanadaPostalCode(phrase);
        } else if (country.equals("United States")) {
            return parseUSPostalCode(phrase);
        }
        return "";
    }

    /**
     * Retrieves the Canadian postal code from a string
     *
     * @param phrase the string to parse for a postal code
     * @return the postal code within the string, else empty string
     */
    public static String parseCanadaPostalCode(String phrase) {
        try {
            for (int i = 0; i < phrase.length(); i++) {

                if (isFSAFormat(phrase, i)) {

                    //If there is a space, skip it and check the next 3
                    if (phrase.charAt(i + 3) == ' ') {

                        if (isLDUFormat(phrase, i + 4)) {
                            return phrase.substring(i, i + 3).concat(phrase.substring(i + 4, i + 7));
                        }
                        //Otherwise, check the 3 immediatly following    
                    } else if (isLDUFormat(phrase, i + 3)) {

                        return phrase.substring(i, i + 6);
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    public static String[] parseCanadaPostalCode(String[] phrases) {
        String[] postalCodes = new String[phrases.length];
        for (int i = 0; i < phrases.length; i++) {
            postalCodes[i] = parseCanadaPostalCode(phrases[i]);
        }
        return postalCodes;
    }

    public static String parseUSPostalCode(String phrase) {
        return phrase;
    }

    /**
     * Codes the postal code into a lat and lng object
     *
     * @param address the address to geocode
     * @return the address in a GeocodeResponse object
     */
    public static GeocodeResponse geocode(String address) {
        if (address.length() > 1) {
            Geocoder geocoder = new Geocoder();
            GeocoderRequest gcr = new GeocoderRequestBuilder().setAddress(address).setLanguage("en").getGeocoderRequest();
            return geocoder.geocode(gcr);
        }
        return null;
    }

    public static GeocodeResponse[] geocode(String[] address) {
        GeocodeResponse[] response = new GeocodeResponse[address.length];
        for (int i = 0; i < address.length; i++) {
            response[i] = geocode(address[i]);
            try { //Prevent google from detecting too many requests
                Thread.sleep(1700);
            } catch (InterruptedException ex) {
                Logger.getLogger(PostalCodeUtility.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return response;
    }

    /**
     * Checks if the format is LetterNumberLetter format
     */
    private static boolean isFSAFormat(String phrase, int i) {
        try {
            if (Character.isLetter(phrase.charAt(i))
                    && Character.isDigit(phrase.charAt(i + 1))
                    && Character.isLetter(phrase.charAt(i + 2))) {
                return true;

            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks to see if the format is NumberLetterNumber format
     */
    private static boolean isLDUFormat(String phrase, int i) {
        try {
            if (Character.isDigit(phrase.charAt(i))
                    && Character.isLetter(phrase.charAt(i + 1))
                    && Character.isDigit(phrase.charAt(i + 2))) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
