package com.stockholdergame.client.model.dto.game {
    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.TotalScoreDto")]
    public class TotalScoreDto extends ScoreValuesDto {
        public function TotalScoreDto() {
        }

        public var totalPlayedGamesCount:int;
        public var scores:ArrayCollection;
        public var totalScores:int;
    }
}
