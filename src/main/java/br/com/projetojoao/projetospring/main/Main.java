package br.com.projetojoao.projetospring.main;

import br.com.projetojoao.projetospring.model.DataEpisode;
import br.com.projetojoao.projetospring.model.DataSeason;
import br.com.projetojoao.projetospring.model.DataSerie;
import br.com.projetojoao.projetospring.model.Episode;
import br.com.projetojoao.projetospring.service.ApiConnection;
import br.com.projetojoao.projetospring.service.DataConvert;

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

        String json = apiConnection.getData(URL + serieName.replace(" ", "+") + APIKEY);

        DataSerie dataSerie = convert.getData(json, DataSerie.class);
        dataSerie.getSerieInfo();

        System.out.println("All seasons of " + serieName);

        List<DataSeason> seasons = new ArrayList<>();

        for(int i = 1; i <= dataSerie.totalSeasons(); i++){
            json = apiConnection.getData(URL + serieName.replace(" ", "+") + "&Season=" + i + APIKEY);
            DataSeason dataSeason = convert.getData(json, DataSeason.class);
            seasons.add(dataSeason);
        }

        seasons.forEach(s -> System.out.println("Season: " + s.seasonNumber()));


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


        List<Episode> episodeos = seasons.stream()
                .flatMap(s -> s.eps().stream()
                        .map(d -> new Episode(s.seasonNumber(), d)))
                .collect(Collectors.toList());

        episodeos.forEach(System.out::println);

    }
}
