package edu.cwi.swdv110.music;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws IOException {
        Path csvPath = Path.of("plays.csv");
        if (!Files.exists(csvPath)) {
            System.out.println("Error: plays.csv file could not be found!");
            return;
        }

        List<Play> plays = Files.lines(csvPath)
            .skip(1)
            .filter(line -> line != null && !line.isBlank())
            .map(Play::parse)
            .collect(Collectors.toCollection(ArrayList::new));

        int removedRows = PlayCleaner.clean(plays);
        System.out.println("Loaded " + plays.size() + " plays (" + removedRows + " bad rows removed)");
        System.out.println();

        PlayFormatter compact = play -> play.song() + " – " + play.artist();

        PlayFormatter detailed = play -> String.format("%-12s%-8s%-19s%-19s%d:%02d",
            play.date(),
            play.user(),
            play.song().length() > 18 ? play.song().substring(0, 15) + "..." : play.song(),
            play.artist().length() > 18 ? play.artist().substring(0, 15) + "..." : play.artist(),
            play.seconds() / 60,
            play.seconds() % 60
        );

        System.out.println("Minutes per user:");
        MusicStats.secondsPerUser(plays).forEach((user, totalSecs) -> 
            System.out.println("  " + user + "    " + (totalSecs / 60) + " min")
        );
        System.out.println();

        System.out.println("Plays per genre:");
        MusicStats.playsPerGenre(plays).forEach((genre, count) -> {
            StringBuilder bars = new StringBuilder();
            for (int i = 0; i < count; i++) {
                bars.append("#");
            }
            System.out.printf("  %-13s%s%n", genre, bars);
        });
        System.out.println();

        System.out.println("Plays by day:");
        MusicStats.playsByDay(plays).forEach((day, count) -> 
            System.out.printf("  %-11s%d%n", day, count)
        );
        System.out.println();

        System.out.println("Top 3 artists: " + MusicStats.topArtists(plays, 3));
        
        System.out.print("Longest play:  ");
        MusicStats.longestPlay(plays).ifPresent(p -> System.out.println(compact.format(p)));
        System.out.println();

        System.out.println("Most recent:");
        MusicStats.recent(plays, 5).forEach(p -> System.out.println("  " + detailed.format(p)));

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.print("User to summarize (or 'quit'): ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                break;
            }

            String targetUser = input.toLowerCase();
            long count = plays.stream().filter(PlayFilters.byUser(targetUser)).count();

            if (count == 0) {
                System.out.println("No plays found for user: " + input);
                continue;
            }

            long mins = MusicStats.totalMinutes(plays, PlayFilters.byUser(targetUser));
            String favGenre = MusicStats.topGenre(plays, targetUser).orElse("None");
            int rate = (int) Math.round(MusicStats.skipRate(plays, targetUser) * 100);

            System.out.printf("%s: %d plays, %d min, top genre %s, skip rate %d%%%n",
                targetUser, count, mins, favGenre, rate);

            long genreMins = MusicStats.totalMinutes(plays, PlayFilters.byUser(targetUser).and(PlayFilters.byGenre(favGenre)));
            System.out.printf("  %s minutes: %d%n", favGenre, genreMins);

            String listens = plays.stream()
                .filter(PlayFilters.byUser(targetUser).and(PlayFilters.skipped().negate()))
                .map(compact::format)
                .distinct()
                .sorted()
                .collect(Collectors.joining("; "));
                
            System.out.println("  Full listens:  " + listens);
        }
        sc.close();
    }
}
