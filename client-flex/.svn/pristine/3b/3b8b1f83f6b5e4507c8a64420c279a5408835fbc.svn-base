package com.stockholdergame.client.model {
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.resources.IResourceManager;
    import mx.resources.ResourceManager;

    public class Locales {

        private static var _instance:Locales;

        public function Locales() {
        }

        public static function get instance():Locales {
            if (_instance == null) {
                _instance = new Locales();
            }
            return _instance;
        }

        private var resourceManager:IResourceManager = ResourceManager.getInstance();

        private var _locales:ArrayCollection = new ArrayCollection(
                    [{label:resourceManager.getString("common", "en_US"), data:"en_US"},
                     {label:resourceManager.getString("common", "ru_RU"), data:"ru_RU"}]
                    );

        public function get locales():ArrayCollection {
            return _locales;
        }

        public function getLocaleItemIndex(language:String):int {
            for (var i:int = 0; i < locales.length; i++) {
                var object:Object = locales[i];
                if (object.data == language) {
                    return i;
                }
            }
            return 0;
        }

        public function getLocaleItem(language:String):Object {
            for each (var object:Object in _locales) {
                if (object.data == language) {
                    return object;
                }
            }
            return null;
        }
    }
}
