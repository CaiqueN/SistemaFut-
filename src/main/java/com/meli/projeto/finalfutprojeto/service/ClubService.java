package com.meli.projeto.finalfutprojeto.service;

import com.meli.projeto.finalfutprojeto.dto.ClubDto;
import com.meli.projeto.finalfutprojeto.model.Club;
import com.meli.projeto.finalfutprojeto.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Service
public class ClubService {
    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public void createClube(ClubDto clubDto) {

        validarNome(clubDto.getNome());
        validarEstado(clubDto.getEstado());
        validarDataCriacao(clubDto.getDataCriacao());
        verificarNome(clubDto.getNome(), clubDto.getEstado());

        Club club = new Club();
        club.setAtivo(true);
        club.setNome(clubDto.getNome());
        club.setEstado(clubDto.getEstado());
        club.setDatacriacao(clubDto.getDataCriacao());

        this.clubRepository.save(club);
    }

    private void validarNome(String nome) {
        if (nome == null || nome.length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome deve conter 3 caracteres");
        }

    }

    private void validarEstado(String estado) {
        if (estado == null || estado.length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O estado deve conter apenas 2 caracteres");
        }
    }

    private void validarDataCriacao(LocalDate dataCriacao) {
        if (dataCriacao == null || dataCriacao.isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data da fundancao deve ser maior que a data de hoje");
        }
    }

    private void verificarNome(String nome, String estado) {
        if (this.clubRepository.existsByNomeAndEstado(nome, estado)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, " Ja contem um time com este mesmo nome e estado: ");

        }

    }

    public void updateClube(ClubDto clubDto, Long id) {

        validarNome(clubDto.getNome());
        validarEstado(clubDto.getEstado());
        validarDataCriacao(clubDto.getDataCriacao());
        verificarNomeParaUpdate(id, clubDto.getNome(), clubDto.getEstado(), clubDto.getDataCriacao());

        if (this.clubRepository.existsById(id)) {

            Club club = new Club();
            club.setNome(clubDto.getNome());
            club.setEstado(clubDto.getEstado());
            club.setDatacriacao(clubDto.getDataCriacao());
            club.setId(id);

            this.clubRepository.save(club);
        }

    }

    private void verificarNomeParaUpdate(Long id, String nome, String estado, LocalDate dataCriacao) {
        if (nome == null || nome.length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Para a atualização de nome deve conter apenas 3 caracteres");
        }

        if (estado == null || estado.length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Para a atualização de estado deve conter apenas 2 caracteres");
        }

        List<String> estadosValidos = Arrays.asList(
                "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
                "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
                "RS", "RO", "RR", "SC", "SP", "SE", "TO"
        );

        if (!estadosValidos.contains(estado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O estado informado é inválido");
        }

        if (dataCriacao == null || dataCriacao.isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A data de criação não pode ser no futuro ou posterior a data de hoje");
        }
    }

    public void inativarClub(ClubDto clubDto, Long id) {

        if (!clubRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O clube näo existe em nosso sistema");
        }

        Club club = new Club();
        club.setNome(clubDto.getNome());
        club.setEstado(clubDto.getEstado());
        club.setDatacriacao(clubDto.getDataCriacao());
        club.setAtivo(false);
        club.setId(id);

        this.clubRepository.save(club);

    }

    public ClubDto buscarClubPorid(Long id) {
        if (!clubRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clube nao existe em nosso sistema");
        }

        else {
            Club club = this.clubRepository.findById(id).get();
            ClubDto clubDto = new ClubDto();
            String nome = club.getNome();
            String estado = club.getEstado();
            LocalDate dataCriacao = club.getDatacriacao();
            boolean ativo = club.isAtivo();
            clubDto.setNome(nome);
            clubDto.setEstado(estado);
            clubDto.setDataCriacao(dataCriacao);
            clubDto.setAtivo(ativo);
            return clubDto;
        }
    }

    @Autowired
    ClubRepository repository;
    public Page<Club> search(String nome, String estado, Boolean ativo, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return repository.search(nome, estado, ativo, pageRequest);
    }

    public Page<Club> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

}

