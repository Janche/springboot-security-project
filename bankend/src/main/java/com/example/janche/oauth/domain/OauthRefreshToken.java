package com.example.janche.oauth.domain;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "oauth_refresh_token")
public class OauthRefreshToken {
    @Column(name = "token_id")
    private String tokenId;

    private byte[] token;

    private byte[] authentication;

    /**
     * @return token_id
     */
    public String getTokenId() {
        return tokenId;
    }

    /**
     * @param tokenId
     */
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    /**
     * @return token
     */
    public byte[] getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(byte[] token) {
        this.token = token;
    }

    /**
     * @return authentication
     */
    public byte[] getAuthentication() {
        return authentication;
    }

    /**
     * @param authentication
     */
    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }
}