package com.stockholdergame.server.web.controller.swaggerstub;

import com.stockholdergame.server.web.dto.swaggerstub.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This is stub for swagger.
 */
@Api(value = "/", authorizations = { @Authorization("Basic") }, tags = "Authorization")
@RequestMapping("/")
public class CustomTokenEndpoint {

    @ApiOperation("Get access token")
    @RequestMapping(value = "/oauth/token", method = RequestMethod.POST)
    public AccessToken getAccessToken(
            @ApiParam(defaultValue = "password") @RequestParam(value = "grant_type") String type,
            @RequestParam("username") String username,
            @RequestParam("password") String password)
            throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }
}
