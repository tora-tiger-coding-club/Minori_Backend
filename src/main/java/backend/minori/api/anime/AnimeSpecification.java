package backend.minori.api.anime;

import backend.minori.domain.Anime;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class AnimeSpecification {
    public static Specification<Anime> filterBy(String season, String title, String genre, String tag) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (season != null) {
                predicates.add(criteriaBuilder.equal(root.get("season"), season));
            }
            if (title != null) {
                predicates.add(criteriaBuilder.like(root.get("titleKr"), "%" + title + "%"));
            }
            if (genre != null) {
                predicates.add(criteriaBuilder.like(root.get("genre"), "%" + genre + "%"));
            }
            if (tag != null) {
                predicates.add(criteriaBuilder.like(root.get("tag"), "%" + tag + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
