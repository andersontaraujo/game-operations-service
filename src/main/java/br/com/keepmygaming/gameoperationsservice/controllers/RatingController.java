package br.com.keepmygaming.gameoperationsservice.controllers;

import br.com.keepmygaming.gameoperationsservice.dtos.CreateRatingRequestDTO;
import br.com.keepmygaming.gameoperationsservice.dtos.GameRatingResponseDTO;
import br.com.keepmygaming.gameoperationsservice.dtos.RatingResponseDTO;
import br.com.keepmygaming.gameoperationsservice.dtos.UpdateRatingRequestDTO;
import br.com.keepmygaming.gameoperationsservice.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ratings")
public class RatingController {

    private final RatingService rateService;

    @GetMapping
    public ResponseEntity<List<RatingResponseDTO>> findAllRatings() {
        List<RatingResponseDTO> games = rateService.findAllRatings();
        return ResponseEntity.ok(games);
    }

    @PostMapping
    public ResponseEntity<RatingResponseDTO> createRating(@Valid @RequestBody CreateRatingRequestDTO request) {
        RatingResponseDTO game = rateService.createRating(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(game.getId()).toUri();
        return ResponseEntity.created(location).body(game);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponseDTO> findRatingById(@PathVariable String id) {
        RatingResponseDTO game = rateService.findRatingById(id);
        return ResponseEntity.ok(game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponseDTO> updateRating(@PathVariable String id, @Valid @RequestBody UpdateRatingRequestDTO request) {
        RatingResponseDTO game = rateService.updateRating(id, request);
        return ResponseEntity.ok(game);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable String id) {
        rateService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<GameRatingResponseDTO> retrieveGameRating(@PathVariable String gameId) {
        GameRatingResponseDTO gameRating = rateService.retrieveGameRating(gameId);
        return ResponseEntity.ok(gameRating);
    }

}