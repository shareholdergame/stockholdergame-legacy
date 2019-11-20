package com.stockholdergame.client.model.dto {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.ConfirmationDto")]
    public class ConfirmationDto {

        public function ConfirmationDto(verificationCode:String) {
            this.verificationCode = verificationCode;
        }

        public var verificationCode:String;
    }
}