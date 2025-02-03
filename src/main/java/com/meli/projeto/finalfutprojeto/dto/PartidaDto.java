package com.meli.projeto.finalfutprojeto.dto;

import com.meli.projeto.finalfutprojeto.model.Club;
import com.meli.projeto.finalfutprojeto.model.Estadio;
import java.time.LocalDateTime;

public class PartidaDto {

    private Club clubeCasa;
    private Club clubeFora;
    private LocalDateTime dataHora;
    private String resultado;
    private Estadio estadio;
    private int golsCasa;
    private int golsFora;


    public Club getClubeCasa() {
        return clubeCasa;
    }

    public void setClubeCasa(Club clubeCasa) {
        this.clubeCasa = clubeCasa;
    }

    public Club getClubeFora() {
        return clubeFora;
    }

    public void setClubeFora(Club clubeFora) {
        this.clubeFora = clubeFora;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public int getGolsCasa() {
        return golsCasa;
    }

    public void setGolsCasa(int golsCasa) {
        this.golsCasa = golsCasa;
    }

    public int getGolsFora() {
        return golsFora;
    }

    public void setGolsFora(int golsFora) {
        this.golsFora = golsFora;
    }

}