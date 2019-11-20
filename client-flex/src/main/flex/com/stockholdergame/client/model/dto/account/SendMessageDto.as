package com.stockholdergame.client.model.dto.account {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.SendMessageDto")]
    public class SendMessageDto extends ChatMessageTextDto {
        public function SendMessageDto() {
        }

        public var recipientNames:Array;
    }
}
