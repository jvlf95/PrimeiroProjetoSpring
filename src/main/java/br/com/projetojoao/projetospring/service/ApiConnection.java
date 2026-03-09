package br.com.projetojoao.projetospring.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiConnection {

    public String getData(String url){

        // create a new HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // create a new HttpRequest and a uri that the client will send
        // a request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;

        // try client's request to return a response
        try{
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

        }catch(IOException e){
            throw new RuntimeException(e);
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }

        String json = response.body();

        return json;


    }
}
