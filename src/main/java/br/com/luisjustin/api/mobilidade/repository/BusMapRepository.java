package br.com.luisjustin.api.mobilidade.repository;

import br.com.luisjustin.api.mobilidade.model.BusMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusMapRepository extends JpaRepository<BusMap, Long> {
    List<BusMap> findByBusLineId(Long bus_line_id);
}
