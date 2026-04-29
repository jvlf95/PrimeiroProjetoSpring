package br.com.projetojoao.projetospring;

import br.com.projetojoao.projetospring.main.Main;
import br.com.projetojoao.projetospring.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetospringApplication implements CommandLineRunner {

	@Autowired
	private SerieRepository serieRepository;



	public static void main(String[] args) {
		SpringApplication.run(ProjetospringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(serieRepository);
		main.menu();


	}
}
