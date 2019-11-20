package com.stockholdergame.client.mvc.controller {

    import com.stockholdergame.client.model.dto.RegistrationDto;
    import com.stockholdergame.client.model.dto.ServerStatisticsDto;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.model.dto.LoginDto;

    import com.stockholdergame.client.mvc.proxy.LoginServiceProxy;
    import com.stockholdergame.client.mvc.proxy.MessagingServiceProxy;
    import com.stockholdergame.client.ui.events.BusinessActions;

    import flash.utils.ByteArray;

    public class LoginCommand extends ProxyAwareCommand {

        public function LoginCommand() {
            registerNotificationHandler(BusinessActions.SIGN_IN, handleSignIn);
            registerNotificationHandler(BusinessActions.SIGN_OUT, handleSignOut);
            registerNotificationHandler(BusinessActions.RESTORE_PASSWORD, handleRestorePassword);
            registerNotificationHandler(BusinessActions.SIGN_UP, handleSignUp);
            registerNotificationHandler(BusinessActions.CHECK_USER_NAME_EXISTENCE, handleCheckUserExistence);
            registerNotificationHandler(BusinessActions.CHECK_EMAIL_USAGE, handleCheckEmailUsage);
            registerNotificationHandler(BusinessActions.GET_CAPTCHA, handleGetCaptcha);
            registerNotificationHandler(BusinessActions.SET_UNAUTH_LANGUAGE, handleSetUnauthLanguage);
        }

        private function handleSetUnauthLanguage(language:String):void {
            accountServiceProxy.setUnauthLanguage(language, function():void {});
        }

        private function handleGetCaptcha():void {
            accountServiceProxy.getCaptcha(getCaptchaCallback);
        }

        private function getCaptchaCallback(result:ByteArray):void {
            sendNotification(Notifications.CAPTCHA_IMAGE, result);
        }

        private function handleCheckEmailUsage(email:String):void {
            accountServiceProxy.checkEmailUsage(email, checkEmailUsageCallback);
        }

        private function checkEmailUsageCallback(result:Boolean):void {
            sendNotification(Notifications.EMAIL_USAGE_CHECKED, result);
        }

        private function handleCheckUserExistence(newUserName:String):void {
            accountServiceProxy.checkUserNameExistence(newUserName, checkUserExistenceCallback);
        }

        private function checkUserExistenceCallback(result:Boolean):void {
            sendNotification(Notifications.USER_NAME_EXISTENCE_CHECKED, result);
        }

        private function handleSignUp(registrationDto:RegistrationDto):void {
            accountServiceProxy.registerNewUser(registrationDto, registerNewUserCallback);
        }

        private function registerNewUserCallback():void {
            sendNotification(Notifications.SIGN_UP_COMPLETE);
        }

        private function handleRestorePassword(userOrEmail:String):void {
            accountServiceProxy.resetPassword(userOrEmail, resetPasswordCallback);
        }

        private function resetPasswordCallback():void {
            sendNotification(Notifications.PASSWORD_RESET_SUCCESSFULLY);
        }

        private function handleSignOut():void {
            (getProxy(MessagingServiceProxy.NAME) as MessagingServiceProxy).unsubscribe();
            (getProxy(LoginServiceProxy.NAME) as LoginServiceProxy).logout();
        }

        private function handleSignIn(loginDto:LoginDto):void {
            (getProxy(LoginServiceProxy.NAME) as LoginServiceProxy).login(loginDto);
        }
    }
}
