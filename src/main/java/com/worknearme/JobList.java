package com.worknearme;

/**
 *
 * @author Dan, Bader
 *
 * This class is used for storing each of the arrays of Jobs for the site. It is
 * used simply to store all the jobs in one object for easy JSON conversion.
 *
 */
public class JobList {

    Job[] programming, media, retail, admin, design, foodService;

    public JobList(Job[] programming, Job[] media, Job[] retail, Job[] admin, Job[] design, Job[] foodService) {
        this.programming = programming;
        this.media = media;
        this.retail = retail;
        this.admin = admin;
        this.design = design;
        this.foodService = foodService;
    }

    public JobList() {
    }

//---------------------------GETTERS AND SETTERS-------------------------------
    public Job[] getProgramming() {
        return programming;
    }

    public void setProgramming(Job[] programming) {
        this.programming = programming;
    }

    public Job[] getMedia() {
        return media;
    }

    public void setMedia(Job[] media) {
        this.media = media;
    }

    public Job[] getRetail() {
        return retail;
    }

    public void setRetail(Job[] retail) {
        this.retail = retail;
    }

    public Job[] getAdmin() {
        return admin;
    }

    public void setAdmin(Job[] admin) {
        this.admin = admin;
    }

    public Job[] getDesign() {
        return design;
    }

    public void setDesign(Job[] design) {
        this.design = design;
    }

    public Job[] getFoodService() {
        return foodService;
    }

    public void setFoodService(Job[] foodService) {
        this.foodService = foodService;
    }
}
