package projetos.contas_pagar_api.dto;

import projetos.contas_pagar_api.model.entity.Fornecedor;
import projetos.contas_pagar_api.model.entity.Lancamento;

import java.util.List;

public record FornecedorDto (
        String nome,
        String email,
        String cpf,
        String cnpj,
        List<Lancamento> lancamentoList
) {
    public static FornecedorDto toDto(Fornecedor fornecedor) {
        return new FornecedorDto(
                fornecedor.getNome(),
                fornecedor.getEmail(),
                fornecedor.getCpf(),
                fornecedor.getCnpj(),
                fornecedor.getLancamentoList()
        );
    }

    public static Fornecedor toModel(FornecedorDto fornecedorDto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(fornecedorDto.nome);
        fornecedor.setEmail(fornecedorDto.email);
        fornecedor.setCpf(fornecedorDto.cpf);
        fornecedor.setCnpj(fornecedorDto.cnpj);
        fornecedor.setLancamentoList(fornecedorDto.lancamentoList);

        return fornecedor;
    }

}
