package com.stockholdergame.client.model.dto.game {
    import flash.utils.ByteArray;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.GameSeriesResultDto")]
    public class GameSeriesResultDto {
        public function GameSeriesResultDto() {
        }

        public var userName:String;
        public var avatar:ByteArray;
        public var bot:Boolean;
        public var locale:String;
        public var winner:Boolean;
        public var totalFunds:int;
        public var totalPoints:int;
    }
}
