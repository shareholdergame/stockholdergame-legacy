package com.stockholdergame.client.mvc.controller {
    import com.stockholdergame.client.model.dto.ConfirmationDto;
    import com.stockholdergame.client.model.dto.PasswordChangingDto;
    import com.stockholdergame.client.model.dto.ProfileDto;
    import com.stockholdergame.client.model.dto.account.AccountOperationDto;
    import com.stockholdergame.client.model.dto.account.ChangeEmailDto;
    import com.stockholdergame.client.model.dto.account.ChangeUserNameDto;
    import com.stockholdergame.client.model.dto.account.CreateFriendRequestDto;
    import com.stockholdergame.client.model.dto.account.FriendRequestStatusDto;
    import com.stockholdergame.client.model.dto.account.UserDto;
    import com.stockholdergame.client.model.dto.account.UserFilterDto;
    import com.stockholdergame.client.model.dto.account.UsersList;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.MessageResources;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.ui.message.Message;
    import com.stockholdergame.client.ui.message.MessageSeverity;

    import mx.collections.ArrayCollection;

    public class ProfileCommand extends ProxyAwareCommand {
        public function ProfileCommand() {
            registerNotificationHandler(BusinessActions.SAVE_PROFILE, handleSaveProfile);
            registerNotificationHandler(BusinessActions.CHANGE_PASSWORD, handleChangePassword);
            registerNotificationHandler(BusinessActions.CHANGE_USER_NAME, handleChangeUserName);
            registerNotificationHandler(BusinessActions.CHANGE_EMAIL, handleChangeEmail);
            registerNotificationHandler(BusinessActions.REMOVE_ACCOUNT, handleRemoveAccount);
            registerNotificationHandler(BusinessActions.RESTORE_ACCOUNT, handleRestoreAccount);
            registerNotificationHandler(BusinessActions.GET_FRIENDS, handleGetFriendsList);
            registerNotificationHandler(BusinessActions.GET_FRIEND_REQUESTS, handleGetFriendRequestsList);
            registerNotificationHandler(BusinessActions.GET_FRIEND_REQUESTS_FOR_HOME_PAGE, handleGetFriendRequestsForHomePage);
            registerNotificationHandler(BusinessActions.CONFIRM_OPERATION, handleConfirmOperation);
            registerNotificationHandler(BusinessActions.RESEND_VERIFICATION_EMAIL, handleResendVerificationEmail);
            registerNotificationHandler(BusinessActions.CANCEL_ACCOUNT_OPERATION, handleCancelAccountOperation);
            registerNotificationHandler(BusinessActions.SEND_FRIEND_REQUEST, handleSendFriendRequest);
            registerNotificationHandler(BusinessActions.CHANGE_FRIEND_REQUEST_STATUS, handleChangeFriendRequestStatus);
            registerNotificationHandler(BusinessActions.CANCEL_FRIEND_REQUEST, handleCancelFriendRequest);
            registerNotificationHandler(BusinessActions.CHANGE_LANGUAGE, handleChangeLanguage);
            registerNotificationHandler(BusinessActions.UPDATE_AVATAR, handleUpdateAvatar);
        }

        private function handleGetFriendRequestsForHomePage():void {
            var userFilter:UserFilterDto = new UserFilterDto();
            userFilter.maxResults = 3;
            userFilter.offset = 0;
            userFilter.friendFilters = [UserFilterDto.WITH_FRIEND_REQUESTS];
            socialServiceProxy.getUsers(userFilter, function (result:UsersList):void {
                // todo - filter my requests on server
                var requestorsOnly:ArrayCollection = new ArrayCollection();
                for each (var userDto:UserDto in result.users) {
                    if (userDto.friendRequestor) {
                        requestorsOnly.addItem(userDto);
                    }
                }
                sendNotification(Notifications.FRIEND_REQUESTS_HOME_PAGE_LIST_REFRESHED, requestorsOnly);
            });
        }

        private function handleGetFriendsList(userFilter:UserFilterDto):void {
            userFilter.friendFilters = [UserFilterDto.FRIENDS_ONLY];
            userFilter.showRemoved = true;
            socialServiceProxy.getUsers(userFilter, function (result:Object):void {
                sendNotification(Notifications.FRIENDS_LIST_REFRESHED, result);
            });
        }

        private function handleUpdateAvatar(update:Boolean):void {
            accountServiceProxy.updateAvatar(update, updateAvatarCallback);
        }

        private function updateAvatarCallback(result:Boolean):void {
            if (result) {
                refreshAccountCallback();
            } else {
                sendNotification(Notifications.SHOW_POPUP, new Message(MessageResources.AVATAR_NOT_FOUND, MessageSeverity.ERROR));
            }
        }

        private function handleChangeLanguage(language:String):void {
            accountServiceProxy.changeLanguage(language, refreshAccountCallback);
        }

        private function handleCancelFriendRequest(requesteeName:String):void {
            socialServiceProxy.cancelFriendRequest(requesteeName, refreshFriendsList);
        }

        private function handleChangeFriendRequestStatus(friendRequestStatus:FriendRequestStatusDto):void {
            socialServiceProxy.changeFriendRequestStatus(friendRequestStatus, refreshFriendsList);
        }

        private function handleSendFriendRequest(friendRequest:CreateFriendRequestDto):void {
            socialServiceProxy.sendFriendRequest(friendRequest, function ():void {
                sendNotification(Notifications.FRIEND_REQUEST_SENT, friendRequest.userName);
                refreshFriendsList();
            });
        }

        private function refreshFriendsList():void {
            sendNotification(Notifications.REFRESH_PROFILE_PAGE);
        }

        private function handleGetFriendRequestsList(userFilter:UserFilterDto):void {
            userFilter.friendFilters = [UserFilterDto.WITH_FRIEND_REQUESTS];
            userFilter.showRemoved = true;
            socialServiceProxy.getUsers(userFilter, function (result:UsersList):void {
                // todo - filter my requests
                /*var requestorsOnly:ArrayCollection = new ArrayCollection();
                 for each (var userDto:UserDto in result.users) {
                 if (userDto.friendRequestor) {
                 requestorsOnly.addItem(userDto);
                 }
                 } */
                sendNotification(Notifications.FRIEND_REQUESTS_LIST_REFRESHED, result/*requestorsOnly*/);
            });
        }

        private function handleRestoreAccount():void {
            accountServiceProxy.restoreAccount(refreshAccountCallback);
        }

        private function handleRemoveAccount():void {
            accountServiceProxy.removeAccount(refreshAccountCallback);
        }

        private function handleChangeUserName(changeUserNameDto:ChangeUserNameDto):void {
            accountServiceProxy.changeUserName(changeUserNameDto, changeUserNameCallback);
        }

        private function changeUserNameCallback():void {
            sendNotification(Notifications.USER_NAME_CHANGED);
            accountServiceProxy.getAccountInfo(getAccountInfoResultCallback);
        }

        private function handleChangeEmail(changeEmailDto:ChangeEmailDto):void {
            accountServiceProxy.changeEmail(changeEmailDto, changeEmailCallback);
        }

        private function changeEmailCallback():void {
            sendNotification(Notifications.EMAIL_CHANGED);
            accountServiceProxy.getAccountInfo(getAccountInfoResultCallback);
        }

        private function handleChangePassword(passwordChangingDto:PasswordChangingDto):void {
            accountServiceProxy.changePassword(passwordChangingDto, changePasswordResultCallback);
        }

        private function handleSaveProfile(profileDto:ProfileDto):void {
            accountServiceProxy.changeProfileData(profileDto, changeProfileDataCallback);
        }

        private function changeProfileDataCallback():void {
            sendNotification(Notifications.PROFILE_DATA_SAVED);
            accountServiceProxy.getAccountInfo(getAccountInfoResultCallback);
        }

        private function refreshAccountCallback():void {
            accountServiceProxy.getAccountInfo(getAccountInfoResultCallback);
        }

        private function changePasswordResultCallback():void {
            sendNotification(Notifications.SHOW_POPUP, new Message(MessageResources.PASSWORD_CHANGED, MessageSeverity.INFO));
        }

        private function getAccountInfoResultCallback(result:Object):void {
            sendNotification(Notifications.ACCOUNT_DATA_REFRESHED, result);
        }

        private function handleConfirmOperation(data:Object):void {
            var operation:AccountOperationDto = data.operation;
            var operationConfirmedCallback:Function = getOperationConfirmationHandler(operation);
            var verificationCode:String = data.verificationCode;
            if (operation.operationType == AccountOperationDto.OPER_TYPE_STATUS_CHANGED) {
                accountServiceProxy.confirmAccountStatus(new ConfirmationDto(verificationCode),
                        operationConfirmedCallback);
            } else if (operation.operationType == AccountOperationDto.OPER_TYPE_EMAIL_CHANGED) {
                accountServiceProxy.confirmNewEmail(new ConfirmationDto(verificationCode),
                        operationConfirmedCallback);
            }
        }

        private function handleResendVerificationEmail(operationType:String):void {
            accountServiceProxy.resendConfirmationEmail(operationType, resendVerificationEmailCallback);
        }

        private function resendVerificationEmailCallback():void {
            sendNotification(Notifications.SHOW_POPUP, new Message(MessageResources.VERIFICATION_EMAIL_PREPARED, MessageSeverity.INFO));
        }

        private function handleCancelAccountOperation(operationType:String):void {
            accountServiceProxy.cancelAccountOperation(operationType, refreshAccountCallback);
        }

        private function getOperationConfirmationHandler(operation:AccountOperationDto):Function {
            return function ():void {
                if (operation.operationType == AccountOperationDto.OPER_TYPE_STATUS_CHANGED) {
                    sendNotification(Notifications.ACCOUNT_STATUS_CHANGED, operation.newValue);
                }
                sendNotification(Notifications.OPERATION_CONFIRMED, operation);
                refreshAccountCallback();
            }
        }
    }
}
