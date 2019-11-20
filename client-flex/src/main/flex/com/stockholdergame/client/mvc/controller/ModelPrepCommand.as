package com.stockholdergame.client.mvc.controller {

    import com.stockholdergame.client.mvc.proxy.AccountServiceProxy;

    import com.stockholdergame.client.mvc.proxy.GameServiceProxy;
    import com.stockholdergame.client.mvc.proxy.LoginServiceProxy;

    import com.stockholdergame.client.mvc.proxy.MessagingServiceProxy;
    import com.stockholdergame.client.mvc.proxy.SocialServiceProxy;

    import org.puremvc.as3.interfaces.INotification;
    import org.puremvc.as3.patterns.command.SimpleCommand;

    public class ModelPrepCommand extends SimpleCommand {

        override public function execute(notification:INotification):void {
            facade.registerProxy(new AccountServiceProxy());
            facade.registerProxy(new LoginServiceProxy());
            facade.registerProxy(new SocialServiceProxy());
            facade.registerProxy(new GameServiceProxy());
            facade.registerProxy(new MessagingServiceProxy());
        }
    }
}