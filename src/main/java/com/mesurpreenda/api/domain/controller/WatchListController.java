package com.mesurpreenda.api.domain.controller;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;
import com.mesurpreenda.api.data.entity.WatchList;
import com.mesurpreenda.api.data.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    // 1) Criar nova watchlist (com colaborador inicial)
    @PostMapping
    public ResponseEntity<WatchList> createWatchList(
            @RequestParam("title") String title,
            @RequestParam("creatorId") String creatorId) {

        WatchList created = watchListService.createWatchList(title, creatorId);
        URI location = URI.create("/api/watchlist/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    // 2) Listar todas as watchlists
    @GetMapping
    public List<WatchList> getAllWatchLists() {
        return watchListService.getAllWatchLists();
    }

    // 3) Buscar watchlist por ID
    @GetMapping("/{id}")
    public ResponseEntity<WatchList> getWatchListById(@PathVariable String id) {
        return watchListService.getWatchListById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4) Atualizar título da watchlist
    @PutMapping("/{id}")
    public ResponseEntity<WatchList> updateWatchListTitle(
            @PathVariable String id,
            @RequestParam("title") String newTitle) {

        return watchListService.updateWatchListTitle(id, newTitle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5) Deletar watchlist
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchList(@PathVariable String id) {
        boolean removed = watchListService.deleteWatchList(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // 6) Adicionar colaborador
    @PostMapping("/{id}/collaborator")
    public ResponseEntity<String> addCollaborator(
            @PathVariable String id,
            @RequestParam("userId") String userId) {

        watchListService.addCollaborator(id, userId);
        return ResponseEntity.ok("Collaborator added");
    }

    // 7) Remover colaborador
    @DeleteMapping("/{id}/collaborator")
    public ResponseEntity<String> removeCollaborator(
            @PathVariable String id,
            @RequestParam("userId") String userId) {

        watchListService.removeCollaborator(id, userId);
        return ResponseEntity.ok("Collaborator removed");
    }

    // 8) Adicionar filme à watchlist
    @PostMapping("/{id}/movie")
    public ResponseEntity<String> addMovie(
            @PathVariable String id,
            @RequestBody Movie movie) {

        watchListService.addMovieToWatchList(id, movie);
        return ResponseEntity.ok("Movie added to watchlist");
    }

    // 9) Remover filme da watchlist
    @DeleteMapping("/{id}/movie")
    public ResponseEntity<String> removeMovie(
            @PathVariable String id,
            @RequestParam("movieId") Long movieId) {

        watchListService.removeMovieFromWatchList(id, movieId);
        return ResponseEntity.ok("Movie removed from watchlist");
    }

    // 10) Adicionar série à watchlist
    @PostMapping("/{id}/series")
    public ResponseEntity<String> addSeries(
            @PathVariable String id,
            @RequestBody Series series) {

        watchListService.addSeriesToWatchList(id, series);
        return ResponseEntity.ok("Series added to watchlist");
    }

    // 11) Remover série da watchlist
    @DeleteMapping("/{id}/series")
    public ResponseEntity<String> removeSeries(
            @PathVariable String id,
            @RequestParam("seriesId") Long seriesId) {

        watchListService.removeSeriesFromWatchList(id, seriesId);
        return ResponseEntity.ok("Series removed from watchlist");
    }

    // 12) Sortear um conteúdo (filme ou série) da watchlist
    @GetMapping("/{id}/random")
    public ResponseEntity<Object> randomFromWatchList(@PathVariable String id) {
        Object random = watchListService.getRandomFromWatchList(id);
        return ResponseEntity.ok(random);
    }

    // 13) Sortear apenas um filme da watchlist
    @GetMapping("/{id}/random/movie")
    public ResponseEntity<Object> randomMovieFromWatchList(@PathVariable String id) {
        Movie randomMovie = watchListService.getRandomMovieFromWatchList(id);
        return ResponseEntity.ok(randomMovie);
    }

    // 14) Sortear apenas uma série da watchlist
    @GetMapping("/{id}/random/series")
    public ResponseEntity<Object> randomSeriesFromWatchList(@PathVariable String id) {
        Series randomSeries = watchListService.getRandomSeriesFromWatchList(id);
        return ResponseEntity.ok(randomSeries);
    }
}
