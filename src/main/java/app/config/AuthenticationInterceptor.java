package app.config;

import app.model.entity.user.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);

        boolean isLoggedIn = session != null && session.getAttribute("user_id") != null;
        Object userRole = session != null ? session.getAttribute("user_role") : null;

        if (isPublicResource(uri)) {
            return true;
        }

        if (!isLoggedIn) {
            response.sendRedirect("/login");
            return false;
        }

        if (isAdminResource(uri) && userRole != UserRole.ADMIN) {
            response.sendRedirect("/index");
            return false;
        }

        return true;
    }

    private boolean isPublicResource(String uri) {
        return uri.equals("/")
                || uri.equals("/index")
                || uri.equals("/products")
                || uri.equals("/login")
                || uri.equals("/register")
                || uri.startsWith("/css/")
                || uri.startsWith("/images/")
                || uri.startsWith("/js/");
    }

    private boolean isAdminResource(String uri) {
        return uri.equals("/admin")
                || uri.equals("/admin-products")
                || uri.equals("/product-create")
                || uri.matches("/products/.+/update")
                || uri.matches("/products/.+/delete")
                || uri.matches("/orders/.+/status");
    }
}