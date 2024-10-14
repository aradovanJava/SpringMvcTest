package hr.java.web.helloworld.controller.rest;

import hr.java.web.helloworld.controller.mvc.ArticleController;
import hr.java.web.helloworld.dto.ArticleDTO;
import hr.java.web.helloworld.dto.SearchArticleDTO;
import hr.java.web.helloworld.service.ArticleService;
import hr.java.web.helloworld.service.ArticleServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/article")
@AllArgsConstructor
public class ArticleRestController {

    private ArticleService articleService;

    @GetMapping("/all")
    public List<ArticleDTO> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/name/{articleName}")
    public List<ArticleDTO> getArticlesByName(@PathVariable String articleName) {
        SearchArticleDTO dto = new SearchArticleDTO();
        dto.setArticleName(articleName);
        List<ArticleDTO> articleDTOList = articleService.filterByParameters(dto);
        return  articleDTOList;
    }

}
