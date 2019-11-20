package com.stockholdergame.client.mvc.controller {

    import com.stockholdergame.client.mvc.proxy.AccountServiceProxy;
    import com.stockholdergame.client.mvc.proxy.GameServiceProxy;
    import com.stockholdergame.client.mvc.proxy.MessagingServiceProxy;
    import com.stockholdergame.client.mvc.proxy.SocialServiceProxy;

    import org.puremvc.as3.patterns.proxy.Proxy;

    public class ProxyAwareCommand extends DispatchCommand {

        function ProxyAwareCommand() {
        }

        protected function getProxy(name:String):Proxy {
            return facade.retrieveProxy(name) as Proxy;
        }

        protected function get socialServiceProxy():SocialServiceProxy {
            return SocialServiceProxy(getProxy(SocialServiceProxy.NAME));
        }

        protected function get gameServiceProxy():GameServiceProxy {
            return GameServiceProxy(getProxy(GameServiceProxy.NAME));
        }

        protected function get accountServiceProxy():AccountServiceProxy {
            return AccountServiceProxy(getProxy(AccountServiceProxy.NAME));
        }

        protected function get messagingServiceProxy():MessagingServiceProxy {
            return MessagingServiceProxy(getProxy(MessagingServiceProxy.NAME));
        }
    }
}
