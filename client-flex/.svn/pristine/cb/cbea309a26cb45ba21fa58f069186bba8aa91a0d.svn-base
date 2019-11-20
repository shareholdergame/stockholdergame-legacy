package com.stockholdergame.client.ui.components.dialog.dialogClasses {
    import com.stockholdergame.client.model.dto.account.UserDto;

    import mx.collections.ArrayCollection;

    [Bindable]
    public class UserChat {
        public function UserChat(chatName:String, participants:ArrayCollection) {
            this.chatName = chatName;
            this.participants = participants;
        }

        public var chatName:String;
        public var participants:ArrayCollection;
        public var messagesStack:String = "";
        public var newMessagesCount:int = 0;
        public var newMessageText:String;

        public function get recipientNames():Array {
            var userNames:Array = [];
            for each (var user:UserDto in participants) {
                userNames.push(user.userName);
            }
            return userNames;
        }
    }
}
