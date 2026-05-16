package br.com.projetojoao.projetospring.main;

import br.com.projetojoao.projetospring.model.*;
import br.com.projetojoao.projetospring.repository.SerieRepository;
import br.com.projetojoao.projetospring.service.ApiConnection;
import br.com.projetojoao.projetospring.service.DataConvert;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner read = new Scanner(System.in);
    private ApiConnection apiConnection = new ApiConnection();
    private DataConvert dataConvert = new DataConvert();
    private static final String URL = "https://www.omdbapi.com/?t=";
    private static final String APIKEY = "&apikey=b423b0ca";
    private List<DataSerie> dataSeries = new ArrayList<>();
    private SerieRepository serieRepository;
    private List<Serie> serieList = new ArrayList<>();

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
        listSearchedSeries();
        System.out.println("Choose a serie to by its name");
        System.out.println("Serie: ");
        var serieName = read.nextLine();

        // try to find the serie
        Optional<Serie> serieFounded = serieList.stream()
                .filter(s -> s.getTitle().toUpperCase().contains(serieName.toUpperCase()))
                .findFirst();

        // validate if the serie is present or not
        if(serieFounded.isPresent()){
            var serie = serieFounded.get();

            List<DataSeason> dataSeasonList = new ArrayList<>();

            for(int i = 1; i <= serie.getTotalSeasons(); i++){
                String json = apiConnection.getData(URL + serie.getTitle().replace(" ", "+")+ APIKEY);
                DataSeason dataSeason = dataConvert.getData(json, DataSeason.class);
                dataSeasonList.add(dataSeason);

            }
            dataSeasonList.forEach(System.out::println);

            List<Episode> episodeList = dataSeasonList.stream()
                    .flatMap(d -> d.eps().stream()
                            .map(e -> new Episode(d.seasonNumber(), e)))
                    .collect(Collectors.toList());

            serie.setEpisodeList(episodeList);
            serieRepository.save(serie);

        }else{
            System.out.println("Serie not founded!");
        }
    }

    private void listSearchedSeries() {
        // get all data from Serie in database
        serieList = serieRepository.findAll();

        // sorted the serieList by genre
        serieList.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }
}
