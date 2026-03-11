package example.persistence;

import example.domain.Search;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SearchRepository extends CrudRepository<Search, UUID> {
}
