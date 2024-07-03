package projetos.contas_pagar_api.dto;

import org.springframework.cglib.core.Local;
import projetos.contas_pagar_api.model.entity.Lancamento;

import java.time.LocalDate;

public record LancamentoDto(
        String descricao,
        Double valor,
        LocalDate dataVencimento,
        LocalDate dataPagamento
) {
    public static LancamentoDto toDto(Lancamento lancamento) {
        return new LancamentoDto(
                lancamento.getDescricao(),
                lancamento.getValor(),
                lancamento.getDataVencimento(),
                lancamento.getDataPagamento()
        );
    }

    public static Lancamento toModel(LancamentoDto lancamentoDto) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao(lancamentoDto.descricao);
        lancamento.setValor(lancamentoDto.valor);
        lancamento.setDataVencimento(lancamentoDto.dataVencimento);
        lancamento.setDataPagamento(lancamentoDto.dataPagamento);

        return lancamento;
    }
}