package com.stockholdergame.client.mvc.controller {
    import com.stockholdergame.client.model.dto.account.UserFilterDto;
    import com.stockholdergame.client.model.dto.game.UserStatisticsFilterDto;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.events.BusinessActions;

    public class SearchUserCommand extends ProxyAwareCommand {
        public function SearchUserCommand() {
            registerNotificationHandler(BusinessActions.FILTER_USERS, handleFilterUsers);
            registerNotificationHandler(BusinessActions.FILTER_INVITED_USERS, handleFilterInvitedUsers);
            registerNotificationHandler(BusinessActions.GET_USER_STATISTICS, handleGetUserStatistics);
            registerNotificationHandler(BusinessActions.GET_TOP10, handleGetUserStatistics);
        }

        private function handleFilterInvitedUsers(filterDto:UserFilterDto):void {
            socialServiceProxy.getUsers(filterDto, getInvitedUsersCallback);
        }

        private function getInvitedUsersCallback(result:Object):void {
            sendNotification(Notifications.INVITED_USERS_LIST_LOADED, result);
        }

        private function handleFilterUsers(filterDto:UserFilterDto):void {
            socialServiceProxy.getUsers(filterDto, getUsersCallback);
        }

        private function getUsersCallback(result:Object):void {
            sendNotification(Notifications.USERS_LIST_LOADED, result);
        }

        private function handleGetUserStatistics(filterDto:UserStatisticsFilterDto):void {
            socialServiceProxy.getUserStatistics(filterDto, filterDto.showTop10 ? getTop10Callback : getUserStatisticsCallback);
        }

        private function getTop10Callback(result:Object):void {
            sendNotification(Notifications.TOP10_LOADED, result);
        }

        private function getUserStatisticsCallback(result:Object):void {
            sendNotification(Notifications.USER_STATISTICS_LOADED, result);
        }
    }
}
