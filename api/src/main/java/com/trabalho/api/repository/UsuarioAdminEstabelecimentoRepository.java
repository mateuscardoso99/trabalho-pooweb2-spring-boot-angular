package com.trabalho.api.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trabalho.api.model.UsuarioAdminEstabelecimento;

@Repository
public interface UsuarioAdminEstabelecimentoRepository extends JpaRepository<UsuarioAdminEstabelecimento,Long>{
    @Query("SELECT adm FROM UsuarioAdminEstabelecimento adm WHERE adm.estabelecimento.id = ?1")
    Collection<UsuarioAdminEstabelecimento> findAllByEstabelecimento(Long idEstabelecimento);

    @Query("SELECT adm FROM UsuarioAdminEstabelecimento adm WHERE adm.estabelecimento.id = ?1 AND adm.id = ?2")
    Optional<UsuarioAdminEstabelecimento> findUsuarioEstabelecimento(Long idEstabelecimento, Long idUsuario);
}
