package com.example.janche.oauth.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Table(name = "oauth_access_token")
public class OauthAccessToken {
    @Id
    @Column(name = "authentication_id")
    private String authenticationId;

    @Column(name = "token_id")
    private String tokenId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "refresh_token")
    private String refreshToken;

    private byte[] token;

    private byte[] authentication;
}