package com.stockholdergame.client.mvc.mediator {
    import com.stockholdergame.client.model.dto.account.ChatDto;
    import com.stockholdergame.client.model.dto.account.ChatHistoryDto;
    import com.stockholdergame.client.model.dto.account.UserDto;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.components.dialog.ChatDialog;
    import com.stockholdergame.client.ui.events.BusinessActions;

    import mx.collections.ArrayCollection;

    public class ChatDialogMediator extends ProxyAwareMediator {

        public static const NAME:String = "com.stockholdergame.client.mvc.mediator.ChatDialogMediator";

        public function ChatDialogMediator(viewComponent:ChatDialog) {
            super(NAME, viewComponent);
            registerNotificationHandler(Notifications.CHAT_HISTORY_CLEANED, handleChatHistoryCleaned);
            registerNotificationHandler(Notifications.CHATS_LOADED, handleChatsLoaded);
            registerNotificationHandler(Notifications.CHAT_HISTORY_LOADED, handleChatHistoryLoaded);
        }

        private function handleChatsLoaded(chats:ArrayCollection):void {
            for each (var chatDto:ChatDto in chats) {
                var participants:ArrayCollection = new ArrayCollection();
                for each (var name:String in chatDto.recipientNames) {
                    var recipient:UserDto = new UserDto();
                    recipient.userName = name;
                    participants.addItem(recipient);
                }
                messageInputDialog.startChat(participants, false);
            }
        }

        private function handleChatHistoryLoaded(chatHistory:ChatHistoryDto):void {
            messageInputDialog.showHistory(chatHistory);
        }

        private function handleChatHistoryCleaned():void {
            messageInputDialog.cleanCurrentChat();
        }

        override public function onRegister():void {
            registerAction(BusinessActions.SEND_MESSAGE);
            registerAction(BusinessActions.GET_CHAT_HISTORY);
            registerAction(BusinessActions.CLEAR_CHAT_HISTORY);
            registerAction(BusinessActions.MARK_CHAT_MESSAGES_AS_READ);
        }

        private function get messageInputDialog():ChatDialog {
            return ChatDialog(viewComponent);
        }
    }
}
