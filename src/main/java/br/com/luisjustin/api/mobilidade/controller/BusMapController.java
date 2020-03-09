package br.com.luisjustin.api.mobilidade.controller;

import br.com.luisjustin.api.mobilidade.model.BusMap;
import br.com.luisjustin.api.mobilidade.repository.BusMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusMapController {

    @Autowired
    private BusMapRepository busmapRepository;

    @GetMapping("/bus_map")
    public Page<BusMap> getBusMap(Pageable pageable) {
        return busmapRepository.findAll(pageable);
    }

}
