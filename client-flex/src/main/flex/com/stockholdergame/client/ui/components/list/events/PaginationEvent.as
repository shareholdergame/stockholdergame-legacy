package com.stockholdergame.client.ui.components.list.events {

    import com.stockholdergame.client.ui.components.list.listClasses.PaginationData;

    import flash.events.Event;

    public class PaginationEvent extends Event {

        public const PAGE_CHANGED:String = "pageChanged";

        public function PaginationEvent(paginationData:PaginationData) {
            super(PAGE_CHANGED, false, false);
            this._paginationData = paginationData;
        }

        private var _paginationData:PaginationData;

        public function get paginationData():PaginationData {
            return _paginationData;
        }

        override public function clone():Event {
            return new PaginationEvent(_paginationData);
        }
    }
}
