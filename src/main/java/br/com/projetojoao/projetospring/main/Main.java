package br.com.projetojoao.projetospring.main;

import br.com.projetojoao.projetospring.model.DataSeason;
import br.com.projetojoao.projetospring.model.DataSerie;
import br.com.projetojoao.projetospring.model.Episode;
import br.com.projetojoao.projetospring.model.Serie;
import br.com.projetojoao.projetospring.repository.SerieRepository;
import br.com.projetojoao.projetospring.service.ApiConnection;
import br.com.projetojoao.projetospring.service.DataConvert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner read = new Scanner(System.in);
    private ApiConnection apiConnection = new ApiConnection();
    private DataConvert dataConvert = new DataConvert();
    private static final String URL = "https://www.omdbapi.com/?t=";
    private static final String APIKEY = "&apikey=b423b0ca";
    private List<DataSerie> dataSeries = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();
    private SerieRepository serieRepository;

    public Main(SerieRepository serieRepository){
        this.serieRepository = serieRepository;
    }

    public void menu(){

        int option = -1;

        while(option != 0){
            System.out.println("""
                Choose one of the options bellow:
                1 - Find series
                2 - Find episodes
                3 - List searched series
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
        Serie serie = new Serie(dataSerie);
        serieRepository.save(serie);
        System.out.println("Data saved!");
    }
    private DataSerie getDataSerie(){
        System.out.print("Write a serie name and get the info: ");
        String name = read.nextLine();
        String json = apiConnection.getData(URL + name.replace(" ", "+") + APIKEY);
        DataSerie dataSerie = dataConvert.getData(json, DataSerie.class);

        return dataSerie;
    }

    private void findEpisodesSerie(){
        System.out.println("All series: ");
        listSearchedSeries();
        System.out.println("Choose a serie by its name");
        System.out.print("Name: ");
        String name = read.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitle().toLowerCase().contains(name.toLowerCase()))
                .findFirst();

        if(serie.isPresent()){
            var serieFounded = serie.get();

            List<DataSeason> seasons = new ArrayList<>();

            for(int i = 1; i <= serieFounded.getTotalSeasons(); i++){
                String json = apiConnection.getData(URL + serieFounded.getTitle().replace(" ", "+") + "&Season=" + i + APIKEY);
                DataSeason dataSeason = dataConvert.getData(json, DataSeason.class);
                seasons.add(dataSeason);
            }

            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(d -> d.eps().stream()
                            .map(e -> new Episode(d.seasonNumber(), e)))
                    .collect(Collectors.toList());

            serieFounded.setEpisodes(episodes);
            serieRepository.save(serieFounded);
        }else{
            System.out.println("Serie not found!");
        }



    }

    private void listSearchedSeries(){
        series = serieRepository.findAll(); // find and return all data from database
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }


}
