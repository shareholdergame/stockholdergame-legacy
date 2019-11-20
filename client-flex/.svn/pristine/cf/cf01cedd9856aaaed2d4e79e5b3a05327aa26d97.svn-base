package com.stockholdergame.client.mvc.mediator {
    import com.stockholdergame.client.model.dto.account.UsersList;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.components.page.SearchUserPage;
    import com.stockholdergame.client.ui.events.BusinessActions;

    public class SearchUserPageMediator extends ProxyAwareMediator {

        public static const NAME:String = "com.stockholdergame.client.mvc.mediator.SearchUserPageMediator";

        public function SearchUserPageMediator(viewComponent:SearchUserPage) {
            super(NAME, viewComponent);
            registerNotificationHandler(Notifications.USERS_LIST_LOADED, handleUsersListLoaded);
            registerNotificationHandler(Notifications.FRIEND_REQUEST_SENT, handleFriendRequestSent);
        }

        override public function onRegister():void {
            registerAction(BusinessActions.FILTER_USERS);
            registerAction(BusinessActions.SEND_FRIEND_REQUEST);
            registerAction(BusinessActions.CHANGE_FRIEND_REQUEST_STATUS);
            registerAction(BusinessActions.EDIT_INVITATIONS);
            registerAction(BusinessActions.INITIATE_GAME);
            registerAction(BusinessActions.SHOW_SEND_MESSAGE_DIALOG);
            registerAction(BusinessActions.SHOW_INVITATIONS_PAGE);
            //registerAction(BusinessActions.CANCEL_GAME);
        }

        private function handleUsersListLoaded(usersList:UsersList):void {
            searchUserPage.usersList.dataProvider = usersList.users;
            searchUserPage.paginationBar.totalItems = usersList.totalCount;
        }

        private function handleFriendRequestSent(userName:String):void {
            if (searchUserPage.visible && searchUserPage.currentState == SearchUserPage.SEARCH_STATE) {
                searchUserPage.markUserAsFriendRequestee(userName);
            }
        }

        private function get searchUserPage():SearchUserPage {
            return viewComponent as SearchUserPage;
        }
    }
}
