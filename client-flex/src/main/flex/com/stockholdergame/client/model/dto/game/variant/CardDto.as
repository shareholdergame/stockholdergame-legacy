package com.stockholdergame.client.model.dto.game.variant {
    import com.stockholdergame.client.util.sort.SortUtil;

    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.variant.CardDto")]
    public class CardDto {
        public function CardDto() {
        }

        public var id:int;
        public var groupName:String;
        public var quantity:int;
        public var displayOrder:int;
        private var _cardOperations:ArrayCollection;

        public function get cardOperations():ArrayCollection {
            return _cardOperations;
        }

        public function set cardOperations(value:ArrayCollection):void {
            _cardOperations = SortUtil.sort(value, compareTo);
        }

        private static function compareTo(a:CardOperationDto, b:CardOperationDto):int {
            var result:int = 0;

            if (a.operation == b.operation) {
                result = 0;
            } else if (a.operation  == "x"
                || (a.operation == "+" && b.operation != "x")
                || (a.operation == ":" && b.operation == "-")) {
                result = -1;
            } else {
                result = 1;
            }

            if (result == 0) {
                result = a.operandValue > b.operandValue ? -1 : 1;
            }

            return result;
        }
    }
}
