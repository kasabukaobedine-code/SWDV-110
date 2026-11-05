package edu.cwi.swdv110.music;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class MusicStats {

    private MusicStats() { }

    public static final Comparator<Play> NEWEST_FIRST = Comparator
        .comparing(Play::date).reversed()
        .thenComparing(Play::artist)
        .thenComparing(Play::song);

    public static long totalMinutes(List<Play> plays, Predicate<Play> filter) {
        return plays.stream()
            .filter(filter)
            .mapToInt(Play::seconds)
            .sum() / 60;
    }

    public static Map<String, Long> playsPerGenre(List<Play> plays) {
        return plays.stream()
            .collect(Collectors.groupingBy(
                Play::genre,
                TreeMap::new,
                Collectors.counting()
            ));
    }

    public static Map<String, Integer> secondsPerUser(List<Play> plays) {
        return plays.stream()
            .collect(Collectors.groupingBy(
                Play::user,
                TreeMap::new,
                Collectors.summingInt(Play::seconds)
            ));
    }

    public static List<String> topArtists(List<Play> plays, int n) {
        return plays.stream()
            .collect(Collectors.groupingBy(Play::artist, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                .thenComparing(Map.Entry.comparingByKey()))
            .limit(n)
            .map(Map.Entry::getKey)
            .toList();
    }

    public static Optional<Play> longestPlay(List<Play> plays) {
        return plays.stream()
            .max(Comparator.comparingInt(Play::seconds));
    }

    public static List<String> songsBy(List<Play> plays, String artist) {
        return plays.stream()
            .filter(play -> play.artist().equalsIgnoreCase(artist))
            .map(Play::song)
            .distinct()
            .sorted()
            .toList();
    }

    public static List<Play> recent(List<Play> plays, int n) {
        return plays.stream()
            .sorted(NEWEST_FIRST)
            .limit(n)
            .toList();
    }

    public static Map<DayOfWeek, Long> playsByDay(List<Play> plays) {
        return plays.stream()
            .collect(Collectors.groupingBy(
                play -> play.date().getDayOfWeek(),
                TreeMap::new,
                Collectors.counting()
            ));
    }

    public static double skipRate(List<Play> plays, String user) {
        long totalPlays = plays.stream()
            .filter(PlayFilters.byUser(user))
            .count();
            
        if (totalPlays == 0) {
            return 0.0;
        }
        
        long skips = plays.stream()
            .filter(PlayFilters.byUser(user).and(PlayFilters.skipped()))
            .count();
            
        return (double) skips / totalPlays;
    }

    public static Optional<String> topGenre(List<Play> plays, String user) {
        return plays.stream()
            .filter(PlayFilters.byUser(user))
            .collect(Collectors.groupingBy(Play::genre, Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.<String, Long>comparingByValue()
                .thenComparing(Map.Entry.<String, Long>comparingByKey().reversed()))
            .map(Map.Entry::getKey);
    }
}
