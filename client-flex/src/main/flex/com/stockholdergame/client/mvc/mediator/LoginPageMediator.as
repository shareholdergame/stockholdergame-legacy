package com.stockholdergame.client.mvc.mediator {
    import com.stockholdergame.client.model.dto.LoginDto;
    import com.stockholdergame.client.model.dto.RegistrationDto;
    import com.stockholdergame.client.model.dto.ServerStatisticsDto;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.components.page.LoginPage;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.ui.events.BusinessEvent;
    import com.stockholdergame.client.util.ImageLoader;

    import flash.display.Bitmap;

    import flash.utils.ByteArray;

    import mx.styles.StyleManager;

    public class LoginPageMediator extends AbstractMediator {

        public static const NAME:String = "com.stockholdergame.client.mvc.mediator.LoginPageMediator";

        private var registrationDto:RegistrationDto;

        public function LoginPageMediator(viewComponent:LoginPage) {
            super(NAME, viewComponent);
            registerNotificationHandler(Notifications.SIGN_UP_COMPLETE, handleSignUpComplete);
            registerNotificationHandler(Notifications.PASSWORD_RESET_SUCCESSFULLY, showSignInPage);
            registerNotificationHandler(Notifications.USER_LOGGED_OUT, showSignInPage);
            registerNotificationHandler(Notifications.USER_NAME_EXISTENCE_CHECKED, handleUserAlreadyExists);
            registerNotificationHandler(Notifications.EMAIL_USAGE_CHECKED, handleEmailUsed);
            registerNotificationHandler(Notifications.CAPTCHA_IMAGE, handleCaptchaImage);
        }

        override public function onRegister():void {
            registerAction(BusinessActions.SIGN_IN);
            registerAction(BusinessActions.GET_CAPTCHA);
            registerAction(BusinessActions.RESTORE_PASSWORD);
            registerAction(BusinessActions.CHECK_USER_NAME_EXISTENCE);
            registerAction(BusinessActions.CHECK_EMAIL_USAGE);
            registerAction(BusinessActions.SHOW_RULES_PAGE);
            registerAction(BusinessActions.SET_UNAUTH_LANGUAGE);
            addEventListener(BusinessActions.SIGN_UP, onSignUp);
        }

        private function onSignUp(event:BusinessEvent):void {
            registrationDto = RegistrationDto(event.data);
            sendNotification(event.type, event.data);
        }

        private function handleSignUpComplete():void {
            if (registrationDto != null) {
                var loginDto:LoginDto = new LoginDto();
                loginDto.userName = registrationDto.userName;
                loginDto.password = registrationDto.password;
                registrationDto = null;
                sendNotification(BusinessActions.SIGN_IN, loginDto);
            }
        }

        private function handleUserAlreadyExists(exists:Boolean):void {
            if (loginPage.currentState == LoginPage.SIGN_UP_STATE) {
                loginPage.signUpPage.newUserNameTxt.setStyle("color", StyleManager.getColorName(exists ? "red" : "green"));
            }
        }

        private function handleEmailUsed(used:Boolean):void {
            if (loginPage.currentState == LoginPage.SIGN_UP_STATE) {
                loginPage.signUpPage.newUserEmailTxt.setStyle("color", StyleManager.getColorName(used ? "red" : "green"));
            }
        }

        private function handleCaptchaImage(image:ByteArray):void {
            // CAPTCHA is switched off.
            /*if (loginPage.currentState == LoginPage.SIGN_UP_STATE && image != null) {
                ImageLoader.getInstance().loadImage(image, function (bitmap:Bitmap):void {
                    loginPage.signUpPage.captchaImage.addChild(bitmap);
                });
            }*/
        }

        private function showSignInPage():void {
            loginPage.currentState = LoginPage.SIGN_IN_STATE;
        }

        private function get loginPage():LoginPage {
            return LoginPage(viewComponent);
        }
    }
}
