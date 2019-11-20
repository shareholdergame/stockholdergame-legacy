package com.stockholdergame.client.mvc.mediator {

    import com.stockholdergame.client.model.dto.game.GameFilterDto;
    import com.stockholdergame.client.model.dto.game.lite.GamesList;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.components.page.GamesInProgressPage;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.ui.events.BusinessEvent;

    public class GamesInProgressPageMediator extends ProxyAwareMediator {

        public static const NAME:String = "com.stockholdergame.client.mvc.mediator.GamesInProgressPageMediator";

        public function GamesInProgressPageMediator(viewComponent:GamesInProgressPage) {
            super(NAME, viewComponent);
            registerNotificationHandler(Notifications.GAMES_IN_PROGRESS_LOADED, handleGamesInProgressLoaded);
            registerNotificationHandler(Notifications.ACCOUNT_STATUS_CHANGED, handleAccountStatusChanged);
        }

        override public function onRegister():void {
            addEventListener(BusinessActions.REFRESH_PAGE_DATA, onRefreshPageData);
            registerAction(BusinessActions.PLAY_GAME);
            registerAction(BusinessActions.SHOW_GAME_CHAT_WINDOW);
        }

        private function onRefreshPageData(event:BusinessEvent):void {
            sendNotification(BusinessActions.GET_GAMES_IN_PROGRESS, GameFilterDto(event.data));
        }

        private function handleGamesInProgressLoaded(list:GamesList):void {
            gamesInProgressPage.gamesInProgress = list;
        }

        private function handleAccountStatusChanged(status:String):void {
            gamesInProgressPage.redraw();
        }

        private function get gamesInProgressPage():GamesInProgressPage {
            return viewComponent as GamesInProgressPage;
        }
    }
}
