package br.com.projetojoao.projetospring.main;

import br.com.projetojoao.projetospring.model.DataSeason;
import br.com.projetojoao.projetospring.model.DataSerie;
import br.com.projetojoao.projetospring.service.ApiConnection;
import br.com.projetojoao.projetospring.service.DataConvert;

import java.util.Scanner;

public class Main {
    private Scanner read = new Scanner(System.in);
    private ApiConnection apiConnection = new ApiConnection();
    private DataConvert convert = new DataConvert();
    private final String URL = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=b423b0ca";

    public void menu(){
        System.out.print("Write the serie's name to getting info: ");
        String serieName = read.nextLine();
        String json = apiConnection.getData(URL + serieName.replace(" ", "+") + APIKEY);

        DataSerie dataSerie = convert.getData(json, DataSerie.class);
        dataSerie.getSerieInfo();

        System.out.println("All seasons of " + serieName);

        for(int i = 1; i <= dataSerie.totalSeasons(); i++){
            json = apiConnection.getData(URL + serieName.replace(" ", "+") + "&Season=" + i + APIKEY);
            DataSeason dataSeason = convert.getData(json, DataSeason.class);
            System.out.println("-----------------------------------------");
            dataSeason.getSeasonInfo();
            System.out.println("-----------------------------------------");
        }

    }
}
