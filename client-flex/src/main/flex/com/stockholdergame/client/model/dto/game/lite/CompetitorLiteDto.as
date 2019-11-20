package com.stockholdergame.client.model.dto.game.lite {
    import com.stockholdergame.client.model.dto.account.UserViewDto;

    import flash.utils.ByteArray;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.lite.CompetitorLite")]
    public class CompetitorLiteDto implements UserViewDto {
        public function CompetitorLiteDto() {
        }

        public var gameId:Number;
        public var avatar:ByteArray;
        public var bot:Boolean;
        public var locale:String;
        public var userName:String;
        public var removed:Boolean;
        public var moveOrder:int;
        public var initiator:Boolean;
        public var out:Boolean;
        public var winner:Boolean;
        public var joined:Date;
        public var totalFunds:int;
        public var invitation:Boolean = false;
        public var invitationCreated:Date;
        public var invitationExpired:Date;
        public var invitationStatus:String;

        public var currentMove:Boolean;
        public var me:Boolean = false;
    }
}
