package br.com.projetojoao.projetospring;

import br.com.projetojoao.projetospring.Model.DataSerie;
import br.com.projetojoao.projetospring.Service.ApiConnection;
import br.com.projetojoao.projetospring.Service.DataConvert;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ProjetospringApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ProjetospringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("First SpringBoot application!");

		Scanner read = new Scanner(System.in);

		ApiConnection apiConnection = new ApiConnection();

		System.out.println("Write a serie name and to getting your info");
		System.out.print("Name: ");
		String serieName = read.nextLine();

		String json = apiConnection.getData(serieName);

		System.out.println(json);

		DataConvert convert = new DataConvert();
		DataSerie dataSerie = convert.getData(json, DataSerie.class);

		dataSerie.getDataSerie();



	}
}
