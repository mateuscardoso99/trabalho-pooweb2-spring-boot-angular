package com.trabalho.api.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trabalho.api.model.UsuarioAdminEmpresa;

@Repository
public interface UsuarioAdminEmpresaRepository extends JpaRepository<UsuarioAdminEmpresa,Long>{
    @Query("SELECT adm FROM UsuarioAdminEmpresa adm WHERE adm.empresa.id = ?1")
    Collection<UsuarioAdminEmpresa> findAllByEmpresa(Long idEmpresa);

    @Query("SELECT adm FROM UsuarioAdminEmpresa adm WHERE adm.empresa.id = ?1 AND adm.id = ?2")
    Optional<UsuarioAdminEmpresa> findUsuarioEmpresa(Long idEmpresa, Long idUsuario);
}
