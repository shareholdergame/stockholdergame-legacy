package com.stockholdergame.client.model.dto.game {
    import com.stockholdergame.client.util.sort.SortUtil;

    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.MoveStepDto")]
    public class MoveStepDto {

        public function MoveStepDto() {
        }

        public static const ZERO_STEP:String = "ZERO_STEP";
        public static const FIRST_BUY_SELL_STEP:String = "FIRST_BUY_SELL_STEP";
        public static const PRICE_CHANGE_STEP:String = "PRICE_CHANGE_STEP";
        public static const LAST_BUY_SELL_STEP:String = "LAST_BUY_SELL_STEP";
        public static const COMPENSATION_STEP:String = "COMPENSATION_STEP";
        public static const BANKRUPTING_STEP:String = "BANKRUPTING_STEP";
        public static const REPURCHASE_STEP:String = "REPURCHASE_STEP";

        public var stepType:String;
        private var _shareQuantities:ArrayCollection;
        private var _sharePrices:ArrayCollection;
        private var _compensations:ArrayCollection;
        public var cashValue:int;
        private var _originalStepId:Number = 0;

        public function get shareQuantities():ArrayCollection {
            return _shareQuantities;
        }

        public function set shareQuantities(value:ArrayCollection):void {
            _shareQuantities = SortUtil.sortByNumericField(value, "id");
        }

        public function get sharePrices():ArrayCollection {
            return _sharePrices;
        }

        public function set sharePrices(value:ArrayCollection):void {
            _sharePrices = SortUtil.sortByNumericField(value, "id");
        }

        public function get compensations():ArrayCollection {
            return _compensations;
        }

        public function set compensations(value:ArrayCollection):void {
            _compensations = SortUtil.sortByNumericField(value, "id");
        }

        // local properties
        public function get stepNum():int {
            return stepType == ZERO_STEP ? 0 : stepType == FIRST_BUY_SELL_STEP ? 1 : stepType == PRICE_CHANGE_STEP
                    ? 2 : stepType == LAST_BUY_SELL_STEP ? 3 : stepType == COMPENSATION_STEP ? 4 : stepType == BANKRUPTING_STEP ? 5 : 6;
        }

        public function get originalStepId():Number {
            return _originalStepId;
        }

        public function set originalStepId(value:Number):void {
            _originalStepId = value > 0 ? value : 0;
        }
    }
}
