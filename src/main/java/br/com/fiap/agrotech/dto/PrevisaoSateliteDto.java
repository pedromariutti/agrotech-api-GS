package br.com.fiap.agrotech.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO (Record) para recepção de dados orbitais da NASA/ESA")
public record PrevisaoSateliteDto(

        @Schema(description = "Região de análise do satélite", example = "Setor_A_Principal")
        @NotBlank(message = "A região de monitoramento orbital é obrigatória.")
        String regiao,

        @Schema(description = "Indicador booleano de chuva para as próximas horas", example = "true")
        @NotNull(message = "O status de chuva iminente deve ser informado.")
        Boolean chuvaIminente
) {}