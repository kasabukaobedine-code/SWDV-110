package edu.cwi.swdv110.music;

import java.util.List;
import java.util.Locale;
import java.util.function.UnaryOperator;

public final class PlayCleaner {

    private PlayCleaner() { }

    public static String titleCase(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }
        
        char[] chars = text.trim().toLowerCase(Locale.ROOT).toCharArray();
        boolean foundDelimiter = true;
        
        for (int i = 0; i < chars.length; i++) {
            if (Character.isWhitespace(chars[i]) || chars[i] == '-') {
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

        plays.removeIf(play -> play == null
            || play.seconds() <= 0
            || play.song() == null
            || play.song().isBlank());

        UnaryOperator<Play> cleanerUpper = play -> new Play(
            play.user() == null ? "" : play.user().toLowerCase(Locale.ROOT),
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

