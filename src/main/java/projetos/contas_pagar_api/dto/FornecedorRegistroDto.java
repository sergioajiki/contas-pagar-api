package projetos.contas_pagar_api.dto;

import jakarta.validation.constraints.NotBlank;
import projetos.contas_pagar_api.model.entity.Fornecedor;

public record FornecedorRegistroDto(
        @NotBlank(message = "Field nome can not be null or empty")
        String nome,
        @NotBlank(message = "Field email can not be null or empty")
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
