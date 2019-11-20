package com.stockholdergame.client.model.dto.game {
import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.GameInitiationDto")]
    public class GameInitiationDto {
        public function GameInitiationDto(gameVariantId:Number, gameOffer:Boolean) {
            this.gameVariantId = gameVariantId;
            this.offer = gameOffer;
        }

        public var gameVariantId:Number;
        public var offer:Boolean;
        public var switchMoveOrder:Boolean;

        public var invitedUsers:ArrayCollection = new ArrayCollection();
    }
}
