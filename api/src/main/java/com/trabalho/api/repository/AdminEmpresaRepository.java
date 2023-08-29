package com.trabalho.api.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trabalho.api.model.AdminEmpresa;

@Repository
public interface AdminEmpresaRepository extends JpaRepository<AdminEmpresa,Long>{
    @Query("SELECT adm FROM AdminEmpresa adm WHERE adm.empresa.id = ?1")
    Collection<AdminEmpresa> findAllByEmpresa(Long idEmpresa);

    @Query("SELECT adm FROM AdminEmpresa adm WHERE adm.empresa.id = ?1 AND adm.id = ?2")
    Optional<AdminEmpresa> findUsuarioEmpresa(Long idEmpresa, Long idUsuario);
}
