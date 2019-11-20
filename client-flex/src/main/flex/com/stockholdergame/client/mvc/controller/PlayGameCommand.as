package com.stockholdergame.client.mvc.controller {
    import com.stockholdergame.client.model.assembler.GameViewAssembler;
    import com.stockholdergame.client.model.dto.game.DoMoveDto;
    import com.stockholdergame.client.model.dto.game.GameDto;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.util.DateUtil;

    public class PlayGameCommand extends ProxyAwareCommand {
        public function PlayGameCommand() {
            registerNotificationHandler(BusinessActions.LOAD_GAME, handleLoadGame);
            registerNotificationHandler(BusinessActions.DO_MOVE, handleDoMove);
        }

        private function handleDoMove(doMove:DoMoveDto):void {
            gameServiceProxy.doMove(doMove, doMoveCallback);
        }

        private function handleLoadGame(gameId:Number):void {
            gameServiceProxy.getGameById(gameId, getGameByIdCallback);
        }

        private function getGameByIdCallback(game:GameDto):void {
            var isGameLoaded:Boolean = sessionManager.isGameLoaded(game.id);
            var gameAdapted:GameDto = GameViewAssembler.assembleGame(game);
            sessionManager.saveGameInSession(gameAdapted);
            sendNotification(!isGameLoaded ? Notifications.GAME_LOADED : Notifications.MOVE_DONE, gameAdapted);
        }

        private function doMoveCallback(game:GameDto):void {
            if (game.finished) {
                sendNotification(BusinessActions.LOAD_GAME, game.id);
            } else {
                var gameAdapted:GameDto = GameViewAssembler.assembleGame(game);
                sessionManager.saveGameInSession(gameAdapted);
                sendNotification(Notifications.MOVE_DONE, gameAdapted);
            }
        }
    }
}
