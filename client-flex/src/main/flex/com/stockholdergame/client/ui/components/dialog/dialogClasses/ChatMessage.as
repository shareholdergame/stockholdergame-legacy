package com.stockholdergame.client.ui.components.dialog.dialogClasses {
    import com.stockholdergame.client.model.dto.account.UserDto;

    import mx.collections.ArrayCollection;

    public class ChatMessage {

        public function ChatMessage(chatMessageId:Number, sender:UserDto, participants:ArrayCollection, message:String, sent:Date, unread:Boolean) {
            this.chatMessageId = chatMessageId;
            this.participants = participants;
            this.sender = sender;
            this.message = message;
            this.sent = sent;
            this.unread = unread;
        }

        public var chatMessageId:Number;
        public var participants:ArrayCollection;
        public var sender:UserDto;
        public var message:String;
        public var sent:Date;
        public var unread:Boolean;
    }
}
