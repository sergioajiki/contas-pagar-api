package projetos.contas_pagar_api.dto;

import projetos.contas_pagar_api.model.entity.Lancamento;
import projetos.contas_pagar_api.model.entity.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public record UsuarioRegistroDto(
        String nome,
        String email,
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
