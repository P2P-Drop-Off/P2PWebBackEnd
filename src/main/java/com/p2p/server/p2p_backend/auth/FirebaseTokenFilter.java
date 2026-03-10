package com.p2p.server.p2p_backend.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.p2p.server.p2p_backend.exceptions.ItemNotFoundException;
import com.p2p.server.p2p_backend.service.AdminService;
import com.p2p.server.p2p_backend.service.StoreUserService;
import com.p2p.server.p2p_backend.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class FirebaseTokenFilter extends OncePerRequestFilter {

    private FirebaseAuth fireBaseAuth;
    private final UserService userService;
    private final AdminService adminService;
    private final StoreUserService storeUserService;
    private final FirebaseAuth firebaseAuth;

    public FirebaseTokenFilter(UserService userService, StoreUserService storeUserService,
                               AdminService adminService, FirebaseAuth firebaseAuth) {
        this.userService = userService;
        this.storeUserService = storeUserService;
        this.adminService = adminService;
        this.firebaseAuth = firebaseAuth;
    }

    private GrantedAuthority validateAuthority(String uid, String userType) throws Exception{
        if (userType == null) {
            return new SimpleGrantedAuthority("USER"); // default to USER 
        }
        
        if ((userType.equals("STORE_USER") && storeUserService.getStoreUser(uid) == null) ||
                (userType.equals("USER") && userService.getUser(uid) != null) ||
                (userType.equals("ADMIN") && adminService.getAdmin(uid) != null)) {
            return new SimpleGrantedAuthority(userType);
        }else{
            throw new ItemNotFoundException("get 403'd");
        }
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            String token = parseJwt(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            FirebaseToken decoded = firebaseAuth.verifyIdToken(token);

            String uid = decoded.getUid();
            String userType = (String) decoded.getClaims().get("userType");
            GrantedAuthority role = validateAuthority(uid, userType);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                uid,
                null,
                List.of(role)
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);

        } catch (FirebaseAuthException | ItemNotFoundException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } catch(Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}