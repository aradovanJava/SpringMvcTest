package hr.java.web.helloworld.service;

import hr.java.web.helloworld.domain.Article;
import hr.java.web.helloworld.domain.SearchArticle;
import hr.java.web.helloworld.dto.ArticleDTO;
import hr.java.web.helloworld.dto.SearchArticleDTO;
import hr.java.web.helloworld.repository.ArticleRepository;
import hr.java.web.helloworld.repository.SpringDataArticleRepository;
import hr.java.web.helloworld.repository.SpringDataCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;
    private SpringDataCategoryRepository categoryRepository;

    @Override
    public List<ArticleDTO> getAllArticles() {
        return articleRepository.getAllArticles().stream()
                .map(this::convertArticleToArticleDTO)
                .toList();
    }

    @Override
    public List<ArticleDTO> getArticlesByName(String articleName) {
        return articleRepository.getArticlesByName(articleName).stream()
                .map(this::convertArticleToArticleDTO)
                .toList();
    }

    @Override
    public List<ArticleDTO> getArticlesByNameLike(String articleName) {
        return articleRepository.getArticlesByName(articleName).stream()
                .filter(article -> article.getName().toLowerCase().contains(articleName.toLowerCase()))
                .map(this::convertArticleToArticleDTO)
                .toList();
    }

    @Override
    public void saveNewArticle(ArticleDTO article) {
        articleRepository.saveNewArticle((convertArticleDtoToArticle(article)));
    }

    @Override
    public List<ArticleDTO> filterByParameters(SearchArticleDTO searchArticleDTO) {Article article = new Article();
        article.setName(searchArticleDTO.getArticleName());

        //Example<Article> articleExample = Example.of(article);

        return articleRepository.filterByParameters(convertSearchArticleDtoToSearchArticle(searchArticleDTO))
                .stream().map(this::convertArticleToArticleDTO)
                .toList();
    }

    private ArticleDTO convertArticleToArticleDTO(Article article) {
        return new ArticleDTO(article.getName(),
                article.getDescription(), article.getPrice(),
                article.getCategory().getName());
    }

    private Article convertArticleDtoToArticle(ArticleDTO articleDTO) {
        Integer latestId =
                articleRepository.getAllArticles().stream()
                .max((a1, a2) -> a1.getId().compareTo(a2.getId()))
                .get().getId();

        return new Article(latestId + 1, articleDTO.getArticleName(),
                articleDTO.getArticleDescription(), articleDTO.getArticlePrice(),
                categoryRepository.findByName(articleDTO.getCategoryName()));
    }

    private SearchArticle convertSearchArticleDtoToSearchArticle(SearchArticleDTO searchArticleDTO) {
        return new SearchArticle(
                searchArticleDTO.getArticleName(),
                searchArticleDTO.getArticleDescription(),
                searchArticleDTO.getLowerPrice(),
                searchArticleDTO.getUpperPrice(),
                categoryRepository.findByName(searchArticleDTO.getCategoryName()));
    }
}
