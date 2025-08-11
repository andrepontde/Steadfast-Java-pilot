package io.github.andrepontde;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class DbConnection {
    String url = "jdbc:sqlite:sessions.db";
    private static Connection conn;
    Statement stmnt = null;


    public DbConnection() {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been stablished");
            
            stmnt = conn.createStatement();
            String sStm = "CREATE TABLE IF NOT EXISTS sessions (" +
                        "id INTEGER PRIMARY KEY NOT NULL, " +
                        "dateStart DATETIME NOT NULL, " +
                        "dateFinish DATETIME NOT NULL, "+
                        "description TEXT, " +
                        "execs TEXT)";
            stmnt.executeUpdate(sStm);
            sStm = "CREATE TABLE IF NOT EXISTS bannedProgs (" +
                        "id INTEGER PRIMARY KEY NOT NULL, " +
                        "name TEXT, " +
                        "description TEXT)";
            stmnt.executeUpdate(sStm);
            //TODO - add a relational database so that when programs are added 


            //             sStm = "CREATE TABLE IF NOT EXISTS Session (" +
            //             "id INTEGER PRIMARY KEY NOT NULL, " +
            //             "name TEXT, " +
            //             "progs TEXT)";
            // stmnt.executeUpdate(sStm);
            stmnt.close();
            

        }catch (SQLException e){
            System.out.println("The connection could not be established" + e);
        }
    }



    public void addSession(String dateStrt, String dateFnsh, String txt, String execs){
        String sql = "INSERT INTO sessions (dateStart, dateFinish, description, execs) VALUES (?, ?, ?)";

        //Added prepared statement to prevent injection (Nobody would inject anything here since it is a local db
        //but it is always good to practice industry standards)

        try {
            PreparedStatement pstmnt = conn.prepareStatement(sql);
            pstmnt.setString(1, dateStrt);
            pstmnt.setString(2, dateFnsh);
            pstmnt.setString(3, txt);
            pstmnt.setString(4, execs);
            System.out.println("Session added correctly");
        } catch (SQLException e) {
            System.out.println("Session could not be added" + e);
        }
    }


    
}
