package com.stockholdergame.client.model.dto.game.lite {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.lite.InvitationLite")]
    public class InvitationLite {
        public function InvitationLite() {
        }

        public var createdTime:Date;
        public var inviterName:String;
        public var inviteeName:String;
    }
}
