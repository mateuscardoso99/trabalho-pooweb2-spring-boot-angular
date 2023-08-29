package com.trabalho.api.service;

import java.util.Arrays;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.Cliente;
import com.trabalho.api.model.Permissoes;
import com.trabalho.api.repository.ClienteRepository;
import com.trabalho.api.request.CadastroCliente;

@Service
public class ClienteService {
    private final PasswordEncoder passwordEncoder;
    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder){
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Cliente findById(Long id) throws Exception{
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(() -> new DataNotFoundException("cliente não encontrado"));
        return cliente;
    }

    @Transactional
    public Cliente save(CadastroCliente cliente){
        Cliente c = new Cliente();
        c.setNome(cliente.getNome());
        c.setEmail(cliente.getEmail());
        c.setSenha(passwordEncoder.encode(cliente.getSenha()));
        c.setPermissoes(Arrays.asList(Permissoes.CLIENTE));
        return clienteRepository.save(c);
    }

    @Transactional
    public Cliente atualizar(CadastroCliente cadastroCliente, Long id) throws Exception{
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(() -> new DataNotFoundException("cliente não encontrado"));
        cliente.setEmail(cadastroCliente.getEmail());
        cliente.setNome(cadastroCliente.getNome());
        cliente.setSenha(passwordEncoder.encode(cadastroCliente.getSenha()));
        return this.clienteRepository.save(cliente);
    }

    @Transactional
    public void apagar(Long id) throws Exception{
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(() -> new DataNotFoundException("cliente não encontrado"));
        this.clienteRepository.delete(cliente);
    }
}
