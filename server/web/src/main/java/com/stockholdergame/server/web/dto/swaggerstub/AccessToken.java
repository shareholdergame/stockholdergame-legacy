package com.stockholdergame.server.web.dto.swaggerstub;

import io.swagger.annotations.ApiModelProperty;

public class AccessToken {

    @ApiModelProperty(name = "access_token")
    public String accessToken;

    @ApiModelProperty(name = "token_type")
    public String tokenType;

    @ApiModelProperty(name = "refresh_token")
    public String refreshToken;

    @ApiModelProperty(name = "expires_in")
    public long expiresIn;

    public String scope;

    public String jti;
}
