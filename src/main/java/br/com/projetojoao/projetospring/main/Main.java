package br.com.projetojoao.projetospring.main;

import br.com.projetojoao.projetospring.model.DataEpisode;
import br.com.projetojoao.projetospring.model.DataSeason;
import br.com.projetojoao.projetospring.model.DataSerie;
import br.com.projetojoao.projetospring.service.ApiConnection;
import br.com.projetojoao.projetospring.service.DataConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        seasons.forEach(System.out::println);

        /*
        for(int i = 0; i < dataSerie.totalSeasons(); i++){
            List<DataEpisode> episodes = seasons.get(i).eps();
            for(int j = 0; j < episodes.size(); j++){
                System.out.println(episodes.get(j).title());
            }
        }

         */

        seasons.forEach(s -> s.eps().forEach(e -> System.out.println(e.title())));

    }
}
