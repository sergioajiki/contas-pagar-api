package projetos.contas_pagar_api.service;


import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetos.contas_pagar_api.advice.exception.DuplicateEntryException;
import projetos.contas_pagar_api.advice.exception.InvalidDataException;
import projetos.contas_pagar_api.advice.exception.InvalidEmailFormatException;
import projetos.contas_pagar_api.advice.exception.NotFoundException;
import projetos.contas_pagar_api.dto.FornecedorDto;
import projetos.contas_pagar_api.dto.FornecedorInfoDto;
import projetos.contas_pagar_api.dto.FornecedorRegistroDto;
import projetos.contas_pagar_api.model.entity.Fornecedor;
import projetos.contas_pagar_api.model.repository.FornecedorRepository;
import projetos.contas_pagar_api.util.EmailValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FornecedorService implements IFornecedorService {
    private FornecedorRepository fornecedorRepository;

    @Autowired
    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }


    public List<Fornecedor> findAll() {
        return this.fornecedorRepository.findAll();
    }

    public List<FornecedorInfoDto> findInfoAllFornecedor() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        return fornecedores.stream()
                .map(FornecedorInfoDto::toDto)
                .collect(Collectors.toList());
    }

    public FornecedorDto findById(Long id) {
        Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findById(id);
        if (fornecedorOptional.isEmpty()) {
            throw new NotFoundException(String.format("Fornecedor com id %d não encontrado", id));
        }
        Fornecedor fornecedor = fornecedorOptional.get();
        FornecedorDto fornecedorDto = FornecedorDto.toDto(fornecedor);
        return fornecedorDto;
    }

    public FornecedorRegistroDto create(FornecedorRegistroDto fornecedorToCreateDto) {
        Fornecedor fornecedorToSave = FornecedorDto.toModel(fornecedorToCreateDto);

        validateCpfCnpjRegister(fornecedorToSave);

        boolean isEmail = EmailValidator.isValidEmail(fornecedorToSave.getEmail());
        if (!isEmail) {
            throw new InvalidEmailFormatException("Invalid email format");
        }

        if (fornecedorToSave.getCpf() != null && !fornecedorToSave.getCpf().isEmpty()) {
            Optional<Fornecedor> verificaCpfOptional = Optional.ofNullable(fornecedorRepository.findByCpf(fornecedorToSave.getCpf()));
            if (verificaCpfOptional.isPresent()) {
                throw new DuplicateEntryException("Cpf já está cadastrado");
            }
        }

        if (fornecedorToSave.getCnpj() != null && !fornecedorToSave.getCnpj().isEmpty()) {
            Optional<Fornecedor> verificaCnpjOptional = Optional.ofNullable(fornecedorRepository.findByCnpj(fornecedorToSave.getCnpj()));
            if (verificaCnpjOptional.isPresent()) {
                throw new DuplicateEntryException("Cnpj já está cadastrado");
            }
        }

        Optional<Fornecedor> verificaEmailOptional = Optional.ofNullable(fornecedorRepository.findByEmail(fornecedorToSave.getEmail()));
        if (verificaEmailOptional.isPresent()) {
            throw new DuplicateEntryException("Email já está cadastrado");
        }

        fornecedorRepository.save(fornecedorToSave);
        FornecedorRegistroDto fornecedorRegistrado = FornecedorRegistroDto.toDto(fornecedorToSave);
        return fornecedorRegistrado;
    }

    public FornecedorRegistroDto update(Long id, FornecedorRegistroDto fornecedorToUpdateDto) {
        boolean isEmail = EmailValidator.isValidEmail(fornecedorToUpdateDto.email());
        if (!isEmail) {
            throw new InvalidEmailFormatException("Invalid email format");
        }

        Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findById(id);

        if (fornecedorOptional.isEmpty()) {
            throw new NotFoundException(String.format("Fornecedor com id %d não encontrado", id));
        }

        Fornecedor fornecedorToUpdate = fornecedorOptional.get();

        validateCpfCnpjRegister(fornecedorToUpdate);
        fornecedorToUpdate.setNome(fornecedorToUpdateDto.nome());
        fornecedorToUpdate.setEmail(fornecedorToUpdateDto.email());
        fornecedorToUpdate.setCpf(fornecedorToUpdateDto.cpf());
        fornecedorToUpdate.setCnpj(fornecedorToUpdateDto.cnpj());

        fornecedorRepository.save(fornecedorToUpdate);
        FornecedorRegistroDto fornecedorUpdated = FornecedorRegistroDto.toDto((fornecedorToUpdate));
        return fornecedorUpdated;
    }

    public void delete(Long id) {
        Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findById(id);
        if (fornecedorOptional.isEmpty()) {
            throw new NotFoundException(String.format("Fornecedor com id %d não encontrado", id));
        }
        fornecedorRepository.delete(fornecedorOptional.get());
    }

    private void validateCpfCnpjRegister(Fornecedor fornecedor) {

        boolean isCpfEmpty = fornecedor.getCpf() == null || fornecedor.getCpf().isEmpty();
        boolean isCnpjEmpty = fornecedor.getCnpj() == null || fornecedor.getCnpj().isEmpty();
        if (isCpfEmpty && isCnpjEmpty) {
            throw new InvalidDataException("Nenhum valor foi informado: Cpf ou Cnpj deve ser preenchido");
        }
        if (!isCpfEmpty && !isCnpjEmpty) {
            throw new InvalidDataException("Não deve existir ambos valores: Cpf e Cnpj");
        }
        if (!isCpfEmpty) {
            fornecedor.setCnpj(null);
        }
        if (!isCnpjEmpty) {
            fornecedor.setCpf(null);
        }
    }

}
