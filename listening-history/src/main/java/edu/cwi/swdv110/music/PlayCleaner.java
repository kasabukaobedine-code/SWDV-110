package edu.cwi.swdv110.music;

import java.util.List;
import java.util.function.UnaryOperator;

public final class PlayCleaner {

    private PlayCleaner() { }

    public static String titleCase(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }
        
        char[] chars = text.trim().toLowerCase().toCharArray();
        boolean foundDelimiter = true;
        
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ' || chars[i] == '-') {
                foundDelimiter = true;
            } else if (foundDelimiter) {
                chars[i] = Character.toUpperCase(chars[i]);
                foundDelimiter = false;
            }
        }
        
        return new String(chars);
    }

    public static int clean(List<Play> plays) {
        if (plays == null) {
            return 0;
        }

        int initialSize = plays.size();

        plays.removeIf(play -> play.seconds() <= 0 || play.song().isBlank());

        UnaryOperator<Play> cleanerUpper = play -> new Play(
            play.user().toLowerCase(),
            play.song(),
            play.artist(),
            titleCase(play.genre()),
            play.date(),
            play.seconds()
        );

        plays.replaceAll(cleanerUpper);

        return initialSize - plays.size();
    }
}
