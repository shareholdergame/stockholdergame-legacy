package com.stockholdergame.client.model.dto.account {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.IncomingChatMessageDto")]
    public class IncomingChatMessageDto extends ChatMessageDto {
        public function IncomingChatMessageDto() {
        }

        public var recipientNames:Array;
    }
}
