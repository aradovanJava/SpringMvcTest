package hr.java.web.helloworld.service;

import hr.java.web.helloworld.domain.Article;
import hr.java.web.helloworld.dto.ArticleDTO;
import hr.java.web.helloworld.repository.SpringDataArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ValidationService {

    private SpringDataArticleRepository repository;

    public Boolean checkDuplicateArticle(ArticleDTO articleDTO) {
        List<Article> articleList =
                repository.findByNameAndPrice(
                        articleDTO.getArticleName(), articleDTO.getArticlePrice());

        return articleList.isEmpty();
    }

}
