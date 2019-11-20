package com.stockholdergame.client.model.dto.game {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.ChangeInvitationStatusDto")]
    public class ChangeInvitationStatusDto {
        public function ChangeInvitationStatusDto() {
        }

        public var gameId:Number;
        public var inviteeNames:Array;
        public var status:String;
    }
}
