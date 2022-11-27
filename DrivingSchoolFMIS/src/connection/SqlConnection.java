/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

/*
 *
 * @author ANOYMASS
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Platform;
import static settings.AlertClass.makeAlert;
import static settings.AlertClass.makePromptAlert;

public class SqlConnection {

    public Connection DbConnector() {

        try {
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
   
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pamseudb", "root", "");
           
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            boolean action = makePromptAlert("Connection Lost", "You are not connected to the internet\n"
                    + "1. To continue; Connect your computer to the internet then Click Ok\n"
                    + "2. To exit the application; click cancel");

            if (action) {
                return DbConnector();
            } else {
                Platform.exit();
            }

        }

        return null;

    }

    public static boolean checkConnection() {
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            System.out.println("Internet is connected");
            return true;
        } catch (MalformedURLException e) {
            makeAlert("error", "You are not connected to the internet");
            checkConnection();
            return false;
        } catch (IOException e) {
            makeAlert("error", "You are not connected to the internet");
            checkConnection();
            return false;
        }
    }
}
