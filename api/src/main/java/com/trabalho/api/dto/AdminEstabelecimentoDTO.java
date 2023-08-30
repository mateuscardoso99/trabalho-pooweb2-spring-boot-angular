package com.trabalho.api.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.trabalho.api.model.AdminEstabelecimento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminEstabelecimentoDTO extends UsuarioDTO{
    private Long idEstabelecimento;
    private String estabelecimento;

    public static AdminEstabelecimentoDTO convert(AdminEstabelecimento adminEstab){
        AdminEstabelecimentoDTO adminDTO = new AdminEstabelecimentoDTO();
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

    public static Collection<AdminEstabelecimentoDTO> convert(Collection<AdminEstabelecimento> usuarios){
        Collection<AdminEstabelecimentoDTO> admins = new ArrayList<>();
        usuarios.forEach(e -> {
            admins.add(convert(e));
        });
        return admins;
    }
}
