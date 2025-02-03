package com.meli.projeto.finalfutprojeto.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "partida")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clube_casa_id", nullable = false)
    private Club clubeCasa;

    @ManyToOne
    @JoinColumn(name = "clube_fora_id", nullable = false)
    private Club clubeFora;

    @ManyToOne
    @JoinColumn(name ="estadio_id", nullable = false )
    private Estadio estadio;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(length = 6)
    private String resultado;

    @Column(nullable = false)
    private int golsCasa;

    @Column(nullable = false)
    private int golsFora;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
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
