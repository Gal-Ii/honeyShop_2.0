package app.web.dto.product;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {

    @NotBlank(message = "Product name is required.")
    @Size(min=2, max=100, message = "Product name mast be between 2 and 100 symbols.")
    private String name;

    @Size(max=1000, message = "Description must be up to 1000 symbols.")
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @Size(max = 500, message = "Image path must be up to 500 symbols.")
    private String imageUrl;

    @NotNull
    @PositiveOrZero
    private Integer items;

    private Boolean isActive;
}
