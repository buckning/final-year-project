package com.andrewmcglynn.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Login to a MySQL database called login on port 3306
 * @author Andrew McGlynn
 */
public class LoginDatabase {
    private Connection connection;
    private PreparedStatement sqlFindPassword;

    /**
     * Constructor, this initialises the mySQL connector driver
     */
    public LoginDatabase(){
        try{
             Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
        }
        catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error with com.mysql.jdbc.Driver!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    /**
     * Connect to the database
     *
     * @throws java.sql.SQLException throws exception if not able to connect
     */
    public void connect() throws SQLException{
             // connect to database
		   connection = DriverManager.getConnection(
		           "jdbc:mysql://localhost:3306/login?user=root" ,"root","");
           sqlFindPassword = connection.prepareStatement(
    	         "SELECT password FROM users WHERE username = ?" );
    }
    /**
     * Validate that the passed username and password are stored in the database.
     *
     * @param username the user name
     * @param password the associated password
     * @return returns true if username and corresponding password are correct
     */
    public boolean validate(String username, String password){
        boolean val = false;
       //login to the database
       //sql to create simple database
       //CREATE TABLE users  (username varchar(20), password varchar(20))
       //sql query SELECT password FROM users where username='name'
          try{
             sqlFindPassword.setString( 1, username );
             ResultSet rs = sqlFindPassword.executeQuery();

             rs.next();
             String storedPassword = rs.getString(1);
             if(storedPassword.equals(password)){
                 val = true;
             }
		  }
          catch(Exception e){		  }
       return val;
    }
}
