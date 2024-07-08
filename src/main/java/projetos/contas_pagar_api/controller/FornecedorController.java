package projetos.contas_pagar_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetos.contas_pagar_api.dto.FornecedorDto;
import projetos.contas_pagar_api.dto.FornecedorInfoDto;
import projetos.contas_pagar_api.dto.FornecedorRegistroDto;
import projetos.contas_pagar_api.service.FornecedorService;

import java.util.List;

@RestController
@Tag(name = "Fornecedores")
@RequestMapping("/fornecedor")
public class FornecedorController {
    private final FornecedorService fornecedorService;
    @Autowired
    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }
    @GetMapping
    @Operation(description = "Lista de todos os fornecedores")
    public ResponseEntity<List<FornecedorInfoDto>> getAllFornecedores(){
        List<FornecedorInfoDto> allFornecedores = fornecedorService.findInfoAllFornecedor();
        return ResponseEntity.status(HttpStatus.OK).body(allFornecedores);
    }

    @GetMapping("/{id}")
    @Operation(description = "Busca um fornecedor por id")
    public ResponseEntity<FornecedorDto> getFornecedorById(@RequestParam Long id) {
        FornecedorDto fornecedorById = fornecedorService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(fornecedorById);
    }

    @PostMapping
    @Operation(description = "Cadastrar um fornecedor")
    public ResponseEntity<FornecedorRegistroDto> registerFornecedor(@RequestBody @Valid FornecedorRegistroDto fornecedorRegistroDto) {
        FornecedorRegistroDto fornecedorRegistrado = fornecedorService.create(fornecedorRegistroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorRegistrado);
    }

    @PutMapping("/{id}")
    @Operation(description = "Atualiza as informações do fornecedor selecionado por id")
    public ResponseEntity<FornecedorRegistroDto> updateFornecedor(@RequestParam Long id, @Valid @RequestBody FornecedorRegistroDto fornecedorUpdateDto) {
        FornecedorRegistroDto fornecedorAtualizado = fornecedorService.update(id, fornecedorUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(fornecedorAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Exclui um fornecedor por id")
    public ResponseEntity<Void> deleteFornecedor(@RequestParam Long id) {
        fornecedorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

