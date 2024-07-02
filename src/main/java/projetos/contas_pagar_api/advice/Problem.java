package projetos.contas_pagar_api.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import projetos.contas_pagar_api.dto.ErrorMesssageDto;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public record Problem(
        int status,
        String message,
        String detail,
        List<ErrorMesssageDto> erros
) {
}
