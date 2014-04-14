package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dan, Bader
 *
 * A utility class for performing various actions involving the database
 *
 */
public class DbUtility {

    /**
     * Checks to see if provided username and password exist together within the
     * database
     *
     * @param username the username to search for in the database
     * @param password the password that should go along with the username
     * @return true if the username/password combo exists, false otherwise
     * @throws ClassNotFoundException
     */
    public Boolean inDB(String username, String password) throws ClassNotFoundException {
        try {
            Connection connection = connectToDb();
            ResultSet resultSet = runQuery("Select username, password from 3909db.user "
                    + "where username = '" + username + "'", connection);
            resultSet.next();

            //if found in DB
            if (resultSet.getString(1).equals(username) && resultSet.getString(2).equals(password)) {
                closeConnection(connection);
                return true;
            }

            //if not found in DB
            closeConnection(connection);
            return false;
        } catch (SQLException ex) {
            //No data found in db
            return false;
        }
    }

    /**
     * Gets all the information about a user from the DB based on the input
     * username
     *
     * @param username the username to get the corresponding data for
     * @return a hashmap of the information about a user in the database
     */
    public HashMap getUserInfo(String username) {
        HashMap info = new HashMap();
        try {
            Connection connection = connectToDb();
            ResultSet resultSet = runQuery("Select email, firstName, lastName, postal_code "
                    + "from 3909DB.User where username = '" + username + "'", connection);
            resultSet.next();
            info.put("email", resultSet.getString(1));
            info.put("firstName", resultSet.getString(2));
            info.put("lastName", resultSet.getString(3));
            info.put("postalCode", resultSet.getString(4));
            closeConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(DbUtility.class.getName()).log(Level.SEVERE, null, ex);
        }

        return info;
    }

    /**
     * Checks to see if the user is in the database, if not, inserts user into
     * the database
     *
     * @param username the username to insert NOT NULL
     * @param password the password to insert NOT NULL
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @param email the person's email address
     * @param address the person's address
     * @return true if inserted, false if not
     */
    public boolean insertNewUser(String username, String password, String firstName,
            String lastName, String email, String address1, String address2, String city,
            String province, String postalCode) {
        boolean rtn = false;
        Connection connection = connectToDb();

        if (username == null || password == null || username.equals("") || password.equals("")) {
            return false;
        }
        try {
            //Check to see if the username is in the database
            ResultSet resultSet = runQuery("Select * from 3909DB.user where"
                    + " username = '" + username + "'", connection);
            if (!resultSet.isBeforeFirst()) { //If there are no results
                //Insert the user into the database
                Statement statement = connection.createStatement();
                String query = "INSERT INTO 3909db.user "
                        + "(username, password, firstName, lastName, address_line1, address_line2, city, province, postal_code, email) VALUES "
                        + "('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "', '"
                        + address1 + "', '" + address2 + "', '" + city + "', '" + province
                        + "', '" + postalCode + "', '" + email + "')";
                statement.executeUpdate(query);
                rtn = true;
            }
            closeConnection(connection);
        } catch (Exception ex) {
            return false;
        }

        return rtn;
    }

    /**
     * Executes a query on the database and returns the Result Set
     *
     * @param query The query to execute
     * @param connection The connection to the DB
     * @return The returned result set
     */
    private ResultSet runQuery(String query, Connection connection) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    /**
     * Connections to the database
     *
     * @return a connection to the database
     */
    private Connection connectToDb() {
        try {
            String url = "jdbc:mysql://localhost:3306";
//            String url = "jdbc:mysql://localhost";
            String driver = "com.mysql.jdbc.Driver";
            String dbUname = "dan";
            String dbPass = "password";


            // Load database driver if it's not already loaded.
            Class.forName(driver);
            // Establish network connection to database.
            Connection connection = DriverManager.getConnection(url, dbUname, dbPass);
            return connection;
        } catch (SQLException ex) {
            Logger.getLogger(DbUtility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Closes a connection to the database
     *
     * @param connection the connection to close
     * @throws SQLException
     */
    private static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
