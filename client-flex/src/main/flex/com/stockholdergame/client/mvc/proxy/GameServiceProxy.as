package com.stockholdergame.client.mvc.proxy {
    import com.stockholdergame.client.model.dto.game.CreateInvitationDto;
    import com.stockholdergame.client.model.dto.game.DoMoveDto;
    import com.stockholdergame.client.model.dto.game.GameDto;
    import com.stockholdergame.client.model.dto.game.GameFilterDto;
    import com.stockholdergame.client.model.dto.game.GameInitiationDto;
    import com.stockholdergame.client.model.dto.game.ChangeInvitationStatusDto;
    import com.stockholdergame.client.model.dto.game.InvitationDto;
    import com.stockholdergame.client.model.dto.game.ScoreFilterDto;
    import com.stockholdergame.client.model.dto.game.lite.CompetitorLiteDto;
    import com.stockholdergame.client.model.dto.game.lite.GameLiteDto;
    import com.stockholdergame.client.model.dto.game.lite.GamesList;
    import com.stockholdergame.client.remote.factory.RemoteServiceFactory;

    import mx.collections.ArrayCollection;

    public class GameServiceProxy extends AbstractProxy {

        public static const NAME:String = 'GameServiceProxy';

        public function GameServiceProxy() {
            super(NAME, RemoteServiceFactory.GAME_SERVICE);
        }

        public function getGameVariants(resultHandler:Function):void {
            getService(resultHandler).getGameVariants();
        }

        public function getGames(filterDto:GameFilterDto, resultHandler:Function):void {
            getService(resultProcessor(resultHandler)).getGames(filterDto);
        }

        private function resultProcessor(resultHandler:Function):Function {
            return function(gamesList:GamesList):void {
                processItems(gamesList.games);
                resultHandler(gamesList);
            }
        }

        private static function processItems(result:ArrayCollection):void {
            for each (var gameLite:GameLiteDto in result) {
                if (gameLite.gameStatus == GameDto.RUNNING) {
                    calculateCurrentMove(gameLite);
                }
                addEmptyCompetitors(gameLite);
            }
        }

        private static function calculateCurrentMove(gameLite:GameLiteDto):void {
            switchToNextMover(gameLite, gameLite.lastMoveNumber, gameLite.lastMoveOrder);
        }

        private static function switchToNextMover(game:GameLiteDto, lastMoveNumber:int, lastMoveOrder:int):void {
            if (lastMoveOrder == game.competitors.length) {
                if (lastMoveNumber == game.movesQuantity) {
                    return;
                }
                lastMoveNumber++;
                lastMoveOrder = 1;
            } else {
                lastMoveOrder++;
            }
            var nextCompetitor:CompetitorLiteDto;
            var competitors:ArrayCollection = game.competitors;
            for each (var competitorDto:CompetitorLiteDto in competitors) {
                if (competitorDto.moveOrder == lastMoveOrder) {
                    nextCompetitor = competitorDto;
                }
            }
            if (nextCompetitor.out) {
                switchToNextMover(game, lastMoveNumber, lastMoveOrder);
            } else if (lastMoveOrder == game.lastMoveOrder) {
                // game finished
            } else {
                game.lastMoveNumber = lastMoveNumber;
                game.lastMoveOrder = lastMoveOrder;
            }
        }

        private static function addEmptyCompetitors(gameLite:GameLiteDto):void {
            if (gameLite == null || gameLite.competitors == null) {
                return;
            }
            for each (var competitor:CompetitorLiteDto in gameLite.competitors) {
                if (competitor.userName == getUserName()) {
                    if (competitor.invitation && competitor.invitationStatus == InvitationDto.ACCEPTED) {
                        gameLite.currentUserJoined = true;
                    }
                    competitor.me = true;
                    if (competitor.initiator) {
                        gameLite.currentUserInitiator = true;
                    }
                }
                if (gameLite.gameStatus == GameDto.RUNNING) {
                    competitor.currentMove = (competitor.moveOrder == gameLite.lastMoveOrder);
                }
            }
        }

        private static function getUserName():String {
            return sessionManager.getAccountInfo().userName;
        }

        public function initiateGame(gameInitiationDto:GameInitiationDto, resultHandler:Function):void {
            getService(resultHandler).initiateGame(gameInitiationDto);
        }

        public function joinToGame(gameId:Number, resultHandler:Function):void {
            getService(resultHandler).joinToGame(gameId);
        }

        public function cancelGame(gameId:int, resultHandler:Function):void {
            getService(resultHandler).cancelGame(gameId);
        }

        public function getGameById(gameId:int, resultHandler:Function):void {
            getService(resultHandler).getGameById(gameId);
        }

        public function doMove(doMoveDto:DoMoveDto, resultHandler:Function):void {
            getService(resultHandler).doMove(doMoveDto);
        }

        public function getGameVariantsSummary(resultHandler:Function):void {
            getService(resultHandler).getGameVariantsSummary();
        }

        public function getGamerActivitySummary(resultHandler:Function):void {
            getService(resultHandler).getGamerActivitySummary();
        }

        public function inviteUser(invitationDto:CreateInvitationDto, resultHandler:Function):void {
            getService(resultHandler).inviteUser(invitationDto);
        }

        public function changeInvitationStatus(invitationStatusDto:ChangeInvitationStatusDto, resultHandler:Function):void {
            getService(resultHandler).changeInvitationStatus(invitationStatusDto);
        }

        public function getScores(scoreFilter:ScoreFilterDto, resultHandler:Function):void {
            getService(resultHandler).getScores(scoreFilter);
        }
    }
}
