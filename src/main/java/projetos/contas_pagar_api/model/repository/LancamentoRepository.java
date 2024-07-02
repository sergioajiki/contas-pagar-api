package projetos.contas_pagar_api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetos.contas_pagar_api.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
