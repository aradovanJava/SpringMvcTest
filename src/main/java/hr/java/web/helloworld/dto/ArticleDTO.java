package hr.java.web.helloworld.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleDTO {
    @NotNull(message = "Article name should not be empty!")
    @Size(min=3, max=50, message = "The length of the article name must be between 3 and 50 characters!")
    private String articleName;
    private String articleDescription;

    @NotNull(message = "The article price must not be empty!")
    @DecimalMin(value = "0", message = "The minimum value for the article price is 0!")
    private BigDecimal articlePrice;

    @NotNull(message = "The category name must not be empty!")
    private String categoryName;
}
