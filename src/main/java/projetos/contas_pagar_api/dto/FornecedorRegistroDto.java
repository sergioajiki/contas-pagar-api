package projetos.contas_pagar_api.dto;

import projetos.contas_pagar_api.model.entity.Fornecedor;

public record FornecedorRegistroDto(
        String nome,
        String email,
        String cpf,
        String cnpj
) {
    public static FornecedorRegistroDto toDto(Fornecedor fornecedor) {
        return new FornecedorRegistroDto(
                fornecedor.getNome(),
                fornecedor.getEmail(),
                fornecedor.getCpf(),
                fornecedor.getCnpj()
        );
    }
}
