package br.com.keepmygaming.gameoperationsservice.repositories;

import br.com.keepmygaming.gameoperationsservice.models.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Rating> findByGameId(String gameId);
}