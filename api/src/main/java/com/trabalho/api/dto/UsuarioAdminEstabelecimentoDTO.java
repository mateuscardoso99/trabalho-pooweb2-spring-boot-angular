package com.trabalho.api.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.trabalho.api.model.UsuarioAdminEstabelecimento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioAdminEstabelecimentoDTO extends UsuarioDTO{
    private Long idEstabelecimento;
    private String estabelecimento;

    public static UsuarioAdminEstabelecimentoDTO convert(UsuarioAdminEstabelecimento adminEstab){
        UsuarioAdminEstabelecimentoDTO adminDTO = new UsuarioAdminEstabelecimentoDTO();
        adminDTO.setId(adminEstab.getId());
        adminDTO.setEmail(adminEstab.getEmail());
        adminDTO.setNome(adminEstab.getNome());
        adminDTO.setAtivo(adminEstab.isAtivo());
        adminDTO.setPermissoes(adminEstab.getPermissoes());
        adminDTO.setEndereco(adminEstab.getEndereco() != null ? EnderecoDTO.convert(adminEstab.getEndereco()) : null);
        adminDTO.setIdEstabelecimento(adminEstab.getEstabelecimento().getId());
        adminDTO.setEstabelecimento(adminEstab.getEstabelecimento().getNome());
        return adminDTO;
    }

    public static Collection<UsuarioAdminEstabelecimentoDTO> convert(Collection<UsuarioAdminEstabelecimento> usuarios){
        Collection<UsuarioAdminEstabelecimentoDTO> admins = new ArrayList<>();
        usuarios.forEach(e -> {
            admins.add(convert(e));
        });
        return admins;
    }
}
