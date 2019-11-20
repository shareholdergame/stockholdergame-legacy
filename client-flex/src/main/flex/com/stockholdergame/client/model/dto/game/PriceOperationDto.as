package com.stockholdergame.client.model.dto.game {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.PriceOperationDto")]
    public class PriceOperationDto {
        public function PriceOperationDto() {
        }

        public var shareId:Number;
        public var priceOperationId:Number;
    }
}
