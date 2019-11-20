package com.stockholdergame.client.mvc.mediator {

    import com.stockholdergame.client.model.dto.account.MyAccountDto;
    import com.stockholdergame.client.model.dto.account.UsersList;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.components.dialog.ChangePasswordDialog;
    import com.stockholdergame.client.ui.components.page.ProfilePage;
    import com.stockholdergame.client.ui.components.page.SearchUserPage;
    import com.stockholdergame.client.ui.components.page.pageClasses.SearchUserPageData;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.ui.events.BusinessEvent;

    import mx.managers.PopUpManager;

    public class ProfilePageMediator extends ProxyAwareMediator {

        public static const NAME:String = "com.stockholdergame.client.mvc.mediator.ProfilePageMediator";

        private var changePasswordDialog:ChangePasswordDialog;

        public function ProfilePageMediator(viewComponent:ProfilePage) {
            super(NAME, viewComponent);
            registerNotificationHandler(Notifications.ACCOUNT_DATA_REFRESHED, handleAccountDataRefreshed);
            registerNotificationHandler(Notifications.FRIENDS_LIST_REFRESHED, handleFriendsListLoaded);
            registerNotificationHandler(Notifications.FRIEND_REQUESTS_LIST_REFRESHED, handleFriendRequestsListLoaded);
            registerNotificationHandler(Notifications.USER_NAME_EXISTENCE_CHECKED, handleUserAlreadyExists);
            registerNotificationHandler(Notifications.EMAIL_USAGE_CHECKED, handleEmailUsed);
            registerNotificationHandler(Notifications.ACCOUNT_STATUS_CHANGED, handleAccountStatusChanged);
            registerNotificationHandler(Notifications.USER_NAME_CHANGED, handleUserNameChanged);
            registerNotificationHandler(Notifications.EMAIL_CHANGED, handleEmailChanged);
            registerNotificationHandler(Notifications.PROFILE_DATA_SAVED, handleProfileDataSaved);
            registerNotificationHandler(Notifications.REFRESH_PROFILE_PAGE, handleRefreshProfilePage);
        }

        override public function onRegister():void {
            registerAction(BusinessActions.GET_FRIENDS);
            registerAction(BusinessActions.GET_FRIEND_REQUESTS);
            registerAction(BusinessActions.REMOVE_ACCOUNT);
            registerAction(BusinessActions.RESTORE_ACCOUNT);
            registerAction(BusinessActions.CHANGE_FRIEND_REQUEST_STATUS);
            registerAction(BusinessActions.CANCEL_FRIEND_REQUEST);
            registerAction(BusinessActions.CHANGE_LANGUAGE);
            registerAction(BusinessActions.UPDATE_AVATAR);
            registerAction(BusinessActions.CHECK_USER_NAME_EXISTENCE);
            registerAction(BusinessActions.CHANGE_USER_NAME);
            registerAction(BusinessActions.CHECK_EMAIL_USAGE);
            registerAction(BusinessActions.CHANGE_EMAIL);
            registerAction(BusinessActions.SAVE_PROFILE);
            registerAction(BusinessActions.SHOW_SEND_MESSAGE_DIALOG);
            registerAction(BusinessActions.SHOW_CONFIRMATION_PAGE);
            addEventListener(BusinessActions.REFRESH_ACCOUNT_DATA, handleRefreshAccountData);
            addEventListener(BusinessActions.SHOW_CHANGE_PASSWORD_DIALOG, onShowChangePasswordDialog);
            addEventListener(BusinessActions.SHOW_SEARCH_USER_DIALOG, onShowSearchUserDialog);
        }

        private function onShowSearchUserDialog(event:BusinessEvent):void {
            sendNotification(Notifications.SHOW_SEARCH_USER_PAGE, new SearchUserPageData(SearchUserPage.SEARCH_STATE, null));
        }

        private function handleUserAlreadyExists(exists:Boolean):void {
            profilePage.changeUserName.markContentAsWrong(exists);
        }

        private function handleUserNameChanged():void {
            profilePage.changeUserName.commitValue();
        }

        private function handleEmailChanged():void {
            profilePage.changeEmail.commitValue();
        }

        private function handleEmailUsed(used:Boolean):void {
            profilePage.changeEmail.markContentAsWrong(used);
        }

        private function handleProfileDataSaved():void {
            profilePage.currentState = "";
        }

        private function handleRefreshProfilePage():void {
            profilePage.refreshData();
        }

        private function onShowChangePasswordDialog(event:BusinessEvent):void {
            if (changePasswordDialog == null) {
                changePasswordDialog = new ChangePasswordDialog();
                registerAction(BusinessActions.CHANGE_PASSWORD, null, changePasswordDialog);
            }
            PopUpManager.addPopUp(changePasswordDialog, profilePage, true);
            PopUpManager.centerPopUp(changePasswordDialog);
        }

        private function handleAccountDataRefreshed(account:MyAccountDto):void {
            profilePage.accountInfo = account;
        }

        private function handleRefreshAccountData(event:BusinessEvent):void {
            handleAccountDataRefreshed(sessionManager.getAccountInfo());
        }

        private function handleFriendsListLoaded(usersList:UsersList):void {
            profilePage.friendsPanel.friends = usersList;
            sessionManager.setFriends(usersList.users);
        }

        private function handleFriendRequestsListLoaded(usersList:UsersList):void {
            profilePage.friendsPanel.friendRequests = usersList;
        }

        private function handleAccountStatusChanged(status:String):void {
            if (profilePage.friendsPanel != null) {
                profilePage.friendsPanel.validateNow();
            }
        }

        private function get profilePage():ProfilePage {
            return viewComponent as ProfilePage;
        }
    }
}
