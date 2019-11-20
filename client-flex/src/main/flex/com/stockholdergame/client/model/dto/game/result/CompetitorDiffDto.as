package com.stockholdergame.client.model.dto.game.result {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.result.CompetitorDiffDto")]
    public class CompetitorDiffDto {

        public function CompetitorDiffDto() {
        }

        public var gameSeriesId:Number;
        public var gameId:Number;
        public var firstUserName:String;
        public var secondUserName:String;
        public var fundsAbsoluteDiff:Number;
        public var fundsRelativeDiff:Number;
    }
}
