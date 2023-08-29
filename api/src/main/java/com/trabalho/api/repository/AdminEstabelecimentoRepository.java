package com.trabalho.api.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trabalho.api.model.AdminEstabelecimento;

@Repository
public interface AdminEstabelecimentoRepository extends JpaRepository<AdminEstabelecimento,Long>{
    @Query("SELECT adm FROM AdminEstabelecimento adm WHERE adm.estabelecimento.id = ?1")
    Collection<AdminEstabelecimento> findAllByEstabelecimento(Long idEstabelecimento);
}
