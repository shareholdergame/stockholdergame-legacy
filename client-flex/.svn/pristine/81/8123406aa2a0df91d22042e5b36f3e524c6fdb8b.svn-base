package com.stockholdergame.client.mvc.controller {
    import com.stockholdergame.client.model.dto.account.ChatHistoryDto;
    import com.stockholdergame.client.model.dto.account.GetChatHistoryDto;
    import com.stockholdergame.client.model.dto.account.SendMessageDto;
    import com.stockholdergame.client.model.dto.account.UserDto;
    import com.stockholdergame.client.model.dto.game.CompetitorDto;
    import com.stockholdergame.client.model.dto.game.GameDto;
    import com.stockholdergame.client.model.dto.game.lite.CompetitorLiteDto;
    import com.stockholdergame.client.model.dto.game.lite.GameLiteDto;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.events.BusinessActions;

    import mx.collections.ArrayCollection;

    public class ChatCommand extends ProxyAwareCommand {

        public function ChatCommand() {
            registerNotificationHandler(BusinessActions.SEND_MESSAGE, handleSendMessage);
            registerNotificationHandler(BusinessActions.GET_CHAT_HISTORY, handleGetChatHistory);
            registerNotificationHandler(BusinessActions.MARK_CHAT_MESSAGES_AS_READ, handleMarkMessagesAsRead);
            registerNotificationHandler(BusinessActions.CLEAR_CHAT_HISTORY, handleClearChatHistory);
            registerNotificationHandler(BusinessActions.GET_UNREAD_CHAT_MESSAGES, handleGetUnreadChatMessages);
            registerNotificationHandler(BusinessActions.SHOW_GAME_CHAT_WINDOW, handleShowGameChatWindow);
            registerNotificationHandler(BusinessActions.GET_CHATS, handleGetChats);
        }

        private function handleGetUnreadChatMessages():void {
            socialServiceProxy.getUnreadChatMessages(getUnreadChatMessagesCallback);
        }

        private function getUnreadChatMessagesCallback(incomingChatMessages:ArrayCollection):void {
            messagingServiceProxy.transformToChatMessage(incomingChatMessages, function(chatMessages:ArrayCollection):void {
                sendNotification(Notifications.UNREAD_MESSAGES_LOADED, chatMessages);
            })
        }

        private function handleClearChatHistory(recipients:Array):void {
            socialServiceProxy.clearChatHistory(recipients, clearChatHistoryCallback);
        }

        private function clearChatHistoryCallback():void {
            sendNotification(Notifications.CHAT_HISTORY_CLEANED);
        }

        private function handleGetChatHistory(getChatHistory:GetChatHistoryDto):void {
            socialServiceProxy.getChatHistory(getChatHistory, getChatHistoryCallback);
        }

        private function handleGetChats():void {
            socialServiceProxy.getChats(getChatsCallback);
        }

        private function getChatsCallback(chats:ArrayCollection):void {
            sendNotification(Notifications.CHATS_LOADED, chats);
        }

        private function getChatHistoryCallback(chatHistory:ChatHistoryDto):void {
            sendNotification(Notifications.CHAT_HISTORY_LOADED, chatHistory);
        }

        private function handleMarkMessagesAsRead(messageIds:ArrayCollection):void {
            socialServiceProxy.markChatMessagesAsRead(messageIds, markMessagesAsReadCallback);
        }

        private function handleShowGameChatWindow(game:Object):void {
            var participants:ArrayCollection = new ArrayCollection();
            var competitors:ArrayCollection = game.competitors;
            if (game is GameDto) {
                for each (var competitor:CompetitorDto in competitors) {
                    if (competitor.userName != sessionManager.getAccountInfo().userName) {
                        var user:UserDto = new UserDto();
                        user.userName = competitor.userName;
                        participants.addItem(user);
                    }
                }
            } else if (game is GameLiteDto) {
                for each (var competitor1:CompetitorLiteDto in competitors) {
                    if (competitor1.userName != sessionManager.getAccountInfo().userName) {
                        var user1:UserDto = new UserDto();
                        user1.userName = competitor1.userName;
                        participants.addItem(user1);
                    }
                }
            } else {
                return;
            }

            sendNotification(BusinessActions.SHOW_SEND_MESSAGE_DIALOG, participants);
        }


        private function markMessagesAsReadCallback():void {
        }

        private function handleSendMessage(sendMessageDto:SendMessageDto):void {
            socialServiceProxy.sendMessage(sendMessageDto, sendMessageCallback);
        }

        private function sendMessageCallback():void {
            sendNotification(Notifications.MESSAGE_SENT);
        }
    }
}
