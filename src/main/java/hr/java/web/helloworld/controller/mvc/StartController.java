package hr.java.web.helloworld.controller.mvc;

import hr.java.web.helloworld.domain.Category;
import hr.java.web.helloworld.dto.ArticleDTO;
import hr.java.web.helloworld.dto.SearchArticleDTO;
import hr.java.web.helloworld.publisher.CustomSpringEventPublisher;
import hr.java.web.helloworld.service.ArticleService;
import hr.java.web.helloworld.service.CategoryService;
import hr.java.web.helloworld.service.ValidationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/start")
@AllArgsConstructor
@SessionAttributes({"categoryList", "searchArticleDTO"})
public class StartController {

    private ArticleService articleService;
    private CategoryService categoryService;
    private ValidationService validationService;

    @GetMapping
    public String start(Model model, HttpServletRequest request) {
        model.addAttribute("searchArticleDTO", new SearchArticleDTO());
        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("filteredArticles", articleService.getAllArticles());
        return "startPage";
    }

    @GetMapping("/saveNewArticle.html")
    public String openSaveNewArticleScreen(Model model) {
        model.addAttribute("articleDTO", new SearchArticleDTO());
        model.addAttribute("categoryList", categoryService.findAll());
        return "start";
    }

    @PostMapping("/saveNewArticle.html")
    public String saveNewArticle(@ModelAttribute @Valid ArticleDTO articleDTO,
                                 BindingResult bindingResult,
                                 Model model)
    {
        if(!validationService.checkDuplicateArticle(articleDTO)) {
            bindingResult.addError(new ObjectError("articleDTO",
                    "You provided a duplicate article!"));
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("articleDTO", articleDTO);
            return "start";
        }

        articleService.saveNewArticle(articleDTO);
        return "redirect:/start";
    }

    @PostMapping("/articleSearch.html")
    public String articleSearch(@ModelAttribute SearchArticleDTO searchArticleDTO, Model model) {
        model.addAttribute("filteredArticles",
                articleService.filterByParameters(searchArticleDTO));
        return "startPage";
    }

}
