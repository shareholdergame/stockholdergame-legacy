package com.stockholdergame.server.services.game.impl;

import com.stockholdergame.server.dao.InvitationDao;
import com.stockholdergame.server.dto.game.InvitationDto;
import com.stockholdergame.server.dto.game.variant.GameVariantDto;
import com.stockholdergame.server.dto.mapper.DtoMapper;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.event.BusinessEventType;
import com.stockholdergame.server.model.game.Game;
import com.stockholdergame.server.model.game.GameEventType;
import com.stockholdergame.server.model.game.Invitation;
import com.stockholdergame.server.model.game.InvitationStatus;
import com.stockholdergame.server.services.event.BusinessEventBuilder;
import com.stockholdergame.server.services.event.impl.AbstractEventPublisher;
import com.stockholdergame.server.services.game.InvitationService;
import com.stockholdergame.server.services.mail.MailPreparationService;
import com.stockholdergame.server.services.messaging.GameEventProducer;
import com.stockholdergame.server.util.collections.ChunkHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alexander Savin
 *         Date: 27.12.12 16.14
 */
@Service("invitationService")
public class InvitationServiceImpl extends AbstractEventPublisher implements InvitationService {

    public static final int EXPIRED_INVITATIONS_CHUNK_SIZE = 10; // todo - make it configurable

    @Autowired
    private InvitationDao invitationDao;

    @Autowired
    private GameEventProducer gameEventProducer;

    @Autowired
    private MailPreparationService mailPreparationService;

    @Override
    @Transactional
    public void notifyByEmail(String inviterName, GamerAccount inviteeAccount, String[] inviteeNames, Game game, GameVariantDto gameVariantDto) {
        List<String> invitedUsers = new ArrayList<>();
        for (String inviteeName : inviteeNames) {
            if (!inviteeName.equals(inviteeAccount.getUserName())) {
                invitedUsers.add(inviteeName);
            }
        }
        mailPreparationService.prepareInvitationMessage(inviteeAccount.getUserName(), inviteeAccount.getEmail(),
                inviterName, gameVariantDto.getMovesQuantity(), game.getCompetitorsQuantity(),
                game.getGameSeries().getSwitchMoveOrder(), game.getRounding(),
                invitedUsers, inviteeAccount.getLocale());
    }

    @Override
    @Transactional
    public void dropExpiredInvitations() {
        ExpiredInvitationsChunkHandler batchExecutor = new ExpiredInvitationsChunkHandler();
        batchExecutor.perform(EXPIRED_INVITATIONS_CHUNK_SIZE);

        publishEvent(BusinessEventBuilder.<Set>initBuilder()
            .setType(BusinessEventType.START_GAMES).setPayload(batchExecutor.getGameIds()).toEvent());
    }

    @Override
    @Transactional
    public void removeInvitations(Long gameId) {
        List<Invitation> invitations = invitationDao.findByGameId(gameId);
        for (Invitation invitation : invitations) {
            if (invitation.getStatus().equals(InvitationStatus.CREATED)) {
                throw new ApplicationException("Can't remove invitation in status CREATED for game " + gameId);
            }
            invitationDao.remove(invitation);
        }
    }

    private class ExpiredInvitationsChunkHandler extends ChunkHandler<Invitation> {

        private Set<Long> gameIds = new HashSet<>();

        public Set<Long> getGameIds() {
            return gameIds;
        }

        @Override
        protected void process(Invitation invitation) {
            gameIds.add(invitation.getGameId());
            invitation.setStatus(InvitationStatus.EXPIRED);
            invitationDao.update(invitation);
            InvitationDto invitationDto = DtoMapper.map(invitation, InvitationDto.class);
            gameEventProducer.fireEvent(GameEventType.INVITATION_EXPIRED, null, invitation.getInviteeId(), null, invitationDto);
            gameEventProducer.fireEvent(GameEventType.INVITATION_EXPIRED, null, invitation.getInviterId(), null, invitationDto);
        }

        @Override
        protected List<Invitation> find(int limit) {
            return invitationDao.findExpired(new Date(), limit);
        }
    }
}
