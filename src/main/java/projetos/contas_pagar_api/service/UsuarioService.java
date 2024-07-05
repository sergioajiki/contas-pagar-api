package projetos.contas_pagar_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetos.contas_pagar_api.advice.exception.DuplicateEntryException;
import projetos.contas_pagar_api.advice.exception.NotFoundException;
import projetos.contas_pagar_api.dto.UsuarioDto;
import projetos.contas_pagar_api.dto.UsuarioRegistroDto;
import projetos.contas_pagar_api.model.entity.Usuario;
import projetos.contas_pagar_api.model.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService {
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDto> findAll() {
        List<Usuario> usuarioList = usuarioRepository.findAll();
        return usuarioList.stream()
                .map(UsuarioDto::toDto)
                .collect(Collectors.toList());
    }

    public UsuarioDto findById(Long id) {
        var usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            throw new NotFoundException(String.format("Usuario com id %d não encontrado", id));
        }
        Usuario usuario = usuarioOptional.get();
        UsuarioDto usuarioDto = UsuarioDto.toDto(usuario);
        return usuarioDto;
    }

    public UsuarioRegistroDto create(UsuarioRegistroDto usuarioToCreate) {
        Usuario usuarioToSave = UsuarioDto.toModel(usuarioToCreate);
        var verificaEmailOptional = Optional.ofNullable(usuarioRepository.findByEmail(usuarioToCreate.email())) ;
        if(verificaEmailOptional.isPresent()) {
            throw new DuplicateEntryException("Email já está cadastrado");
        }
        usuarioRepository.save(usuarioToSave);

        return UsuarioRegistroDto.toDto(usuarioToSave);
    }

    public UsuarioRegistroDto update(Long id, UsuarioRegistroDto usuarioToUpdateDto) {
        var usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isEmpty()) {
            throw new NotFoundException(String.format("Usuario com id %d não encontrado", id));
        }
        Usuario usuarioToUpdate = usuarioOptional.get();
        usuarioToUpdate.setNome(usuarioToUpdateDto.nome());
        usuarioToUpdate.setEmail(usuarioToUpdateDto.email());
        usuarioToUpdate.setPassword(usuarioToUpdateDto.password());
        usuarioRepository.save(usuarioToUpdate);
        return UsuarioRegistroDto.toDto(usuarioToUpdate);
    }

    public void delete(Long id) {
        var usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isEmpty()) {
            throw new NotFoundException(String.format("Usuário com id %d não encontrado", id));
        }
        usuarioRepository.delete(usuarioOptional.get());
    }
}
