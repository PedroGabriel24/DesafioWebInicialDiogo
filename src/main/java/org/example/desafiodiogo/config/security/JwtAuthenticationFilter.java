package org.example.desafiodiogo.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.desafiodiogo.dto.auth.ProfileJWTToken;
import org.example.desafiodiogo.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/auth") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/h2-console");
    }

    // Helper: case-insensitive header lookup + attribute/cookie fallbacks
    private String findAuthorizationToken(HttpServletRequest request) {
        // 1) try standard header (case-insensitive)
        try {
            Enumeration<String> names = request.getHeaderNames();
            if (names != null) {
                while (names.hasMoreElements()) {
                    String name = names.nextElement();
                    if (name != null && name.equalsIgnoreCase("Authorization")) {
                        String v = request.getHeader(name);
                        if (v != null && !v.isBlank()) return v;
                    }
                }
            }
        } catch (Exception ex) {
            LOG.debug("Error enumerating headers: {}", ex.getMessage());
        }

        // 2) try known alternative header names
        String[] altHeaders = new String[]{"X-Auth-Token", "x-auth-token", "authorization", "Authorization"};
        for (String h : altHeaders) {
            try {
                String v = request.getHeader(h);
                if (v != null && !v.isBlank()) return v;
            } catch (Exception ignored) {}
        }

        // 3) try request attributes (some frameworks put token here)
        Object attr = request.getAttribute("Authorization");
        if (attr == null) attr = request.getAttribute("authorization");
        if (attr != null) {
            String s = attr.toString();
            if (!s.isBlank()) return s;
        }

        // 4) try cookies
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c == null || c.getName() == null) continue;
                    if (c.getName().equalsIgnoreCase("Authorization") || c.getName().equalsIgnoreCase("token") || c.getName().equalsIgnoreCase("access_token")) {
                        String s = c.getValue();
                        if (s != null && !s.isBlank()) return s;
                    }
                }
            }
        } catch (Exception ignored) {}

        // 5) try query params (already present in original code but keep as fallback)
        String p = request.getParameter("token");
        if (p == null || p.isBlank()) p = request.getParameter("access_token");
        if (p == null || p.isBlank()) p = request.getParameter("authorization");
        if (p != null && !p.isBlank()) return p.trim();

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Try multiple locations for the token: Authorization (Bearer or raw), X-Auth-Token, query params, cookies, attributes
        String raw = findAuthorizationToken(request);
        String token = null;

        if (raw != null && !raw.isBlank()) {
            raw = raw.trim();
            if (raw.startsWith("Bearer ")) {
                token = raw.substring(7).trim();
            } else {
                token = raw;
            }
        }

        if (token != null) {
            try {
                if (tokenProvider.validateToken(token)) {
                    String username = tokenProvider.getClaims(token).getSubject();
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    try {
                        ProfileJWTToken payload = tokenProvider.parsePayload(token, ProfileJWTToken.class);
                        if (payload != null) {
                            if (payload.getProfile() != null && !payload.getProfile().isBlank()) {
                                authorities.add(new SimpleGrantedAuthority("ROLE_" + payload.getProfile()));
                                LOG.debug("Authority from payload.profile: ROLE_{}", payload.getProfile());
                            } else {
                                Map<String, Object> inner = payload.getPayload();
                                if (inner != null) {
                                    Object tipo = inner.get("tipo");
                                    Object profileObj = inner.get("profile");
                                    Object roleObj = inner.get("role");

                                    String resolved = null;
                                    if (tipo != null) resolved = tipo.toString();
                                    else if (profileObj != null) resolved = profileObj.toString();
                                    else if (roleObj != null) resolved = roleObj.toString();

                                    if (resolved != null && !resolved.isBlank()) {
                                        authorities.add(new SimpleGrantedAuthority("ROLE_" + resolved));
                                        LOG.debug("Authority from nested payload: ROLE_{}", resolved);
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        LOG.debug("Failed to parse token payload for authorities: {}", ex.getMessage());
                    }

                    if (authorities.isEmpty() && userDetails instanceof Users) {
                        Users u = (Users) userDetails;
                        if (u.getTipo() != null) {
                            authorities.add(new SimpleGrantedAuthority("ROLE_" + u.getTipo().name()));
                            LOG.debug("Authority from user.tipo: ROLE_{}", u.getTipo().name());
                        }
                    }

                    if (authorities.isEmpty()) {
                        userDetails.getAuthorities().forEach(a -> authorities.add(new SimpleGrantedAuthority(a.getAuthority())));
                    }

                    LOG.debug("Final authorities for user {} : {}", username, authorities);

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    LOG.debug("Invalid JWT token");
                }
            } catch (Exception ex) {
                LOG.debug("Error validating token or setting security context: {}", ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
