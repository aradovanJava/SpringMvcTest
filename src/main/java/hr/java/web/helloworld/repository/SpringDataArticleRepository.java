package hr.java.web.helloworld.repository;

import hr.java.web.helloworld.domain.Article;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SpringDataArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findByName(String name);

    List<Article> findByNameLike(String name);
    List<Article> findByNameAndPrice(String name, BigDecimal price);
}
