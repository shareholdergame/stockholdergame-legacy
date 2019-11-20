package com.stockholdergame.client.model.dto {
    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.ServerStatisticsDto")]
    public class ServerStatisticsDto {
        public function ServerStatisticsDto() {
        }

        public var onlineUsersCount:int;
        public var onlineUsers:ArrayCollection;

        public function get onlineUsersAsText():String {
            var text:String = "";
            for each (var userName:String in onlineUsers) {
                if (text.length > 0) {
                    text += ", ";
                }
                text += userName;
            }
            if (onlineUsersCount > onlineUsers.length) {
                text += "...";
            }
            return text;
        }
    }
}
