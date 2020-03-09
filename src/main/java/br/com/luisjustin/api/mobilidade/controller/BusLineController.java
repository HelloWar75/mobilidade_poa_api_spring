package br.com.luisjustin.api.mobilidade.controller;

import br.com.luisjustin.api.mobilidade.model.BusLine;
import br.com.luisjustin.api.mobilidade.repository.BusLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusLineController {

    @Autowired
    BusLineRepository buslineRepository;

    @GetMapping("/bus_line")
    public Page<BusLine> getBusMap(Pageable pageable) {
        return buslineRepository.findAll(pageable);
    }

}
