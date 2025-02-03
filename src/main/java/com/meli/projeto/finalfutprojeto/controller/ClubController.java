package com.meli.projeto.finalfutprojeto.controller;

import com.meli.projeto.finalfutprojeto.dto.ClubDto;
import com.meli.projeto.finalfutprojeto.model.Club;
import com.meli.projeto.finalfutprojeto.service.ClubService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/club")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @PostMapping("/criar")
    public ResponseEntity<String> createClube(@RequestBody ClubDto ClubDto) {
        this.clubService.createClube(ClubDto);
        return new ResponseEntity<>("Clube registrado com sucesso", HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateClube(@RequestBody ClubDto ClubDto, @PathVariable Long id) {
        this.clubService.updateClube(ClubDto, id);
        return new ResponseEntity<>("Club atualizado com sucesso", HttpStatus.OK);
    }

    @DeleteMapping("/delete/club/{id}")
    public ResponseEntity<String> inativarClub(@RequestBody ClubDto ClubDto, @PathVariable Long id) {
        this.clubService.inativarClub(ClubDto, id);
        return new ResponseEntity<>("CluB inativado com sucesso: ", HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ClubDto> buscarClub(@PathVariable Long id) {
      ClubDto club = this.clubService.buscarClubPorid(id);
      return new ResponseEntity<ClubDto>(club ,HttpStatus.OK);

    }

    @GetMapping("lista")
    public Page<Club> search(@RequestParam(value = "nome", required = false) String nome,
                             @RequestParam(value = "estado", required = false) String estado,
                             @RequestParam(value = "ativo", required = false) Boolean ativo,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size) {
        return clubService.search(nome, estado, ativo, page, size);
    }

    @GetMapping
    public Page<Club> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clubService.findAll(pageable);
    }

}