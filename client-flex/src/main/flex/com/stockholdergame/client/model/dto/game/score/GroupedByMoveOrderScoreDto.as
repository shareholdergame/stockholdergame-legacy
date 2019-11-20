package com.stockholdergame.client.model.dto.game.score {

    [Bindable]
    public class GroupedByMoveOrderScoreDto {
        public function GroupedByMoveOrderScoreDto() {
        }

        public var moveOrder:int;

        public var totalPlayed:int;
        public var winningsCount:int;
        public var defeatsCount:int;
        public var bankruptsCount:int;
    }
}
