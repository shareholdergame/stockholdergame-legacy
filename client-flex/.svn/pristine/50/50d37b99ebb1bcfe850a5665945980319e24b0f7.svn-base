package com.stockholdergame.client.util.collection {
    import mx.collections.ArrayCollection;

    public class CollectionUtil {
        public function CollectionUtil() {
        }

        public static function createCollection(arg1:Object, arg2:Object = null, arg3:Object = null):ArrayCollection {
            var collection:ArrayCollection = new ArrayCollection();
            collection.addItem(arg1);
            if (arg2 != null) {
                collection.addItem(arg2);
            }
            if (arg3 != null) {
                collection.addItem(arg3);
            }
            return collection;
        }

        public static function createCollectionFromArray(arr:Array):ArrayCollection {
            var collection:ArrayCollection = new ArrayCollection();
            if (arr != null && arr.length > 0) {
                for each (var object:Object in arr) {
                    collection.addItem(object);
                }
            }
            return collection;
        }

        public static function subList(list:ArrayCollection, startIndex:int, endIndex:int = -1):ArrayCollection {
            if (endIndex < 0) {
                endIndex = list.length;
            }
            var copy:ArrayCollection = new ArrayCollection();
            if (startIndex >= list.length || endIndex < startIndex) {
                return copy; // returns empty list;
            }
            for (var i:int = 0; i < list.length; i++) {
                var object:Object = list.getItemAt(i);
                if (i < startIndex || i > endIndex) {
                    continue;
                }
                copy.addItem(object);
            }
            return copy;
        }
    }
}
