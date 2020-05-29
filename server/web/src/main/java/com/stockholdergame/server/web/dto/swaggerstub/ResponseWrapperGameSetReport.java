package com.stockholdergame.server.web.dto.swaggerstub;

import com.stockholdergame.server.web.dto.ResponseStatus;
import com.stockholdergame.server.web.dto.ResponseWrapper;
import com.stockholdergame.server.web.dto.game.GameSetReport;
import io.swagger.annotations.ApiModel;

@ApiModel("ResponseWrapper<GameSetReport>")
public class ResponseWrapperGameSetReport extends ResponseWrapper<GameSetReport> {
    ResponseWrapperGameSetReport(ResponseStatus responseStatus, GameSetReport gameSetReport) {
        super(responseStatus, gameSetReport);
    }
}
