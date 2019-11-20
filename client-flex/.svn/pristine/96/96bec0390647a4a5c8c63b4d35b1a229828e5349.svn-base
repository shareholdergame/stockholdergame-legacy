package com.stockholdergame.client.mvc.mediator {

    import com.stockholdergame.client.ClientApplication;
    import com.stockholdergame.client.model.dto.account.ChatHistoryDto;
    import com.stockholdergame.client.model.dto.account.MyAccountDto;
    import com.stockholdergame.client.model.dto.game.GameDto;
    import com.stockholdergame.client.model.session.SessionManager;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.mvc.proxy.MessagingServiceProxy;
    import com.stockholdergame.client.ui.MessageResources;
    import com.stockholdergame.client.ui.components.PopupMessageBox;
    import com.stockholdergame.client.ui.components.dialog.ConfirmationDialog;
    import com.stockholdergame.client.ui.components.dialog.ChatDialog;
    import com.stockholdergame.client.ui.components.dialog.dialogClasses.ChatMessage;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.ui.message.Message;
    import com.stockholdergame.client.ui.message.MessageSeverity;

    import flash.geom.Point;
    import flash.net.URLRequest;
    import flash.net.navigateToURL;

    import mx.collections.ArrayCollection;
    import mx.core.UIComponent;
    import mx.managers.PopUpManager;

    public class ApplicationMediator extends ProxyAwareMediator {

        public static const NAME:String = "com.stockholdergame.client.mvc.mediator.ApplicationMediator";

        private var confirmationDialog:ConfirmationDialog;

        private var messageInputDialog:ChatDialog;

        private var messageQueue:ArrayCollection = new ArrayCollection();

        public function ApplicationMediator(viewComponent:ClientApplication) {
            super(NAME, viewComponent);
            registerNotificationHandler(Notifications.SHOW_POPUP, handleShowPopup);
            registerNotificationHandler(BusinessActions.SHOW_CONFIRMATION_PAGE, handleShowConfirmationPage);
            registerNotificationHandler(Notifications.AUTHENTICATION_SUCCESSFUL, handleAuthenticationSuccessful);
            registerNotificationHandler(Notifications.USER_LOGGED_OUT, handleUserLoggedOut);
            registerNotificationHandler(BusinessActions.SHOW_SEND_MESSAGE_DIALOG, handleShowSendMessageDialog);
            registerNotificationHandler(Notifications.MESSAGE_RECEIVED, handleMessageReceived);
            registerNotificationHandler(Notifications.ACCOUNT_STATUS_CHANGED, handleAccountStatusChanged);
            registerNotificationHandler(Notifications.UNREAD_MESSAGES_LOADED, handleUnreadMessagesLoaded);
            registerNotificationHandler(BusinessActions.SHOW_RULES_PAGE, handleShowRulesPage);
        }

        override public function onRegister():void {
            registerMediatorIfNotExists(LoginPageMediator.NAME, application.loginPage);
        }

        private function handleShowSendMessageDialog(participants:ArrayCollection):void {
            showMessageDialog();
            if (messageQueue.length > 0) {
                var ids:ArrayCollection = new ArrayCollection();
                for each (var chatMessage:ChatMessage in messageQueue) {
                    messageInputDialog.showIncomingMessage(chatMessage);
                    if (chatMessage.unread) {
                        ids.addItem(chatMessage.chatMessageId);
                    }
                }
                messageQueue.removeAll();
                sendNotification(Notifications.SET_ZERO_MESSAGES_COUNTER);
                if (ids.length > 0) {
                    sendNotification(BusinessActions.MARK_CHAT_MESSAGES_AS_READ, ids);
                }
            }
            if (participants != null) {
                messageInputDialog.startChat(participants);
            } else {
                messageInputDialog.selectLastChat();
            }
        }

        private function showMessageDialog():void {
            createMessageDialogIfNotExists();
            if (messageInputDialog.isPopUp) {
                PopUpManager.removePopUp(messageInputDialog);
            } else {
                showDialog(messageInputDialog, false, application);
            }
        }

        private function createMessageDialogIfNotExists():void {
            if (messageInputDialog == null) {
                messageInputDialog = new ChatDialog();
                registerMediatorIfNotExists(ChatDialogMediator.NAME, messageInputDialog);
                sendNotification(BusinessActions.GET_CHATS);
            }
        }

        private function handleShowPopup(msg:Message):void {
            //ErrorsHolder.instance.putError(new ApplicationError(new Date(), msg.messageText)); //todo - save in error stack
            var popupMessageBox:PopupMessageBox = PopupMessageBox(PopUpManager.createPopUp(application, PopupMessageBox));
            popupMessageBox.message = msg;
            PopUpManager.bringToFront(popupMessageBox);
            if (msg.messageSeverity != MessageSeverity.NOTIFICATION) {
                PopUpManager.centerPopUp(popupMessageBox);
            }
        }

        private function handleShowRulesPage(rulesVersion:String):void {
            var locale:String = application.currentState == ClientApplication.AUTHENTICATED_STATE
                    ? SessionManager.instance.getAccountInfo().locale : application.loginPage.languagesCombo.selectedItem.data;
            var rulesUrl:String = (rulesVersion != null && rulesVersion == GameDto.LEGACY_RULES ? "legacy-rules/" : "rules/") + locale + "/index.html";
            navigateToURL(new URLRequest(rulesUrl), "_blank");
        }

        private function handleAuthenticationSuccessful():void {
            sendNotification(BusinessActions.INIT_USER_SESSION);
            application.currentState = ClientApplication.AUTHENTICATED_STATE;
            registerMediatorIfNotExists(AuthenticatedPageContainerMediator.NAME, application.authenticatedPageContainer);
            sendNotification(BusinessActions.SHOW_HOME_PAGE);
            sendNotification(BusinessActions.GET_UNREAD_CHAT_MESSAGES);
        }

        private function handleUserLoggedOut():void {
            application.currentState = ClientApplication.NOT_AUTHENTICATED_STATE;
            if (messageInputDialog != null && messageInputDialog.isPopUp) {
                PopUpManager.removePopUp(messageInputDialog);
            }
            messageInputDialog = null;
            facade.removeMediator(ChatDialogMediator.NAME);
        }

        private function handleShowConfirmationPage():void {
            if (confirmationDialog == null) {
                confirmationDialog = new ConfirmationDialog();
                registerMediatorIfNotExists(ConfirmationDialogMediator.NAME, confirmationDialog);
            }
            showDialog(confirmationDialog, true, application);
            confirmationDialog.accountOperations = sessionManager.getAccountInfo().accountOperations;
        }

        private function handleMessageReceived(chatMessage:ChatMessage):void {
            createMessageDialogIfNotExists();
            if (messageInputDialog.isPopUp) {
                messageInputDialog.showIncomingMessage(chatMessage);
                if (chatMessage.unread) {
                    var ids:ArrayCollection = new ArrayCollection();
                    ids.addItem(chatMessage.chatMessageId);
                    sendNotification(BusinessActions.MARK_CHAT_MESSAGES_AS_READ, ids);
                }
            } else {
                messageQueue.addItem(chatMessage);
                sendNotification(Notifications.INCREASE_NEW_MESSAGES_COUNTER);
                handleShowPopup(new Message(MessageResources.NEW_INCOMING_MESSAGE, MessageSeverity.NOTIFICATION, chatMessage.sender.avatar,
                        chatMessage.sender.userName, [chatMessage.message]));
            }
        }

        private function handleUnreadMessagesLoaded(incomingChatMessages:ArrayCollection):void {
            if (incomingChatMessages.length == 0) {
                return;
            }
            for each (var chatMessage:ChatMessage in incomingChatMessages) {
                messageQueue.addItem(chatMessage);
                sendNotification(Notifications.INCREASE_NEW_MESSAGES_COUNTER);
            }
            handleShowPopup(new Message(MessageResources.UNREAD_MESSAGES_COUNT, MessageSeverity.INFO, null, null, [incomingChatMessages.length]))
        }

        private static function showDialog(dialog:UIComponent, modal:Boolean, parent:UIComponent, point:Point = null):void {
            if (!dialog.isPopUp) {
                PopUpManager.addPopUp(dialog, parent, modal);
                if (point == null) {
                    PopUpManager.centerPopUp(dialog);
                } else {
                    dialog.x = point.x;
                    dialog.y = point.y;
                }
                PopUpManager.bringToFront(dialog);
            }
        }

        private function handleAccountStatusChanged(status:String):void {
            var messagingProxy:MessagingServiceProxy = MessagingServiceProxy(facade.retrieveProxy(MessagingServiceProxy.NAME));
            if (status == MyAccountDto.ACCOUNT_STATUS_REMOVED) {
                messagingProxy.unsubscribe();
            } else {
                messagingProxy.subscribeOnEvents(sessionManager.getAccountInfo().subtopicName);
            }
        }

        private function get application():ClientApplication {
            return viewComponent as ClientApplication;
        }
    }
}
