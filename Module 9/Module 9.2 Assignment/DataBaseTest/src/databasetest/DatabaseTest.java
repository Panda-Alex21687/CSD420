package databasetest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTest {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/databasedb";
        String user = "student1";
        String password = "pass";

        try {
            
            
            Connection conn = DriverManager.getConnection(url, user, password);

            System.out.println("Database connection successful!");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM address33");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("ID") + " - " +
                    rs.getString("FIRSTNAME") + " " +
                    rs.getString("LASTNAME") + ", " +
                    rs.getString("STREET") + ", " +
                    rs.getString("CITY") + ", " +
                    rs.getString("STATE") + " " +
                    rs.getString("ZIP")
                );
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }
}