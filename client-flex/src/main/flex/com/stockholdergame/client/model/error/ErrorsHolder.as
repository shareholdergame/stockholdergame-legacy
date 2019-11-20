package com.stockholdergame.client.model.error {

    public class ErrorsHolder {

        private static var _instance:ErrorsHolder;

        function ErrorsHolder() {
        }

        public static function get instance():ErrorsHolder {
            if (_instance == null) {
                _instance = new ErrorsHolder();
            }
            return _instance;
        }

        private var _errors:Array = new Array();

        public function putError(error:ApplicationError):void {
            _errors.push(error);
        }
    }
}