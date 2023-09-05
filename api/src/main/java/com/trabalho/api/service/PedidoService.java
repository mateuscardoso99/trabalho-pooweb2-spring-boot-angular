package com.trabalho.api.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.Cliente;
import com.trabalho.api.model.Estabelecimento;
import com.trabalho.api.model.Pedido;
import com.trabalho.api.model.Pedido.StatusPedido;
import com.trabalho.api.repository.EstabelecimentoRepository;
import com.trabalho.api.repository.PedidoRepository;
import com.trabalho.api.request.CadastroPedido;
import com.trabalho.api.security.UserDetailsImpl;

@Service
public class PedidoService {
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository, EstabelecimentoRepository estabelecimentoRepository){
        this.pedidoRepository = pedidoRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    public Collection<Pedido> findAllByCliente(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.pedidoRepository.findAllByCliente(userDetails.getId());
    }

    public Collection<Pedido> findAllByEstabelecimento(Long idEstab){
        return this.pedidoRepository.findAllByEstabelecimento(idEstab);
    }

    public Pedido findByIdAndClienteId(Long idPedido) throws Exception{
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.pedidoRepository.findByIdCliente(userDetails.getId(), idPedido).orElseThrow(() -> new DataNotFoundException("pedido não encontrado"));
    }

    public Pedido findByIdAndEstabelecimentoId(Long idEstab, Long idPedido) throws Exception{
        return this.pedidoRepository.findByIdEstabelecimento(idEstab, idPedido).orElseThrow(() -> new DataNotFoundException("pedido não encontrado"));
    }

    @Transactional
    public Pedido salvar(CadastroPedido dados) throws Exception{
        Optional<Estabelecimento> estabelecimento = this.estabelecimentoRepository.findById(dados.getIdEstabelecimento());
        if(!estabelecimento.isPresent()){
            throw new DataNotFoundException("estab não encontrado");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Cliente cliente = new Cliente();
        cliente.setId(userDetails.getId());
        cliente.setEmail(userDetails.getEmail());
        cliente.setNome(userDetails.getUsername());

        Pedido pedido = new Pedido();
        pedido.setDescricao(dados.getDescricao());
        pedido.setStatusPedido(StatusPedido.PENDENTE);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setEstabelecimento(estabelecimento.get());
        pedido.setCliente(cliente);

        return this.pedidoRepository.save(pedido);
    }

    @Transactional
    public void handleAtivacao(Long idPedido, boolean ativar) throws Exception{
        Pedido p = this.findByIdAndClienteId(idPedido);
        p.setAtivo(ativar ? true : false);
        p.setStatusPedido(ativar ? StatusPedido.PENDENTE : StatusPedido.CANCELADO);
        this.pedidoRepository.save(p);
    }

    @Transactional
    public Pedido mudarStatus(Long idEstab, Long idPedido, StatusPedido statusPedido) throws Exception{
        Pedido pedido = this.findByIdAndEstabelecimentoId(idEstab, idPedido);
        pedido.setStatusPedido(statusPedido);
        return pedidoRepository.save(pedido);
    }
}
