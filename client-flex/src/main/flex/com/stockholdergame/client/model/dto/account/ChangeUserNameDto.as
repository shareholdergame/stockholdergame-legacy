package com.stockholdergame.client.model.dto.account {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.ChangeUserNameDto")]
    public class ChangeUserNameDto {
        public function ChangeUserNameDto() {
        }

        public var newUserName:String;
    }
}
