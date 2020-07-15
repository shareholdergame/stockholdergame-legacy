package com.stockholdergame.server.web.dto.swaggerstub;

import com.stockholdergame.server.web.dto.ResponseStatus;
import com.stockholdergame.server.web.dto.ResponseWrapper;
import com.stockholdergame.server.web.dto.game.GameSet;
import io.swagger.annotations.ApiModel;

@ApiModel("ResponseWrapper<GameSet>")
public class ResponseWrapperGameSet extends ResponseWrapper<GameSet> {
    ResponseWrapperGameSet(ResponseStatus responseStatus, GameSet gameSet) {
        super(responseStatus, gameSet);
    }
}
