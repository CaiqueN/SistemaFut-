package com.meli.projeto.finalfutprojeto.dto;

import java.time.LocalDate;

public class ClubDto {

        private String nome;
        private LocalDate dataCriacao;
        private String estado;
        private boolean ativo;

        public ClubDto(String nome, String estado, LocalDate datacriacao) {
        }

        public ClubDto() {

        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public LocalDate getDataCriacao() {
            return dataCriacao;
        }

        public void setDataCriacao(LocalDate dataCriacao) {
            this.dataCriacao = dataCriacao;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public boolean isAtivo() {
            return ativo;
        }

        public void setAtivo(boolean ativo) {
            this.ativo = ativo;
        }
    }