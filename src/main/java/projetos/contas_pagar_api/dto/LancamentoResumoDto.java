package projetos.contas_pagar_api.dto;

import projetos.contas_pagar_api.model.entity.Lancamento;

import java.time.LocalDate;

public record LancamentoResumoDto(
        Long id,
        Double valor,
        LocalDate dataVencimento,
        Long idUsuario,
        String nomeUsuario
) {
    public static LancamentoResumoDto toDto(Lancamento lancamento) {
        return new LancamentoResumoDto(
                lancamento.getId(),
                lancamento.getValor(),
                lancamento.getDataVencimento(),
                lancamento.getUsuario().getId(),
                lancamento.getUsuario().getNome()
        );
    }
}
