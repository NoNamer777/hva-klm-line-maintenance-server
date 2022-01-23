package Klm1.KLMLineMaintenanceServer.repositories.security;

import Klm1.KLMLineMaintenanceServer.models.helper.AuthenticationException;
import Klm1.KLMLineMaintenanceServer.models.helper.AppconfigJ;
import Klm1.KLMLineMaintenanceServer.models.helper.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

  @Autowired
  AppconfigJ appconfigJ;

  // path prefixes that will be protected by the authentication filter
  private static final Set<String> SECURED_PATHS =
    Set.of("/userRequests","/equipment","/eType", "/locations","/request");

  @Override
  protected void doFilterInternal(HttpServletRequest req,
                                  HttpServletResponse res,
                                  FilterChain chain) throws ServletException, IOException {


    try {
      // get requested path
      String path = req.getServletPath();

      // OPTIONS requests and non-secured area should pass through without check
      if (HttpMethod.OPTIONS.matches(req.getMethod()) ||
        SECURED_PATHS.stream().noneMatch(path::startsWith)) {
        chain.doFilter(req, res);
        return;
      }


      // get the encoded token string from the authorization request header
      String encodedToken = req.getHeader(HttpHeaders.AUTHORIZATION);

      if (encodedToken == null) {
        // avoid giving clues to the caller (do not say that header is not present, for example)
        throw new AuthenticationException("authentication problem");
      }

      // remove the bearer initial string
      encodedToken = encodedToken.replace("Bearer ", "");


      // decode the token
      JWTokenInfo tokenInfo = JWToken.decode(encodedToken, appconfigJ.passphrase);

      if (tokenInfo == null) {
        throw new UnAuthorizedException("Please! You need to logon first");
      }


      req.setAttribute(tokenInfo.KEY, tokenInfo);

      chain.doFilter(req, res);
    } catch (AuthenticationException e) {
      res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error");
      return;
    }

  }
}
