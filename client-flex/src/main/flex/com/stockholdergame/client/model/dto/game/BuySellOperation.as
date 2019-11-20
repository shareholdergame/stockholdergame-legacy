package com.stockholdergame.client.model.dto.game {

    [Bindable]
    public class BuySellOperation extends BuySellDto {
        public function BuySellOperation() {
        }

        public var quantity:int;
        public var price:int;
        public var canBuyQuantity:int;
        public var lockedQuantity:int = 0;

        public function get buySellDto():BuySellDto {
            var buySellDto:BuySellDto = new BuySellDto();
            buySellDto.shareId = this.shareId;
            buySellDto.buySellQuantity = this.buySellQuantity;
            return buySellDto;
        }
    }
}
