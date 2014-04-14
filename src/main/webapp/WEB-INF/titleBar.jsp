<div id="titleBar">
            <div id="logo">
                <h1>Work Near Me</h1>
                <p><a href="Controller"><img src="./images/worknearme-small2.jpg" alt="Work Near Me"></a></p>
            </div>

            <div id="errorBar">
                <div id="errorMessage">
                    <p><jsp:getProperty name="infoBean" property="errorMessage" /></p>
                </div>
            </div>

            <div id="userInfo">
                <p>
                    <a href="<jsp:getProperty name="infoBean" property="usernameLink" />"><jsp:getProperty name="infoBean" property="username" /></a>
                    | 
                    <a href="<jsp:getProperty name="infoBean" property="link" />"><jsp:getProperty name="infoBean" property="linkName" /></a>
                </p>
            </div>
        </div>