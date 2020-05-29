package com.stockholdergame.server.web.dto.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

public class PlayerInvitation {

    public InvitationStatus invitationStatus;

    @ApiModelProperty(dataType = "String")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    public LocalDateTime statusSetAt;
}
