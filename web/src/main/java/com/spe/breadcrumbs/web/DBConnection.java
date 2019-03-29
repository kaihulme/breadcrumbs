package com.spe.breadcrumbs.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//TODO Get Application to retrieve the password from a properties file not in the git
public class DBConnection{


    private String dbURL;
    private String username;
    private String password;
    private Properties properties = new Properties();
    private void loadProperties(String filePath) throws IOException {
        FileInputStream input = new FileInputStream(filePath);
        properties.load(input);
        dbURL = properties.getProperty("spring.datasource.url");
        username = properties.getProperty("spring.datasource.username");
        password = properties.getProperty("spring.datasource.password");
    }
    public Connection getConnection() throws IOException {
        String home = System.getProperty("user.home");
        String filePath = home + "/.secret.properties";
        loadProperties(filePath);
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://@129.213.29.31:3306/breadcrumbsData","root","Breadcrumbs1!");
        }catch(ClassNotFoundException e){
            System.out.println("driver wasn't found");
        }catch (SQLException e){
            System.out.println("SQL Connection fail");
            e.printStackTrace();
        }
        return con;
    }
}