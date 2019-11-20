package com.stockholdergame.client.mvc.mediator {

    import com.stockholdergame.client.model.session.SessionManager;
    import com.stockholdergame.client.mvc.facade.NotificationHandlingHelper;
    import com.stockholdergame.client.ui.events.BusinessEvent;

    import flash.events.Event;
    import flash.events.IEventDispatcher;
    import flash.utils.getDefinitionByName;

    import org.puremvc.as3.interfaces.IMediator;
    import org.puremvc.as3.interfaces.INotification;
    import org.puremvc.as3.interfaces.IProxy;
    import org.puremvc.as3.patterns.mediator.Mediator;

    public class AbstractMediator extends Mediator {

        public function AbstractMediator(name:String, viewComponent:Object) {
            super(name, viewComponent);
        }

        private var notificationHelper:NotificationHandlingHelper = new NotificationHandlingHelper();

        protected function registerNotificationHandler(notificationName:String, handler:Function):void {
            notificationHelper.registerNotificationHandler(notificationName, handler);
        }

        override public function listNotificationInterests():Array {
            var interests:Array = [];
            for (var key:String in notificationHelper.notificationHandlersMap) {
                interests.push(key);
            }
            return interests;
        }

        override public function handleNotification(notification:INotification):void {
            notificationHelper.executeHandler(notification);
        }

        protected function getProxy(proxyName:String):IProxy {
            return facade.retrieveProxy(proxyName);
        }

        protected function getMediator(mediatorName:String):IMediator {
            return facade.retrieveMediator(mediatorName);
        }

        protected function addEventListener(eventType:String, handler:Function):void {
            viewComponent.addEventListener(eventType, handler);
        }

        protected function registerMediatorIfNotExists(mediatorName:String, viewComponent:Object):void {
            if (viewComponent == null) {
                throw new Error("Can't register mediator " + mediatorName + ". Argument 'viewComponent' is null");
            }
            if (!facade.hasMediator(mediatorName)) {
                var classDefinition:Class = Class(getDefinitionByName(mediatorName));
                facade.registerMediator(new classDefinition(viewComponent));
            }
        }

        protected function registerAction(eventType:String, notificationName:String = null, viewComponent:IEventDispatcher = null):void {
            var _notificationName:String = notificationName != null ? notificationName : eventType;
            var handler:Function = function(event:Event):void {
                if (event is BusinessEvent) {
                    sendNotification(_notificationName, BusinessEvent(event).data);
                } else {
                    sendNotification(_notificationName);
                }
            };

            if (viewComponent == null) {
                addEventListener(eventType, handler);
            } else {
                viewComponent.addEventListener(eventType, handler);
            }
        }

        protected static function get sessionManager():SessionManager {
            return SessionManager.instance;
        }
    }
}
