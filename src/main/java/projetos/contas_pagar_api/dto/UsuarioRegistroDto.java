package projetos.contas_pagar_api.dto;

import jakarta.validation.constraints.NotBlank;
import projetos.contas_pagar_api.model.entity.Lancamento;
import projetos.contas_pagar_api.model.entity.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public record UsuarioRegistroDto(
        @NotBlank(message = "Field nome can not be null or empty")
        String nome,
        @NotBlank(message = "Field email can not be null or empty")
        String email,
        @NotBlank(message = "Field password can not be null or empty")
        String password
) {
    public static UsuarioRegistroDto toDto(Usuario usuario) {
        return new UsuarioRegistroDto(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPassword()
        );
    }

    public static Usuario toModel(UsuarioRegistroDto usuarioRegistroDto) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuario.getNome());
        usuario.setEmail(usuario.getEmail());
        usuario.setPassword(usuario.getPassword());

        return usuario;
    }
}
