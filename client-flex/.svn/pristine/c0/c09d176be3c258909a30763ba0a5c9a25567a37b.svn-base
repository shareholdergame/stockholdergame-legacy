package com.stockholdergame.client.mvc.controller {
    import com.stockholdergame.client.model.assembler.ScoreGrouper;
    import com.stockholdergame.client.model.dto.game.GameDto;
    import com.stockholdergame.client.model.dto.game.GameFilterDto;
    import com.stockholdergame.client.model.dto.game.ScoreFilterDto;
    import com.stockholdergame.client.model.dto.game.TotalScoreDto;
    import com.stockholdergame.client.model.dto.game.lite.GamesList;
    import com.stockholdergame.client.model.dto.game.variant.GameVariantDto;
    import com.stockholdergame.client.model.dto.game.variant.GameVariantHolder;
    import com.stockholdergame.client.model.dto.game.variant.GameVariantSummary;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.events.BusinessActions;

    import mx.collections.ArrayCollection;
    import mx.controls.Alert;

    public class GameListCommand extends ProxyAwareCommand {

        public function GameListCommand() {
            registerNotificationHandler(BusinessActions.GET_GAME_VARIANT_SUMMARY, handleGetGameVariantSummary);
            registerNotificationHandler(BusinessActions.GET_OPEN_GAMES, handleGetOpenGames);
            registerNotificationHandler(BusinessActions.GET_GAME_OFFERS, handleGetGameOffers);
            registerNotificationHandler(BusinessActions.GET_ALL_GAME_OFFERS, handleGetAllGameOffers);
            registerNotificationHandler(BusinessActions.GET_GAMES_IN_PROGRESS, handleGamesInProgress);
            registerNotificationHandler(BusinessActions.GET_INVITATIONS_INCOMING, handleGetInvitations);
            registerNotificationHandler(BusinessActions.GET_INVITATIONS_OUTGOING, handleGetInvitationsOutgoing);
            registerNotificationHandler(BusinessActions.GET_INVITATIONS_FOR_HOME_PAGE, handleGetInvitationsForHomePage);
            registerNotificationHandler(BusinessActions.GET_FINISHED_GAMES, handleFinishedGames);
            registerNotificationHandler(BusinessActions.GET_SCORES, handleGetScores);
            registerNotificationHandler(BusinessActions.GET_TOTAL_SCORE, handleGetTotalScore);
            registerNotificationHandler(BusinessActions.GET_GAMES_IN_PROGRESS_FOR_HOME_PAGE, handleGamesInProgressForHomePage);
        }

        private function handleGetScores(scoreFilter:ScoreFilterDto):void {
            gameServiceProxy.getScores(scoreFilter, getScoresCallback);
        }

        private function handleGetTotalScore(scoreFilter:ScoreFilterDto):void {
            gameServiceProxy.getScores(scoreFilter, getTotalScoreCallback);
        }

        private function getTotalScoreCallback(totalScore:TotalScoreDto):void {
            sendNotification(Notifications.TOTAL_SCORE_LOADED, totalScore);
        }

        private function getScoresCallback(totalScore:TotalScoreDto):void {
            totalScore.scores = ScoreGrouper.group(totalScore.scores);
            sendNotification(Notifications.SCORES_LOADED, totalScore);
        }

        private function handleFinishedGames(gameFilterDto:GameFilterDto):void {
            gameFilterDto.gameStatus = GameDto.FINISHED;
            handleGetGames(gameFilterDto, Notifications.FINISHED_GAMES_LOADED);
        }

        private function handleGetInvitations(gameFilterDto:GameFilterDto):void {
            gameFilterDto.gameStatus = GameDto.OPEN;
            handleGetGames(gameFilterDto, Notifications.INVITATIONS_LOADED);
        }

        private function handleGetInvitationsOutgoing(gameFilterDto:GameFilterDto):void {
            gameFilterDto.gameStatus = GameDto.OPEN;
            handleGetGames(gameFilterDto, Notifications.INVITATIONS_OUTGOING_LOADED);
        }

        private function handleGetInvitationsForHomePage(gameFilterDto:GameFilterDto):void {
            gameFilterDto.gameStatus = GameDto.OPEN;
            handleGetGames(gameFilterDto, Notifications.INVITATIONS_FOR_HOME_PAGE_LOADED);
        }

        private function handleGamesInProgressForHomePage(gameFilterDto:GameFilterDto):void {
            gameFilterDto.gameStatus = GameDto.RUNNING;
            handleGetGames(gameFilterDto, Notifications.GAMES_IN_PROGRESS_FOR_HOME_PAGE_LOADED);
        }

        private function handleGamesInProgress(gameFilterDto:GameFilterDto):void {
            gameFilterDto.gameStatus = GameDto.RUNNING;
            handleGetGames(gameFilterDto, Notifications.GAMES_IN_PROGRESS_LOADED);
        }

        private function handleGetGameOffers(gameFilterDto:GameFilterDto):void {
            gameFilterDto.gameStatus = GameDto.OPEN;
            gameFilterDto.offer = true;
            gameFilterDto.initiator = false;
            handleGetGames(gameFilterDto, Notifications.GAME_OFFERS_LOADED);
        }

        private function handleGetAllGameOffers(gameFilterDto:GameFilterDto):void {
            gameFilterDto.gameStatus = GameDto.OPEN;
            gameFilterDto.offer = true;
            gameFilterDto.initiator = true;
            handleGetGames(gameFilterDto, Notifications.ALL_GAME_OFFERS_LOADED);
        }

        private function handleGetGameVariantSummary():void {
            gameServiceProxy.getGameVariantsSummary(getGameVariantsSummaryCallback);
        }

        private function getGameVariantsSummaryCallback(gameVariantsSummaryList:ArrayCollection):void {
            var gameVariants:ArrayCollection = sessionManager.getGameVariants();
            for each (var gameVariantDto:GameVariantDto in gameVariants) {
                var gameVariantSummaryList1:ArrayCollection = new ArrayCollection();
                for each (var gameVariantSummary:GameVariantSummary in gameVariantsSummaryList) {
                    if (gameVariantSummary.gameVariantId == gameVariantDto.id) {
                        gameVariantSummaryList1.addItem(gameVariantSummary);
                    }
                }
                var gameVariantHolder:GameVariantHolder = sessionManager.getGameVariantHolder(gameVariantDto.id);
                gameVariantHolder.gameVariantSummaryList = gameVariantSummaryList1;
            }
            sendNotification(Notifications.GAME_VARIANTS_LOADED, gameVariants);
        }

        private function handleGetOpenGames(gameFilter:GameFilterDto):void {
            handleGetGames(gameFilter, Notifications.OPEN_GAMES_LOADED);
        }

        private function handleGetGames(gameFilter:GameFilterDto, notification:String):void {
            gameServiceProxy.getGames(gameFilter, function(result:GamesList):void {
                sendNotification(notification, result);
            });
        }
    }
}
