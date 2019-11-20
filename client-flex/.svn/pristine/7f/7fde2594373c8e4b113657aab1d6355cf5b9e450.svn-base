package com.stockholdergame.client.mvc.mediator {
    import com.stockholdergame.client.model.dto.game.TotalScoreDto;
    import com.stockholdergame.client.model.dto.game.lite.GamesList;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.components.page.StatisticsPage;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.ui.events.BusinessEvent;

    public class StatisticsPageMediator extends AbstractMediator {

        public static const NAME:String = "com.stockholdergame.client.mvc.mediator.StatisticsPageMediator";

        public function StatisticsPageMediator(viewComponent:StatisticsPage) {
            super(NAME, viewComponent);
            registerNotificationHandler(Notifications.FINISHED_GAMES_LOADED, handleFinishedGamesLoaded);
            registerNotificationHandler(Notifications.SCORES_LOADED, handleScoresLoaded);
        }

        private function handleScoresLoaded(totalScore:TotalScoreDto):void {
            statisticsPage.totalScore = totalScore;
        }

        override public function onRegister():void {
            registerAction(BusinessActions.GET_SCORES);
            registerAction(BusinessActions.GET_FINISHED_GAMES);
            registerAction(BusinessActions.SHOW_GAME_CHAT_WINDOW);
            addEventListener(BusinessActions.VIEW_GAME, onViewGame);
        }

        private function handleFinishedGamesLoaded(list:GamesList):void {
            statisticsPage.finishedGames = list;
        }

        private function onViewGame(event:BusinessEvent):void {
            var gameId:Number = Number(event.data);
            if (!sessionManager.isGameLoaded(gameId)) {
                sendNotification(BusinessActions.LOAD_GAME, gameId);
            } else {
                sendNotification(Notifications.GAME_LOADED, sessionManager.getGame(gameId));
            }
        }

        private function get statisticsPage():StatisticsPage {
            return StatisticsPage(viewComponent);
        }
    }
}
