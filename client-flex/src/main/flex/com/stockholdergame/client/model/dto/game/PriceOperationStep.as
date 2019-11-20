package com.stockholdergame.client.model.dto.game {
    import mx.collections.ArrayCollection;

    [Bindable]
    public class PriceOperationStep {
        public function PriceOperationStep() {
        }

        public var operations:ArrayCollection = new ArrayCollection();
        public var cash:int;
        public var appliedCardId:Number;
    }
}
