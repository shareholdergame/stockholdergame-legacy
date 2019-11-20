package com.stockholdergame.client.model.dto.game {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.BuySellDto")]
    public class BuySellDto {
        public function BuySellDto() {
        }

        public var shareId:Number;
        public var buySellQuantity:int = 0;
    }
}
