package com.stockholdergame.client.ui.components.list.listClasses {

    import com.stockholdergame.client.model.dto.game.GameDto;
    import com.stockholdergame.client.model.dto.game.variant.GameVariantDto;
    import com.stockholdergame.client.model.dto.game.variant.GameVariantSummary;

    import flash.utils.Dictionary;

    import mx.collections.ArrayCollection;

    [Bindable]
    public class GameVariantListItemData {
        public function GameVariantListItemData(gameVariant:GameVariantDto, gameVariantSummaryList:ArrayCollection) {
            this.gameVariant = gameVariant;
            this.gameVariantSummaryList = gameVariantSummaryList;

            calculateTotalGamers();
        }

        public var gameVariant:GameVariantDto;

        public var gameVariantSummaryList:ArrayCollection;

        public var openGames:ArrayCollection;

        private var totalGamesPerMethod:Dictionary = new Dictionary();

        private var totalWaitingPerMethod:Dictionary = new Dictionary();

        public function getPlayingUsers():ArrayCollection {
            if (gameVariantSummaryList != null && gameVariantSummaryList.length > 0) {
                for each (var gameVariantSummary:GameVariantSummary in gameVariantSummaryList) {
                    if (gameVariantSummary.initiationMethod == GameDto.GAME_OFFER) {
                        return gameVariantSummary.playingUsers;
                    }
                }
            }
            return new ArrayCollection();
        }

        public function getTotalWaiting(offer:Boolean):int {
            var initiationMethod:String = offer ? GameDto.GAME_OFFER : GameDto.INVITATION;
            return totalWaitingPerMethod[initiationMethod] != null ? totalWaitingPerMethod[initiationMethod] : 0;
        }

        public function getTotalGames(offer:Boolean):int {
            var initiationMethod:String = offer ? GameDto.GAME_OFFER : GameDto.INVITATION;
            return totalGamesPerMethod[initiationMethod] != null ? totalGamesPerMethod[initiationMethod] : 0;
        }

        public function getGamersCount(offer:Boolean):int {
            var initiationMethod:String = offer ? GameDto.GAME_OFFER : GameDto.INVITATION;
            if (gameVariantSummaryList != null && gameVariantSummaryList.length > 0) {
                for each (var gameVariantSummary:GameVariantSummary in gameVariantSummaryList) {
                    if (gameVariantSummary.initiationMethod == initiationMethod) {
                        return gameVariantSummary.competitorsQuantity;
                    }
                }
            }
            return 0;
        }

        public function getNotShownGamersCount(offer:Boolean):int {
            return getGamersCount(offer) - getPlayingUsers().length;
        }

        private function calculateTotalGamers():void {
            for each (var gameVariantSummary:GameVariantSummary in gameVariantSummaryList) {
                if (totalWaitingPerMethod[gameVariantSummary.initiationMethod] == null) {
                    totalWaitingPerMethod[gameVariantSummary.initiationMethod] = 0;
                }
                if (totalGamesPerMethod[gameVariantSummary.initiationMethod] == null) {
                    totalGamesPerMethod[gameVariantSummary.initiationMethod] = 0;
                }
                if (gameVariantSummary.gameVariantId == gameVariant.id) {
                    if (gameVariantSummary.gameStatus == GameDto.RUNNING) {
                        totalGamesPerMethod[gameVariantSummary.initiationMethod] += gameVariantSummary.gamesQuantity;
                    } else if (gameVariantSummary.gameStatus == GameDto.OPEN) {
                        totalWaitingPerMethod[gameVariantSummary.initiationMethod] += gameVariantSummary.gamesQuantity;
                    }
                }
            }
        }
    }
}
