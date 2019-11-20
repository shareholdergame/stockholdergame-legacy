package com.stockholdergame.client.model.dto.view {
    import com.stockholdergame.client.model.dto.game.variant.*;
    import com.stockholdergame.client.util.sort.SortUtil;

    import mx.collections.ArrayCollection;

    [Bindable]
    public class GameVariantCommonData {
        public function GameVariantCommonData() {
        }

        public var rounding:String;
        public var gamerInitialCash:int;
        public var priceScale:PriceScaleDto;
        private var _shares:ArrayCollection;
        private var _cardSet:ArrayCollection;

        public function get shares():ArrayCollection {
            return _shares;
        }

        public function set shares(value:ArrayCollection):void {
            _shares = SortUtil.sortByNumericField(value, "id");
        }

        public function get cardSet():ArrayCollection {
            return _cardSet;
        }

        public function set cardSet(value:ArrayCollection):void {
            _cardSet = value;
        }
    }
}
