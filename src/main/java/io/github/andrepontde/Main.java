package io.github.andrepontde;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

        //TODO Make code be able to run in the background without a window opened only when there is a timer active.
        //TODO - Make code run with the windows API after preview is complete, since the lerning curve for that is absolutely insane honestly.
        //This approach would make this code event driven and more efficient than this. 


public class Main {
    public static void main(String[] args) {

        DbConnection conn = new DbConnection();
        CommandRunner tcmd = new CommandRunner();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        HashSet<String> sProgs = new HashSet<>();
        List<String> prgs;

        String line;
        int focusTimeMinutes = 30; // Default 30 minutes for testing
        int selectedProfileId = -1;
        int x;

        try (Scanner in = new Scanner(System.in)) {
            System.out.println("======================================");
            System.out.println("    STEADFAST - FOCUS SESSION APP    ");
            System.out.println("======================================");
            
            System.out.println("Would you like to load or create a new profile? (l/c)");
            line = in.next();
        
        if (line.equalsIgnoreCase("c")) {
            // CREATE NEW PROFILE
            System.out.println("\n--- Creating New Profile ---");
            System.out.print("Profile name: ");
            String pName = in.next();
            System.out.print("Profile description: ");
            in.nextLine(); // consume newline
            String pDesc = in.nextLine();
            
            selectedProfileId = conn.addProfile(pName, pDesc);
            if (selectedProfileId == -1) {
                System.out.println("Failed to create profile. Exiting...");
                return;
            }

            System.out.println("\nDo you want to add from existing programs or scan for new ones? (e/n)");
            line = in.next();
            
            if (line.equalsIgnoreCase("e")) {
                // ADD FROM EXISTING BANNED PROGRAMS
                HashMap<Integer, String> existingPrograms = conn.getBannedPrograms();
                if (existingPrograms.isEmpty()) {
                    System.out.println("No existing programs found. Scanning for new ones...");
                    line = "n"; // Force to scan mode
                } else {
                    System.out.println("\n--- Existing Banned Programs ---");
                    for (HashMap.Entry<Integer, String> entry : existingPrograms.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    
                    System.out.println("\nEnter program IDs to add to this profile (separated by spaces, or 'done' to finish):");
                    in.nextLine(); // consume newline
                    String input = in.nextLine();
                    
                    if (!input.equalsIgnoreCase("done")) {
                        String[] programIds = input.split("\\s+");
                        for (String idStr : programIds) {
                            try {
                                int programId = Integer.parseInt(idStr);
                                if (existingPrograms.containsKey(programId)) {
                                    conn.linkProgramToProfile(selectedProfileId, programId);
                                    sProgs.add(existingPrograms.get(programId));
                                } else {
                                    System.out.println("Program ID " + programId + " not found.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid ID: " + idStr);
                            }
                        }
                    }
                }
            }
            
            if (line.equalsIgnoreCase("n")) {
                // SCAN FOR NEW PROGRAMS
                System.out.println("\n--- Scanning Running Programs ---");
                prgs = tcmd.runPsCmd();

                System.out.println("\nSelect programs to ban (enter number, or 'q' to finish):");
                while (!line.equals("q")) {
                    System.out.print("Program number (or 'q' to finish): ");
                    line = in.next();
                    
                    if (!line.equals("q")) {
                        try {
                            x = Integer.parseInt(line);
                            if (x >= 0 && x < prgs.size()) {
                                String formattedProgram = prgs.get(x).replaceAll("\\s+", "");
                                if (!formattedProgram.isEmpty() && !formattedProgram.equals("MainWindowTitle") && !formattedProgram.equals("----")) {
                                    // Add to database
                                    int programId = conn.addBannedProgram(formattedProgram, "Program selected from running processes");
                                    if (programId != -1) {
                                        conn.linkProgramToProfile(selectedProfileId, programId);
                                        sProgs.add(formattedProgram);
                                        System.out.println("Added: " + formattedProgram);
                                    }
                                }
                            } else {
                                System.out.println("Invalid program number!");
                            }
                        } catch (NumberFormatException e) {
                            if (!line.equals("q")) {
                                System.out.println("Please enter a valid number or 'q'");
                            }
                        }
                    }
                }
            }

        } else if (line.equalsIgnoreCase("l")) {
            // LOAD EXISTING PROFILE
            System.out.println("\n--- Available Profiles ---");
            HashMap<Integer, String> profiles = conn.getAllProfiles();
            
            if (profiles.isEmpty()) {
                System.out.println("No profiles found. Please create one first.");
                return;
            }
            
            for (HashMap.Entry<Integer, String> entry : profiles.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            
            System.out.print("Enter profile ID to load: ");
            try {
                selectedProfileId = in.nextInt();
                if (profiles.containsKey(selectedProfileId)) {
                    System.out.println("Loaded profile: " + profiles.get(selectedProfileId));
                    
                    // Load programs for this profile
                    HashMap<Integer, String> profilePrograms = conn.getProfilePrograms(selectedProfileId);
                    for (String programName : profilePrograms.values()) {
                        sProgs.add(programName);
                    }
                    
                    System.out.println("Programs to be blocked: " + sProgs);
                } else {
                    System.out.println("Profile not found!");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Invalid profile ID!");
                return;
            }
        }

        // SET FOCUS TIME
        System.out.print("\nEnter focus time in minutes (default 30): ");
        try {
            int minutes = in.nextInt();
            if (minutes > 0) {
                focusTimeMinutes = minutes;
            }
        } catch (Exception e) {
            System.out.println("Using default time: 30 minutes");
            focusTimeMinutes = 30;
        }

        final int focusTimeSeconds = focusTimeMinutes * 60; // Convert to seconds for scheduler
        final int finalSelectedProfileId = selectedProfileId; // Make final for lambda

        // START FOCUS SESSION
        if (sProgs.isEmpty()) {
            System.out.println("No programs selected to block. Exiting...");
            return;
        }

        System.out.println("\n======================================");
        System.out.println("    FOCUS SESSION STARTING!");
        System.out.println("Duration: " + focusTimeMinutes + " minutes");
        System.out.println("Blocked programs: " + sProgs);
        System.out.println("======================================");

        // Record session start
        String startTime = java.time.LocalDateTime.now().toString();
        
        // Schedule program blocking
        scheduler.scheduleAtFixedRate(() -> tcmd.minimizeApps(sProgs), 1, 2, TimeUnit.SECONDS);
        
        // Schedule session end
        scheduler.schedule(() -> {
            scheduler.shutdown();
            String endTime = java.time.LocalDateTime.now().toString();
            
            // Record session in database
            conn.addSession(finalSelectedProfileId, startTime, endTime, "Focus session completed");
            
            System.out.println("\n======================================");
            System.out.println("    FOCUS SESSION COMPLETED!");
            System.out.println("Session has been saved to database.");
            System.out.println("Applications are no longer blocked.");
            System.out.println("======================================");
        }, focusTimeSeconds, TimeUnit.SECONDS);

        // Keep main thread alive and show countdown
        new Thread(() -> {
            int remaining = focusTimeSeconds;
            while (remaining > 0 && !scheduler.isShutdown()) {
                try {
                    Thread.sleep(60000); // Update every minute
                    remaining -= 60;
                    if (remaining > 0) {
                        System.out.println("Time remaining: " + (remaining / 60) + " minutes");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
        
        } // Close try-with-resources for Scanner
    }
}