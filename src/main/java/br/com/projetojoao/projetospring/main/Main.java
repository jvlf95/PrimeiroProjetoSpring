package br.com.projetojoao.projetospring.main;

import br.com.projetojoao.projetospring.model.DataSeason;
import br.com.projetojoao.projetospring.model.DataSerie;
import br.com.projetojoao.projetospring.service.ApiConnection;
import br.com.projetojoao.projetospring.service.DataConvert;
import ch.qos.logback.core.encoder.JsonEscapeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private Scanner read = new Scanner(System.in);
    private ApiConnection apiConnection = new ApiConnection();
    private DataConvert dataConvert = new DataConvert();
    private static final String URL = "https://www.omdbapi.com/?t=";
    private static final String APIKEY = "&apikey=b423b0ca";
    private List<DataSerie> series = new ArrayList<>();

    public void menu(){

        int option = -1;

        while(option != 0){
            System.out.println("""
                Choose one of the options bellow:
                1 - Find series
                2 - Find episodes
                3 - List searched series
                4 - Series by category
                0 - Exit
                """);
            System.out.print("Option: ");
            option = read.nextInt();
            read.nextLine();

            switch(option){
                case 1:
                    showWebSerie();
                    break;
                case 2:
                    findEpisodesSerie();
                    break;
                case 3:
                    listSearchedSeries();
                    break;
                case 4:
                    separateCategory();
                    break;
                case 0:
                    System.out.println("Leaving...");
                    break;
                default:
                    System.out.println("[ERROR] Invalid option!");
                    break;

            }
        }


    }

    private void showWebSerie(){
        DataSerie dataSerie = getDataSerie();
        series.add(dataSerie);
        System.out.println(dataSerie);
    }
    private DataSerie getDataSerie(){
        System.out.print("Write a serie name and get the info: ");
        String name = read.nextLine();
        String json = apiConnection.getData(URL + name.replace(" ", "+") + APIKEY);
        DataSerie dataSerie = dataConvert.getData(json, DataSerie.class);

        return dataSerie;
    }

    private void findEpisodesSerie(){
        DataSerie dataSerie = getDataSerie();
        List<DataSeason> seasons = new ArrayList<>();

        for(int i = 1; i <= dataSerie.totalSeasons(); i++){
            String json = apiConnection.getData(URL + dataSerie.title().replace(" ", "+") + "&Season=" + i + APIKEY);
            DataSeason dataSeason = dataConvert.getData(json, DataSeason.class);
            seasons.add(dataSeason);
        }

        seasons.forEach(System.out::println);

    }

    private void listSearchedSeries(){
        series.forEach(System.out::println);
    }

    private void separateCategory(){
        Map<String, List<DataSerie>> serieCategory = series.stream()
                .collect(Collectors.groupingBy(DataSerie::genre));

        System.out.println(serieCategory);
    }

}
