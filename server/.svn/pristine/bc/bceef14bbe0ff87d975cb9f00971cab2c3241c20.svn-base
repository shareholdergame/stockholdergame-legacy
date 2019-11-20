package com.stockholdergame.server.services.account.impl;

import com.stockholdergame.server.dao.FriendDao;
import com.stockholdergame.server.dao.FriendRequestDao;
import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.dao.ProfileDao;
import com.stockholdergame.server.dto.ProfileDto;
import com.stockholdergame.server.dto.account.CreateFriendRequestDto;
import com.stockholdergame.server.dto.account.FriendRequestDto;
import com.stockholdergame.server.dto.account.FriendRequestStatusDto;
import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.exceptions.BusinessExceptionType;
import com.stockholdergame.server.model.account.Friend;
import com.stockholdergame.server.model.account.FriendPk;
import com.stockholdergame.server.model.account.FriendRequest;
import com.stockholdergame.server.model.account.FriendRequestStatus;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.account.Profile;
import com.stockholdergame.server.model.account.Sex;
import com.stockholdergame.server.model.game.GameEventType;
import com.stockholdergame.server.services.UserInfoAware;
import com.stockholdergame.server.services.account.GravatarService;
import com.stockholdergame.server.services.account.ProfileService;
import com.stockholdergame.server.services.messaging.GameEventProducer;
import com.stockholdergame.server.services.security.DeniedForRemovedUser;
import com.stockholdergame.server.util.AccountUtils;
import com.stockholdergame.server.util.Countries;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Alexander Savin
 *         Date: 9.5.2010 15.25.12
 */
@Service
public class ProfileServiceImpl extends UserInfoAware implements ProfileService {

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Autowired
    private FriendRequestDao friendRequestDao;

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private GravatarService gravatarService;

    @Autowired
    private GameEventProducer gameEventProducer;

    public void setProfileDao(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public void setGamerAccountDao(GamerAccountDao gamerAccountDao) {
        this.gamerAccountDao = gamerAccountDao;
    }

    public void setFriendRequestDao(FriendRequestDao friendRequestDao) {
        this.friendRequestDao = friendRequestDao;
    }

    public void changeProfileData(ProfileDto profileDto) {
        if (!StringUtils.isEmpty(profileDto.getCountry()) && !Countries.contains(profileDto.getCountry())) {
            throw new BusinessException(BusinessExceptionType.INVALID_COUNTRY, profileDto.getCountry());
        }

        GamerAccount gamerAccount = findGamerAccountByUserName(getCurrentUser().getUserName());
        Profile profile = gamerAccount.getProfile();
        if (gamerAccount.getProfile() == null) {
            profile = new Profile(gamerAccount);
            gamerAccount.setProfile(profile);
            profileDao.create(profile);
        }

        profile.setBirthday(profileDto.getBirthday());
        profile.setCountry(profileDto.getCountry());
        profile.setRegion(profileDto.getRegion());
        profile.setCity(profileDto.getCity());
        profile.setSex(profileDto.getSex() != null ? Sex.valueOf(profileDto.getSex()) : null);
        profile.setAbout(profileDto.getAbout());

        profileDao.update(profile);
    }

    @DeniedForRemovedUser
    public void sendFriendRequest(CreateFriendRequestDto createFriendRequestDto) {
        String userName = createFriendRequestDto.getUserName();
        GamerAccount gamerAccount = gamerAccountDao.findByUserName(userName);
        if (gamerAccount == null) {
            throw new BusinessException(BusinessExceptionType.USER_NOT_FOUND, userName);
        } else if (AccountUtils.isRemoved(gamerAccount.getStatus())) {
            throw new BusinessException(BusinessExceptionType.USER_REMOVED, userName);
        }

        boolean isAlreadyFriend = friendDao.isFriendOfUser(getCurrentUser().getId(), gamerAccount.getId());
        if (isAlreadyFriend) {
            throw new BusinessException(BusinessExceptionType.USER_ALREADY_FRIEND, createFriendRequestDto.getUserName());
        }

        FriendRequest friendRequest = friendRequestDao.findByUniqueParameters(getCurrentUser().getId(),
                gamerAccount.getId(), FriendRequestStatus.CREATED);
        if (friendRequest != null) {
            throw new BusinessException(BusinessExceptionType.FRIEND_REQUEST_ALREADY_SENT);
        }

        Date current = new Date();
        friendRequest = new FriendRequest();
        friendRequest.setRequestorId(getCurrentUser().getId());
        friendRequest.setRequesteeId(gamerAccount.getId());
        friendRequest.setCreatedDate(current);
        friendRequest.setStatus(FriendRequestStatus.CREATED);

        friendRequestDao.create(friendRequest);

        gameEventProducer.fireEvent(GameEventType.FRIEND_REQUEST_RECEIVED, getCurrentUser().getId(), gamerAccount.getId(), friendRequest.getId(),
                new FriendRequestDto(getCurrentUser().getUserName(), current, FriendRequestStatus.CREATED.name()));
    }

    @DeniedForRemovedUser
    public void changeFriendRequestStatus(FriendRequestStatusDto friendRequestStatus) {
        String userName = friendRequestStatus.getRequestorName();
        GamerAccount gamerAccount = gamerAccountDao.findByUserName(userName);
        if (gamerAccount == null) {
            throw new BusinessException(BusinessExceptionType.USER_NOT_FOUND, userName);
        }

        FriendRequest friendRequest = friendRequestDao.findByUniqueParameters(gamerAccount.getId(),
                getCurrentUser().getId(), FriendRequestStatus.CREATED);
        if (friendRequest == null) {
            throw new BusinessException(BusinessExceptionType.FRIEND_REQUEST_NOT_FOUND);
        }
        if (!FriendRequestStatus.CREATED.equals(friendRequest.getStatus())) {
            throw new BusinessException(BusinessExceptionType.ILLEGAL_FRIEND_REQUEST_STATUS);
        }

        FriendRequestStatus newStatus = FriendRequestStatus.valueOf(friendRequestStatus.getStatus());

        if (AccountUtils.isRemoved(gamerAccount.getStatus())) {
            newStatus = FriendRequestStatus.CANCELLED;
        }

        Date current = new Date();
        friendRequest.setStatus(newStatus);
        friendRequest.setCompletedDate(current);
        friendRequestDao.update(friendRequest);

        if (FriendRequestStatus.CONFIRMED.equals(newStatus)) {
            Friend friend1 = new Friend();
            FriendPk friendPk1 = new FriendPk();
            friendPk1.setGamerId(friendRequest.getRequestorId());
            friendPk1.setFriendId(friendRequest.getRequesteeId());
            friend1.setId(friendPk1);

            Friend friend2 = new Friend();
            FriendPk friendPk2 = new FriendPk();
            friendPk2.setGamerId(friendRequest.getRequesteeId());
            friendPk2.setFriendId(friendRequest.getRequestorId());
            friend2.setId(friendPk2);

            friendDao.create(friend1);
            friendDao.create(friend2);
        }

        gameEventProducer.fireEvent(GameEventType.FRIEND_REQUEST_STATUS_CHANGED, getCurrentUser().getId(), gamerAccount.getId(),
                friendRequest.getId(), new FriendRequestDto(getCurrentUser().getUserName(), current, newStatus.name()));
        /*sendNotification(gamerAccount.getId(), friendRequest.getId(), GameEventType.FRIEND_REQUEST_STATUS_CHANGED,
                new FriendRequestDto(getCurrentUser().getUserName(), current, newStatus.name()));*/
    }

    public void cancelFriendRequest(String requesteeUserName) {
        GamerAccount gamerAccount = gamerAccountDao.findByUserName(requesteeUserName);
        if (gamerAccount == null) {
            throw new BusinessException(BusinessExceptionType.USER_NOT_FOUND, requesteeUserName);
        }

        FriendRequest friendRequest = friendRequestDao.findByUniqueParameters(getCurrentUser().getId(),
                gamerAccount.getId(), FriendRequestStatus.CREATED);
        if (friendRequest == null) {
            throw new BusinessException(BusinessExceptionType.FRIEND_REQUEST_NOT_FOUND);
        }
        if (!FriendRequestStatus.CREATED.equals(friendRequest.getStatus())) {
            throw new BusinessException(BusinessExceptionType.ILLEGAL_FRIEND_REQUEST_STATUS);
        }

        Date current = new Date();
        friendRequest.setStatus(FriendRequestStatus.CANCELLED);
        friendRequest.setCompletedDate(current);
        friendRequestDao.update(friendRequest);

        gameEventProducer.fireEvent(GameEventType.FRIEND_REQUEST_STATUS_CHANGED, getCurrentUser().getId(), gamerAccount.getId(),
                friendRequest.getId(), new FriendRequestDto(getCurrentUser().getUserName(), current, FriendRequestStatus.CANCELLED.name()));
    }

    @Override
    public boolean updateAvatar(boolean update) {
        GamerAccount gamerAccount = findGamerAccountByUserName(getCurrentUser().getUserName());
        Profile profile = gamerAccount.getProfile();
        if (update) {
            if (profile == null) {
                profile = new Profile(gamerAccount);
                gamerAccount.setProfile(profile);
                profileDao.create(profile);
            }

            byte[] avatar = gravatarService.downloadAvatar(gamerAccount.getEmail(), false);
            byte[] smallAvatar = gravatarService.downloadAvatar(gamerAccount.getEmail(), true);

            if (avatar != null && avatar.length > 0) {
                profile.setAvatar(avatar);
                profile.setSmallAvatar(smallAvatar);
                profileDao.update(profile);
                return true;
            } else {
                return false;
            }
        } else {
            if (profile != null) {
                profile.setAvatar(null);
                profile.setSmallAvatar(null);
                profileDao.update(profile);
            }
            return true;
        }
    }

    private GamerAccount findGamerAccountByUserName(String userName) {
        GamerAccount gamerAccount = gamerAccountDao.findByUserName(userName);
        if (gamerAccount == null) {
            throw new BusinessException(BusinessExceptionType.USER_NOT_FOUND, userName);
        }
        return gamerAccount;
    }
}
