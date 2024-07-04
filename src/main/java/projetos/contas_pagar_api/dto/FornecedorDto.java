package projetos.contas_pagar_api.dto;

import projetos.contas_pagar_api.model.entity.Fornecedor;
import projetos.contas_pagar_api.model.entity.Lancamento;


import java.util.List;
import java.util.stream.Collectors;

public record FornecedorDto (
        String nome,
        String email,
        String cpf,
        String cnpj,
        List<LancamentoResumoDto> lancamentoList
) {
    public static FornecedorDto toDto(Fornecedor fornecedor) {
        List<Lancamento> lancamentoList = fornecedor.getLancamentoList();
        List<LancamentoResumoDto> lancamentoDtoList = lancamentoList.stream()
                .map(LancamentoResumoDto::toDto)
                .collect(Collectors.toList());
        return new FornecedorDto(
                fornecedor.getNome(),
                fornecedor.getEmail(),
                fornecedor.getCpf(),
                fornecedor.getCnpj(),
                lancamentoDtoList
        );
    }

    public static Fornecedor toModel(FornecedorRegistroDto fornecedorRegistroDto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(fornecedorRegistroDto.nome());
        fornecedor.setEmail(fornecedorRegistroDto.email());
        fornecedor.setCpf(fornecedorRegistroDto.cpf());
        fornecedor.setCnpj(fornecedorRegistroDto.cnpj());
        return fornecedor;
    };
}
