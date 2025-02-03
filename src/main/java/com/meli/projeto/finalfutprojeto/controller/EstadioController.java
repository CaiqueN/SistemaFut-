package com.meli.projeto.finalfutprojeto.controller;

import com.meli.projeto.finalfutprojeto.dto.EstadioDto;
import com.meli.projeto.finalfutprojeto.model.Club;
import com.meli.projeto.finalfutprojeto.model.Estadio;
import com.meli.projeto.finalfutprojeto.service.ClubService;
import com.meli.projeto.finalfutprojeto.service.EstadioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estadio")


public class EstadioController {

    @Autowired
    private EstadioService estadioService;

    @PostMapping("/criar")
    public ResponseEntity<String> createEstadio(@RequestBody EstadioDto EstadioDto) {
        this.estadioService.createEstadio(EstadioDto);
        return new ResponseEntity<>("Estadio registrado com sucesso", HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEstadio(@RequestBody EstadioDto EstadioDto, @PathVariable Long id) {
        this.estadioService.updateEstadio(EstadioDto, id);
        return new ResponseEntity<>("Estadio atualizado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<EstadioDto> buscarEstadio(@PathVariable Long id) {
        EstadioDto estadio = this.estadioService.buscarEstadioPorid(id);
        return new ResponseEntity<>(estadio, HttpStatus.OK);

    }

    @GetMapping("/lista")
    public Page<Estadio> search(@RequestParam(value = "nome", required = false) String nomeEstadio,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size) {
        return estadioService.search(nomeEstadio, page, size);
    }

    @GetMapping
    public Page<Estadio> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return estadioService.findAll(pageable);

    }
}