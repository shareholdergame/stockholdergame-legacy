package com.stockholdergame.client.mvc.proxy {

    import com.stockholdergame.client.model.dto.LoginDto;
    import com.stockholdergame.client.model.session.SessionManager;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.remote.factory.RemoteServiceFactory;
    import com.stockholdergame.client.remote.security.LoginService;
    import com.stockholdergame.client.ui.MessageResources;
    import com.stockholdergame.client.ui.message.Message;
    import com.stockholdergame.client.ui.message.MessageSeverity;

    import mx.messaging.messages.CommandMessage;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    import org.puremvc.as3.patterns.proxy.Proxy;

    public class LoginServiceProxy extends Proxy {

        public static const NAME:String = 'LoginServiceProxy';

        private var loginService:LoginService;

        public function LoginServiceProxy() {
            super(NAME);
            loginService = RemoteServiceFactory.instance.getLoginService(onResult, onFault);
        }

        public function login(loginDto:LoginDto):void {
            loginService.login(loginDto.userName, loginDto.password);
        }

        public function logout():void {
            loginService.logout();
        }

        private function onFault(event:FaultEvent):void {
            if (event.fault.faultCode == "Client.Authentication") {
                sendNotification(Notifications.SHOW_POPUP, new Message(MessageResources.AUTHENTICATION_ERROR, MessageSeverity.ERROR));
                sendNotification(Notifications.AUTHENTICATION_FAILED, event.fault);
            } else {
                sendNotification(Notifications.SHOW_POPUP, new Message(MessageResources.ERROR_MESSAGE, MessageSeverity.ERROR, null, null,
                        [event.fault.faultCode + " - " + event.fault.faultString + " - " + event.fault.faultDetail]));
            }
        }

        private function onResult(event:ResultEvent):void {
            var oper:uint = (event.token.message as CommandMessage).operation;
            if (oper == CommandMessage.LOGIN_OPERATION) {
                sendNotification(Notifications.AUTHENTICATION_SUCCESSFUL, event.result);
            } else if (oper == CommandMessage.LOGOUT_OPERATION) {
                MessagingServiceProxy(facade.retrieveProxy(MessagingServiceProxy.NAME)).unsubscribe();
                SessionManager.instance.clearSession();
                sendNotification(Notifications.USER_LOGGED_OUT);
            }
        }
    }
}
