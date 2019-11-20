package com.stockholdergame.client.util.sort {
    import mx.collections.ArrayCollection;
    import mx.collections.Sort;
    import mx.collections.SortField;

    public class SortUtil {

        public function SortUtil() {
        }

        public static function sortByNumericField(collection:ArrayCollection, fieldName:String, descending:Boolean = false):ArrayCollection {
            var dataSortField:SortField = new SortField();
            dataSortField.name = fieldName;
            dataSortField.numeric = true;
            dataSortField.descending = descending;

            var numericDataSort:Sort = new Sort();
            numericDataSort.fields = [dataSortField];
            collection.sort = numericDataSort;
            collection.refresh();

            return collection;
        }

        public static function sortByNumericFields(collection:ArrayCollection, fieldNames:Array, descending:Boolean = false):ArrayCollection {
            var dataSortFields:ArrayCollection = new ArrayCollection();
            for each (var fieldName:String in fieldNames) {
                var dataSortField:SortField = new SortField();
                dataSortField.name = fieldName;
                dataSortField.numeric = true;
                dataSortField.descending = descending;
                dataSortFields.addItem(dataSortField);
            }

            var numericDataSort:Sort = new Sort();
            numericDataSort.fields = dataSortFields.toArray();
            collection.sort = numericDataSort;
            collection.refresh();

            return collection;
        }

        public static function sortByStringField(collection:ArrayCollection, fieldName:String):ArrayCollection {
            var dataSortField:SortField = new SortField();
            dataSortField.name = fieldName;
            dataSortField.numeric = false;
            dataSortField.caseInsensitive = true;

            var numericDataSort:Sort = new Sort();
            numericDataSort.fields = [dataSortField];
            collection.sort = numericDataSort;
            collection.refresh();

            return collection;
        }

        public static function sort(collection:ArrayCollection, compareFunction:Function):ArrayCollection {
            var dataSortField:SortField = new SortField();
            dataSortField.compareFunction = compareFunction;

            var numericDataSort:Sort = new Sort();
            numericDataSort.fields = [dataSortField];
            collection.sort = numericDataSort;
            collection.refresh();

            return collection;
        }
    }
}
