<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="infoBean" scope="session" type="beans.InfoBean" />
<!DOCTYPE html>
<html>
    <head>
        <title>Find Jobs in Winnipeg - Work Near Me - Job Search</title>
        <script src="baseJS.js"></script>
        <script src="http://maps.google.com/maps/api/js?sensor=false"></script>
        <meta charset="UTF-8" />
        <link rel="stylesheet" href="./CSS/baseCSS.css" />
    </head>
    <body>
        <%@include file="titleBar.jsp" %>
        <div id="mapBar">
            <div id="detailCol">
                <p>Blah.</p>
            </div>
            <div id="mapCol">
            </div>
        </div>
        <div id="lowerBar">
            <div id="formBar">
                <form>
                    <div class="column-four">
                    </div>
                    <div class="column-four">
                        <h3>Categories</h3>
                        <div class="column-eighth">
                            <div class="row">
                                <input id="chk_Programming" name="chk_Programming" type="checkbox" <jsp:getProperty name="infoBean" property="p" /> aria-labelledby="Programming-ariaLabel" onclick = "handleCheckBoxClick('P')"/>
                                <span><label for="chk_Programming" id="Programming-ariaLabel">Programming</label></span>
                            </div>
                            <div class="row">
                                <input id="chk_Media" name="chk_Media" type="checkbox" <jsp:getProperty name="infoBean" property="m" /> aria-labelledby="Media-ariaLabel" onclick = "handleCheckBoxClick('M')"/>
                                <span><label for="chk_Media" id="Media-ariaLabel">Media</label></span>
                            </div>
                            <div class="row">
                                <input id="chk_Retail" name="chk_Retail" type="checkbox" <jsp:getProperty name="infoBean" property="r" /> aria-labelledby="Retail-ariaLabel" onclick = "handleCheckBoxClick('R')"/>
                                <span><label for="chk_Retail" id="Retail-ariaLabel">Retail</label></span>
                            </div>
                            <div class="row">
                                <input id="chk_Admin" name="chk_Admin" type="checkbox" <jsp:getProperty name="infoBean" property="a" /> aria-labelledby="Admin-ariaLabel" onclick = "handleCheckBoxClick('A')"/>
                                <span><label for="chk_Admin" id="Admin-ariaLabel">Admin</label></span>
                            </div>
                        </div>
                        <div class="column-eighth">
                            <div class="row">
                                <input id="chk_Design" name="chk_Design" type="checkbox" <jsp:getProperty name="infoBean" property="d" /> aria-labelledby="Design-ariaLabel" onclick = "handleCheckBoxClick('D')"/>
                                <span><label for="chk_Design" id="Design-ariaLabel">Design</label></span>
                            </div>
                            <div class="row">
                                <input id="chk_Legal" name="chk_Legal" type="checkbox" checked aria-labelledby="Legal-ariaLabel" onclick = "handleCheckBoxClick('L')"/>
                                <span><label for="chk_Legal" id="Legal-ariaLabel">Legal</label></span>
                            </div>
                            <div class="row">
                                <input id="chk_FoodService" name="chk_FoodService" type="checkbox" <jsp:getProperty name="infoBean" property="f" /> aria-labelledby="FoodService-ariaLabel" onclick = "handleCheckBoxClick('F')"/>
                                <span><label for="chk_FoodService" id="FoodService-ariaLabel">Food Service</label></span>
                            </div>
                            <div class="row">
                                <input id="chk_Sales" name="chk_Sales" type="checkbox" checked aria-labelledby="Sales-ariaLabel" onclick = "handleCheckBoxClick('S')"/>
                                <span><label for="chk_Sales" id="Sales-ariaLabel">Sales</label></span>
                            </div>
                        </div>
                    </div>
                    <div class="column-four">
                        <h3>My Details</h3>
                        <div class="column-eighth">
                            <div class="row">
                                <label for="sldr_DistanceFromHome" id="DistanceFromHome-ariaLabel">Distance From Home</label>
                                <input id="sldr_DistanceFromHome" name="sldr_DistanceFromHome" type="range" aria-labelledby="DistanceFromHome-ariaLabel" min="0" max="100" step="10" value="50" />
                            </div>
                            <div class="row">
                                <label for="sldr_AgeOfListing" id="AgeOfListing-ariaLabel">Age Of Listing</label>
                                <input id="sldr_AgeOfListing" name="sldr_AgeOfListing" type="range" aria-labelledby="AgeOfListing-ariaLabel" min="0" max="30" step="1" value="10" />
                            </div>
                        </div>
                        <div class="column-eighth">
                            <div class="row">
                                <label for="sldr_Salary" id="Salary-ariaLabel">Salary</label>
                                <input id="sldr_Salary" name="sldr_Salary" type="range" aria-labelledby="Salary-ariaLabel" min="0" max="150" step="10" value="60" />
                            </div>
                            <div class="row">
                                <label for="sldr_SomethingElse" id="SomethingElse-ariaLabel">Something Else</label>
                                <input id="sldr_SomethingElse" name="sldr_SomethingElse" type="range" aria-labelledby="SomethingElse-ariaLabel" min="0" max="100" step="10" value="70" />
                            </div>
                        </div>
                    </div>
                    <div class="column-four">
                    </div>
                </form>
            </div>
            <div id="footerBar">
                <p>
                    <a href="/">work near me</a> is a free service that utilizes data from New Media Manitoba, Kijiji, and Craigslist. 
                </p>
                <p>                    
                    <a href="/">Privacy Policy</a> | <a href="/">Copyright</a> | <a href="/">Contact</a>
                </p>
            </div>
        </div>
    </body>
</html>

