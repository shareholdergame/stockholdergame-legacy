package com.stockholdergame.server.dao;

import com.stockholdergame.server.model.game.Invitation;
import com.stockholdergame.server.model.game.InvitationStatus;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Savin
 *         Date: 18.2.11 22.23
 */
public interface InvitationDao extends GenericDao<Invitation, Long> {

    Invitation findCreatedInvitationByGameIdAndInviteeName(Long gameId, String userName);

    Long[] countUserInvitations(Long userId);

    List<Invitation> findByParameters(Long inviterId, Long inviteeId, InvitationStatus... statuses);

    int countInvitationsByGameIdAndStatus(Long gameId, InvitationStatus status);

    List<Invitation> findByGameId(Long gameId);

    List<Invitation> findExpired(Date date, int limit);

    List<Invitation> findByInviterId(Long gamerId);
}
