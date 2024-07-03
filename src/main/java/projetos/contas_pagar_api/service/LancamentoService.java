package projetos.contas_pagar_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetos.contas_pagar_api.advice.exception.NotFoundException;
import projetos.contas_pagar_api.model.entity.Lancamento;
import projetos.contas_pagar_api.model.repository.LancamentoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService implements ILancamentoService {
    private LancamentoRepository lancamentoRepository;

    @Autowired
    public LancamentoService(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    @Override
    public List findAll() {
        return this.lancamentoRepository.findAll();
    }

    @Override
    public Lancamento findById(Long id) {
        Optional<Lancamento> lancamentoOptional = lancamentoRepository.findById(id);
        if (lancamentoOptional.isEmpty()) {
            throw new NotFoundException(String.format("Lançamento com id %d não encontrado", id));
        }
        return lancamentoOptional.get();
    }

    @Override
    public Lancamento create(Lancamento lancamentoToCreate) {
        lancamentoRepository.save(lancamentoToCreate);
        return lancamentoToCreate;
    }

    @Override
    public Lancamento update(Long id, Lancamento lancamentoToUpdate) {
        var lancamentoOptional = lancamentoRepository.findById(id);
        if (lancamentoOptional.isEmpty()) {
            throw new NotFoundException(String.format("Lançamento com id %d não encontrado", id));
        }
        lancamentoRepository.save(lancamentoToUpdate);

        return lancamentoToUpdate;
    }

    @Override
    public void delete(Long id) {
        var lancamentoOptional = lancamentoRepository.findById(id);
        if (lancamentoOptional.isEmpty()) {
            throw new NotFoundException(String.format("Lançamento com id %d não encontrado", id));
        }
        lancamentoRepository.delete(lancamentoOptional.get());
    }
}
