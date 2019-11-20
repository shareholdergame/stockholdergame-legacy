package com.stockholdergame.client.remote.security {
    import mx.messaging.ChannelSet;
    import mx.rpc.AsyncResponder;
    import mx.rpc.AsyncToken;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class LoginService {

        private var _channelSet:ChannelSet;
        private var asyncResponder:AsyncResponder;

        public function LoginService(channelSet:ChannelSet, resultHandler:Function, faultHandler:Function) {
            this._channelSet = channelSet;
            this.asyncResponder =  new AsyncResponder(function(result:ResultEvent, token:Object = null):void {
                                                  resultHandler.call(this, result)
                                              },
                                              function(result:FaultEvent, token:Object = null):void {
                                                  faultHandler.call(this, result)
                                              });
        }

        public function login(userName:String, password:String):void {
            if (!_channelSet.authenticated) {
                var token:AsyncToken = _channelSet.login(userName, password);
                token.addResponder(asyncResponder);
            }
        }

        public function logout():void {
            if (_channelSet.authenticated) {
                var token:AsyncToken = _channelSet.logout();
                token.addResponder(asyncResponder);
            }
        }
    }
}