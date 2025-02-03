package com.meli.projeto.finalfutprojeto.repository;

import com.meli.projeto.finalfutprojeto.model.Club;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<Club, Long> {
    boolean existsByNomeAndEstado(String nome, String estado);

    @Query("SELECT c FROM Club c WHERE "
            + "(:nome IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND "
            + "(:estado IS NULL OR c.estado = :estado) AND "
            + "(:ativo IS NULL OR c.ativo = :ativo)")
    Page<Club> search(@Param("nome") String nome,
                      @Param("estado") String estado,
                      @Param("ativo") Boolean ativo,
                      Pageable pageable);
}


