package com.stockholdergame.client.model.dto.account {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.ChatMessageTextDto")]
    public class ChatMessageTextDto {
        public function ChatMessageTextDto() {
        }

        public var message:String;
    }
}
