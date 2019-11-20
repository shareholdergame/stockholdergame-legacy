package com.stockholdergame.client.mvc.mediator {

    import com.stockholdergame.client.model.dto.ServerStatisticsDto;
    import com.stockholdergame.client.model.dto.game.GameFilterDto;
    import com.stockholdergame.client.model.dto.game.GamerActivitySummary;
    import com.stockholdergame.client.model.dto.game.ScoreFilterDto;
    import com.stockholdergame.client.model.dto.game.TotalScoreDto;
    import com.stockholdergame.client.model.dto.game.UserStatisticsFilterDto;
    import com.stockholdergame.client.model.dto.game.UserStatisticsList;
    import com.stockholdergame.client.model.dto.game.lite.GameLiteDto;
    import com.stockholdergame.client.model.dto.game.lite.GamesList;
    import com.stockholdergame.client.model.session.SessionManager;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.components.page.HomePage;
    import com.stockholdergame.client.ui.components.page.SearchUserPage;
    import com.stockholdergame.client.ui.components.page.pageClasses.SearchUserPageData;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.ui.events.BusinessEvent;

    import mx.collections.ArrayCollection;

    public class HomePageMediator extends ProxyAwareMediator {

        public static const NAME:String = "com.stockholdergame.client.mvc.mediator.HomePageMediator";

        public function HomePageMediator(viewComponent:HomePage) {
            super(NAME, viewComponent);
            registerNotificationHandler(Notifications.SUMMARY_LOADED, handleGamerActivityLoaded);
            registerNotificationHandler(Notifications.SERVER_STATISTICS_LOADED, handleServerStatisticsLoaded);
            registerNotificationHandler(Notifications.TOP10_LOADED, handleTop10Loaded);
            registerNotificationHandler(Notifications.GAME_OFFERS_LOADED, handleOpenGamesLoaded);
            registerNotificationHandler(Notifications.INVITATIONS_FOR_HOME_PAGE_LOADED, handleInvitationsLoaded);
            registerNotificationHandler(Notifications.GAMES_IN_PROGRESS_FOR_HOME_PAGE_LOADED, handleGamesInProgressLoaded);
            registerNotificationHandler(Notifications.TOTAL_SCORE_LOADED, handleTotalScoreLoaded);
            registerNotificationHandler(Notifications.FRIEND_REQUESTS_HOME_PAGE_LIST_REFRESHED, handleFriendRequestsListRefreshed);
        }

        override public function onRegister():void {
            addEventListener(BusinessActions.SHOW_CONFIRMATION_PAGE, onShowConfirmationDialog);
            addEventListener(BusinessActions.REFRESH_PAGE_DATA, onRefreshPageData);
            addEventListener(BusinessActions.SHOW_INVITE_USER_DIALOG, onShowInviteUserDialog);

            registerAction(BusinessActions.SHOW_GAMES_IN_PROGRESS_PAGE);
            registerAction(BusinessActions.SHOW_INVITATIONS_PAGE);
            registerAction(BusinessActions.SHOW_RULES_PAGE);
            registerAction(BusinessActions.SHOW_STATISTICS_PAGE);
            registerAction(BusinessActions.SHOW_PROFILE_PAGE);
            registerAction(BusinessActions.SHOW_NEW_GAME_PAGE);
            registerAction(BusinessActions.SHOW_RATING_PAGE);
            registerAction(BusinessActions.JOIN_TO_GAME);
            registerAction(BusinessActions.CHANGE_INVITATION_STATUS);
            registerAction(BusinessActions.CANCEL_GAME);
            registerAction(BusinessActions.PLAY_GAME);
            registerAction(BusinessActions.SHOW_GAME_CHAT_WINDOW);
        }

        private function onRefreshPageData(event:BusinessEvent):void {
            sendNotification(BusinessActions.GET_SUMMARY);
            sendNotification(BusinessActions.GET_SERVER_STATISTICS);
            var filter:UserStatisticsFilterDto = new UserStatisticsFilterDto();
            filter.showTop10 = true;
            sendNotification(BusinessActions.GET_TOP10, filter);
        }

        private function handleServerStatisticsLoaded(serverStatisctics:ServerStatisticsDto):void {
            homePage.gameOffersBox.serverStatistics = serverStatisctics;
        }

        private function handleTop10Loaded(top10:UserStatisticsList):void {
            homePage.gameOffersBox.userStatisticsListData = top10;
        }

        private function onShowConfirmationDialog(event:BusinessEvent):void {
            sendNotification(BusinessActions.SHOW_CONFIRMATION_PAGE, homePage.accountInfo.accountOperations);
        }

        private function onShowInviteUserDialog(event:BusinessEvent):void {
            sendNotification(Notifications.SHOW_SEARCH_USER_PAGE, new SearchUserPageData(SearchUserPage.INVITE_STATE, GameLiteDto(event.data)));
        }

        private function handleGamerActivityLoaded(summary:GamerActivitySummary):void {
            homePage.summary = summary;
            homePage.accountInfo = SessionManager.instance.getAccountInfo();
            homePage.greetingBox.activationReminderVisibility = SessionManager.instance.getAccountInfo().hasActivationOperation();

            refreshInvitationsList();
            refreshGamesInProgressList();
            refreshTotalScore();
            //refreshGameOffersList();
            refreshFriendRequestsList();
        }

        private function refreshFriendRequestsList():void {
            sendNotification(BusinessActions.GET_FRIEND_REQUESTS_FOR_HOME_PAGE);
        }

        private function refreshTotalScore():void {
            var scoreFilter:ScoreFilterDto = new ScoreFilterDto();
            scoreFilter.totalScoreOnly = true;
            sendNotification(BusinessActions.GET_TOTAL_SCORE, scoreFilter);
        }

        /*private function refreshGameOffersList():void {
            var gameFilter:GameFilterDto = new GameFilterDto();
            gameFilter.offset = 0;
            gameFilter.maxResults = 10;
            sendNotification(BusinessActions.GET_GAME_OFFERS, gameFilter);
        }*/

        private function refreshInvitationsList():void {
            var gameFilter:GameFilterDto = new GameFilterDto();
            gameFilter.offset = 0;
            gameFilter.maxResults = 2;
            gameFilter.offer = false;
            gameFilter.initiator = true;
            gameFilter.notInitiator = true;
            //gameFilter.smallAvatar = true;
            sendNotification(BusinessActions.GET_INVITATIONS_FOR_HOME_PAGE, gameFilter);
        }

        private function refreshGamesInProgressList():void {
            var gameFilter:GameFilterDto = new GameFilterDto();
            gameFilter.offset = 0;
            gameFilter.maxResults = 2;
            gameFilter.smallAvatar = true;
            sendNotification(BusinessActions.GET_GAMES_IN_PROGRESS_FOR_HOME_PAGE, gameFilter);
        }

        private function handleOpenGamesLoaded(list:GamesList):void {
            homePage.gameOffersBox.gameOffersList = list;
        }

        private function handleInvitationsLoaded(list:GamesList):void {
            homePage.invitationsBox.invitationsList = list;
        }

        private function handleGamesInProgressLoaded(list:GamesList):void {
            homePage.gamesInProgressBox.gamesInProgressList = list;
        }

        private function handleTotalScoreLoaded(totalScore:TotalScoreDto):void {
            homePage.statisticsBox.totalScore = totalScore;
        }

        private function handleFriendRequestsListRefreshed(users:ArrayCollection):void {
            homePage.profileBox.friendRequests = users;
        }

        private function get homePage():HomePage {
            return viewComponent as HomePage;
        }
    }
}
