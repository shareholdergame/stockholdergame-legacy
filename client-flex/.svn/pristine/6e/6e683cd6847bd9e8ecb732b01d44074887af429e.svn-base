package com.stockholdergame.client.model.dto.account {
    import com.stockholdergame.client.model.dto.PaginationDto;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.UserFilterDto")]
    public class UserFilterDto extends PaginationDto {
        public function UserFilterDto() {
        }

        public static const FRIENDS_ONLY:String = "FRIENDS_ONLY";
        public static const WITH_FRIEND_REQUESTS:String = "WITH_FRIEND_REQUESTS";
        public static const OTHER:String = "OTHER";
        public static const EXCLUDE_BOTS:String = "EXCLUDE_BOTS";

        public var userName:String;
        private var _userNames:Array;
        public var locale:String;
        public var sex:String;
        public var country:String;
        public var city:String;
        public var friendFilters:Array;
        private var _excludedUserNames:Array;
        public var gameId:Number;
        public var showRemoved:Boolean;

        public function get userNames():Array {
            return _userNames;
        }

        public function set userNames(value:Array):void {
            _userNames = value.length == 0 ? null : value;
        }

        public function get excludedUserNames():Array {
            return _excludedUserNames;
        }

        public function set excludedUserNames(value:Array):void {
            _excludedUserNames = value.length == 0 ? null : value;
        }
    }
}
