package com.stockholdergame.client.model.dto.game.score {
    import com.stockholdergame.client.model.dto.account.UserDto;

    import mx.collections.ArrayCollection;

    [Bindable]
    public class GroupedByUserNameScoreDto {
        public function GroupedByUserNameScoreDto() {
        }

        public var user:UserDto;

        public var scoreGroupedByGameVariant:ArrayCollection = new ArrayCollection();

        public var totalPlayed:int;
        public var totalWinningsCount:int;
        public var totalDefeatsCount:int;
        public var totalBankruptsCount:int;
    }
}
