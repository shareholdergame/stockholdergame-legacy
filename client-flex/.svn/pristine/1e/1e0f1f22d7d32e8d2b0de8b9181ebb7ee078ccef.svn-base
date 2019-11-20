package com.stockholdergame.client.mvc.mediator {

    import com.stockholdergame.client.model.dto.account.UserDto;
    import com.stockholdergame.client.model.dto.game.CompetitorDto;
    import com.stockholdergame.client.model.dto.game.GameDto;
    import com.stockholdergame.client.model.dto.game.GameEventDto;
    import com.stockholdergame.client.model.dto.game.event.UserNotification;
    import com.stockholdergame.client.model.session.SessionManager;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.MessageResources;
    import com.stockholdergame.client.ui.components.game.GameEventPopupBox;
    import com.stockholdergame.client.ui.components.page.AuthenticatedPageContainer;
    import com.stockholdergame.client.ui.components.page.FinishedGamePage;
    import com.stockholdergame.client.ui.components.page.GamePage;
    import com.stockholdergame.client.ui.components.page.IGamePage;
    import com.stockholdergame.client.ui.components.page.IRefreshablePage;
    import com.stockholdergame.client.ui.components.page.NewGamePage;
    import com.stockholdergame.client.ui.components.page.ProfilePage;
    import com.stockholdergame.client.ui.components.page.pageClasses.SearchUserPageData;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.ui.events.BusinessEvent;
    import com.stockholdergame.client.ui.message.Message;
    import com.stockholdergame.client.ui.message.MessageSeverity;

    import flash.display.DisplayObject;
    import flash.utils.ByteArray;

    import mx.collections.ArrayCollection;
    import mx.core.Container;
    import mx.managers.PopUpManager;

    public class AuthenticatedPageContainerMediator extends ProxyAwareMediator {

        public static const NAME:String = "com.stockholdergame.client.mvc.mediator.AuthenticatedPageContainerMediator";

        private var gameEventPopup:GameEventPopupBox;

        private var lastSelectedPageIndex:int;

        public function AuthenticatedPageContainerMediator(viewComponent:AuthenticatedPageContainer) {
            super(NAME, viewComponent);
            registerNotificationHandler(BusinessActions.SHOW_HOME_PAGE, handleShowHomePage);
            registerNotificationHandler(BusinessActions.SHOW_GAMES_IN_PROGRESS_PAGE, handleShowGamesInProgressPage);
            registerNotificationHandler(BusinessActions.SHOW_INVITATIONS_PAGE, handleShowInvitationsPage);
            registerNotificationHandler(BusinessActions.SHOW_NEW_GAME_PAGE, handleNewGamePage);
            registerNotificationHandler(BusinessActions.SHOW_RATING_PAGE, handleShowRatingPage);
            registerNotificationHandler(BusinessActions.SHOW_STATISTICS_PAGE, handleShowStatisticsPage);
            registerNotificationHandler(BusinessActions.SHOW_PROFILE_PAGE, handleShowProfilePage);
            registerNotificationHandler(Notifications.SHOW_SEARCH_USER_PAGE, handleShowSearchUserPage);
            registerNotificationHandler(BusinessActions.PLAY_GAME, handlePlayGame);
            registerNotificationHandler(Notifications.INCREASE_NEW_MESSAGES_COUNTER, handleIncreaseNewMessagesCounter);
            registerNotificationHandler(Notifications.SET_ZERO_MESSAGES_COUNTER, handleSetZeroMessagesCounter);
            registerNotificationHandler(Notifications.SESSION_INITIALIZED, handleSessionInitialized);
            registerNotificationHandler(Notifications.GAME_LOADED, handleGameLoaded);
            registerNotificationHandler(Notifications.MOVE_DONE, handleMoveDone);
            registerNotificationHandler(Notifications.MOVE_DONE_NOTIFICATION, handleMoveDoneNotification);
            registerNotificationHandler(Notifications.GAME_FINISHED_NOTIFICATION, handleMoveDoneNotification);
            registerNotificationHandler(Notifications.GAME_INTERRUPTED, handleGameInterrupted);
            registerNotificationHandler(Notifications.USER_JOINED, handleUserJoined);
            registerNotificationHandler(Notifications.REFRESH_CURRENT_PAGE, handleRefreshCurrentPage);
            registerNotificationHandler(Notifications.USER_LOGGED_OUT, handleUserLoggedOut);
        }

        private function handleUserLoggedOut():void {
            authenticatedPageContainer.newGamePage.stopRefreshTimer();
            var pages:Array = authenticatedPageContainer.getChildren();
            for each (var displayObject:DisplayObject in pages) {
                if (displayObject is IRefreshablePage) {
                    IRefreshablePage(displayObject).clearData();
                }
            }
        }

        private function handleUserJoined(gameEvent:GameEventDto):void {
            var selectedPage:Container = authenticatedPageContainer.pagesContainer.selectedChild;
            if (gameEvent.offer && selectedPage is NewGamePage && gameEvent.gameStatus == GameDto.RUNNING) {
                sendNotification(BusinessActions.LOAD_GAME, gameEvent.gameId);
            } else {
                socialServiceProxy.getUserInfoByName([gameEvent.userName], function(users:ArrayCollection):void {
                    var avatar:ByteArray = null;
                    if (users.length > 0) {
                        var user:UserDto = UserDto(users.getItemAt(0));
                        if (user.profile != null) {
                            avatar = user.profile.avatar;
                        }
                    }
                    sendNotification(Notifications.SHOW_POPUP,
                            new Message(gameEvent.gameStatus == GameDto.RUNNING ? MessageResources.GAME_STARTED : MessageResources.USER_JOINED,
                                    MessageSeverity.NOTIFICATION, avatar, gameEvent.userName, [gameEvent.userName]));
                });
            }
        }

        private function handleGameInterrupted(gameId:Number):void {
            var selectedPage:Container = authenticatedPageContainer.pagesContainer.selectedChild;
            if (selectedPage is GamePage && GamePage(selectedPage).game.id == gameId) {
                authenticatedPageContainer.pagesContainer.selectedIndex = lastSelectedPageIndex;
                authenticatedPageContainer.pagesContainer.removeChild(DisplayObject(selectedPage));
                sessionManager.unloadGame(gameId);
            }
        }

        private function handleMoveDoneNotification(userNotification:UserNotification):void {
            var selectedPage:Container = authenticatedPageContainer.pagesContainer.selectedChild;
            if (selectedPage is GamePage && GamePage(selectedPage).game.id == userNotification.body.gameId) {
                sendNotification(Notifications.PLAY_SOUND,
                        userNotification.type == UserNotification.GAME_FINISHED
                                ? Notifications.GAME_FINISHED_NOTIFICATION : Notifications.MOVE_DONE_NOTIFICATION);
            } else {
                socialServiceProxy.getUserInfoByName([userNotification.body.userName], function(users:ArrayCollection):void {
                    var avatar:ByteArray = null;
                    var userInfo:UserDto;
                    if (users.length > 0) {
                        userInfo = UserDto(users.getItemAt(0));
                        if (userInfo.profile != null) {
                            avatar = userInfo.profile.avatar;
                        }
                    }
                    sendNotification(Notifications.SHOW_POPUP, new Message(
                            userNotification.type == UserNotification.GAME_FINISHED ? MessageResources.GAME_FINISHED : MessageResources.MOVE_COMPLETE,
                            MessageSeverity.NOTIFICATION, avatar, userNotification.body.userName,
                            [userNotification.body.userName]));
                });
            }
            if ((selectedPage is GamePage &&  GamePage(selectedPage).game.id == userNotification.body.gameId)) {
                GamePage(selectedPage).refreshData();
            } else if (!(selectedPage is IGamePage)) {
                sendNotification(Notifications.REFRESH_CURRENT_PAGE);
            }
        }

        private function handleNewGamePage(gameInitiationMethod:String):void {
            authenticatedPageContainer.pagesContainer.selectedChild = authenticatedPageContainer.newGamePage;
            authenticatedPageContainer.newGamePage.startRefreshTimer();
        }

        private function handleShowRatingPage():void {
            authenticatedPageContainer.pagesContainer.selectedChild = authenticatedPageContainer.ratingPage;
        }

        private function handleShowStatisticsPage():void {
            authenticatedPageContainer.pagesContainer.selectedChild = authenticatedPageContainer.myStatisticsPage;
        }

        private function handleSetZeroMessagesCounter():void {
            authenticatedPageContainer.resetNewMessagesCounter();
        }

        private function handleIncreaseNewMessagesCounter():void {
            authenticatedPageContainer.increaseNewMessagesCounter();
            sendNotification(Notifications.PLAY_SOUND, Notifications.INCREASE_NEW_MESSAGES_COUNTER);
        }

        private function handleShowHomePage():void {
            authenticatedPageContainer.pagesContainer.selectedChild = authenticatedPageContainer.homePage;
            authenticatedPageContainer.homePage.refreshData();
        }

        private function handleShowProfilePage():void {
            authenticatedPageContainer.pagesContainer.selectedChild = authenticatedPageContainer.profilePage;
        }

        private function handleShowSearchUserPage(pageData:SearchUserPageData):void {
            authenticatedPageContainer.pagesContainer.selectedChild = authenticatedPageContainer.searchUserPage;
            authenticatedPageContainer.searchUserPage.pageData = pageData;
        }

        private function handleShowGamesInProgressPage():void {
            authenticatedPageContainer.pagesContainer.selectedChild = authenticatedPageContainer.gamesInProgressPage;
        }

        private function handleShowInvitationsPage():void {
            authenticatedPageContainer.pagesContainer.selectedChild = authenticatedPageContainer.invitationsPage;
        }

        private function handleSessionInitialized():void {
            authenticatedPageContainer.userName = SessionManager.instance.getAccountInfo().userName;
        }

        private function handleGameLoaded(game:GameDto):void {
            var selectedPage:Object = authenticatedPageContainer.pagesContainer.selectedChild;
            if (selectedPage is IGamePage) {
                authenticatedPageContainer.pagesContainer.removeChild(Container(selectedPage));
                SessionManager.instance.unloadGame(selectedPage.game.id);
            } else {
                lastSelectedPageIndex = authenticatedPageContainer.pagesContainer.selectedIndex;
            }
            var gamePage:Container = processGameLoadedNotification(game);
            authenticatedPageContainer.pagesContainer.addChild(gamePage);
            authenticatedPageContainer.pagesContainer.selectedChild = gamePage;
            if (game.currentMoveNumber == 1 && game.currentCompetitorNumber == 1) {
                sendNotification(Notifications.PLAY_SOUND, Notifications.GAME_LOADED);
            }
            if (gamePage is GamePage) {
                showEventPopupOnDemand(IGamePage(gamePage));
            }
        }

        private function processGameLoadedNotification(game:GameDto):Container {
            var gamePage:Object = game.gameStatus == GameDto.FINISHED ? new FinishedGamePage() : new GamePage();
            gamePage.label = game.label == null || game.label.length == 0 ? createLabel(game) : game.label;
            gamePage.game = game;
            gamePage.addEventListener(BusinessActions.CLOSE_GAME, onCloseGame);
            gamePage.addEventListener(BusinessActions.SHOW_GAME_CHAT_WINDOW, onShowGameChatWindow);
            gamePage.addEventListener(BusinessActions.SHOW_GAME_EVENT_POPUP, onShowGameEventPopup);
            gamePage.addEventListener(BusinessActions.REFRESH_PAGE_DATA, function(event:BusinessEvent):void {
                sendNotification(BusinessActions.LOAD_GAME, gamePage.game.id);
            });
            gamePage.addEventListener(BusinessActions.LOAD_GAME, function(event:BusinessEvent):void {
                sendNotification(BusinessActions.LOAD_GAME, event.data)
            });
            if (gamePage is GamePage) {
                gamePage.addEventListener(BusinessActions.VIEW_GAME, onViewGame);
                gamePage.addEventListener(BusinessActions.DO_MOVE, onDoMove);
            }
            return Container(gamePage);
        }

        private function onShowGameEventPopup(event:BusinessEvent):void {
            showEventPopupOnDemand(IGamePage(event.data));
        }

        private function onViewGame(event:BusinessEvent):void {
            var gameId:Number = Number(event.data);
            var selectedPage:Container = authenticatedPageContainer.pagesContainer.selectedChild;
            if (selectedPage is IGamePage) {
                authenticatedPageContainer.pagesContainer.selectedIndex = lastSelectedPageIndex;
                authenticatedPageContainer.pagesContainer.removeChild(selectedPage);
                handleGameLoaded(sessionManager.getGame(gameId));
            }
        }

        private function onDoMove(event:BusinessEvent):void {
            sendNotification(BusinessActions.DO_MOVE, event.data);
        }

        private function handleMoveDone(game:GameDto):void {
            var children:Array = authenticatedPageContainer.pagesContainer.getChildren();
            for each (var container:Container in children) {
                if (container is GamePage) {
                    var gamePage:GamePage = GamePage(container);
                    if(gamePage.game.id == game.id) {
                        gamePage.game = game;
                        gamePage.selectCurrentMoverAccount();
                        showEventPopupOnDemand(gamePage);
                    }
                }
            }
        }

        private function handleRefreshCurrentPage():void {
            var selectedPage:Container = authenticatedPageContainer.pagesContainer.selectedChild;
            if (selectedPage is IRefreshablePage && !(selectedPage is ProfilePage) && !(selectedPage is IGamePage)) {
                IRefreshablePage(selectedPage).refreshData();
            }
        }

        private function handlePlayGame(gameId:Number):void {
            if (!sessionManager.isGameLoaded(gameId)) {
                sendNotification(BusinessActions.LOAD_GAME, gameId);
            }
        }

        private function onShowGameChatWindow(event:BusinessEvent):void {
            sendNotification(event.type, event.data);
        }

        private function onCloseGame(event:BusinessEvent):void {
            var target:Object = event.currentTarget;
            authenticatedPageContainer.pagesContainer.selectedIndex = lastSelectedPageIndex;
            authenticatedPageContainer.pagesContainer.removeChild(DisplayObject(target));
            sessionManager.unloadGame(target.game.id);
        }

        private static function createLabel(game:GameDto):String {
            var gamelabel:String = "";
            var competitors:ArrayCollection = game.competitors;
            for each (var competitor:CompetitorDto in competitors) {
                if (gamelabel.length > 0) {
                    gamelabel = gamelabel + " - ";
                }
                gamelabel = gamelabel + competitor.userName;
            }
            return gamelabel + " - " + game.movesQuantity + " Client";
        }

        private function showEventPopupOnDemand(gamePage:IGamePage):void {
            var game:GameDto = gamePage.game;
            if (game.finished) {
                if (gameEventPopup == null) {
                    gameEventPopup = new GameEventPopupBox();
                }
                gameEventPopup.game = game;
                if (!gameEventPopup.isPopUp) {
                    PopUpManager.addPopUp(gameEventPopup, DisplayObject(gamePage), true);
                    PopUpManager.centerPopUp(gameEventPopup);
                    gameEventPopup.currentState =
                            gamePage is FinishedGamePage ? GameEventPopupBox.GAME_SERIES_RESULT_STATE : GameEventPopupBox.GAME_FINAL_RESULT_STATE;
                }
            }
        }

        override public function onRegister():void {
            registerMediatorIfNotExists(HomePageMediator.NAME, authenticatedPageContainer.homePage);
            registerMediatorIfNotExists(NewGamePageMediator.NAME, authenticatedPageContainer.newGamePage);
            registerMediatorIfNotExists(GamesInProgressPageMediator.NAME, authenticatedPageContainer.gamesInProgressPage);
            registerMediatorIfNotExists(InvitationsPageMediator.NAME, authenticatedPageContainer.invitationsPage);
            registerMediatorIfNotExists(RatingPageMediator.NAME, authenticatedPageContainer.ratingPage);
            registerMediatorIfNotExists(StatisticsPageMediator.NAME, authenticatedPageContainer.myStatisticsPage);
            registerMediatorIfNotExists(ProfilePageMediator.NAME, authenticatedPageContainer.profilePage);
            registerMediatorIfNotExists(SearchUserPageMediator.NAME, authenticatedPageContainer.searchUserPage);

            registerAction(BusinessActions.SIGN_OUT);
            registerAction(BusinessActions.SHOW_SEND_MESSAGE_DIALOG);
            registerAction(BusinessActions.SHOW_RULES_PAGE);
            addEventListener(BusinessActions.CHANGE_PAGE, handleChangePage);
        }

        private function handleChangePage(event:BusinessEvent):void {
            var action:String = String(event.data);
            sendNotification(action);
        }

        private function get authenticatedPageContainer():AuthenticatedPageContainer {
            return AuthenticatedPageContainer(viewComponent);
        }
    }
}
