package br.com.keepmygaming.gameoperationsservice.services;

import br.com.keepmygaming.gameoperationsservice.dtos.CreateRatingRequestDTO;
import br.com.keepmygaming.gameoperationsservice.dtos.GameRatingResponseDTO;
import br.com.keepmygaming.gameoperationsservice.dtos.RatingResponseDTO;
import br.com.keepmygaming.gameoperationsservice.dtos.UpdateRatingRequestDTO;
import br.com.keepmygaming.gameoperationsservice.exceptions.RatingNotFoundException;
import br.com.keepmygaming.gameoperationsservice.models.Rating;
import br.com.keepmygaming.gameoperationsservice.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.keepmygaming.gameoperationsservice.exceptions.ErrorMessage.GAME_RATING_NOT_FOUND_MESSAGE;
import static br.com.keepmygaming.gameoperationsservice.exceptions.ErrorMessage.RATING_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final ModelMapper modelMapper;

    private final RatingRepository ratingRepository;

    public List<RatingResponseDTO> findAllRatings() {
        return ratingRepository.findAll().stream()
                .map(game -> modelMapper.map(game, RatingResponseDTO.class))
                .collect(Collectors.toList());
    }

    public RatingResponseDTO createRating(CreateRatingRequestDTO createRateRequest) {
        //FIXME: model mapper is setting the id field with the value from gameId field.
        Rating ratingToSave = modelMapper.map(createRateRequest, Rating.class)
                .toBuilder()
                .id(null)
                .build();
        Rating savedRate = ratingRepository.save(ratingToSave);
        return modelMapper.map(savedRate, RatingResponseDTO.class);
    }

    public RatingResponseDTO findRatingById(String id) {
        return ratingRepository.findById(id)
                .map(rate -> modelMapper.map(rate, RatingResponseDTO.class))
                .orElseThrow(() -> new RatingNotFoundException(RATING_NOT_FOUND_MESSAGE, id));
    }

    public RatingResponseDTO updateRating(String id, UpdateRatingRequestDTO updateRateRequest) {
        return ratingRepository.findById(id)
                .map(rate -> {
                    Rating rateUpdated = ratingRepository.save(rate.toBuilder().score(updateRateRequest.getScore()).build());
                    return modelMapper.map(rateUpdated, RatingResponseDTO.class);
                })
                .orElseThrow(() -> new RatingNotFoundException(RATING_NOT_FOUND_MESSAGE, id));
    }

    public void deleteRating(String id) {
        Rating rateToDelete = ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(RATING_NOT_FOUND_MESSAGE, id));
        ratingRepository.delete(rateToDelete);
    }

    public GameRatingResponseDTO retrieveGameRating(String gameId) {
        Double score = calculateAverageGameScore(gameId);
        return GameRatingResponseDTO.builder()
                .gameId(gameId)
                .score(score)
                .build();
    }

    private Double calculateAverageGameScore(String gameId) {
        List<Rating> ratings = ratingRepository.findByGameId(gameId);
        if (ratings.isEmpty()) {
            throw new RatingNotFoundException(GAME_RATING_NOT_FOUND_MESSAGE, gameId);
        }
        return new BigDecimal(ratings.stream().mapToInt(Rating::getScore).sum()).divide(new BigDecimal(ratings.size()), 2, RoundingMode.HALF_UP).doubleValue();
    }
}