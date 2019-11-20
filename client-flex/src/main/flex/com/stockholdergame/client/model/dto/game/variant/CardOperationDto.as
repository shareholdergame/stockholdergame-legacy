package com.stockholdergame.client.model.dto.game.variant {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.variant.CardOperationDto")]
    public class CardOperationDto {
        public function CardOperationDto() {
        }

        public var shareId:Number;
        public var priceOperationId:Number;
        public var operation:String;
        public var operandValue:int;

        // local properties
        public var shareColor:String;
        public var colorLetter:String;
        public var isShareFixed:Boolean;
    }
}
