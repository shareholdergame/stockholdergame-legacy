package com.stockholdergame.client.remote.factory {

    import com.stockholdergame.client.configuration.ApplicationConfiguration;
    import com.stockholdergame.client.configuration.Environment;

    import com.stockholdergame.client.remote.security.LoginService;

    import flash.system.Security;

    import mx.controls.Alert;

    import mx.messaging.Channel;
    import mx.messaging.ChannelSet;
    import mx.messaging.channels.AMFChannel;
    import mx.messaging.channels.SecureAMFChannel;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.remoting.RemoteObject;

    public class RemoteServiceFactory {

        private static var _instance:RemoteServiceFactory;

        public static const ACCOUNT_SERVICE:String = "accountFacade";
        public static const SOCIAL_SERVICE:String = "socialFacade";
        public static const GAME_SERVICE:String = "gameFacade";

        function RemoteServiceFactory() {
        }

        public static function get instance():RemoteServiceFactory {
            if (_instance == null) {
                _instance = new RemoteServiceFactory();
                _instance.initialize();
            }
            return _instance;
        }

        private var channelSet:ChannelSet;

        private function initialize():void {
            var appConf:ApplicationConfiguration = ApplicationConfiguration.instance;
            channelSet = new ChannelSet();
            var env:Environment = appConf.activeEnvironment;
            var channel:Channel;
            if (env.sslEnabled) {
                channel = new SecureAMFChannel(env.mainChannelId, env.mainChannelUrl);
            } else {
                channel = new AMFChannel(env.mainChannelId, env.mainChannelUrl);
            }
            channelSet.addChannel(channel);
        }

        public function getService(destination:String, resultHandler:Function, faultHandler:Function):RemoteObject {
            var service:RemoteObject = new RemoteObject(destination);
            service.channelSet = channelSet;
            service.addEventListener(ResultEvent.RESULT, resultHandler);
            service.addEventListener(FaultEvent.FAULT, faultHandler);
            return service;
        }

        public function isAuthenticated():Boolean {
            return channelSet != null ? channelSet.authenticated : false;
        }

        public function getLoginService(resultHandler:Function, faultHandler:Function):LoginService {
            return new LoginService(channelSet, resultHandler, faultHandler);
        }

        public function disconnectChannelSet():void {
            channelSet.disconnectAll();
        }
    }
}
