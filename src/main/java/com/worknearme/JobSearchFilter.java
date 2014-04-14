/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknearme;

/**
 *
 * @author Dan, Bader
 *
 * This class stores the filter retrieved from the user's cookie. This filter is
 * used to apply checks to the correct check boxes on the index.jsp page.
 *
 */
public class JobSearchFilter {

    boolean P, M, R, A, D, F;

    public JobSearchFilter(boolean P, boolean M, boolean R, boolean A, boolean D, boolean F) {
        this.P = P;
        this.M = M;
        this.R = R;
        this.A = A;
        this.D = D;
        this.F = F;
    }

    public boolean isP() {
        return P;
    }

    public void setP(boolean P) {
        this.P = P;
    }

    public boolean isM() {
        return M;
    }

    public void setM(boolean M) {
        this.M = M;
    }

    public boolean isR() {
        return R;
    }

    public void setR(boolean R) {
        this.R = R;
    }

    public boolean isA() {
        return A;
    }

    public void setA(boolean A) {
        this.A = A;
    }

    public boolean isD() {
        return D;
    }

    public void setD(boolean D) {
        this.D = D;
    }

    public boolean isF() {
        return F;
    }

    public void setF(boolean F) {
        this.F = F;
    }
}
