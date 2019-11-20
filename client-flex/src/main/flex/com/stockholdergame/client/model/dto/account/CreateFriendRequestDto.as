package com.stockholdergame.client.model.dto.account {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.CreateFriendRequestDto")]
    public class CreateFriendRequestDto {
        public function CreateFriendRequestDto() {
        }

        public var userName:String;
        public var message:String;
    }
}
