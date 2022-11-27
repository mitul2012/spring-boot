package com.practice.demo.filter;

import com.practice.demo.service.UserService;
import com.practice.demo.utils.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    Logger log = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JWTUtil jwtUtil;

   // private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/auth");

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException,BadCredentialsException {
         String authorization  = request.getHeader("Authorization");
         String token = null;
         String userName = null;

//         if(this.requestMatcher.matches(request)){
//             System.out.println("MATCHED");
//             filterChain.doFilter(request,response);
//             return;
//         }

         if(authorization == null || !authorization.startsWith("Bearer ")){
             log.error("Invalid Bearer token, Authentication failed,");
             resolver.resolveException(request, response, null, new BadCredentialsException("Bad Credential"));
             return;
         }
         try{
             token = authorization.substring(7);
             userName = jwtUtil.getUserNameFromToken(token);



             if (userName != null && SecurityContextHolder.getContext().getAuthentication()==null){
                 UserDetails userDetails = userService.loadUserByUsername(userName);

                 if(jwtUtil.validateToken(token,userDetails)){
                     UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                             new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                     usernamePasswordAuthenticationToken.setDetails(
                             new WebAuthenticationDetailsSource().buildDetails(request)
                     );
                     SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                     filterChain.doFilter(request,response);
                 }
             }
         }catch (Exception e){
             log.error("Error occured while validating security token",e.getMessage());
             resolver.resolveException(request, response, null, new ExpiredJwtException(null,null,"Token Expired"));
             return;
         }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();
        return "/auth".equals(path) || "/save".equals(path);
    }
}
