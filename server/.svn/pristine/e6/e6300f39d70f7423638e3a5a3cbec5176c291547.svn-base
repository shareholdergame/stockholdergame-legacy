package com.stockholdergame.server.services.account.impl;

import com.stockholdergame.server.dao.ChatMessageDao;
import com.stockholdergame.server.dao.FriendDao;
import com.stockholdergame.server.dao.FriendRequestDao;
import com.stockholdergame.server.dao.GameDao;
import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.dao.InvitationDao;
import com.stockholdergame.server.model.account.FriendRequest;
import com.stockholdergame.server.model.account.FriendRequestStatus;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.game.Game;
import com.stockholdergame.server.model.game.GameStatus;
import com.stockholdergame.server.model.game.Invitation;
import com.stockholdergame.server.model.game.InvitationStatus;
import com.stockholdergame.server.services.account.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserActivityServiceImpl implements UserActivityService {

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Autowired
    private FriendRequestDao friendRequestDao;

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private ChatMessageDao chatMessageDao;

    @Autowired
    private InvitationDao invitationDao;

    public void stopUserActivity(Long gamerId) {
        cancelFriendRequests(gamerId);
        cancelInvitations(gamerId);
        cancelGames(gamerId);
    }

    private void cancelInvitations(Long gamerId) {
        List<Invitation> invitations = invitationDao.findByInviterId(gamerId);
        for (Invitation invitation : invitations) {
            if (invitation.getStatus().equals(InvitationStatus.CREATED)) {
                invitation.setStatus(InvitationStatus.CANCELLED);
                invitationDao.update(invitation);
            }
        }
    }

    public void removeUserActivity(Long gamerId) {
        friendDao.removeByGamerId(gamerId);
        cancelGames(gamerId);
        GamerAccount ga = gamerAccountDao.findByPrimaryKey(gamerId);
        chatMessageDao.removeUserChatsByUserId(ga.getUserName());
    }

    private void cancelFriendRequests(Long gamerId) {
        List<FriendRequest> outgoingRequests = friendRequestDao.findByRequestorId(gamerId);
        for(FriendRequest outgoingRequest : outgoingRequests) {
            outgoingRequest.setStatus(FriendRequestStatus.CANCELLED);
            outgoingRequest.setCompletedDate(new Date());
            friendRequestDao.update(outgoingRequest);

            // todo - send notification ?
        }
    }

    private void cancelGames(Long gamerId) {
        List<Game> games = gameDao.findGamesByGamerId(gamerId);
        for (Game game : games) {
            if (GameStatus.OPEN.equals(game.getGameStatus())) {
                game.setGameStatus(GameStatus.CANCELLED);
                gameDao.update(game);
            } else if (GameStatus.RUNNING.equals(game.getGameStatus())) {
                game.setGameStatus(GameStatus.INTERRUPTED);
                gameDao.update(game);
            }
        }
    }
}
