package com.stockholdergame.server.web.dto.swaggerstub;

import com.stockholdergame.server.web.dto.GameListResponse;
import com.stockholdergame.server.web.dto.ResponseStatus;
import com.stockholdergame.server.web.dto.ResponseWrapper;
import io.swagger.annotations.ApiModel;

@ApiModel("ResponseWrapper<GameListResponse>")
public class ResponseWrapperGameListResponse extends ResponseWrapper<GameListResponse> {
    protected ResponseWrapperGameListResponse(ResponseStatus status, GameListResponse body) {
        super(status, body);
    }
}
