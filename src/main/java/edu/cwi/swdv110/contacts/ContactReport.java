package edu.cwi.swdv110.contacts;

import java.util.List;

public final class ContactReport {

    private static final String ROW = "%-22s %-30s %-15s %-9s%n";

    private ContactReport() { }

    public static String generate(List<Contact> contacts) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format(ROW, "NAME", "EMAIL", "PHONE", "USERNAME"));
        sb.append("-".repeat(78)).append("\n");
        
        for (Contact c : contacts) {
            String name = c.name();
            String email = c.email();
            String phone = c.phone();
            String username = ContactParser.makeUsername(name);
            
            String rowString = String.format(ROW, name, email, phone, username);
            
            boolean emailValid = ContactParser.isValidEmail(email);
            boolean phoneValid = !phone.equals("INVALID");
            
            if (!emailValid || !phoneValid) {
                sb.append("!! ").append(rowString);
            } else {
                sb.append("   ").append(rowString);
            }
        }
        
        return sb.toString();
    }
}
