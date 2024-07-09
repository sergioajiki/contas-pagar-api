package projetos.contas_pagar_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetos.contas_pagar_api.dto.LancamentoDto;
import projetos.contas_pagar_api.service.LancamentoService;

import java.util.List;

@RestController
@Tag(name = "Lançamentos")
@RequestMapping("/lancamento")
public class LancamentoController {
    private final LancamentoService lancamentoService;

    @Autowired
    public LancamentoController(LancamentoService lancamentoService) {
        this.lancamentoService = lancamentoService;
    }

    @GetMapping
    @Operation(description = "Lista de lançamentos")
    public ResponseEntity<List<LancamentoDto>> getAllLancamentos() {
        List<LancamentoDto> allLancamentos = lancamentoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allLancamentos);
    }

    @GetMapping("/{id}")
    @Operation(description = "Busca um lançamento por id")
    public ResponseEntity<LancamentoDto> getLancamentoById(@PathVariable Long id) {
        LancamentoDto lancamentoById = lancamentoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(lancamentoById);
    }

    @PostMapping
    @Operation(description = "Cadastra um lançamento")
    public ResponseEntity<LancamentoDto> registerLancamento(@RequestBody @Valid LancamentoDto lancamentoRegistroDto) {
        LancamentoDto lancamentoRegistrado = lancamentoService.create(lancamentoRegistroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoRegistrado);
    }

    @PutMapping("/{id}")
    @Operation(description = "Atualiza as informações do lançamento")
    public ResponseEntity<LancamentoDto> updateLancamento(@PathVariable Long id, @RequestBody @Valid LancamentoDto lancamentoDto) {
        LancamentoDto lancamentoUpdated = lancamentoService.update(id, lancamentoDto);
        return ResponseEntity.status(HttpStatus.OK).body(lancamentoUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Exclui um lançamento por id")
    public ResponseEntity<Void> deleteLancamento(@PathVariable Long id) {
        lancamentoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
