package com.trabalho.api.dto;

import com.trabalho.api.model.AdminEstabelecimento;
import com.trabalho.api.model.Estabelecimento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminEstabelecimentoDTO extends UsuarioDTO{
    private EstabelecimentoDTO estabelecimento;

    public AdminEstabelecimentoDTO(UsuarioDTO usuarioDTO, Estabelecimento estab){
        this.id = usuarioDTO.id;
        this.email = usuarioDTO.email;
        this.endereco = usuarioDTO.endereco;
        this.nome = usuarioDTO.nome;
        this.permissoes = usuarioDTO.permissoes;
        this.estabelecimento = EstabelecimentoDTO.convert(estab);
    }

    public static AdminEstabelecimentoDTO convert(AdminEstabelecimento adminEstab){
        AdminEstabelecimentoDTO adminDTO = new AdminEstabelecimentoDTO();
        adminDTO.setId(adminEstab.getId());
        adminDTO.setEmail(adminEstab.getEmail());
        adminDTO.setNome(adminEstab.getNome());
        adminDTO.setPermissoes(adminEstab.getPermissoes());
        adminDTO.setEndereco(EnderecoDTO.convert(adminEstab.getEndereco()));
        adminDTO.setEstabelecimento(EstabelecimentoDTO.convert(adminEstab.getEstabelecimento()));
        return adminDTO;
    }
}
