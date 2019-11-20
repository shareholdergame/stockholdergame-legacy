package com.stockholdergame.client.util.cache {

    import mx.collections.ArrayCollection;

    public class LRUCache {

        public static const DEFAULT_SIZE:int = 100;

        private var _maxSize:uint = DEFAULT_SIZE;

        private var _keys:ArrayCollection = new ArrayCollection();

        private var _items:ArrayCollection = new ArrayCollection();

        public function LRUCache() {
        }

        public function pushItem(itemKey:Object, item:Object):void {
            if (_keys.length == _maxSize) {
                removeAt(0);
            }
            if (_keys.contains(itemKey)) {
                _items.setItemAt(item, _keys.getItemIndex(itemKey));
            } else {
                pushItemAtEnd(itemKey, item);
            }
        }

        private function pushItemAtEnd(itemKey:Object, item:Object):void {
            _keys.addItem(itemKey);
            _items.addItem(item);
        }

        public function getItem(itemKey:Object):Object {
            if (!_keys.contains(itemKey)) {
                return null;
            }
            var keyIndex:int = _keys.getItemIndex(itemKey);
            var item:Object = _items.getItemAt(keyIndex);

            if (keyIndex < DEFAULT_SIZE / 2) {
                removeAt(keyIndex);
                pushItemAtEnd(itemKey, item);
            }

            return item;
        }

        private function removeAt(keyIndex:int):void {
            _keys.removeItemAt(keyIndex);
            _items.removeItemAt(keyIndex);
        }

        public function clear():void {
            _keys = new ArrayCollection();
            _items = new ArrayCollection();
        }

        public function get maxSize():uint {
            return _maxSize;
        }

        public function set maxSize(value:uint):void {
            value > 0 ? _maxSize = value : _maxSize;
        }
    }
}
