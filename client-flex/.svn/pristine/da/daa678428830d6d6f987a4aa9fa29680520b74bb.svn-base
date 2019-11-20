package com.stockholdergame.client.model.dto.game {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.GameEventDto")]
    public class GameEventDto {
        public function GameEventDto() {
        }

        public var gameId:Number;
        public var movesQuantity:int;
        public var userName:String;
        public var gameStatus:String;
        public var offer:Boolean;
        public var created:Date;
        public var eventType:String;
        public var data:Object;

        public static const FRIEND_REQUEST_RECEIVED:String = "FRIEND_REQUEST_RECEIVED";
        public static const FRIEND_REQUEST_STATUS_CHANGED:String = "FRIEND_REQUEST_STATUS_CHANGED";
    }
}
