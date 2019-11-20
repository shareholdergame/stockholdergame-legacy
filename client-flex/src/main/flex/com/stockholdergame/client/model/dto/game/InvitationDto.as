package com.stockholdergame.client.model.dto.game {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.InvitationDto")]
    public class InvitationDto {
        public function InvitationDto() {
        }

        public static const CREATED:String = "CREATED";
        public static const ACCEPTED:String = "ACCEPTED";
        public static const REJECTED:String = "REJECTED";
        public static const EXPIRED:String = "EXPIRED";
        public static const CANCELLED:String = "CANCELLED";

        public var id:Number;
        public var gameVariantId:Number;
        public var gameId:Number;
        public var competitorsQuantity:int;
        public var inviterName:String;
        public var inviteeName:String;
        public var createdTime:Date;
    }
}
