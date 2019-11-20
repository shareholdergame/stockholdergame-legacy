package com.stockholdergame.client.mvc.facade {
    import org.puremvc.as3.interfaces.INotification;

    public class NotificationHandlingHelper {
        public function NotificationHandlingHelper() {
        }

        private var _notificationHandlersMap:Array = [];

        public function get notificationHandlersMap():Array {
            return _notificationHandlersMap;
        }

        public function registerNotificationHandler(notificationName:String, handler:Function):void {
            if (_notificationHandlersMap[notificationName] == null) {
                _notificationHandlersMap[notificationName] = handler;
            } else {
                throw new Error("Handler for notification '" + notificationName + "' already registered.");
            }
        }

        public function executeHandler(notification:INotification):void {
            var name:String = notification.getName();
            var data:Object = notification.getBody();

            var handler:Function = _notificationHandlersMap[name];
            if (handler != null) {
                var argsCount:int = handler.length;
                if (argsCount > 0) {
                    handler(data);
                } else {
                    handler();
                }
            }
        }
    }
}
