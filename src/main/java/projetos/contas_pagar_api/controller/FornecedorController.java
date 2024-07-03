package projetos.contas_pagar_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projetos.contas_pagar_api.dto.FornecedorInfoDto;
import projetos.contas_pagar_api.service.FornecedorService;

import java.util.Calendar;
import java.util.List;

@RestController
@Tag(name = "Fornecedores")
@RequestMapping("/")
public class FornecedorController {
    private final FornecedorService fornecedorService;
    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }
    @GetMapping
    @Operation(description = "Lista de todos os fornecedores")
    public ResponseEntity<List<FornecedorInfoDto>> getAllFornecedores(){
        List<FornecedorInfoDto> allFornecedores = fornecedorService.findInfoAllFornecedor();
        return ResponseEntity.status(HttpStatus.OK).body(allFornecedores);
    }
}

