package projetos.contas_pagar_api.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import projetos.contas_pagar_api.dto.ErrorMesssageDto;

import java.util.List;

// Ela indica que apenas os campos que não são nulos devem ser incluídos na representação JSON do objeto.
// Isso ajuda a evitar a inclusão de campos vazios na resposta JSON, tornando a resposta mais limpa e legível.
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Problem(
        int status,
        String message,
        String detail,
        List<ErrorMesssageDto> erros
) {
}
