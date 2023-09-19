package com.trabalho.api.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trabalho.api.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    @Query("SELECT p FROM Pedido p WHERE p.cliente.id = ?1 AND p.id = ?2")
    Optional<Pedido> findByIdCliente(Long idCliente, Long idPedido);

    @Query("SELECT p FROM Pedido p WHERE p.cliente.id = ?1")
    Collection<Pedido> findAllByCliente(Long idCliente);

    @Query("SELECT p FROM Pedido p WHERE p.estabelecimento.id = ?1 AND p.id = ?2")
    Optional<Pedido> findByIdEstabelecimento(Long idEstab, Long idPedido);

    @Query("SELECT p FROM Pedido p WHERE p.estabelecimento.id = ?1")
    Collection<Pedido> findAllByEstabelecimento(Long idEstab);

    @Query("SELECT p FROM Pedido p WHERE p.estabelecimento.id = ?1 AND p.estabelecimento.empresa.id = ?2")
    Collection<Pedido> findAllByEstabelecimentoIdAndEmpresaId(Long idEstab, Long idEmpresa);
}
