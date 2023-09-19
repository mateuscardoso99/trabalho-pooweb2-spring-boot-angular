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
import com.trabalho.api.security.JwtUtils;
import com.trabalho.api.security.UserDetailsImpl;

import jakarta.servlet.http.HttpServletRequest;

/**
 * os métodos que pegam o id_empresa do token, são chamados pelo EmpresaController
 * intuito é impedir que usuario de uma empresa veja dados de outra
 */
@Service
public class PedidoService {
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final PedidoRepository pedidoRepository;
    private final JwtUtils jwtUtils;

    public PedidoService(PedidoRepository pedidoRepository, EstabelecimentoRepository estabelecimentoRepository, JwtUtils jwtUtils){
        this.pedidoRepository = pedidoRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.jwtUtils = jwtUtils;
    }

    public Collection<Pedido> findAllByCliente(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.pedidoRepository.findAllByCliente(userDetails.getId());
    }

    public Collection<Pedido> findAllByEstabelecimento(Long idEstab){
        return this.pedidoRepository.findAllByEstabelecimento(idEstab);
    }

    public Collection<Pedido> findAllByEstabelecimentoIdAndEmpresaId(HttpServletRequest request, Long idEstab){
        String token = jwtUtils.getTokenFromRequest(request);
        Long idEmpresa = Long.parseLong(jwtUtils.getClaimsFromJwtToken(token).get("id_empresa").toString());
        return this.pedidoRepository.findAllByEstabelecimentoIdAndEmpresaId(idEstab,idEmpresa);
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
        Optional<Estabelecimento> estabelecimento = this.estabelecimentoRepository.findById(dados.idEstabelecimento());
        if(!estabelecimento.isPresent()){
            throw new DataNotFoundException("estab não encontrado");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Cliente cliente = new Cliente();
        cliente.setId(userDetails.getId());
        cliente.setEmail(userDetails.getEmail());
        cliente.setNome(userDetails.getUsername());

        Pedido pedido = new Pedido();
        pedido.setDescricao(dados.descricao());
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
    }

    @Transactional
    public Pedido mudarStatus(Long idEstab, Long idPedido, StatusPedido statusPedido) throws Exception{
        Pedido pedido = this.findByIdAndEstabelecimentoId(idEstab, idPedido);
        pedido.setStatusPedido(statusPedido);
        return pedido;
    }
}
