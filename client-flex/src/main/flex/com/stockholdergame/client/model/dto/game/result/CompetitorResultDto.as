package com.stockholdergame.client.model.dto.game.result {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.result.CompetitorResultDto")]
    public class CompetitorResultDto {
        public function CompetitorResultDto() {
        }

        public var gameSeriesId:Number;
        public var gameId:Number;
        public var userName:String;
        public var moveOrder:int;
        public var winner:Boolean = false;
        public var out:Boolean = false;
        public var totalFunds:int;
        public var totalPoints:Number;
    }
}
