package com.stockholdergame.client.model.dto.game.variant {
    import com.stockholdergame.client.util.sort.SortUtil;

    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.variant.GameVariantDto")]
    public class GameVariantDto {

        public function GameVariantDto() {
        }

        public var id:Number;
        public var name:String;
        public var maxGamersQuantity:int;
        public var rounding:String;
        public var movesQuantity:int;
        public var gamerInitialCash:int;
        public var priceScale:PriceScaleDto;
        private var _shares:ArrayCollection;
        private var _cardGroups:ArrayCollection;

        public function get shares():ArrayCollection {
            return _shares;
        }

        public function set shares(value:ArrayCollection):void {
            _shares = SortUtil.sortByNumericField(value, "id");
        }

        public function get cardGroups():ArrayCollection {
            return _cardGroups;
        }

        public function set cardGroups(value:ArrayCollection):void {
            _cardGroups = SortUtil.sortByNumericField(value, "id");
        }
    }
}