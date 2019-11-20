package com.stockholdergame.client.model.dto.game {
    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.UserStatisticsList")]
    public class UserStatisticsList {
        public function UserStatisticsList() {
        }

        public var totalCount:int;
        public var userStatistics:ArrayCollection;

        public function get top10UsersAsText():String {
            var top10:String = "";
            if (userStatistics != null) {
                for each (var statistics:UserStatistics in userStatistics) {
                    if (top10.length > 0) {
                        top10 += ", "
                    }
                    top10 += statistics.user.userName;
                }
            }
            return top10;
        }
    }
}
