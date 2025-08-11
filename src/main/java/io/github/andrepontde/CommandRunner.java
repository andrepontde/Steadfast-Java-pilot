package io.github.andrepontde;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CommandRunner {
    HashMap<String, String> cmdMap;   
    String currentOS;
    ProcessBuilder runner;
    List<String> progs;

    public CommandRunner() {
        currentOS  = System.getProperty("os.name").toLowerCase();
        progs = new ArrayList<>();
        cmdMap = new HashMap<>();

        if(currentOS.contains("win")){
            cmdMap.put("tasks", "tasklist");
            cmdMap.put("minimize", "UNDEFINED");
        }else{
            cmdMap.put("tasks", "ps");
            cmdMap.put("minimize", "UNDEFINED");
        }
        
    }

    //NOTE - This build will focus on a Windows only approach for the time being, since nircmd is not available on 
    //bsh systems.

    public List<String> runPsCmd(){
        
        try {
            runner = new ProcessBuilder("powershell",
                "Get-Process | Where-Object { $_.MainWindowTitle } | Select-Object MainWindowTitle");
            Process process = runner.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            
            while ((line = br.readLine()) != null) { 
                progs.add(line);
            }

        } catch(Exception e){
            System.out.println("Unable to run command error: " + e);
        }

        int x = 0;
        for (String line : progs) {
            System.out.printf("%s: %s \n", x, line);
            x++;
        }

        return progs;

        
    }

    public void minimizeApps(HashSet<String> processes){
        System.out.println(processes);
        try {
            for (String elem : processes) {
                System.out.println("Minimizing program: " + elem);
                runner = new ProcessBuilder("nircmd/nircmd.exe","win", "min", "ititle", elem);
            }
            Process process = runner.start();
            System.out.println(process);


        } catch(Exception e){
            System.out.println("Unable to run command error: " + e);
        }

    }

    //TODO Add window listener to stop active windows from displaying by using the command runner an

}

    //TO-DO make the cmd runner scan the active processes and display them with the buffered reader and 
    //make the user select which programs to block in the next session 

