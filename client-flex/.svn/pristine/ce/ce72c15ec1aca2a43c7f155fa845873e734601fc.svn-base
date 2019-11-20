package com.stockholdergame.client.model.dto.view {
    import com.stockholdergame.client.util.sort.SortUtil;

    import mx.collections.ArrayCollection;

    [Bindable]
    public class GameSubVariant {
        public function GameSubVariant() {
        }

        public var id:Number;
        public var name:String;
        public var maxGamersQuantity:int;
        public var movesQuantity:int;
        private var _cardGroups:ArrayCollection;

        public function get cardGroups():ArrayCollection {
            return _cardGroups;
        }

        public function set cardGroups(value:ArrayCollection):void {
            _cardGroups = SortUtil.sortByNumericField(value, "id");
        }
    }
}
