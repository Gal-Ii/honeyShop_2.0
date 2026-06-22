package app.web.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartRequest {

    @NotNull
    private UUID productId;

    @NotNull
    @Positive
    @Builder.Default
    private Integer quantity=1;
}
