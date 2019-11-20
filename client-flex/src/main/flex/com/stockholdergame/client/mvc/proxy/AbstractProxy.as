package com.stockholdergame.client.mvc.proxy {

    import com.stockholdergame.client.model.session.SessionManager;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.remote.factory.RemoteServiceFactory;
    import com.stockholdergame.client.ui.MessageResources;
    import com.stockholdergame.client.ui.message.Message;
    import com.stockholdergame.client.ui.message.MessageSeverity;

    import mx.controls.Alert;

    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.remoting.RemoteObject;

    import org.puremvc.as3.patterns.proxy.Proxy;

    public class AbstractProxy extends Proxy {

        protected var serviceName:String;

        public function AbstractProxy(proxyName:String, serviceName:String) {
            super(proxyName);
            this.serviceName = serviceName;
        }

        protected function getService(onResultFunc:Function, onFaultFunc:Function = null, destination:String = null):RemoteObject {
            return RemoteServiceFactory.instance.getService(destination == null ? serviceName : destination,
                    getResultHandler(onResultFunc),
                    getFaultHandler(onFaultFunc));
        }

        private function getFaultHandler(onFaultFunc:Function):Function {
            var faultHandler:Function;
            if (onFaultFunc != null) {
                faultHandler = function(event:FaultEvent):void {
                    onFaultFunc(event.fault.faultCode, event.fault.faultString, event.fault.faultDetail);
                }
            } else {
                faultHandler = function(event:FaultEvent):void {
                    sendNotification(Notifications.SHOW_POPUP, new Message(MessageResources.ERROR_MESSAGE, MessageSeverity.ERROR, null, null,
                            [prepareFaultString(event.fault.faultString)]));
                };
            }
            return faultHandler;
        }

        private static function prepareFaultString(faultString:String):String {
            return faultString.replace("com.stockholdergame.server.exceptions.BusinessException : ", "")
                    .replace("com.stockholdergame.server.exceptions.ApplicationException : ", "");
        }

        private function getResultHandler(onResultFunc:Function):Function {
            var resultHandler:Function;
            if (onResultFunc != null) {
                resultHandler = function(event:ResultEvent):void {
                    executeCallback(event.result, onResultFunc);
                }
            } else {
                throw new Error(this.serviceName + " Result handler isn't specified");
            }
            return resultHandler;
        }

        protected static function executeCallback(result:Object, callback:Function):void {
            var argsCount:int = callback.length;
            if (argsCount > 0) {
                callback(result);
            } else {
                callback();
            }
        }

        protected static function get sessionManager():SessionManager {
            return SessionManager.instance;
        }
    }
}
