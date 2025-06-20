package com.mesurpreenda.api.data.testes;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;
import com.mesurpreenda.api.data.entity.User;
import com.mesurpreenda.api.data.entity.WatchList;
import com.mesurpreenda.api.data.repository.MovieRepository;
import com.mesurpreenda.api.data.repository.SeriesRepository;
import com.mesurpreenda.api.data.repository.UserRepository;
import com.mesurpreenda.api.data.repository.WatchListRepository;
import com.mesurpreenda.api.data.service.WatchListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchListServiceTest {

    @Mock
    private WatchListRepository watchListRepo;

    @Mock
    private MovieRepository movieRepo;

    @Mock
    private SeriesRepository seriesRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private WatchListService service;

    private User existingUser;
    private WatchList existingWatchList;
    private Movie sampleMovie;
    private Series sampleSeries;

    @BeforeEach
    void setUp() {
        existingUser = new User();
        existingUser.setId("user-123");
        existingUser.setName("Alice");
        existingUser.setEmail("alice@example.com");

        existingWatchList = new WatchList();
        existingWatchList.setId("wl-abc");
        existingWatchList.setTitle("MinhaLista");

        sampleMovie = new Movie();
        sampleMovie.setId(Long.valueOf("550"));
        sampleMovie.setTitle("Inception");
        sampleMovie.setGenre("Sci-Fi");
        sampleMovie.setYear("2010");

        Series series = new Series();
        series.setId(1L);
        series.setTitle("Test Series");
        series.setGenre(Arrays.asList(1, 2, 3));
        series.setSeasons(new ArrayList<>());
        sampleSeries = series;
    }

    @Nested
    @DisplayName("Criar e buscar WatchList")
    class CreateAndFind {

        @Test
        @DisplayName("Deve criar WatchList com colaborador inicial")
        void createWatchList_success() {
            // 1) Mock para usuário existir
            when(userRepo.findById("user-123")).thenReturn(Optional.of(existingUser));

            // 2) Mock para save devolver a própria entidade
            when(watchListRepo.save(any(WatchList.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            // 3) Execução
            WatchList result = service.createWatchList("FilmesTop", "user-123");

            // 4) Verificações
            assertThat(result).isNotNull();
            assertThat(result.getTitle()).isEqualTo("FilmesTop");
            assertThat(result.getCollaborators()).containsExactly(existingUser);

            verify(watchListRepo, times(1)).save(any(WatchList.class));
        }

        @Test
        @DisplayName("Deve lançar 404 se usuário não existir ao criar WatchList")
        void createWatchList_userNotFound() {
            when(userRepo.findById("missing")).thenReturn(Optional.empty());
            // Não precisamos stubar save, já que nem chegaremos lá

            assertThatThrownBy(() -> service.createWatchList("Qualquer", "missing"))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("User not found");

            verify(watchListRepo, never()).save(any());
        }

        @Test
        @DisplayName("Deve retornar lista de todas as WatchLists")
        void getAllWatchLists_success() {
            List<WatchList> fakeList = List.of(existingWatchList);
            when(watchListRepo.findAll()).thenReturn(fakeList);

            List<WatchList> result = service.getAllWatchLists();
            assertThat(result).hasSize(1).containsExactly(existingWatchList);

            verify(watchListRepo, times(1)).findAll();
        }

        @Test
        @DisplayName("Deve retornar WatchList por ID")
        void getWatchListById_found() {
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));

            Optional<WatchList> maybe = service.getWatchListById("wl-abc");
            assertThat(maybe).isPresent().get().isEqualTo(existingWatchList);

            verify(watchListRepo, times(1)).findById("wl-abc");
        }

        @Test
        @DisplayName("Deve retornar vazio se WatchList não existir")
        void getWatchListById_notFound() {
            when(watchListRepo.findById("nope")).thenReturn(Optional.empty());

            Optional<WatchList> maybe = service.getWatchListById("nope");
            assertThat(maybe).isEmpty();

            verify(watchListRepo, times(1)).findById("nope");
        }

        @Test
        @DisplayName("Deve atualizar título da WatchList")
        void updateWatchListTitle_success() {
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));

            // Stubar save para devolver a própria entidade alterada
            when(watchListRepo.save(any(WatchList.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Optional<WatchList> maybe = service.updateWatchListTitle("wl-abc", "NovoTítulo");
            assertThat(maybe).isPresent();
            assertThat(maybe.get().getTitle()).isEqualTo("NovoTítulo");

            ArgumentCaptor<WatchList> captor = ArgumentCaptor.forClass(WatchList.class);
            verify(watchListRepo).save(captor.capture());
            assertThat(captor.getValue().getTitle()).isEqualTo("NovoTítulo");
        }

        @Test
        @DisplayName("Não atualiza título se WatchList não existir")
        void updateWatchListTitle_notFound() {
            when(watchListRepo.findById("nope")).thenReturn(Optional.empty());

            Optional<WatchList> maybe = service.updateWatchListTitle("nope", "Qualquer");
            assertThat(maybe).isEmpty();

            verify(watchListRepo, never()).save(any());
        }

        @Test
        @DisplayName("Deve excluir WatchList existente")
        void deleteWatchList_success() {
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));

            boolean result = service.deleteWatchList("wl-abc");
            assertThat(result).isTrue();

            verify(watchListRepo, times(1)).delete(existingWatchList);
        }

        @Test
        @DisplayName("Excluir retorna false se não existir")
        void deleteWatchList_notFound() {
            when(watchListRepo.findById("nope")).thenReturn(Optional.empty());

            boolean result = service.deleteWatchList("nope");
            assertThat(result).isFalse();

            verify(watchListRepo, never()).delete(any());
        }
    }


    @Nested
    @DisplayName("Gerenciar colaboradores")
    class Collaborators {

        @Test
        @DisplayName("Deve adicionar colaborador à WatchList")
        void addCollaborator_success() {
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));
            when(userRepo.findById("user-456"))
                    .thenReturn(Optional.of(new User())); // usuário fictício

            service.addCollaborator("wl-abc", "user-456");

            // Ao final, a lista de colaboradores deve conter o novo usuário
            assertThat(existingWatchList.getCollaborators()).hasSize(1);

            verify(watchListRepo).save(existingWatchList);
        }

        @Test
        @DisplayName("Falha ao adicionar colaborador se WatchList não existir")
        void addCollaborator_watchListNotFound() {
            when(watchListRepo.findById("none")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.addCollaborator("none", "user-456"))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("WatchList not found");

            verify(watchListRepo, never()).save(any());
        }

        @Test
        @DisplayName("Falha ao adicionar colaborador se usuário não existir")
        void addCollaborator_userNotFound() {
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));
            when(userRepo.findById("missing")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.addCollaborator("wl-abc", "missing"))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("User not found");

            verify(watchListRepo, never()).save(any());
        }

        @Test
        @DisplayName("Deve remover colaborador existente")
        void removeCollaborator_success() {
            // Preenche colaborador na watchlist
            User colaborador = new User();
            colaborador.setId("user-789");
            existingWatchList.getCollaborators().add(colaborador);

            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));

            service.removeCollaborator("wl-abc", "user-789");

            assertThat(existingWatchList.getCollaborators()).isEmpty();
            verify(watchListRepo).save(existingWatchList);
        }

        @Test
        @DisplayName("Falha ao remover colaborador se WatchList não existir")
        void removeCollaborator_watchListNotFound() {
            when(watchListRepo.findById("none")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.removeCollaborator("none", "u1"))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("WatchList not found");

            verify(watchListRepo, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Gerenciar filmes e séries na WatchList")
    class ManageContent {

        @Test
        @DisplayName("Deve adicionar um filme existente ou novo")
        void addMovieToWatchList_success_existingMovie() {
            existingWatchList.getFavoriteMovies().clear();
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));
            when(movieRepo.findById("550")).thenReturn(Optional.of(sampleMovie));

            service.addMovieToWatchList("wl-abc", sampleMovie);

            assertThat(existingWatchList.getFavoriteMovies()).contains(sampleMovie);
            verify(watchListRepo).save(existingWatchList);
            verify(movieRepo, never()).save(any()); // não salva porque já existia
        }

        @Test
        @DisplayName("Deve adicionar e salvar novo filme se não existir")
        void addMovieToWatchList_success_newMovie() {
            existingWatchList.getFavoriteMovies().clear();
            Movie newMovie = new Movie();
            newMovie.setId(Long.valueOf("999"));
            newMovie.setTitle("Novo Filme");

            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));
            when(movieRepo.findById("999")).thenReturn(Optional.empty());
            when(movieRepo.save(newMovie)).thenReturn(newMovie);

            service.addMovieToWatchList("wl-abc", newMovie);

            assertThat(existingWatchList.getFavoriteMovies()).contains(newMovie);
            verify(movieRepo).save(newMovie);
            verify(watchListRepo).save(existingWatchList);
        }

        @Test
        @DisplayName("Falha ao adicionar filme se WatchList não existir")
        void addMovieToWatchList_watchListNotFound() {
            when(watchListRepo.findById("none")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.addMovieToWatchList("none", sampleMovie))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("WatchList not found");

            verify(movieRepo, never()).save(any());
            verify(watchListRepo, never()).save(any());
        }

        @Test
        @DisplayName("Deve remover filme existente na watchlist")
        void removeMovieFromWatchList_success() {
            existingWatchList.getFavoriteMovies().add(sampleMovie);
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));

            service.removeMovieFromWatchList("wl-abc", Long.valueOf(sampleMovie.getId()));

            assertThat(existingWatchList.getFavoriteMovies()).doesNotContain(sampleMovie);
            verify(watchListRepo).save(existingWatchList);
        }

        @Test
        @DisplayName("Falha ao remover filme se não existir watchlist")
        void removeMovieFromWatchList_watchListNotFound() {
            when(watchListRepo.findById("none")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.removeMovieFromWatchList("none", 550L))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("WatchList not found");

            verify(watchListRepo, never()).save(any());
        }

        @Test
        @DisplayName("Deve adicionar série existente ou nova")
        void addSeriesToWatchList_success_existingSeries() {
            existingWatchList.getFavoriteSeries().clear();
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));
            when(seriesRepo.findById("1")).thenReturn(Optional.of(sampleSeries));

            service.addSeriesToWatchList("wl-abc", sampleSeries);

            assertThat(existingWatchList.getFavoriteSeries()).contains(sampleSeries);
            verify(watchListRepo).save(existingWatchList);
            verify(seriesRepo, never()).save(any());
        }

        @Test
        @DisplayName("Deve adicionar e salvar nova série se não existir")
        void addSeriesToWatchList_success_newSeries() {
            existingWatchList.getFavoriteSeries().clear();
            Series newSeries = new Series();
            newSeries.setId(Long.valueOf("888"));
            newSeries.setTitle("Nova Série");

            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));
            when(seriesRepo.findById("888")).thenReturn(Optional.empty());
            when(seriesRepo.save(newSeries)).thenReturn(newSeries);

            service.addSeriesToWatchList("wl-abc", newSeries);

            assertThat(existingWatchList.getFavoriteSeries()).contains(newSeries);
            verify(seriesRepo).save(newSeries);
            verify(watchListRepo).save(existingWatchList);
        }

        @Test
        @DisplayName("Falha ao adicionar série se WatchList não existir")
        void addSeriesToWatchList_watchListNotFound() {
            when(watchListRepo.findById("none")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.addSeriesToWatchList("none", sampleSeries))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("WatchList not found");

            verify(seriesRepo, never()).save(any());
            verify(watchListRepo, never()).save(any());
        }

        @Test
        @DisplayName("Deve remover série existente na watchlist")
        void removeSeriesFromWatchList_success() {
            existingWatchList.getFavoriteSeries().add(sampleSeries);
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));

            service.removeSeriesFromWatchList("wl-abc", sampleSeries.getId());

            assertThat(existingWatchList.getFavoriteSeries()).doesNotContain(sampleSeries);
            verify(watchListRepo).save(existingWatchList);
        }

        @Test
        @DisplayName("Falha ao remover série se não existir watchlist")
        void removeSeriesFromWatchList_watchListNotFound() {
            when(watchListRepo.findById("none")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.removeSeriesFromWatchList("none", 100L))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("WatchList not found");

            verify(watchListRepo, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Sortear conteúdo da WatchList")
    class RandomFromWatchList {

        @Test
        @DisplayName("Deve retornar conteúdo aleatório de watchlist mista")
        void getRandomFromWatchList_success() {
            // Coloca um filme e uma série na lista
            existingWatchList.getFavoriteMovies().add(sampleMovie);
            existingWatchList.getFavoriteSeries().add(sampleSeries);

            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));

            Object randomObj = service.getRandomFromWatchList("wl-abc");
            assertThat(randomObj).isIn(sampleMovie, sampleSeries);
        }

        @Test
        @DisplayName("Falha se watchlist não existir ao sortear")
        void getRandomFromWatchList_watchListNotFound() {
            when(watchListRepo.findById("none")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.getRandomFromWatchList("none"))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("WatchList not found");
        }

        @Test
        @DisplayName("Falha se não houver conteúdo na watchlist ao sortear")
        void getRandomFromWatchList_empty() {
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList)); // sem itens

            assertThatThrownBy(() -> service.getRandomFromWatchList("wl-abc"))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("No content in watchlist");
        }

        @Test
        @DisplayName("Deve retornar filme aleatório quando só existir filmes")
        void getRandomMovieFromWatchList_success() {
            existingWatchList.getFavoriteMovies().add(sampleMovie);
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));

            Movie randomMovie = service.getRandomMovieFromWatchList("wl-abc");
            assertThat(randomMovie).isEqualTo(sampleMovie);
        }

        @Test
        @DisplayName("Falha se não existir filme ao sortear filme")
        void getRandomMovieFromWatchList_noMovies() {
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));

            assertThatThrownBy(() -> service.getRandomMovieFromWatchList("wl-abc"))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("No movies in watchlist");
        }

        @Test
        @DisplayName("Deve retornar série aleatória quando só existir série")
        void getRandomSeriesFromWatchList_success() {
            existingWatchList.getFavoriteSeries().add(sampleSeries);
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));

            Series randomSeries = service.getRandomSeriesFromWatchList("wl-abc");
            assertThat(randomSeries).isEqualTo(sampleSeries);
        }

        @Test
        @DisplayName("Falha se não existir série ao sortear série")
        void getRandomSeriesFromWatchList_noSeries() {
            when(watchListRepo.findById("wl-abc")).thenReturn(Optional.of(existingWatchList));

            assertThatThrownBy(() -> service.getRandomSeriesFromWatchList("wl-abc"))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("No series in watchlist");
        }
    }
}
