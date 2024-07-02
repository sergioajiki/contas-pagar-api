package projetos.contas_pagar_api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetos.contas_pagar_api.model.entity.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    Fornecedor findByCnpj(String cnpj);
    Fornecedor findByCpf(String cpf);
    Fornecedor findByEmail(String email);
}
