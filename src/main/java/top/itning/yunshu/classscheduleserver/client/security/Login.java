package top.itning.yunshu.classscheduleserver.client.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.itning.yunshu.classscheduleserver.dao.UserDao;
import top.itning.yunshu.classscheduleserver.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Component
@Order(1)
@WebFilter(filterName = "SecurityFilter", urlPatterns = "/server")
public class Login implements Filter {
    private final UserDao userDao;

    @Autowired
    public Login(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        String servletPath = req.getServletPath();
        if ("/login".equals(servletPath) && "GET".equals(req.getMethod())) {
            chain.doFilter(request, response);
            return;
        }
        if ("/logout".equals(servletPath) && "GET".equals(req.getMethod())) {
            HttpSession session = req.getSession();
            session.setAttribute("login", null);
            resp.sendRedirect("/server");
            return;
        }
        if ("/login".equals(servletPath)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            Optional<User> userOptional = userDao.findById(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (user.getPassword().equals(password)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("login", user);
                    resp.sendRedirect("/server");
                } else {
                    req.getRequestDispatcher("login").forward(request, response);
                }
            } else {
                req.getRequestDispatcher("login").forward(request, response);
            }
            return;
        }
        if (servletPath.startsWith("/server")) {
            HttpSession session = req.getSession();
            if (session.getAttribute("login") != null) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect("login");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
