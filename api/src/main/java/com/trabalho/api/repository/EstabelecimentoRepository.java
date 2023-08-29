package com.trabalho.api.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trabalho.api.model.Estabelecimento;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long>{
    @Query("SELECT e FROM Estabelecimento e WHERE e.empresa.id = ?1")
    Collection<Estabelecimento> findAllByEmpresa(Long id);
}
