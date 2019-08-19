package com.example.janche.oauth.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Table(name = "oauth_refresh_token")
public class OauthRefreshToken {
    @Id
    @Column(name = "token_id")
    private String tokenId;

    private byte[] token;

    private byte[] authentication;
}