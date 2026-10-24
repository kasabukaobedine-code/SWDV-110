package edu.cwi.swdv110.contacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        List<Contact> list = new ArrayList<>();
        
        try {
            File file = new File("contacts.txt");
            Scanner fileScanner = new Scanner(file);
            
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                
                try {
                    Contact c = ContactParser.parseLine(line);
                    list.add(c);
                } catch (IllegalArgumentException e) {
                    // Skips broken lines safely without crashing
                }
            }
            fileScanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: contacts.txt file could not be found!");
            return;
        }
        
        String report = ContactReport.generate(list);
        System.out.print(report);
        
        Scanner inputScanner = new Scanner(System.in);
        String search = "";
        
        while (!search.equalsIgnoreCase("quit")) {
            System.out.println();
            System.out.print("Enter a name to search (or type 'quit' to exit): ");
            search = inputScanner.nextLine().trim();
            
            if (search.isEmpty() || search.equalsIgnoreCase("quit")) {
                continue;
            }
            
            System.out.println("\nSearch Results:");
            boolean foundAny = false;
            
            for (Contact c : list) {
                String nameLower = c.name().toLowerCase();
                String searchLower = search.toLowerCase();
                
                if (nameLower.contains(searchLower)) {
                    System.out.println("-> Name: " + c.name() + " | Email: " + c.email() + " | Phone: " + c.phone());
                    foundAny = true;
                }
            }
            
            if (!foundAny) {
                System.out.println("No matching contacts found for: " + search);
            }
        }
        
        System.out.println("Goodbye!");
        inputScanner.close();
    }
}

