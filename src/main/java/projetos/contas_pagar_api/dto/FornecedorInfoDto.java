package projetos.contas_pagar_api.dto;

import projetos.contas_pagar_api.model.entity.Fornecedor;

public record FornecedorInfoDto(
        String nome,
        String email,
        String cpf,
        String cnpj
) {
    public static FornecedorInfoDto toDto(Fornecedor fornecedor) {
        return new FornecedorInfoDto(
                fornecedor.getNome(),
                fornecedor.getEmail(),
                fornecedor.getCpf(),
                fornecedor.getCnpj()
        );
    }
}
