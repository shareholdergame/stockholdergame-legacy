package com.stockholdergame.client.mvc.controller {
    import com.stockholdergame.client.model.session.SessionManager;
    import com.stockholdergame.client.mvc.facade.NotificationHandlingHelper;

    import org.puremvc.as3.interfaces.INotification;
    import org.puremvc.as3.patterns.command.SimpleCommand;

    public class DispatchCommand extends SimpleCommand {

        public function DispatchCommand() {
        }

        private var notificationHelper:NotificationHandlingHelper = new NotificationHandlingHelper();

        protected function registerNotificationHandler(notificationName:String, handler:Function):void {
            notificationHelper.registerNotificationHandler(notificationName, handler);
        }

        override public function execute(notification:INotification):void {
            notificationHelper.executeHandler(notification);
        }

        protected static function get sessionManager():SessionManager {
            return SessionManager.instance;
        }
    }
}
