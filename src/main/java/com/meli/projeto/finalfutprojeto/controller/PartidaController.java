package com.meli.projeto.finalfutprojeto.controller;

import com.meli.projeto.finalfutprojeto.dto.PartidaDto;
import com.meli.projeto.finalfutprojeto.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/partida")

public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @PostMapping("/criar")
    public ResponseEntity<String> createPartida(@RequestBody PartidaDto partidaDto) {
        this.partidaService.createPartida(partidaDto);
        return new ResponseEntity<>("Partida registrada com sucesso", HttpStatus.CREATED);
    }

}
