package com.trabalho.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.Cliente;
import com.trabalho.api.model.Permissoes;
import com.trabalho.api.repository.ClienteRepository;
import com.trabalho.api.request.CadastroCliente;
import com.trabalho.api.security.UserDetailsImpl;

@Service
public class ClienteService {
    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder){
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Collection<Cliente> findAll(){
        return this.clienteRepository.findAll();
    }

    public Cliente findById(Long id) throws Exception{
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(() -> new DataNotFoundException("cliente não encontrado"));
        return cliente;
    }

    public Cliente findClienteByUsuarioLogado() throws Exception{
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cliente cliente = this.clienteRepository.findById(userDetailsImpl.getId()).orElseThrow(() -> new DataNotFoundException("cliente não encontrado"));
        return cliente;
    }

    @Transactional
    public Cliente save(CadastroCliente cliente){
        Cliente c = new Cliente();
        c.setNome(cliente.nome());
        c.setEmail(cliente.email());
        c.setSenha(passwordEncoder.encode(cliente.senha()));
        c.setPermissoes(Arrays.asList(Permissoes.CLIENTE));
        c.setPedidos(new ArrayList<>());
        return clienteRepository.save(c);
    }

    @Transactional
    public Cliente atualizar(CadastroCliente cadastroCliente) throws Exception{
        Cliente cliente = this.findClienteByUsuarioLogado();
        cliente.setEmail(cadastroCliente.email());
        cliente.setNome(cadastroCliente.nome());
        cliente.setSenha(passwordEncoder.encode(cadastroCliente.senha()));
        return cliente;
    }

    @Transactional
    public void handleAtivacao(boolean ativar) throws Exception{
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cliente c = this.findById(userDetailsImpl.getId());
        c.setAtivo(ativar ? true : false);
        clienteRepository.save(c);
    }
}
