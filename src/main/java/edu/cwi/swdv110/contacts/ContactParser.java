 package edu.cwi.swdv110.contacts;

public final class ContactParser {

    private ContactParser() { }

    public static String titleCase(String word) {
        if (word == null || word.isEmpty()) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder(word.toLowerCase());
        
        if (sb.length() > 0) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }
        
        for (int i = 0; i < sb.length() - 1; i++) {
            if (sb.charAt(i) == '-') {
                sb.setCharAt(i + 1, Character.toUpperCase(sb.charAt(i + 1)));
            }
        }
        
        return sb.toString();
    }

    public static String normalizeName(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return "";
        }

        String cleaned = raw.trim();
        
        if (cleaned.contains(",")) {
            int commaIndex = cleaned.indexOf(',');
            String last = cleaned.substring(0, commaIndex).trim();
            String firstMiddle = cleaned.substring(commaIndex + 1).trim();
            cleaned = firstMiddle + " " + last;
        }

        String[] parts = cleaned.split("\\s+");
        
        for (int i = 0; i < parts.length; i++) {
            parts[i] = titleCase(parts[i]);
        }

        return String.join(" ", parts);
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        
        String trimmed = email.trim();
        
        if (trimmed.contains(" ")) {
            return false;
        }
        
        int firstAt = trimmed.indexOf('@');
        int lastAt = trimmed.lastIndexOf('@');
        if (firstAt == -1 || firstAt != lastAt || firstAt == 0) {
            return false;
        }
        
        String domain = trimmed.substring(firstAt + 1);
        
        if (domain.isEmpty() || domain.startsWith(".") || domain.endsWith(".")) {
            return false;
        }
        
        if (!domain.contains(".")) {
            return false;
        }
        
        if (domain.contains("..")) {
            return false;
        }
        
        return true;
    }

    public static String normalizeEmail(String email) {
        if (email == null) {
            return "";
        }
        return email.trim().toLowerCase();
    }

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }
        
        int atIndex = email.indexOf('@');
        String beforeAt = email.substring(0, atIndex);
        
        if (beforeAt.length() < 2) {
            return email;
        }
        
        String domain = email.substring(atIndex);
        char firstChar = beforeAt.charAt(0);
        int maskLength = beforeAt.length() - 1;
        
        return firstChar + "*".repeat(maskLength) + domain;
    }

    public static String formatPhone(String raw) {
        if (raw == null) {
            return "INVALID";
        }
        
        StringBuilder digits = new StringBuilder();
        char[] chars = raw.toCharArray();
        
        for (char c : chars) {
            if (Character.isDigit(c)) {
                digits.append(c);
            }
        }
        
        String finalDigits = digits.toString();
        
        if (finalDigits.length() == 11 && finalDigits.startsWith("1")) {
            finalDigits = finalDigits.substring(1);
        }
        
        if (finalDigits.length() != 10) {
            return "INVALID";
        }
        
        String areaCode = finalDigits.substring(0, 3);
        String prefix = finalDigits.substring(3, 6);
        String lineNum = finalDigits.substring(6, 10);
        
        return "(" + areaCode + ") " + prefix + "-" + lineNum;
    }

    public static String makeUsername(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }
        
        String[] words = fullName.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        
        String rawUsername = "";
        if (words.length == 1) {
            rawUsername = firstWord.toLowerCase();
        } else {
            char firstInitial = Character.toLowerCase(firstWord.charAt(0));
            rawUsername = firstInitial + lastWord.toLowerCase();
        }
        
        StringBuilder cleanBuilder = new StringBuilder();
        for (char c : rawUsername.toCharArray()) {
            if (Character.isLetter(c)) {
                cleanBuilder.append(c);
            }
        }
        
        String result = cleanBuilder.toString();
        if (result.length() > 8) {
            result = result.substring(0, 8);
        }
        
        return result;
    }

    public static Contact parseLine(String line) {
        if (line == null) {
            throw new IllegalArgumentException("Line cannot be null");
        }
        
        String[] fields = line.split("\\|");
        if (fields.length != 3) {
            throw new IllegalArgumentException("Line does not have exactly 3 fields");
        }
        
        String parsedName = normalizeName(fields[0]);
        if (parsedName.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        
        String parsedEmail = normalizeEmail(fields[1]);
        String parsedPhone = formatPhone(fields[2]);
        
        return new Contact(parsedName, parsedEmail, parsedPhone);
    }
}
