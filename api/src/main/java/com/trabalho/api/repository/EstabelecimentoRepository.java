package com.trabalho.api.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trabalho.api.model.Estabelecimento;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long>{
    @Query("SELECT e FROM Estabelecimento e WHERE e.empresa.id = ?1")
    Collection<Estabelecimento> findAllByEmpresa(Long id);

    @Query("SELECT e FROM Estabelecimento e WHERE e.empresa.id = ?1 AND e.id = ?2")
    Optional<Estabelecimento> findByIdAndEmpresaId(Long idEstab, Long idEmpresa);

    @Query("SELECT DISTINCT e.endereco.cidade FROM Estabelecimento e WHERE e.endereco.cidade ILIKE %:cidade%")
    Collection<String> findCidadesEstabs(@Param("cidade") String cidade);

    @Query("SELECT e FROM Estabelecimento e WHERE e.endereco.cidade = ?1")
    Collection<Estabelecimento> findEstabelecimentosCidade(String cidade);
}
