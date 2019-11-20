package com.stockholdergame.client.ui.validators {

    import mx.validators.IValidatorListener;
    import mx.validators.ValidationResult;
    import mx.validators.Validator;

    public class PasswordValidator extends Validator {

        public function PasswordValidator() {
            super();
        }

        private var results:Array = new Array();

        private var _retypeSource:Object;

        private var _retypeProperty:String;

        private var _retypeListener:IValidatorListener;

        private var _minPasswordLength:uint = 3;

        private var _maxPasswordLength:uint = 0;

        public function get retypeSource():Object {
            return _retypeSource;
        }

        public function set retypeSource(value:Object):void {
            if (value == null) {
                return;
            }

            removeListenerHandler();

            _retypeSource = value;

            addListenerHandler();
        }

        public function get retypeListener():IValidatorListener {
            return _retypeListener;
        }

        public function set retypeListener(value:IValidatorListener):void {
            if (value == null) {
                return;
            }

            removeListenerHandler();

            _retypeListener = value;

            addListenerHandler();
        }

        public function get retypeProperty():String {
            return _retypeProperty;
        }

        public function set retypeProperty(value:String):void {
            _retypeProperty = value;
        }

        public function get minPasswordLength():uint {
            return _minPasswordLength;
        }

        public function set minPasswordLength(value:uint):void {
            if (_maxPasswordLength == 0 || (_maxPasswordLength > 0 && value <= _maxPasswordLength)) {
                _minPasswordLength = value;
            }
        }

        public function get maxPasswordLength():uint {
            return _maxPasswordLength;
        }

        public function set maxPasswordLength(value:uint):void {
            if (value >= _minPasswordLength) {
                _maxPasswordLength = value;
            }
        }

        override protected function getValueFromSource():Object {
            var value:Object = {};
            value.password = source[property];
            value.retypedPassword = retypeSource[property];
            return value;
        }

        protected override function doValidation(value:Object):Array {
            results = [];

            var pwd:String = value.password;
            var retypePwd:String = value.retypedPassword;

            if (pwd == null || pwd == "") {
                results.push(new ValidationResult(true, "password", "", "Password is empty"));
            }
            if (pwd.length < _minPasswordLength) {
                results.push(new ValidationResult(true, "password", "", "Password is shorter than " + _minPasswordLength +
                        " symbols."));
            }
            if (_maxPasswordLength > 0 && pwd.length > _maxPasswordLength) {
                results.push(new ValidationResult(true, "password", "", "Password is longer than " + _maxPasswordLength +
                        " symbols."));
            }
            if (retypePwd == null || retypePwd != pwd) {
                results.push(new ValidationResult(true, "retypePassword", "", "Retyped password is empty or invalid"));
            }

            return results;
        }

        override protected function get actualListeners():Array {
            var results:Array = [];

            if (source) {
                results.push(source);
                if (source is IValidatorListener) {
                    IValidatorListener(source).validationSubField = "password";
                }
            }

            if (_retypeSource) {
                results.push(_retypeSource);
                if (_retypeSource is IValidatorListener) {
                    IValidatorListener(_retypeSource).validationSubField = "retypePassword";
                }
            }

            if (results.length > 0 && listener) {
                results.push(listener);
            } else {
                results = results.concat(super.actualListeners);
            }

            return results;
        }
    }
}