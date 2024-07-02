package projetos.contas_pagar_api.service;

import org.springframework.stereotype.Service;
import projetos.contas_pagar_api.advice.exception.DuplicateEntryException;
import projetos.contas_pagar_api.advice.exception.NotFoundException;
import projetos.contas_pagar_api.model.entity.Fornecedor;
import projetos.contas_pagar_api.model.repository.FornecedorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService implements IFornecedorService {
    private FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public List<Fornecedor> findAll() {
        return this.fornecedorRepository.findAll();
    }

    @Override
    public Fornecedor findById(Long id) {
        Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findById(id);
        if (fornecedorOptional.isEmpty()) {
            throw new NotFoundException(String.format("Fornecedor com id %d não encontrado", id));
        }
        return fornecedorOptional.get();
    }
    public Fornecedor create(Fornecedor fornecedorToCreate) {
        Optional<Fornecedor> verificaCpfOptional = Optional.ofNullable(fornecedorRepository.findByCpf(fornecedorToCreate.getCpf()));
        if (verificaCpfOptional.isPresent()) {
            throw new DuplicateEntryException("Cpf já está cadastrado");
        }
        Optional<Fornecedor> verificaCnpjOptional = Optional.ofNullable(fornecedorRepository.findByCnpj(fornecedorToCreate.getCnpj()));
        if (verificaCnpjOptional.isPresent()) {
            throw new DuplicateEntryException("Cnpj já está cadastrado");
        }
        Optional<Fornecedor> verificaEmailOptional = Optional.ofNullable(fornecedorRepository.findByEmail(fornecedorToCreate.getEmail()));
        if (verificaEmailOptional.isPresent()) {
            throw new DuplicateEntryException("Email já está cadastrado");
        }

        fornecedorRepository.save(fornecedorToCreate);
        return fornecedorToCreate;
    }

    @Override
    public Fornecedor update(Long id, Fornecedor fornecedorToUpdate) {
        Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findById(id);
        if(fornecedorOptional.isEmpty()) {
            throw new NotFoundException(String.format("Fornecedor com id %d não encontrado", id));
        }

        fornecedorRepository.save(fornecedorToUpdate);
        return fornecedorToUpdate;
    }

    @Override
    public void delete(Long id) {
        Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findById(id);
        if(fornecedorOptional.isEmpty()) {
            throw new NotFoundException(String.format("Fornecedor com id %d não encontrado", id));
        }
        fornecedorRepository.delete(fornecedorOptional.get());
    }
}
