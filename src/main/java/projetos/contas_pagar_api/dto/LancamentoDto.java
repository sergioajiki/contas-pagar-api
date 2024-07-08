package projetos.contas_pagar_api.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.cglib.core.Local;
import projetos.contas_pagar_api.model.entity.Fornecedor;
import projetos.contas_pagar_api.model.entity.Lancamento;
import projetos.contas_pagar_api.model.entity.Usuario;

import java.time.LocalDate;

public record LancamentoDto(
        Long idUsuario,
        Long idFornecedor,
        String descricao,
        @NotBlank(message = "Field valor can not be null or empty")
        Double valor,
        @NotBlank(message = "Field dataVencimento can not be null or empty")
        LocalDate dataVencimento,
        LocalDate dataPagamento
) {
    public static LancamentoDto toDto(Lancamento lancamento) {
        return new LancamentoDto(
                lancamento.getUsuario().getId(),
                lancamento.getFornecedor().getId(),
                lancamento.getDescricao(),
                lancamento.getValor(),
                lancamento.getDataVencimento(),
                lancamento.getDataPagamento()
        );
    }

    public static Lancamento toModel(LancamentoDto lancamentoDto, Fornecedor fornecedor, Usuario usuario) {
        Lancamento lancamento = new Lancamento();
        lancamento.setFornecedor(fornecedor);
        lancamento.setUsuario(usuario);
        lancamento.setDescricao(lancamentoDto.descricao());
        lancamento.setValor(lancamentoDto.valor());
        lancamento.setDataVencimento(lancamentoDto.dataVencimento());
        lancamento.setDataPagamento(lancamentoDto.dataPagamento());

        return lancamento;
    }
}
