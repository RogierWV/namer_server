package io.snynx.namegengrpcserver.repositories;

import io.snynx.namegengrpcserver.models.Favorite;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface FavoriteRepository extends ReactiveCrudRepository<Favorite, Long> {
    Flux<Favorite> getByUid(long Uid);
}
