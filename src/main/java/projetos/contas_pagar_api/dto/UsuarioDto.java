package projetos.contas_pagar_api.dto;

import projetos.contas_pagar_api.model.entity.Lancamento;
import projetos.contas_pagar_api.model.entity.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public record UsuarioDto(
    String nome,
    String email,
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
}
