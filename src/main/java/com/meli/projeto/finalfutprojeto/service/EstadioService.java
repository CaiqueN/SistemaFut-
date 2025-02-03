package com.meli.projeto.finalfutprojeto.service;

import com.meli.projeto.finalfutprojeto.dto.EstadioDto;
import com.meli.projeto.finalfutprojeto.model.Club;
import com.meli.projeto.finalfutprojeto.model.Estadio;
import com.meli.projeto.finalfutprojeto.repository.ClubRepository;
import com.meli.projeto.finalfutprojeto.repository.EstadioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EstadioService {

    private final EstadioRepository estadioRepository;

    public EstadioService(EstadioRepository estadioRepository) {
        this.estadioRepository = estadioRepository;
    }

    public void createEstadio(EstadioDto estadioDto) {

        Estadio estadio = new Estadio();
        validarEstadio(estadioDto.getNomeEstadio());
        estadio.setNomeEstadio(estadioDto.getNomeEstadio());
        verificarEstadioExistente(estadioDto.getNomeEstadio());

        this.estadioRepository.save(estadio);
    }

    private void validarEstadio(String nomeEstadio) {
        if (nomeEstadio == null || nomeEstadio.length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O nome deve conter 3 caracteres" + nomeEstadio);
        }
    }

    private void verificarEstadioExistente(String nomeEstadio) {
        if (this.estadioRepository.existsByNomeEstadio(nomeEstadio)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "O estadio contem cadastrado em nosso sistema : " + nomeEstadio);
        }
    }

    public void updateEstadio(EstadioDto estadioDto, Long id) {

        validarEstadio(estadioDto.getNomeEstadio());
        verificarEstadioUpdate(estadioDto.getNomeEstadio());
        verificarNomeEstadio(estadioDto.getNomeEstadio());

        if (estadioRepository.existsById(id)) {

            Estadio estadio = new Estadio();
            estadio.setNomeEstadio(estadioDto.getNomeEstadio());
            estadio.setId(id);

            this.estadioRepository.save(estadio);
        }
    }

    private void verificarEstadioUpdate(String nomeEstadio) {

        if (nomeEstadio == null || nomeEstadio.length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Para atualizar estadio do novo nome deveria ser maior que 2 caracteres: " + nomeEstadio);
        }

    }

    private void verificarNomeEstadio(String nomeEstadio) {
        if (nomeEstadio == null || nomeEstadio.length() < 3) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um estadio cadastrado em nosso sistema: " + nomeEstadio);

        }

        if (estadioRepository.existsByNomeEstadio(nomeEstadio)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ja existe um estadio cadastrado em nosso sistema:  " + nomeEstadio);
        }
    }

    public EstadioDto buscarEstadioPorid(Long id) {
        if (!estadioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possivel encontrar estadio com o id " + id);
        } else {
            Estadio estadio = estadioRepository.findById(id).get();
            EstadioDto estadioDto = new EstadioDto(estadio.getNomeEstadio());
            String nomeEstadio = estadio.getNomeEstadio();
            estadioDto.setNomeDoEstadio(nomeEstadio);
            return estadioDto;
        }

    }

    @Autowired
    EstadioRepository repository;

    public Page<Estadio> search(String nomeEstadio, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nomeEstadio");
        return repository.search(nomeEstadio, pageRequest);
    }

    public Page<Estadio> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
