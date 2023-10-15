package me.lucaskaminski.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.lucaskaminski.todolist.user.IUserRepository;

@Component
public class FilterTasksAuth extends OncePerRequestFilter {
  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var path = request.getRequestURI();

    if (!path.startsWith("/tasks")) {
      filterChain.doFilter(request, response);
      return;
    }

    var authorization = request.getHeader("Authorization");
    var base64 = authorization.substring("Basic".length()).trim();
    var decoded = new String(Base64.getDecoder().decode(base64));
    var parts = decoded.split(":");

    var username = parts[0];
    var password = parts[1];

    var user = this.userRepository.findByUsername(username);

    if (user == null) {
      response.sendError(401);
      return;
    }

    var passwordVerification = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray());

    if (!passwordVerification.verified) {
      response.sendError(401);
      return;
    }

    request.setAttribute("idUser", user.getId());

    filterChain.doFilter(request, response);
  }

}
