package com.stockholdergame.client.mvc.mediator {
    import com.stockholdergame.client.mvc.proxy.AccountServiceProxy;
    import com.stockholdergame.client.mvc.proxy.GameServiceProxy;
    import com.stockholdergame.client.mvc.proxy.SocialServiceProxy;

    public class ProxyAwareMediator extends AbstractMediator {

        public function ProxyAwareMediator(name:String, viewComponent:Object) {
            super(name, viewComponent);
        }

        protected function get gameServiceProxy():GameServiceProxy {
            return GameServiceProxy(getProxy(GameServiceProxy.NAME));
        }

        protected function get accountServiceProxy():AccountServiceProxy {
            return AccountServiceProxy(getProxy(AccountServiceProxy.NAME));
        }

        protected function get socialServiceProxy():SocialServiceProxy {
            return SocialServiceProxy(getProxy(SocialServiceProxy.NAME));
        }
    }
}
