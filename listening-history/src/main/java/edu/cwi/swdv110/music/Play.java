package edu.cwi.swdv110.music;

import java.time.LocalDate;

public record Play(String user, String song, String artist, String genre, LocalDate date, int seconds) {

    public static Play parse(String csvLine) {
        if (csvLine == null) {
            throw new IllegalArgumentException("CSV line cannot be null");
        }

        String[] parts = csvLine.split(",", -1);
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid number of fields: " + parts.length);
        }

        String user = parts[0].trim();
        String song = parts[1].trim();
        String artist = parts[2].trim();
        String genre = parts[3].trim();
        String dateStr = parts[4].trim();
        String secondsStr = parts[5].trim();

        LocalDate date = LocalDate.parse(dateStr);
        int seconds = Integer.parseInt(secondsStr);

        return new Play(user, song, artist, genre, date, seconds);
    }
}

