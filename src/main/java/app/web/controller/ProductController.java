package app.web.controller;

import app.model.entity.product.Product;
import app.model.entity.user.User;
import app.service.ProductService;
import app.service.UserService;
import app.web.dto.product.ProductCreateRequest;
import app.web.dto.product.ProductUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import app.exception.ProductAlreadyExistsException;
import java.util.UUID;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public ProductController(ProductService productService, UserService userService) {
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping("/products")
    public String createProduct(@Valid @ModelAttribute("productCreateRequest") ProductCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-create";
        }

        try {
            User currentUser = userService.getCurrentUser();
            productService.create(request, currentUser);
        } catch (ProductAlreadyExistsException e) {
            bindingResult.rejectValue(
                    "name",
                    "product.name.exists",
                    "Продукт с това име вече съществува."
            );

            return "product-create";
        }

        return "redirect:/admin";
    }

    @PostMapping("/products/{id}/update")
    public String updateProduct(@PathVariable UUID id, @Valid @ModelAttribute("productUpdateRequest") ProductUpdateRequest updateRequest, BindingResult bindingResult, Model model){
         if (bindingResult.hasErrors()) {
            model.addAttribute("product", productService.getById(id));
            return "product-update";
        }

        try {
            User currentUser = userService.getCurrentUser();
            productService.update(id, updateRequest, currentUser);
        } catch (ProductAlreadyExistsException e) {
            bindingResult.rejectValue(
                    "name",
                    "product.name.exists",
                    "Продукт с това име вече съществува."
            );

            model.addAttribute("product", productService.getById(id));
            return "product-update";
        }

        return "redirect:/admin";
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable UUID id){
        User currentUser = userService.getCurrentUser();
        productService.delete(id, currentUser);
        return "redirect:/admin";
    }

    @GetMapping("/product-create")
    public String productCreatePage(Model model){
        model.addAttribute("productCreateRequest", new ProductCreateRequest());
        return "product-create";
    }


    @GetMapping("/products/{id}/update")
    public String productUpdatePage(@PathVariable UUID id, Model model){
        Product product = productService.getById(id);

        ProductUpdateRequest productUpdateRequest = ProductUpdateRequest.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .items(product.getItems())
                .isActive(product.getIsActive())
                .build();

        model.addAttribute("product", product);
        model.addAttribute("productUpdateRequest", productUpdateRequest);
        return "product-update";
    }

}
