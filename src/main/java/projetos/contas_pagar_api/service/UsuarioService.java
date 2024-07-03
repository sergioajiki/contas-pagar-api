package projetos.contas_pagar_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetos.contas_pagar_api.advice.exception.DuplicateEntryException;
import projetos.contas_pagar_api.advice.exception.NotFoundException;
import projetos.contas_pagar_api.model.entity.Usuario;
import projetos.contas_pagar_api.model.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> findAll() {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        var usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            throw new NotFoundException(String.format("Usuario com id %d não encontrado", id));
        }
        return usuarioOptional.get();
    }

    @Override
    public Usuario create(Usuario usuarioToCreate) {
        var verficaEmailOptional = Optional.ofNullable(usuarioRepository.findByEmail(usuarioToCreate.getEmail()));
        if(verficaEmailOptional.isPresent()) {
            throw new DuplicateEntryException("Email já está cadastrado");
        }
        usuarioRepository.save(usuarioToCreate);
        return usuarioToCreate;
    }

    @Override
    public Usuario update(Long id, Usuario usuarioToUpdate) {
        var usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isEmpty()) {
            throw new NotFoundException(String.format("Usuario com id %d não encontrado", id));
        }
        usuarioRepository.save(usuarioToUpdate);
        return usuarioToUpdate;
    }

    @Override
    public void delete(Long id) {
        var usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isEmpty()) {
            throw new NotFoundException(String.format("Usuário com id %d não encontrado", id));
        }
        usuarioRepository.delete(usuarioOptional.get());
    }
}
