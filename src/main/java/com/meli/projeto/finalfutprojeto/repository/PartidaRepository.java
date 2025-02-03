package com.meli.projeto.finalfutprojeto.repository;

import com.meli.projeto.finalfutprojeto.model.Estadio;
import com.meli.projeto.finalfutprojeto.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

    List<Partida> findByDataHoraAfter(LocalDateTime dataHora);
    List<Partida> findPartidasByEstadioAndDataHora(Estadio estadio, LocalDateTime dataHora);
}
