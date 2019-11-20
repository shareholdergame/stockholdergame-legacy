package com.stockholdergame.client.configuration {

    public class ApplicationConfiguration {

        public static const DEV_ENV_NAME:String = "dev";
        public static const DEV_ENV_PLAYER_NAME:String = "dev_player";
        public static const STAGING_ENV_NAME:String = "staging";
        public static const AMAZON_ENV_NAME:String = "amazon";
        public static const PROD_ENV_NAME:String = "prod";

        public static const DEV_ENV:Environment = new Environment(DEV_ENV_NAME, "localhost", false, true, true);
        public static const DEV_ENV_PLAYER:Environment = new Environment(DEV_ENV_PLAYER_NAME, "localhost", false, false, false);
        public static const STAGING_ENV:Environment = new Environment(STAGING_ENV_NAME, "alexlinux", true, true, true);
        public static const AMAZON_ENV:Environment = new Environment(AMAZON_ENV_NAME, "54.147.153.28", false, true, true);
        public static const PROD_ENV:Environment = new Environment(PROD_ENV_NAME, "stockholdergame.com", false, true, true);

//        private var _activeEnvironment:Environment = DEV_ENV;
//        private var _activeEnvironment:Environment = DEV_ENV_PLAYER;
//        private var _activeEnvironment:Environment = STAGING_ENV;
//        private var _activeEnvironment:Environment = AMAZON_ENV;
        private var _activeEnvironment:Environment = PROD_ENV;

        private static var _instance:ApplicationConfiguration;

        function ApplicationConfiguration() {
        }

        public static function get instance():ApplicationConfiguration {
            if (_instance == null) {
                _instance = new ApplicationConfiguration();
            }
            return _instance;
        }

        public function get activeEnvironment():Environment {
            return _activeEnvironment;
        }
    }
}
