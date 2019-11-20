package com.stockholdergame.client.model.dto {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.PasswordChangingDto")]
    public class PasswordChangingDto {
        public function PasswordChangingDto() {
        }

        public var oldPassword:String;
        public var newPassword:String;
    }
}
