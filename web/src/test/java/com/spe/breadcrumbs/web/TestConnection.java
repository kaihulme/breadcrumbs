package com.spe.breadcrumbs.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestConnection {
    private Connection con = null;
    public Connection getConnection(){
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:mem:test","sa"," ");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void executeSQLFile(String fileName) throws FileNotFoundException {
        String contents;
        try {
            contents = new String(Files.readAllBytes(Paths.get("src","test","resources",fileName)));
            contents = contents.replaceAll("\n","");
            contents = contents.replaceAll("\r"," ");
            String[] sql_stmts = contents.split(";");
            con = getConnection();
            for(String sql : sql_stmts){
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.executeUpdate();
//                con.close();
            }
        } catch (IOException |SQLException e) {
            e.printStackTrace();
        }
    }
}
