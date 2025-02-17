package com.qlish.qlish_api.security.services;


import com.qlish.qlish_api.security.models.Token;
import com.qlish.qlish_api.user.model.UserPrincipal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("OAuth2AuthenticationSuccessHandler invoked.");


        if (response.isCommitted()) {
            return;
        }

        if (authentication instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken) {

            var oAuth2User = oAuth2AuthenticationToken.getPrincipal();


            UserPrincipal userPrincipal = (UserPrincipal) oAuth2User;

            tokenService.deleteTokenByUserId(userPrincipal.getId().toString());

            //generate tokens
            String accessToken = jwtService.generateAccessToken(userPrincipal);
            String refreshToken = jwtService.generateRefreshToken(userPrincipal);

            //Save refresh token
            saveRefreshToken(userPrincipal.getId(), refreshToken);




            String html = """
            <html>
            <head><title>Redirecting...</title></head>
            <body>
                <script type="text/javascript">
                    let token = '%s';
                    fetch('/api/v1/users/dashboard', {
                        method: 'GET',
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    })
                    .then(response => response.text())
                    .then(data => document.write(data))
                    .catch(error => console.error('Error:', error));
                </script>
            </body>
            </html>
        """.formatted(accessToken);

            response.setContentType("text/html");
            response.getWriter().write(html);
        } else {
            throw new ServletException("Authentication principal is not an OIDC user.");
        }



    }

    private void saveRefreshToken(ObjectId userId, String refreshToken) {
        var newTokenEntity = Token.builder()
                .userId(userId.toString())
                .token(refreshToken)
                .tokenType(OAuth2AccessToken.TokenType.BEARER.getValue())
                .isExpired(false)
                .isRevoked(false)
                .build();

        tokenService.saveToken(newTokenEntity);
    }


}
