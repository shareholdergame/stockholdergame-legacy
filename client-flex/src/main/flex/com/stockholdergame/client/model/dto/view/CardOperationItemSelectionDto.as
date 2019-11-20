package com.stockholdergame.client.model.dto.view {

    public class CardOperationItemSelectionDto {
        public function CardOperationItemSelectionDto(shareId:Number, priceOperationId:Number) {
            this.shareId = shareId;
            this.priceOperationId = priceOperationId;
        }

        public var shareId:Number;
        public var priceOperationId:Number;
    }
}
