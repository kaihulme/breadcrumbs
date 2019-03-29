package com.spe.breadcrumbs.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//TODO Get Application to retrieve the password from a properties file not in the git
public class DBConnection{


    private static final String dbURL = "jdbc:mysql://@129.213.29.31:3306/breadcrumbsData";
    private static final String username = "root";
    private static String password = "Breadcrumbs1!";
    private Properties properties = new Properties();


    public Connection getConnection() throws FileNotFoundException {
        Connection con = null;
        FileInputStream input = new FileInputStream("config.properties");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbURL,username,password);
        }catch(ClassNotFoundException e){
            System.out.println("driver wasn't found");
        }catch (SQLException e){
            System.out.println("SQL Connection fail");
            e.printStackTrace();
        }
        return con;
    }
}