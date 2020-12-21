package com.stockholdergame.server.web.controller;

import com.stockholdergame.server.dto.game.CurrentTurnDto;
import com.stockholdergame.server.services.game.GameService;
import com.stockholdergame.server.web.dto.ResponseWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Deprecated
@Api(value = "/", tags = "Moderation API")
@Controller
@RequestMapping("/public/moderation/tools")
public class ModerateController {

    @Autowired
    private GameService gameService;

    @Deprecated
    @ApiOperation("Current turns")
    @RequestMapping(value = "/currentturns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<List<CurrentTurnDto>> currentTurns() {
        return ResponseWrapper.ok(gameService.getCurrentTurns());
    }
}
