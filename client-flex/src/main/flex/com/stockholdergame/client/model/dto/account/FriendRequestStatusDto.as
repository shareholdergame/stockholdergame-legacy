package com.stockholdergame.client.model.dto.account {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.FriendRequestStatusDto")]
    public class FriendRequestStatusDto {
        public function FriendRequestStatusDto() {
        }

        public static const CREATED:String = "CREATED";
        public static const CONFIRMED:String = "CONFIRMED";
        public static const REJECTED:String = "REJECTED";

        public var requestorName:String;
        public var status:String;
    }
}
