package com.stockholdergame.client.model.dto.game {
    import com.stockholdergame.client.model.dto.account.*;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.UserStatistics")]
    public class UserStatistics {
        public function UserStatistics() {
        }

        public var user:UserDto;
        public var allGamesCount:Number;
        public var firstOrderCount:Number;
        public var secondOrderCount:Number;
        public var allWinsCount:Number;
        public var firstOrderWinsCount:Number;
        public var secondOrderWinsCount:Number;
        public var gameSeriesCount:Number;
        public var gameSeriesWinsCount:Number;
        public var drawsCount:Number;
        public var gamesCount:Number;
        public var winsCount:Number;
        public var winsRatio:Number;
        public var maxTotal:Number;
        public var totalWinned:Number;
        public var maxDiff:Number;
        public var bankruptsCount:Number;
        public var daysAfterLastPlay:Number;
        public var daysAfterLastVisit:Number;
    }
}
