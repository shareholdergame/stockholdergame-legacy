package com.stockholdergame.server.services.game;

import com.stockholdergame.server.dto.game.variant.GameVariantDto;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.game.Game;

/**
 * @author Alexander Savin
 *         Date: 27.12.12 16.13
 */
public interface InvitationService {

    void notifyByEmail(String inviterName, GamerAccount inviteeAccount, String[] inviteeNames, Game game, GameVariantDto gameVariantDto);

    void dropExpiredInvitations();

    void removeInvitations(Long gameId);
}
