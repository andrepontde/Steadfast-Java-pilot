package io.github.andrepontde;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


public class DbConnection {
    String url;
    private static Connection conn;
    Statement stmnt = null;

    public DbConnection() {
        initialize("steadfast");
    }

    //Constructor to test custom database
    public DbConnection(String dbName) {
        initialize(dbName);
    }

    public void dbBuilder(String dbName){
        url = "jdbc:sqlite:"+dbName+".db";
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established");
            
            stmnt = conn.createStatement();
            
            // Create profiles table first (referenced by other tables)
            String sStm = "CREATE TABLE IF NOT EXISTS profiles (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL UNIQUE, " +
                        "description TEXT)";
            stmnt.executeUpdate(sStm);
            
            // Create bannedProgs table
            sStm = "CREATE TABLE IF NOT EXISTS bannedProgs (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL UNIQUE, " +
                        "description TEXT)";
            stmnt.executeUpdate(sStm);
            
            // Create junction table for many-to-many relationship between profiles and programs
            sStm = "CREATE TABLE IF NOT EXISTS profile_programs (" +
                        "profile_id INTEGER, " +
                        "program_id INTEGER, " +
                        "PRIMARY KEY (profile_id, program_id), " +
                        "FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE, " +
                        "FOREIGN KEY (program_id) REFERENCES bannedProgs(id) ON DELETE CASCADE)";
            stmnt.executeUpdate(sStm);
            
            // Create sessions table with foreign key to profiles
            sStm = "CREATE TABLE IF NOT EXISTS sessions (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "profile_id INTEGER NOT NULL, " +
                        "dateStart DATETIME NOT NULL, " +
                        "dateFinish DATETIME NOT NULL, " +
                        "description TEXT, " +
                        "FOREIGN KEY (profile_id) REFERENCES profiles(id))";
            stmnt.executeUpdate(sStm);
            
            stmnt.close();
            

        }catch (SQLException e){
            System.out.println("The connection could not be established" + e);
        }
    }

    // Private initialization method to avoid calling overridable methods in constructor
    private void initialize(String dbName) {
        dbBuilder(dbName);
    }


    public void addSession(int profileId, String dateStart, String dateFinish, String description) {
        String sql = "INSERT INTO sessions (profile_id, dateStart, dateFinish, description) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement pstmnt = conn.prepareStatement(sql);
            pstmnt.setInt(1, profileId);
            pstmnt.setString(2, dateStart);
            pstmnt.setString(3, dateFinish);
            pstmnt.setString(4, description);
            pstmnt.executeUpdate();
            System.out.println("Session added correctly");
        } catch (SQLException e) {
            System.out.println("Session could not be added: " + e.getMessage());
        }
    }

    // Add a new profile
    public int addProfile(String name, String description) {
        String sql = "INSERT INTO profiles (name, description) VALUES (?, ?)";
        
        try {
            PreparedStatement pstmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmnt.setString(1, name);
            pstmnt.setString(2, description);
            pstmnt.executeUpdate();
            
            // Get the generated ID
            var generatedKeys = pstmnt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int profileId = generatedKeys.getInt(1);
                System.out.println("Profile added with ID: " + profileId);
                return profileId;
            }
        } catch (SQLException e) {
            System.out.println("Profile could not be added: " + e.getMessage());
        }
        return -1; // Error case
    }

    // Add a new banned program
    public int addBannedProgram(String name, String description) {
        String sql = "INSERT INTO bannedProgs (name, description) VALUES (?, ?)";
        
        try {
            PreparedStatement pstmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmnt.setString(1, name);
            pstmnt.setString(2, description);
            pstmnt.executeUpdate();
            
            var generatedKeys = pstmnt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int programId = generatedKeys.getInt(1);
                System.out.println("Banned program added with ID: " + programId);
                return programId;
            }
        } catch (SQLException e) {
            System.out.println("Banned program could not be added: " + e.getMessage());
        }
        return -1;
    }

    // Link a program to a profile (many-to-many relationship)
    public void linkProgramToProfile(int profileId, int programId) {
        String sql = "INSERT OR IGNORE INTO profile_programs (profile_id, program_id) VALUES (?, ?)";
        
        try {
            PreparedStatement pstmnt = conn.prepareStatement(sql);
            pstmnt.setInt(1, profileId);
            pstmnt.setInt(2, programId);
            pstmnt.executeUpdate();
            System.out.println("Program linked to profile successfully");
        } catch (SQLException e) {
            System.out.println("Could not link program to profile: " + e.getMessage());
        }
    }

    // Remove a program from a profile
    public void unlinkProgramFromProfile(int profileId, int programId) {
        String sql = "DELETE FROM profile_programs WHERE profile_id = ? AND program_id = ?";
        
        try {
            PreparedStatement pstmnt = conn.prepareStatement(sql);
            pstmnt.setInt(1, profileId);
            pstmnt.setInt(2, programId);
            pstmnt.executeUpdate();
            System.out.println("Program unlinked from profile successfully");
        } catch (SQLException e) {
            System.out.println("Could not unlink program from profile: " + e.getMessage());
        }
    }

    // get all programs

    public HashMap<Integer, String> getBannedPrograms(){
        HashMap<Integer, String> bProgs = new HashMap<>();
        
        String sql = "Select id, name from bannedProgs";
        try (Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String programName = rs.getString("name");
                bProgs.put(id, programName);
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve banned programs: " + e.getMessage());
        }

        return bProgs;
    }

    public HashMap<Integer, String> getAllProfiles(){
        HashMap<Integer, String> bProgs = new HashMap<>();
        
        String sql = "Select id, name from profiles";
        try (Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String programName = rs.getString("name");
                bProgs.put(id, programName);
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve profiles: " + e.getMessage());
        }

        return bProgs;
    }

    public HashMap<Integer, String> getProfilePrograms(int id){
        HashMap<Integer, String> bProgs = new HashMap<>();
        
        String sql = "SELECT bannedProgs.id, bannedProgs.name " +
                     "FROM bannedProgs INNER JOIN profile_programs " +
                     "ON bannedProgs.id = profile_programs.program_id " +
                     "WHERE profile_programs.profile_id = ?";
        try (PreparedStatement pstmnt = conn.prepareStatement(sql)) {
            pstmnt.setInt(1, id);
            ResultSet rs = pstmnt.executeQuery();
            while (rs.next()) {
                int pid = rs.getInt("id");
                String programName = rs.getString("name");
                bProgs.put(pid, programName);
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve banned programs from this profile: " + e.getMessage());
        }

        return bProgs;
    }
    
    
}
