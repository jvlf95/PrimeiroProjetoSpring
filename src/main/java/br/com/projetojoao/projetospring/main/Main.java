package br.com.projetojoao.projetospring.main;

import br.com.projetojoao.projetospring.model.DataSeason;
import br.com.projetojoao.projetospring.model.DataSerie;
import br.com.projetojoao.projetospring.model.Episode;
import br.com.projetojoao.projetospring.service.ApiConnection;
import br.com.projetojoao.projetospring.service.DataConvert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner read = new Scanner(System.in);
    private ApiConnection apiConnection = new ApiConnection();
    private DataConvert convert = new DataConvert();
    private static final String URL = "https://www.omdbapi.com/?t=";
    private static final String APIKEY = "&apikey=b423b0ca";

    public void menu(){
        System.out.print("Write the serie's name to getting info: ");
        String serieName = read.nextLine();

        // connection to OMDB API
        String json = apiConnection.getData(URL + serieName.replace(" ", "+") + APIKEY);

        // convert json serie to DateSerie
        DataSerie dataSerie = convert.getData(json, DataSerie.class);
        dataSerie.getSerieInfo();

        List<DataSeason> seasons = new ArrayList<>();

        for(int i = 1; i <= dataSerie.totalSeasons(); i++){
            json = apiConnection.getData(URL + serieName.replace(" ", "+") + "&Season=" + i + APIKEY);
            DataSeason dataSeason = convert.getData(json, DataSeason.class);
            seasons.add(dataSeason);
        }


        /*
        List<DataEpisode> episodes =
                seasons.stream()
                        .flatMap(s -> s.eps().stream())
                        .toList();

        episodes.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DataEpisode::rating).reversed())
                .limit(5)
                .forEach(e -> System.out.println(
                        "\nEpisode: " + e.title() +
                        "\nRating: " + e.rating()
                ));

         */


        // list of episodeos
        List<Episode> episodeos = seasons.stream()
                .flatMap(s -> s.eps().stream()
                        .map(d -> new Episode(s.seasonNumber(), d)))
                .collect(Collectors.toList());

        episodeos.forEach(System.out::println);

        /*
        System.out.print("Write an episode or a part of an episode: ");
        String partTitle = read.nextLine();

        // optional is a container that can or can't have a not null value
        Optional<Episode> searchedEpisode = episodeos.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(partTitle.toUpperCase()))
                .findFirst();

        if(searchedEpisode.isPresent()){
            System.out.println("Episode founded!");
            System.out.println("Season: " + searchedEpisode.get().getSeasonNumber());
        }else{
            System.out.println("Episode not found!");
        }

        /*
        // search eps after a specific date
        //System.out.print("Write a year to see the episodeos: ");
        int year = read.nextInt();
        read.nextLine();

        LocalDate searchDate = LocalDate.of(year, 1, 1);
        // format a date
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodeos.stream()
                .filter(e -> e.getReleased() != null && e.getReleased().isAfter(searchDate))
                //.peek(e -> System.out.println("Primeiro filtro " + e))
                .forEach(e -> System.out.println(
                        "\nTitle: " + e.getTitle() +
                                "\nSeason: " + e.getSeasonNumber() +
                                "\nNumber: " + e.getNumber() +
                                "\nReleased: " + e.getReleased().format(dateTimeFormatter) +
                                "\nRating: " + e.getRating()
                ));

         */

        Map<Integer, Double> ratingSeason = episodeos.stream()
                .filter(r -> r.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeasonNumber,
                        Collectors.averagingDouble(Episode::getRating)));

        System.out.println(ratingSeason);

        DoubleSummaryStatistics stat = episodeos.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));

        System.out.println("\nAverage: " + stat.getAverage() +
                "\nBest Episode: " + stat.getMax() +
                "\nWorst Episode: " + stat.getMin() +
                "\nNumber of Episodes: " + stat.getCount());

    }
}
