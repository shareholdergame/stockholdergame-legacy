package com.stockholdergame.client.util {
    import flash.display.Bitmap;
    import flash.display.BitmapData;
    import flash.display.Loader;
    import flash.events.Event;
    import flash.utils.ByteArray;

    public class ImageLoader {

        private static var _instance:ImageLoader;

        function ImageLoader() {
        }

        public static function getInstance():ImageLoader {
            if (_instance == null) {
                _instance = new ImageLoader();
            }
            return _instance;
        }

        public function loadImage(byteArray:ByteArray, resultHandler:Function):void {
            var loader:Loader = new Loader();
            loader.contentLoaderInfo.addEventListener(Event.COMPLETE, function (event:Event):void {
                var bmpData:BitmapData = new BitmapData(loader.width, loader.height);
                bmpData.draw(loader);
                resultHandler(new Bitmap(bmpData));
            });
            loader.loadBytes(byteArray);
        }
    }
}
