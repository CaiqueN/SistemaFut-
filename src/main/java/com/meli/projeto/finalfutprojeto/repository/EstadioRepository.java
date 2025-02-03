package com.meli.projeto.finalfutprojeto.repository;

import com.meli.projeto.finalfutprojeto.model.Estadio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstadioRepository extends JpaRepository<Estadio, Long> {

    boolean existsByNomeEstadio(String nomeEstadio);

    @Query("SELECT c FROM Estadio c WHERE " +
            "(:nomeEstadio IS NULL OR lower(c.nomeEstadio) LIKE LOWER(CONCAT('%', :nomeEstadio, '%')))")
    Page<Estadio> search(@Param("nomeEstadio") String nomeEstadio, Pageable pageable);
}



