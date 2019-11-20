package com.stockholdergame.client.mvc.mediator {
    import com.stockholdergame.client.model.dto.ProfileDto;
    import com.stockholdergame.client.model.dto.account.UserDto;
    import com.stockholdergame.client.model.dto.game.CompetitorDto;
    import com.stockholdergame.client.model.dto.game.GameDto;
    import com.stockholdergame.client.model.dto.game.GameFilterDto;
    import com.stockholdergame.client.model.dto.game.GameFilterDto;
    import com.stockholdergame.client.model.dto.game.InvitationDto;
    import com.stockholdergame.client.model.dto.game.lite.CompetitorLiteDto;
    import com.stockholdergame.client.model.dto.game.lite.GameLiteDto;
    import com.stockholdergame.client.model.dto.game.lite.GamesList;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.components.list.listClasses.PaginationData;
    import com.stockholdergame.client.ui.components.page.InvitationsPage;
    import com.stockholdergame.client.ui.components.page.SearchUserPage;
    import com.stockholdergame.client.ui.components.page.pageClasses.SearchUserPageData;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.ui.events.BusinessEvent;

    import mx.collections.ArrayCollection;

    public class InvitationsPageMediator extends ProxyAwareMediator {

        public static const NAME:String = "com.stockholdergame.client.mvc.mediator.InvitationsPageMediator";

        public function InvitationsPageMediator(viewComponent:InvitationsPage) {
            super(NAME, viewComponent);
            registerNotificationHandler(Notifications.INVITATIONS_LOADED, handleOpenGamesInvitedLoaded);
            registerNotificationHandler(Notifications.INVITATIONS_OUTGOING_LOADED, handleInvitationsOutgoingLoaded);
            registerNotificationHandler(Notifications.INVITATION_SENT, handleInvitationSent);
            registerNotificationHandler(Notifications.INVITATION_STATUS_CHANGED, handleInvitationStatusChanged);
            registerNotificationHandler(Notifications.ACCOUNT_STATUS_CHANGED, handleAccountStatusChanged);
        }

        override public function onRegister():void {
            registerAction(BusinessActions.CANCEL_GAME);
            registerAction(BusinessActions.JOIN_TO_GAME);
            registerAction(BusinessActions.CHANGE_INVITATION_STATUS);
            registerAction(BusinessActions.SHOW_NEW_GAME_PAGE);
            addEventListener(BusinessActions.SHOW_INVITE_USER_DIALOG, onShowInviteUserDialog);
            addEventListener(BusinessActions.REFRESH_PAGE_DATA, onRefreshPageData);
        }

        private function onRefreshPageData(event:BusinessEvent):void {
            sendNotification(GameFilterDto(event.data).notInitiator
                    ? BusinessActions.GET_INVITATIONS_INCOMING : BusinessActions.GET_INVITATIONS_OUTGOING, event.data);
        }

        private function onShowInviteUserDialog(event:BusinessEvent):void {
            sendNotification(Notifications.SHOW_SEARCH_USER_PAGE, new SearchUserPageData(SearchUserPage.INVITE_STATE, GameLiteDto(event.data)));
        }

        private function handleOpenGamesInvitedLoaded(list:GamesList):void {
            invitationsPage.invitationsIncoming = list;
        }

        private function handleInvitationsOutgoingLoaded(list:GamesList):void {
            invitationsPage.invitationsOutgoing = list;
        }

        private function handleInvitationSent():void {
            invitationsPage.refreshData();
        }

        private function handleInvitationStatusChanged():void {
            invitationsPage.refreshData();
        }

        private function handleAccountStatusChanged(status:String):void {
            invitationsPage.redraw();
        }

        private function get invitationsPage():InvitationsPage {
            return viewComponent as InvitationsPage;
        }
    }
}
