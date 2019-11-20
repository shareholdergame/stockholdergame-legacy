package com.stockholdergame.client.mvc.proxy {

    import com.stockholdergame.client.model.dto.account.CreateFriendRequestDto;
    import com.stockholdergame.client.model.dto.account.FriendRequestStatusDto;
    import com.stockholdergame.client.model.dto.account.GetChatHistoryDto;
    import com.stockholdergame.client.model.dto.account.SendMessageDto;
    import com.stockholdergame.client.model.dto.account.UserDto;
    import com.stockholdergame.client.model.dto.account.UserFilterDto;
    import com.stockholdergame.client.model.dto.account.UsersList;
    import com.stockholdergame.client.model.dto.game.UserStatisticsFilterDto;
    import com.stockholdergame.client.remote.factory.RemoteServiceFactory;
    import com.stockholdergame.client.util.cache.LRUCache;

    import mx.collections.ArrayCollection;

    public class SocialServiceProxy extends AbstractProxy {

        public static const NAME:String = 'SocialServiceProxy';

        private var usersCache:LRUCache = new LRUCache();

        public function SocialServiceProxy() {
            super(NAME, RemoteServiceFactory.SOCIAL_SERVICE);
        }

        public function sendFriendRequest(createFriendRequest:CreateFriendRequestDto, resultHandler:Function):void {
            getService(resultHandler).sendFriendRequest(createFriendRequest);
        }

        public function changeFriendRequestStatus(friendRequestStatus:FriendRequestStatusDto, resultHandler:Function):void {
            getService(resultHandler).changeFriendRequestStatus(friendRequestStatus);
        }

        public function getUsers(userFilter:UserFilterDto, resultHandler:Function):void {
            getService(function (usersList:UsersList):void {
                var users:ArrayCollection = usersList.users;
                for each (var userDto:UserDto in users) {
                    usersCache.pushItem(userDto.userName, userDto);
                }
                resultHandler(usersList);
            }).getUsers(userFilter);
        }

        public function getUserInfoByName(userNames:Array, callback:Function):void {
            var users:ArrayCollection = new ArrayCollection();
            var userNamesNotInCache:ArrayCollection = new ArrayCollection();
            for each (var userName:String in userNames) {
                if (usersCache.getItem(userName) != null) {
                    users.addItem(usersCache.getItem(userName));
                } else {
                    userNamesNotInCache.addItem(userName);
                }
            }
            if (userNamesNotInCache.length > 0) {
                var userFilter:UserFilterDto = new UserFilterDto();
                userFilter.userNames = userNamesNotInCache.toArray();
                getService(function (result:UsersList):void {
                    if (result.totalCount > 0) {
                        var foundUsers:ArrayCollection = result.users;
                        for each (var user:UserDto in foundUsers) {
                            users.addItem(user);
                            usersCache.pushItem(user.userName, user);
                        }
                    }
                    callback(users);
                }).getUsers(userFilter);
            } else {
                callback(users);
            }
        }

        public function cancelFriendRequest(requesteeName:String, resultHandler:Function):void {
            getService(resultHandler).cancelFriendRequest(requesteeName);
        }

        public function sendMessage(sendMessageDto:SendMessageDto, resultHandler:Function):void {
            getService(resultHandler).sendMessage(sendMessageDto);
        }

        public function getChatHistory(getChatHistory:GetChatHistoryDto, resultHandler:Function):void {
            getService(resultHandler).getChatHistory(getChatHistory);
        }

        public function getChats(resultHandler:Function):void {
            getService(resultHandler).getChats();
        }

        public function getLastEvents(resultHandler:Function):void {
            getService(resultHandler).getLastEvents();
        }

        public function getServerStatistics(resultHandler:Function):void {
            getService(resultHandler).getServerStatistics();
        }

        public function clearChatHistory(recipients:Array, resultHandler:Function):void {
            getService(resultHandler).clearChatHistory(recipients);
        }

        public function getUnreadChatMessages(resultHandler:Function):void {
            getService(resultHandler).getUnreadChatMessages();
        }

        public function markChatMessagesAsRead(messageIds:ArrayCollection, resultHandler:Function):void {
            getService(resultHandler).markChatMessagesAsRead(messageIds);
        }

        public function getUserStatistics(filterDto:UserStatisticsFilterDto, resultHandler:Function):void {
            getService(resultHandler).getUserStatistics(filterDto);
        }
    }
}
