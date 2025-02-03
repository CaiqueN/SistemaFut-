package com.meli.projeto.finalfutprojeto.service;

import com.meli.projeto.finalfutprojeto.dto.PartidaDto;
import com.meli.projeto.finalfutprojeto.model.Club;
import com.meli.projeto.finalfutprojeto.model.Estadio;
import com.meli.projeto.finalfutprojeto.model.Partida;
import com.meli.projeto.finalfutprojeto.repository.PartidaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartidaService {

  private final PartidaRepository partidaRepository;

  public PartidaService(PartidaRepository partidaRepository) {
    this.partidaRepository = partidaRepository;
  }

  public void createPartida(PartidaDto partidaDto) {
    validarClubesAtivos(partidaDto.getClubeCasa(), partidaDto.getClubeFora());
    validarClubes(partidaDto.getClubeCasa(), partidaDto.getClubeFora());
    verificarEstadio(partidaDto.getEstadio());
    verificarResultadoNegativo(partidaDto.getGolsCasa(), partidaDto.getGolsFora());
    verificarDataFutura(partidaDto.getDataHora());
    validarPartidaHora(partidaDto.getDataHora());
    verificarEstadioComJogoNoMesmoDia(partidaDto.getEstadio(), partidaDto.getDataHora());

    Partida partida = new Partida();

    partida.setClubeCasa(partidaDto.getClubeCasa());
    partida.setClubeFora(partidaDto.getClubeFora());
    partida.setEstadio(partidaDto.getEstadio());
    partida.setDataHora(partidaDto.getDataHora());
    partida.setResultado(partidaDto.getResultado());
    partida.setGolsCasa(partidaDto.getGolsCasa());
    partida.setGolsFora(partidaDto.getGolsFora());

    this.partidaRepository.save(partida);
  }

  private void verificarEstadioComJogoNoMesmoDia(Estadio estadio, LocalDateTime dataHora) {
    LocalDate dataPartida = dataHora.toLocalDate();
    List<Partida> partidasNoEstadio = partidaRepository.findPartidasByEstadioAndDataHora(estadio, dataPartida.atStartOfDay());

    if (!partidasNoEstadio.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "O estádio já possui outra partida no mesmo dia.");
    }
  }

  public void validarClubes(Club clubeCasa, Club clubeFora) {
    if (clubeCasa == null || clubeFora == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clubes não encontrados");
    }

    if (!this.partidaRepository.existsById(clubeCasa.getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O clube da casa não encontrado.");
    }

    if (!this.partidaRepository.existsById(clubeFora.getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O clube de fora não encontrado.");
    }

    if (clubeCasa.getId().equals(clubeFora.getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O clube não pode enfrentar a si mesmo.");
    }
  }

  public void verificarEstadio(Estadio estadio) {
    if (!this.partidaRepository.existsById(estadio.getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O estádio não encontrado em nosso banco de dados.");
    }
  }

  public void verificarResultadoNegativo(int golsCasa, int golsFora) {
    if (golsCasa < 0 || golsFora < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Os gols não podem ser negativos.");
    }
  }

  public void verificarDataFutura(LocalDateTime dataHora) {
    if (dataHora.isAfter(LocalDateTime.now())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data e hora não podem ser no futuro.");
    }
  }

  public void validarClubesAtivos(Club clubeCasa, Club clubeFora) {
    if (!clubeCasa.isAtivo()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "O clube da casa está inativo.");
    }

    if (!clubeFora.isAtivo()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "O clube de fora está inativo.");
    }
  }

  public void validarPartidaHora(LocalDateTime dataHora) {
    List<Partida> partidasProximas = buscarPartidasProximas(dataHora);

    if (dataHora.isAfter(LocalDateTime.now())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "A partida não pode ocorrer com horários próximos.");
    }

    for (Partida partidaExistente : partidasProximas) {
      long horasDeDiferenca = Duration.between(partidaExistente.getDataHora(), dataHora).toHours();
      if (Math.abs(horasDeDiferenca) < 48) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "A partida não pode ocorrer com horários próximos (menos de 48 horas de diferença).");
      }
    }
  }

  public List<Partida> buscarPartidasProximas(LocalDateTime dataHora) {
    return partidaRepository.findByDataHoraAfter(dataHora);

  }
}

