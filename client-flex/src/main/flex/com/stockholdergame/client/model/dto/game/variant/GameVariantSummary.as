package com.stockholdergame.client.model.dto.game.variant {
    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.GameVariantSummary")]
    public class GameVariantSummary {
        public function GameVariantSummary() {
        }

        public var gameVariantId:Number;
        public var competitorsQuantity:int;
        public var gameStatus:String;
        public var gamesQuantity:int;
        public var alreadyJoined:Boolean;
        public var initiationMethod:String;
        public var playingUsers:ArrayCollection;
    }
}
