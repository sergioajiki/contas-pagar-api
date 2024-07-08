package projetos.contas_pagar_api.dto;

import jakarta.validation.constraints.NotBlank;
import projetos.contas_pagar_api.model.entity.Lancamento;
import projetos.contas_pagar_api.model.entity.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public record UsuarioDto(
        @NotBlank(message = "Field nome can not be null or empty")
        String nome,
        @NotBlank(message = "Field email can not be null or empty")
        String email,
        @NotBlank(message = "Field password can not be null or empty")
        String password,
        List<LancamentoDto> lancamentoList
) {
    public static UsuarioDto toDto(Usuario usuario) {
        List<Lancamento> lancamentoList = usuario.getLancamentoList();
        List<LancamentoDto> lancamentoDtoList = lancamentoList.stream()
                .map(LancamentoDto::toDto)
                .collect(Collectors.toList());
        return new UsuarioDto(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPassword(),
                lancamentoDtoList
        );
    }

    public static Usuario toModel(UsuarioRegistroDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDto.nome());
        usuario.setEmail(usuarioDto.email());
        usuario.setPassword(usuarioDto.password());

        return usuario;
    }
}
