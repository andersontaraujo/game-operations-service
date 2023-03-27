package br.com.keepmygaming.gameoperationsservice.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class GameRatingResponseDTO {
    private String gameId;
    private Double averageScore;
}