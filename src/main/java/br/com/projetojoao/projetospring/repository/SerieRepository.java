package br.com.projetojoao.projetospring.repository;

import br.com.projetojoao.projetospring.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
