package com.stockholdergame.client.mvc.mediator {
    import com.stockholdergame.client.model.dto.game.UserStatisticsList;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.components.page.RatingPage;
    import com.stockholdergame.client.ui.events.BusinessActions;

    public class RatingPageMediator extends ProxyAwareMediator {

        public static const NAME:String = "com.stockholdergame.client.mvc.mediator.RatingPageMediator";

        public function RatingPageMediator(viewComponent:RatingPage) {
            super(NAME, viewComponent);
            registerNotificationHandler(Notifications.USER_STATISTICS_LOADED, handleUserStatisticsLoaded);
        }

        override public function onRegister():void {
            registerAction(BusinessActions.GET_USER_STATISTICS);
            registerAction(BusinessActions.SHOW_SEND_MESSAGE_DIALOG);
        }

        private function handleUserStatisticsLoaded(userStatisticsList:UserStatisticsList):void {
            ratingPage.userStatisticsListData = userStatisticsList;
        }

        private function get ratingPage():RatingPage {
            return viewComponent as RatingPage;
        }
    }
}
