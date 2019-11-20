package com.stockholdergame.client.mvc.proxy {
    import com.stockholdergame.client.model.dto.account.IncomingChatMessageDto;
    import com.stockholdergame.client.model.dto.account.UserDto;
    import com.stockholdergame.client.model.dto.account.UserFilterDto;
    import com.stockholdergame.client.model.dto.account.UsersList;
    import com.stockholdergame.client.model.dto.game.GameEventDto;
    import com.stockholdergame.client.model.dto.game.InvitationDto;
    import com.stockholdergame.client.model.session.SessionManager;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.model.dto.game.event.UserNotification;
    import com.stockholdergame.client.remote.factory.ConsumerFactory;
    import com.stockholdergame.client.ui.MessageResources;
    import com.stockholdergame.client.ui.components.dialog.dialogClasses.ChatMessage;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.ui.message.Message;
    import com.stockholdergame.client.ui.message.MessageSeverity;
    import com.stockholdergame.client.util.StringUtil;
    import com.stockholdergame.client.util.collection.CollectionUtil;

    import flash.utils.ByteArray;

    import flash.utils.Dictionary;

    import mx.collections.ArrayCollection;

    import mx.messaging.Consumer;
    import mx.messaging.events.MessageEvent;
    import mx.messaging.events.MessageFaultEvent;
    import mx.messaging.messages.IMessage;

    import org.puremvc.as3.patterns.proxy.Proxy;

    public class MessagingServiceProxy extends Proxy {

        private var consumers:Dictionary = new Dictionary();

        public static const EVENTS_BUS:String = "eventsBus";

        public static const NAME:String = 'MessagingServiceProxy';

        private var messageMapping:Dictionary = new Dictionary();

        public function MessagingServiceProxy() {
            super(NAME);
            fillMessageMappping();
        }

        private function fillMessageMappping():void {
            messageMapping[UserNotification.INVITATION_RECEIVED] = MessageResources.INVITATION_RECEIVED;
            messageMapping[UserNotification.INVITATION_REJECTED] = MessageResources.INVITATION_REJECTED;
            messageMapping[UserNotification.INVITATION_CANCELLED] = MessageResources.INVITATION_CANCELLED;
        }

        public function subscribeOnEvents(subtopicName:String, onMessageHandler:Function = null, onErrorHandler:Function = null):void {
            subscribe(EVENTS_BUS, subtopicName, onMessageHandler != null ? onMessageHandler : handleMessage,
                    onErrorHandler != null ? onErrorHandler : handleMessageFault);
        }

        private function subscribe(destination:String, subtopicName:String, onMessageHandler:Function, onErrorHandler:Function):void {
            consumers[destination + "/" + subtopicName] = ConsumerFactory.instance.getConsumer(destination,
                    subtopicName, onMessageHandler, onErrorHandler);
        }

        public function unsubscribe():void {
            for each (var consumer:Consumer in consumers) {
                consumer.disconnect();
            }
            ConsumerFactory.instance.disconnectChannelSet();
            consumers = new Dictionary();
        }

        private function handleMessageFault(event:MessageFaultEvent):void {
            sendNotification(Notifications.SHOW_POPUP, new Message(MessageResources.ERROR_MESSAGE, MessageSeverity.ERROR, null, null,
                    [event.faultString]));
        }

        private function handleMessage(event:MessageEvent):void {
            var message:IMessage = event.message;
            var body:Object = message.body;
            if (body is UserNotification) {
                var userNotification:UserNotification = UserNotification(body);
                var gameEvent:GameEventDto;

                if (userNotification.type == UserNotification.MESSAGE_RECEIVED) {
                    transformToChatMessage(CollectionUtil.createCollection(userNotification.body),
                            function(chatMessages:ArrayCollection):void {
                        if (chatMessages != null && chatMessages.length > 0) {
                            sendNotification(Notifications.MESSAGE_RECEIVED, chatMessages.getItemAt(0));
                        }
                    });
                } else if (userNotification.type == UserNotification.MOVE_DONE
                        || userNotification.type == UserNotification.GAME_FINISHED) {
                    sendNotification(Notifications.MOVE_DONE_NOTIFICATION, userNotification);
                } else if (StringUtil.valueIn(userNotification.type, [UserNotification.USER_JOINED, UserNotification.GAME_STARTED])) {
                    gameEvent = GameEventDto(userNotification.body);
                    sendNotification(Notifications.USER_JOINED, gameEvent);
                    sendNotification(Notifications.REFRESH_CURRENT_PAGE);
                } else if (StringUtil.valueIn(userNotification.type, [UserNotification.INVITATION_RECEIVED, UserNotification.INVITATION_REJECTED,
                    UserNotification.INVITATION_CANCELLED, UserNotification.INVITATION_EXPIRED])) {
                    var senderName:String = StringUtil.valueIn(userNotification.type, [UserNotification.INVITATION_RECEIVED,
                        UserNotification.INVITATION_CANCELLED]) ? userNotification.body.inviterName :
                            (userNotification.type == UserNotification.INVITATION_EXPIRED
                                    && userNotification.body.inviterName == SessionManager.instance.getAccountInfo().userName)
                                    || userNotification.type == UserNotification.INVITATION_REJECTED
                                    ? userNotification.body.inviteeName : userNotification.body.inviterName;
                    socialServiceProxy.getUserInfoByName([senderName], function(users:ArrayCollection):void {
                        var avatar:ByteArray = null;
                        if (users.length > 0) {
                            var user:UserDto = UserDto(users.getItemAt(0));
                            if (user.profile != null) {
                                avatar = user.profile.avatar;
                            }
                        }
                        sendNotification(Notifications.SHOW_POPUP,
                                new Message(getFromMessageMapping(userNotification), MessageSeverity.NOTIFICATION, avatar, senderName, [senderName]));
                        sendNotification(Notifications.REFRESH_CURRENT_PAGE);
                    });
                } else if (userNotification.type == UserNotification.GAME_INTERRUPTED) {
                    gameEvent = GameEventDto(userNotification.body);
                    var isMe:Boolean = gameEvent.userName == SessionManager.instance.getAccountInfo().userName;
                    sendNotification(Notifications.SHOW_POPUP,
                            new Message(isMe ? MessageResources.GAME_INTERRUPTED_ME : MessageResources.GAME_INTERRUPTED,
                                    MessageSeverity.INFO, null, null, isMe ? [gameEvent.movesQuantity, 72] : [gameEvent.movesQuantity, gameEvent.userName, 72]));
                    sendNotification(Notifications.GAME_INTERRUPTED, gameEvent.gameId);
                    sendNotification(Notifications.REFRESH_CURRENT_PAGE);
                /*} else if (userNotification.type == UserNotification.INVITATION_STATUS_CHANGED) {
                    sendNotification(Notifications.REFRESH_CURRENT_PAGE);*/
                } else if (userNotification.type == UserNotification.GAME_CANCELED) {
                    gameEvent = GameEventDto(userNotification.body);
                    if (gameEvent.offer) {
                        sendNotification(Notifications.SHOW_POPUP, new Message(MessageResources.GAME_OFFER_CANCELED, MessageSeverity.INFO, null, null,
                                [gameEvent.movesQuantity]));
                    }
                    sendNotification(Notifications.REFRESH_CURRENT_PAGE);
                } else if (userNotification.type == UserNotification.FRIEND_REQUEST_RECEIVED
                        || userNotification.type == UserNotification.FRIEND_REQUEST_STATUS_CHANGED) {
                    sendNotification(BusinessActions.GET_FRIENDS);
                    sendNotification(BusinessActions.GET_SUMMARY);
                } else {
                    sendNotification(Notifications.SHOW_POPUP, new Message(MessageResources.ERROR_MESSAGE, MessageSeverity.ERROR, null, null,
                        [userNotification.type]));
                }
            } else {
                sendNotification(Notifications.SHOW_POPUP, new Message(MessageResources.ERROR_MESSAGE, MessageSeverity.ERROR, null, null,
                    ["Invalid type of notification body"]));
            }
        }

        private function getFromMessageMapping(userNotification:UserNotification):String {
            if (messageMapping[userNotification.type] != null) {
                return messageMapping[userNotification.type];
            } else if (userNotification.type == UserNotification.INVITATION_EXPIRED) {
                var invitation:InvitationDto = InvitationDto(userNotification.body);
                if (SessionManager.instance.getAccountInfo().userName == invitation.inviterName) {
                    return MessageResources.INVITATION_EXPIRED_INVITER;
                } else {
                    return MessageResources.INVITATION_EXPIRED_INVITEE;
                }
            } else {
                return null;
            }
        }

        public function transformToChatMessage(incomingChatMessages:ArrayCollection, callback:Function):void {
            var userNames:ArrayCollection = new ArrayCollection();
            for each (var incomingChatMessage:IncomingChatMessageDto in incomingChatMessages) {
                var recipientNames:Array = incomingChatMessage.recipientNames;
                userNames.addItem(incomingChatMessage.senderName);
                for each (var name:String in recipientNames) {
                    if (name != SessionManager.instance.getAccountInfo().userName && name != incomingChatMessage.senderName) {
                        userNames.addItem(name);
                    }
                }
            }

            var userFilter:UserFilterDto = new UserFilterDto();
            userFilter.userNames = userNames.toArray();
            socialServiceProxy.getUsers(userFilter, function(result:UsersList):void {
                var chatMessages:ArrayCollection = new ArrayCollection();
                for each (var incomingChatMessage:IncomingChatMessageDto in incomingChatMessages) {
                    var sender:UserDto;
                    var participants:ArrayCollection = new ArrayCollection();
                    for each (var userDto:UserDto in result.users) {
                        if (userDto.userName == incomingChatMessage.senderName){
                            sender = userDto;
                        }
                        for each (var name:String in incomingChatMessage.recipientNames) {
                            if (name == userDto.userName && name != incomingChatMessage.senderName) {
                                participants.addItem(userDto);
                            }
                        }
                    }
                    participants.addItem(sender);
                    var chatMessage:ChatMessage = new ChatMessage(incomingChatMessage.chatMessageId, sender, participants, incomingChatMessage.message,
                            incomingChatMessage.sent, incomingChatMessage.unread);
                    chatMessages.addItem(chatMessage);
                }
                callback(chatMessages);
            });
        }

        private function get socialServiceProxy():SocialServiceProxy {
            return SocialServiceProxy(facade.retrieveProxy(SocialServiceProxy.NAME));
        }
    }
}
