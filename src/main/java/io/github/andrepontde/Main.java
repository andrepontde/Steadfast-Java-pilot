package io.github.andrepontde;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {

        DbConnection conn  = new DbConnection();
        CommandRunner tcmd = new CommandRunner();
        Scanner in = new Scanner(System.in);
        String line = "";
        HashSet<String> sProgs = new HashSet<>();
        int x;

        //TODO allow for saved profiles (add this to the database)
        //TODO Make code be able to run in the background without a window opened only when there is a timer active.
        System.out.println("This is the first version of the steadfast preview");
        System.out.println("Please select one or more programs from the list below by /n typing the program number");
        List<String> prgs = tcmd.runPsCmd();
        
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
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //TODO - add scheduler to run code each 2 seconds and finish after certain amount of seconds
        
        tcmd.minimizeApps(sProgs);

        
    }
}