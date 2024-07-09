package projetos.contas_pagar_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetos.contas_pagar_api.dto.UsuarioDto;
import projetos.contas_pagar_api.dto.UsuarioRegistroDto;
import projetos.contas_pagar_api.service.UsuarioService;

import java.util.List;

@RestController
@Tag(name = "Usuários")
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(description = "Lista de todos os usuários")
    public ResponseEntity<List<UsuarioDto>> getAllUsuario() {
        List<UsuarioDto> allUsuario = usuarioService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allUsuario);
    }

    @GetMapping("/{id}")
    @Operation(description = "Busca um fornecedor por id")
    public ResponseEntity<UsuarioDto> getUsuarioById(@PathVariable Long id) {
        UsuarioDto usuarioById = usuarioService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioById);
    }

    @PostMapping
    @Operation(description = "Cadastra um usuário")
    public ResponseEntity<UsuarioRegistroDto> create(@RequestBody @Valid UsuarioRegistroDto usuarioRegistroDto) {
        UsuarioRegistroDto usuarioRegistrado = usuarioService.create(usuarioRegistroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistrado);
    }

    @PutMapping("/{id}")
    @Operation(description = "Atualiza as informaçãoes de um usuário selecionado por Id")
    public ResponseEntity<UsuarioRegistroDto> update(@PathVariable Long id, @RequestBody @Valid UsuarioRegistroDto usuarioRegistroDto) {
        UsuarioRegistroDto usuarioUpdated = usuarioService.update(id, usuarioRegistroDto);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Exclui um usuário")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
