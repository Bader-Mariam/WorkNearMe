package com.worknearme;

import beans.InfoBean;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utility.DbUtility;

/**
 * A controller class to handle all requests into the applications
 *
 * @author Dan, Bader
 */
public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    JobsCache cache = JobsCache.getInstance();

    /**
     * Initializes the cache when the server starts
     *
     * @throws ServletException
     */
    public void init() throws ServletException {
        try {
            cache.updateJobs();
            long delay = 1000 * 60 * 60 * 16;
            new Timer().scheduleAtFixedRate(new updateJobsCacheTimerTask(), 0, delay);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes all requests into the server
     *
     * @param request the request from the server
     * @param response the response to the client
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String address = "/WEB-INF/index.jsp";
        Cookie[] cookies = request.getCookies();
        DbUtility dbUtil = new DbUtility();
        InfoBean infoBean = new InfoBean();
        infoBean.setErrorMessage("");
        infoBean.setUsernameLink("/Controller");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        String action = request.getParameter("action");
        if (action == null || action.equals("")) {
            action = "";
        }
        try {
            JobSearchFilter filter = getFilter(cookies); //Gets a filter
            addFilterToBean(filter, infoBean);

//------------VISITING SITE FROM OUTSIDE // ACTION EQUALS EMPTY STRING---------
            if (action.equals("")) {
                
                //If no session exists
                if (username == null) {
                    //Check to see if user saved their login from previous session
                    String savedUsername = getSavedLoginCookie(cookies);

                    //If a previous login exists (username stored in cookies)
                    if (savedUsername != null) {
                        //Retrieve the user's data from the database and 
                        //populate the beans
                        session.setAttribute("username", savedUsername);
                        infoBean.setUsername(savedUsername);
                        infoBean.setLink("Controller?action=logout");
                        infoBean.setLinkName("logout");

                        //If no previous login exists, user is a guest
                    } else {
                        setGuest(infoBean);
                    }
                    //If session exists    
                } else {
                    infoBean.setUsername(username);
                    infoBean.setLink("Controller?action=logout");
                    infoBean.setLinkName("logout");
                }



//-------------TRYING TO LOG IN // ACTION EQUALS LOGIN--------------------------
            } else if (action.equalsIgnoreCase("login")) { //Trying to Log in
                String logUsername = request.getParameter("username");
                String password = request.getParameter("password");

                //If username and/or password are empty or null
                if (logUsername == null || password == null || logUsername.equals("") || password.equals("")) {
                    //forward to error page
                    setGuest(infoBean);
                    infoBean.setErrorMessage("Username or password is blank");

                } else if (dbUtil.inDB(logUsername, password)) { //If username and password are in DB
                    session.setAttribute("username", logUsername);
                    infoBean.setUsername(logUsername);
                    infoBean.setLink("Controller?action=logout");
                    infoBean.setLinkName("logout");

                    //If remember checkbox is checked, store username as cookies for 30 days
                    String remember = request.getParameter("remember");
                    if (remember != null && remember.equalsIgnoreCase("on")) {
                        Cookie uC = new Cookie("username", logUsername);
                        uC.setMaxAge(60 * 60 * 24 * 30);
                        response.addCookie(uC);
                    }

                } else { //If username and/or password are not in DB
                    //forward to error page
                    infoBean.setErrorMessage("Incorrect username or password");
                    setGuest(infoBean);
                }


//-------------TRYING TO LOG OUT // ACTION EQUALS LOGOUT------------------------
            } else if (action.equalsIgnoreCase("logout")) {
                session.invalidate();
                //Set a blank username cookie to clear current one
                Cookie uC = new Cookie("username", "");
                uC.setMaxAge(-1);
                response.addCookie(uC);
                setGuest(infoBean);
                session = request.getSession(); //New session to attach the beans to



//-------------TRYING TO OBTAIN LIST OF JOBS // ACTION EQUALS JSON-------------
            } else if (action.equalsIgnoreCase("json")) {
                //Write out the json object and leave the method
                Gson json = new Gson();
                response.setContentType("text/plain");
                PrintWriter out = response.getWriter();
                out.write(json.toJson(cache.getJsonJobList()));
                out.close();
                return;

//-------------TRYING TO REGSITER NEW USER // ACTION EQUALS REGISTER-------------                
            } else if (action.equalsIgnoreCase("register")) {
                //Try to insert new user into DB
                boolean inserted = dbUtil.insertNewUser(request.getParameter("reg_username"),
                        request.getParameter("reg_password"), request.getParameter("reg_firstName"),
                        request.getParameter("reg_lastName"), request.getParameter("reg_email"),
                        request.getParameter("reg_address1"), request.getParameter("reg_address2"),
                        request.getParameter("reg_city"), request.getParameter("reg_province"),
                        request.getParameter("reg_postalCode"));
                //forward user back to index to make them log in is successful
                setGuest(infoBean);
                if (!inserted) {
                    infoBean.setErrorMessage("Could not create new user");
                }


//-------------ANYTHING ELSE // ACTION EQUALS ANYTHING NOT HANDLED-------------               
            } else {
                //No session exists
                if (username == null) {
                    //set them as a guest
                    setGuest(infoBean);
                }else{ //session exists
                    infoBean.setUsername(username);
                    infoBean.setLink("Controller?action=logout");
                    infoBean.setLinkName("logout");
                }
                //forward to 404 page
                address = "/WEB-INF/error.jsp";
            }


        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
//-------------FINALLY. ALL REQUEST TYPES HANDLED-------------------------------        
        session.setAttribute("infoBean", infoBean); //Attach bean to session
        RequestDispatcher rd = request.getRequestDispatcher(address);
        rd.forward(request, response);
    }

    /**
     * Sets the bean with common guest information
     *
     * @param bean the bean sent to the jsp page
     */
    public void setGuest(InfoBean bean) {
        bean.setUsername("register");
        bean.setUsernameLink("register.jsp");
        bean.setLink("login.jsp");
        bean.setLinkName("login");
    }

    /**
     * Parses the filter and sets all trues as "checked" and falses as ""
     *
     * @param filter the filter object from the cookie
     * @param infoBean the bean to set the converted values into
     */
    public void addFilterToBean(JobSearchFilter filter, InfoBean infoBean) {
        String str = (filter.isP()) ? "checked" : "";
        infoBean.setP(str);

        str = (filter.isM()) ? "checked" : "";
        infoBean.setM(str);

        str = (filter.isR()) ? "checked" : "";
        infoBean.setR(str);

        str = (filter.isA()) ? "checked" : "";
        infoBean.setA(str);

        str = (filter.isD()) ? "checked" : "";
        infoBean.setD(str);

        str = (filter.isF()) ? "checked" : "";
        infoBean.setF(str);
    }

    /**
     * Gets a cookie used for saving login information
     *
     * @param cookies the cookies from the request
     * @return the username if it exists else null
     */
    public String getSavedLoginCookie(Cookie[] cookies) {
        String uName = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("username")) {
                    uName = c.getValue();
                }
            }
        }
        //Return null if the username is blank, otherwise the username
        return (uName.equals("")) ? null : uName;
    }

    /**
     * Gets the filter from the cookies if it exists
     *
     * @param cookies the cookies from the request
     * @return a filter object representing the cookie
     */
    public JobSearchFilter getFilter(Cookie[] cookies) {
        JobSearchFilter filter = new JobSearchFilter(true, true, true, true, true, true);
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("filter")) {
                    Gson json = new Gson();
                    filter = json.fromJson(java.net.URLDecoder.decode(c.getValue()), JobSearchFilter.class);
                }
            }
        }
        return filter;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
