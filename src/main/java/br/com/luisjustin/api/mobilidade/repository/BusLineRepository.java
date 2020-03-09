package br.com.luisjustin.api.mobilidade.repository;

import br.com.luisjustin.api.mobilidade.model.BusLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusLineRepository extends JpaRepository<BusLine, Long> {

}
