package com.stockholdergame.client.model.dto.game {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.SharePriceDto")]
    public class SharePriceDto {

        public function SharePriceDto() {
        }

        public var id:Number;
        public var price:int;
        public var priceOperationId:Number;

        // local properties
        public var color:String;
        public var letter:String;
        public var mainColor:Boolean = false;
        public var operation:String;
        public var operandValue:int;
        public var oldPrice:int;
    }
}
