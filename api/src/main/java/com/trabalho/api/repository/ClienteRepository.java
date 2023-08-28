package com.trabalho.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabalho.api.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    // @Query("select c from Cliente c join Usuario u")
    // public Optional<Cliente> findById(Long id);
}
