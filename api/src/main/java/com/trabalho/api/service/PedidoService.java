package com.trabalho.api.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.api.dto.PedidoDTO;
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

    public PedidoService(PedidoRepository pedidoRepository, EstabelecimentoRepository estabelecimentoRepository, UsuarioRepository usuarioRepository){
        this.pedidoRepository = pedidoRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Pedido findByIdCliente(Long idPedido) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return this.pedidoRepository.findByIdCliente(userDetails.getId(), idPedido).orElseThrow(() -> new DataNotFoundException("pedido não encontrado"));
    }

    @Transactional
    public PedidoDTO salvar(CadastroPedido dados) throws Exception{
        Optional<Estabelecimento> estabelecimento = this.estabelecimentoRepository.findById(dados.getIdEstabelecimento());
        if(!estabelecimento.isPresent()){
            throw new DataNotFoundException("estab não encontrado");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Usuario usuario = this.usuarioRepository.findByEmail(userDetails.getEmail()).get();
        Cliente cliente = new Cliente();
        cliente.setId(usuario.getId());
        cliente.setEmail(usuario.getEmail());
        cliente.setNome(usuario.getNome());

        Pedido pedido = new Pedido();
        pedido.setDescricao(dados.getDescricao());
        pedido.setStatusPedido(StatusPedido.PENDENTE);
        pedido.setEstabelecimento(estabelecimento.get());
        pedido.setCliente(cliente);

        this.pedidoRepository.save(pedido);
        return PedidoDTO.convert(pedido);
    }

    @Transactional
    public void apagar(Long idPedido) throws Exception{
        Pedido p = this.findByIdCliente(idPedido);
        this.pedidoRepository.deleteById(p.getId());
    }
}
