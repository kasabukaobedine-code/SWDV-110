package edu.cwi.swdv110.music;

import java.time.LocalDate;
import java.util.function.Predicate;

public final class PlayFilters {

    private PlayFilters() { }

    public static Predicate<Play> byUser(String user) {
        return play -> play.user().equalsIgnoreCase(user);
    }

    public static Predicate<Play> byGenre(String genre) {
        return play -> play.genre().equalsIgnoreCase(genre);
    }

    public static Predicate<Play> between(LocalDate from, LocalDate to) {
        return play -> !play.date().isBefore(from) && !play.date().isAfter(to);
    }

    public static Predicate<Play> skipped() {
        return play -> play.seconds() < 30;
    }
}

