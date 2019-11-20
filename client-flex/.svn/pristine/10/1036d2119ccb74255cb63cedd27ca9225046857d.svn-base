package com.stockholdergame.client.mvc.controller {

    import com.stockholdergame.client.ClientApplication;
    import com.stockholdergame.client.mvc.mediator.ApplicationMediator;

    import org.puremvc.as3.interfaces.INotification;
    import org.puremvc.as3.patterns.command.SimpleCommand;

    public class ViewPrepCommand extends SimpleCommand {

        override public function execute(notification:INotification):void {
            var app:ClientApplication = notification.getBody() as ClientApplication;
            facade.registerMediator(new ApplicationMediator(app));
        }
    }
}