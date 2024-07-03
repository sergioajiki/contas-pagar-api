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
        List<LancamentoDto> lancamentoList
) {
    public static FornecedorDto toDto(Fornecedor fornecedor) {
        List<Lancamento> lancamentoList = fornecedor.getLancamentoList();
        List<LancamentoDto> lancamentoDtoList = lancamentoList.stream()
                .map(LancamentoDto::toDto)
                .collect(Collectors.toList());
        return new FornecedorDto(
                fornecedor.getNome(),
                fornecedor.getEmail(),
                fornecedor.getCpf(),
                fornecedor.getCnpj(),
                lancamentoDtoList
        );
    }

//    public static Fornecedor toModel(FornecedorDto fornecedorDto) {
//
//        Fornecedor fornecedor = new Fornecedor();
//        fornecedor.setNome(fornecedorDto.nome);
//        fornecedor.setEmail(fornecedorDto.email);
//        fornecedor.setCpf(fornecedorDto.cpf);
//        fornecedor.setCnpj(fornecedorDto.cnpj);
//        fornecedor.setLancamentoList(fornecedorDto.lancamentoList);
//
//        return fornecedor;
//    }

}
