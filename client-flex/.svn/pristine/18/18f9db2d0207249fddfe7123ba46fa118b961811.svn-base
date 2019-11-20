package com.stockholdergame.client.model.dto.game {
    import com.stockholdergame.client.util.sort.SortUtil;

    import mx.collections.ArrayCollection;

    [Bindable]
    public class BuySellStep {
        public function BuySellStep() {
        }

        private var _operations:ArrayCollection = new ArrayCollection();
        public var cash:int;


        public function get operations():ArrayCollection {
            return _operations;
        }

        public function set operations(value:ArrayCollection):void {
            _operations = SortUtil.sortByNumericField(value, "shareId");
        }
    }
}
