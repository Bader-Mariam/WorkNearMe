/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknearme;

import java.math.BigDecimal;

/**
 *
 * @author Dan, Bader
 *
 * This Class is used for storing the information returned from Kijiji for each
 * job.
 *
 */
public class Job {

    BigDecimal lat, lng;
    String url, title, pubDate;

    public Job() {
    }

    public Job(BigDecimal lat, BigDecimal lng, String url, String title, String pubDate) {
        this.lat = lat;
        this.lng = lng;
        this.url = url;
        this.title = title;
        this.pubDate = pubDate;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String toString() {
        String str = "";
        if (lat == null) {
            str += "null";
        } else {
            str += lat.toPlainString();
        }

        str += " ";

        if (lng == null) {
            str += "null";
        } else {
            str += lng.toPlainString();
        }
        return str;
    }
}
