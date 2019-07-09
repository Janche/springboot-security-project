package com.example.janche.oauth.dto;

import com.example.janche.oauth.domain.OauthClientDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lirong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OauthClientDetailsDTO implements Serializable {

    private String clientId;

    private String resourceIds = "oauth2";

    private String clientSecret;

    private String scope = "select";

    private String authorizedGrantTypes = "authorization_code,refresh_token";

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity = 200;

    private Integer refreshTokenValidity = 400;

    // optional
    private String additionalInformation;

    private String autoapprove = "true";

    public String getScopeWithBlank() {
        if (scope != null && scope.contains(",")) {
            return scope.replaceAll(",", " ");
        }
        return scope;
    }

    public static OauthClientDetailsDTO details2DTO(OauthClientDetails c){
        return OauthClientDetailsDTO.builder()
                .accessTokenValidity(c.getAccessTokenValidity())
                .refreshTokenValidity(c.getRefreshTokenValidity())
                .clientId(c.getClientId())
                .clientSecret(c.getClientSecret())
                .scope(c.getScope())
                .authorities(c.getAuthorities())
                .authorizedGrantTypes(c.getAuthorizedGrantTypes())
                .additionalInformation(c.getAdditionalInformation())
                .webServerRedirectUri((c.getWebServerRedirectUri()))
                .autoapprove(c.getAutoapprove())
                .resourceIds(c.getResourceIds())
                .build();
    }

    public static List<OauthClientDetailsDTO> details2DTOs(List<OauthClientDetails> clientDetailses) {
        List<OauthClientDetailsDTO> dtos = new ArrayList<>();
        for (OauthClientDetails clientDetailse : clientDetailses) {
            dtos.add(OauthClientDetailsDTO.details2DTO(clientDetailse));
        }
        return dtos;
    }


    public boolean isContainsAuthorizationCode() {
        return this.authorizedGrantTypes.contains("authorization_code");
    }

    public boolean isContainsPassword() {
        return this.authorizedGrantTypes.contains("password");
    }

    public boolean isContainsImplicit() {
        return this.authorizedGrantTypes.contains("implicit");
    }

    public boolean isContainsClientCredentials() {
        return this.authorizedGrantTypes.contains("client_credentials");
    }

    public boolean isContainsRefreshToken() {
        return this.authorizedGrantTypes.contains("refresh_token");
    }


}