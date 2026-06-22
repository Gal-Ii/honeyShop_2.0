package app.web.controller;

import app.model.entity.order.OrderStatus;
import app.service.OrderService;
import app.service.ProductService;
import app.service.UserService;
import app.web.dto.order.UpdateOrderStatusRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class AdminController {

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;

    public AdminController(ProductService productService, OrderService orderService, UserService userService) {
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model){
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("updateOrderStatusRequest", new UpdateOrderStatusRequest());
        return "admin";
    }

    @PostMapping("/orders/{id}/status")
    public String updateOrderStatus(@PathVariable UUID id,
                                    @Valid @ModelAttribute("updateOrderStatusRequest") UpdateOrderStatusRequest updateOrderStatusRequest,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("orderStatuses", OrderStatus.values());
            return "admin";
        }

        orderService.updateStatus(id, updateOrderStatusRequest);
        return "redirect:/admin";
    }

    @GetMapping("/admin-products")
    public String adminProductsPage(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "admin-products";
    }

}
