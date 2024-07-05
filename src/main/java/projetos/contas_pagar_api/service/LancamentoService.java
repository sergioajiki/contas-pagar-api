package projetos.contas_pagar_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetos.contas_pagar_api.advice.exception.NotFoundException;
import projetos.contas_pagar_api.dto.LancamentoDto;
import projetos.contas_pagar_api.model.entity.Fornecedor;
import projetos.contas_pagar_api.model.entity.Lancamento;
import projetos.contas_pagar_api.model.entity.Usuario;
import projetos.contas_pagar_api.model.repository.FornecedorRepository;
import projetos.contas_pagar_api.model.repository.LancamentoRepository;
import projetos.contas_pagar_api.model.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LancamentoService implements ILancamentoService {
    private FornecedorRepository fornecedorRepository;
    private LancamentoRepository lancamentoRepository;
    private UsuarioRepository usuarioRepository;


    @Autowired
    public LancamentoService(FornecedorRepository fornecedorRepository, LancamentoRepository lancamentoRepository, UsuarioRepository usuarioRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.lancamentoRepository = lancamentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<LancamentoDto> findAll() {
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        return lancamentoList.stream()
                .map(LancamentoDto::toDto)
                .collect(Collectors.toList());
    }

    public LancamentoDto findById(Long id) {
        Optional<Lancamento> lancamentoOptional = lancamentoRepository.findById(id);
        if (lancamentoOptional.isEmpty()) {
            throw new NotFoundException(String.format("Lançamento com id %d não encontrado", id));
        }
        Lancamento lancamento = lancamentoOptional.get();
        LancamentoDto lancamentoDto = LancamentoDto.toDto(lancamento);
        return lancamentoDto;
    }

    public LancamentoDto create(LancamentoDto lancamentoToCreateDto) {
        Fornecedor fornecedor = fornecedorRepository.findById(lancamentoToCreateDto.idFornecedor())
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado"));
        Usuario usuario = usuarioRepository.findById(lancamentoToCreateDto.idUsuario())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        Lancamento lancamentoToSave = LancamentoDto.toModel(lancamentoToCreateDto, fornecedor, usuario);
        lancamentoRepository.save(lancamentoToSave);
        LancamentoDto lancamentoDto = LancamentoDto.toDto(lancamentoToSave);

        return lancamentoDto;
    }

    public LancamentoDto update(Long id, LancamentoDto lancamentoToUpdateDto) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado"));
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        var lancamentoOptional = lancamentoRepository.findById(id);
        if (lancamentoOptional.isEmpty()) {
            throw new NotFoundException(String.format("Lançamento com id %d não encontrado", id));
        }

        Lancamento lancamentoToUpdate = LancamentoDto.toModel(lancamentoToUpdateDto, fornecedor,usuario);
        lancamentoToUpdate.setId(id);
        lancamentoRepository.save(lancamentoToUpdate);

        return LancamentoDto.toDto(lancamentoToUpdate);
    }

    public void delete(Long id) {
        var lancamentoOptional = lancamentoRepository.findById(id);
        if (lancamentoOptional.isEmpty()) {
            throw new NotFoundException(String.format("Lançamento com id %d não encontrado", id));
        }
        lancamentoRepository.delete(lancamentoOptional.get());
    }

}
