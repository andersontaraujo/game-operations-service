package br.com.keepmygaming.gameoperationsservice.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder(toBuilder = true)
public class CreateRatingRequestDTO {
    @NotBlank(message = "The field 'gameId' must be fulfilled")
    private String gameId;
    @NotNull(message = "The field 'score' must be fulfilled")
    private Integer score;
}