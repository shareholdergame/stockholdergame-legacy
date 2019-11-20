package com.stockholdergame.client.model.dto.account {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.UserDto")]
    public class UserDto extends UserInfoDto {
        public function UserDto() {
        }

        public var removed:Boolean;
        public var online:Boolean;
        public var friend:Boolean;
        public var friendRequestee:Boolean;
        public var friendRequestor:Boolean;
    }
}
