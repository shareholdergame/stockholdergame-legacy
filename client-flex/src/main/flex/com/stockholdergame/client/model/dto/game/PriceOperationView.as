package com.stockholdergame.client.model.dto.game {

    [Bindable]
    public class PriceOperationView extends PriceOperationDto {
        public function PriceOperationView() {
        }

        public var operation:String;
        public var operandValue:int;
        public var newPrice:int;

        public function get priceOperationDto():PriceOperationDto {
            var priceOperationDto:PriceOperationDto = new PriceOperationDto();
            priceOperationDto.shareId = this.shareId;
            priceOperationDto.priceOperationId = this.priceOperationId;
            return priceOperationDto;
        }
    }
}
