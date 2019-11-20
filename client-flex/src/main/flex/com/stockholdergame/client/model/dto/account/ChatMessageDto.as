package com.stockholdergame.client.model.dto.account {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.ChatMessageDto")]
    public class ChatMessageDto extends ChatMessageTextDto {
        public function ChatMessageDto() {
        }

        public var chatMessageId:Number;
        public var senderName:String;
        public var sent:Date;
        public var unread:Boolean;
    }
}
