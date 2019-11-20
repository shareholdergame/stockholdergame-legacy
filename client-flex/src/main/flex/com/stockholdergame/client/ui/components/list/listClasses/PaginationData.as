package com.stockholdergame.client.ui.components.list.listClasses {

    public class PaginationData {
        public function PaginationData(limit:int, offset:int) {
            this._limit = limit;
            this._offset = offset;
        }

        private var _limit:int;
        private var _offset:int;

        public function get limit():int {
            return _limit;
        }

        public function get offset():int {
            return _offset;
        }
    }
}
