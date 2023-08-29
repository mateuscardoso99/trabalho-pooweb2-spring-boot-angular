package com.trabalho.api.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.Cliente;
import com.trabalho.api.model.Estabelecimento;
import com.trabalho.api.model.Pedido;
import com.trabalho.api.model.Usuario;
import com.trabalho.api.model.Pedido.StatusPedido;
import com.trabalho.api.repository.EstabelecimentoRepository;
import com.trabalho.api.repository.PedidoRepository;
import com.trabalho.api.repository.UsuarioRepository;
import com.trabalho.api.request.CadastroPedido;
import com.trabalho.api.security.UserDetailsImpl;

@Service
public class PedidoService {
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private Authentication authentication;

    public PedidoService(PedidoRepository pedidoRepository, EstabelecimentoRepository estabelecimentoRepository, UsuarioRepository usuarioRepository){
        this.pedidoRepository = pedidoRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
    }

    public Collection<Pedido> findAllByCliente(){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return this.pedidoRepository.findAllByCliente(userDetails.getId());
    }

    public Collection<Pedido> findAllByEstabelecimento(Long idEstab){
        return this.pedidoRepository.findAllByEstabelecimento(idEstab);
    }

    public Pedido findByIdCliente(Long idPedido) throws Exception{
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return this.pedidoRepository.findByIdCliente(userDetails.getId(), idPedido).orElseThrow(() -> new DataNotFoundException("pedido não encontrado"));
    }

    public Pedido findByIdEstabelecimento(Long idEstab, Long idPedido) throws Exception{
        return this.pedidoRepository.findByIdEstabelecimento(idEstab, idPedido).orElseThrow(() -> new DataNotFoundException("pedido não encontrado"));
    }

    @Transactional
    public Pedido salvar(CadastroPedido dados) throws Exception{
        Optional<Estabelecimento> estabelecimento = this.estabelecimentoRepository.findById(dados.getIdEstabelecimento());
        if(!estabelecimento.isPresent()){
            throw new DataNotFoundException("estab não encontrado");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Usuario usuario = this.usuarioRepository.findByEmail(userDetails.getEmail()).get();
        Cliente cliente = new Cliente();
        cliente.setId(usuario.getId());
        cliente.setEmail(usuario.getEmail());
        cliente.setNome(usuario.getNome());

        Pedido pedido = new Pedido();
        pedido.setDescricao(dados.getDescricao());
        pedido.setStatusPedido(StatusPedido.PENDENTE);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setEstabelecimento(estabelecimento.get());
        pedido.setCliente(cliente);

        return this.pedidoRepository.save(pedido);
    }

    @Transactional
    public void apagar(Long idPedido) throws Exception{
        Pedido p = this.findByIdCliente(idPedido);
        this.pedidoRepository.deleteById(p.getId());
    }
}
