package com.stockholdergame.server.web.dto.game;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class PlayerInvitation {

    public InvitationStatus invitationStatus;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    public LocalDateTime statusSetAt;
}
