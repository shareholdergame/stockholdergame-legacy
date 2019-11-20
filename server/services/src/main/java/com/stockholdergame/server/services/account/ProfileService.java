package com.stockholdergame.server.services.account;

import com.stockholdergame.server.dto.ProfileDto;
import com.stockholdergame.server.dto.account.CreateFriendRequestDto;
import com.stockholdergame.server.dto.account.FriendRequestStatusDto;

/**
 * @author Alexander Savin
 *         Date: 9.5.2010 15.22.14
 */
public interface ProfileService {

    void changeProfileData(ProfileDto profileDto);

    void sendFriendRequest(CreateFriendRequestDto createFriendRequest);

    void changeFriendRequestStatus(FriendRequestStatusDto friendRequestStatus);

    void cancelFriendRequest(String requesteeUserName);

    boolean updateAvatar(boolean update);
}
