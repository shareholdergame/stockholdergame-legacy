package com.stockholdergame.client.model.dto.game {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.GamerActivitySummary")]
    public class GamerActivitySummary {
        public function GamerActivitySummary() {
        }

        public var lastSessionStart:Date;
        public var lastSessionEnd:Date;
        public var gameOffersNumber:int;
        public var invitationsNumber:int;
        public var gamesInProgressNumber:int;
        public var finishedGamesQuantity:int;
        public var scorersQuantity:int;
        public var messagesQuantity:int;
        public var friendsQuantity:int;
        public var friendRequestsQuantity:int;

        public function get totalMyGames():int {
            return gameOffersNumber + invitationsNumber + gamesInProgressNumber;
        }
    }
}
