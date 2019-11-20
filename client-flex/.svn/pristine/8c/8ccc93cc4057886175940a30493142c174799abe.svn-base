package com.stockholdergame.client.configuration {

    public class Environment {

        public static const PORT:String = "8080";
        public static const SSL_PORT:String = "8443";

        public static const HTTP_PROTOCOL:String = "http://";
        public static const HTTPS_PROTOCOL:String = "https://";

        public static const GAME_AMF_CHANNEL:String = "game-amf";
        public static const GAME_AMF_SECURE_CHANNEL:String = "game-secure-amf";
        public static const GAME_POLLING_AMF_CHANNEL:String = "game-polling-amf";
        public static const GAME_POLLING_AMF_SECURE_CHANNEL:String = "game-secure-polling-amf";

        public static const AMF_PATH:String = "/stockholdergame/messagebroker/amf";
        public static const AMF_SECURE_PATH:String = "/stockholdergame/messagebroker/amfsecure";
        public static const AMF_POLLING_PATH:String = "/stockholdergame/messagebroker/amfpolling";
        public static const AMF_POLLING_SECURE_PATH:String = "/stockholdergame/messagebroker/amfsecurepolling";

        public function Environment(envName:String, host:String, sslEnabled:Boolean, messagingEnabled:Boolean, cookiesEnabled:Boolean) {
            _envName = envName;
            _sslEnabled = sslEnabled;
            _messagingEnabled = messagingEnabled;
            _host = host;
            _cookiesEnabled = cookiesEnabled;
        }

        private var _envName:String;
        private var _sslEnabled:Boolean;
        private var _messagingEnabled:Boolean;
        private var _host:String;
        private var _cookiesEnabled:Boolean;

        public function get envName():String {
            return _envName;
        }

        public function get sslEnabled():Boolean {
            return _sslEnabled;
        }

        public function get messagingEnabled():Boolean {
            return _messagingEnabled;
        }

        public function get host():String {
            return _host;
        }

        public function get cookiesEnabled():Boolean {
            return _cookiesEnabled;
        }

        public function get mainChannelId():String {
            return _sslEnabled ? GAME_AMF_SECURE_CHANNEL : GAME_AMF_CHANNEL;
        }

        public function get pollingChannelId():String {
            return _sslEnabled ? GAME_POLLING_AMF_SECURE_CHANNEL : GAME_POLLING_AMF_CHANNEL;
        }

        public function get mainChannelUrl():String {
            return buildUrl() + (_sslEnabled ? AMF_SECURE_PATH : AMF_PATH);
        }

        public function get pollingChannelUrl():String {
            return buildUrl() + (_sslEnabled ? AMF_POLLING_SECURE_PATH : AMF_POLLING_PATH);
        }

        private function buildUrl():String {
            return (_sslEnabled ? HTTPS_PROTOCOL : HTTP_PROTOCOL) + _host + ":" + (_sslEnabled ? SSL_PORT : PORT);
        }
    }
}
