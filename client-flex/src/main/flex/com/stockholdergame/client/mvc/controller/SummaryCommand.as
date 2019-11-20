package com.stockholdergame.client.mvc.controller {
    import com.stockholdergame.client.model.dto.ServerStatisticsDto;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.mvc.facade.Notifications;

    import mx.collections.ArrayCollection;

    public class SummaryCommand extends ProxyAwareCommand {
        public function SummaryCommand() {
            registerNotificationHandler(BusinessActions.GET_SUMMARY, handleGetSummary);
            registerNotificationHandler(BusinessActions.GET_SERVER_STATISTICS, handleGetServerStatistics);
            registerNotificationHandler(BusinessActions.GET_LAST_EVENTS, handleGetLastEvents);
        }

        private function handleGetLastEvents():void {
            socialServiceProxy.getLastEvents(getLastEventsCallback);
        }

        private function getLastEventsCallback(result:ArrayCollection):void {
            sendNotification(Notifications.LAST_EVENTS_LOADED, result);
        }

        private function handleGetServerStatistics():void {
            socialServiceProxy.getServerStatistics(getServerStatisticsCallback);
        }

        private function getServerStatisticsCallback(serverStatistics:ServerStatisticsDto):void {
            sendNotification(Notifications.SERVER_STATISTICS_LOADED, serverStatistics);
        }

        private function handleGetSummary():void {
            gameServiceProxy.getGamerActivitySummary(getGamerActivitySummaryCallback);
        }

        private function getGamerActivitySummaryCallback(result:Object):void {
            sendNotification(Notifications.SUMMARY_LOADED, result);
        }
    }
}
