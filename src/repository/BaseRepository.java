package repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<A, B> {

    Optional<B> findById(A id);

    List<B> findAll();

    B save(B entity);
}
