package com.stockholdergame.client.model.dto.game {
    import com.stockholdergame.client.model.dto.PaginationDto;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.GameFilterDto")]
    public class GameFilterDto extends PaginationDto {
        public function GameFilterDto() {
        }

        public var gameStatus:String;
        public var offer:Boolean = false;
        public var initiator:Boolean = false;
        public var notInitiator:Boolean = false;
        public var gameVariantId:Number;
        public var userName:String;
        public var smallAvatar:Boolean = false;
        public var legacyRules:Boolean;
    }
}
