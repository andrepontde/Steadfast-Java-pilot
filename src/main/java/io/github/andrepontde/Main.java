package io.github.andrepontde;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

        //TODO allow for saved profiles (add this to the database)
        //TODO Make code be able to run in the background without a window opened only when there is a timer active.
        //TODO - Make code run with the windows API after preview is complete, since the lerning curve for that is absolutely insane honestly.
        //This approach would make this code event driven and more efficient than this. 


public class Main {
    public static void main(String[] args) {

        DbConnection conn  = new DbConnection();
        CommandRunner tcmd = new CommandRunner();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Scanner in = new Scanner(System.in);
        HashSet<String> sProgs = new HashSet<>();
        List<String> prgs = tcmd.runPsCmd();


        String line = "";
        int focusTime = 30;
        int x;

        System.out.println("This is the first version of the steadfast preview");
        System.out.println("Please select one or more programs from the list below by /n typing the program number");
        
        while (line != "q"){
            System.out.println("to exit this menu, enter q");
            line = in.next();
            String formattedProgram;
            try {
                x = Integer.parseInt(line);
                formattedProgram = prgs.get(x).replaceAll("\\s+", "");
                sProgs.add(formattedProgram);
            } catch (NumberFormatException e) {
                line = "q";
            }
            
        }

        scheduler.scheduleAtFixedRate(() -> tcmd.minimizeApps(sProgs), 1, 2, TimeUnit.SECONDS);
        scheduler.schedule(() -> {
            scheduler.shutdown();
            System.out.println("Time is up, applications will be not blocked anymore");
        }, focusTime, TimeUnit.SECONDS);
    }
}