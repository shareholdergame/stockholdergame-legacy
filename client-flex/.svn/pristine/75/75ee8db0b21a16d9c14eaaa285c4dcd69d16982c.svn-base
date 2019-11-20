package com.stockholdergame.client.mvc.proxy {
    import com.stockholdergame.client.model.assembler.AccountInfoAssembler;
    import com.stockholdergame.client.model.dto.ConfirmationDto;
    import com.stockholdergame.client.model.dto.PasswordChangingDto;
    import com.stockholdergame.client.model.dto.ProfileDto;
    import com.stockholdergame.client.model.dto.RegistrationDto;
    import com.stockholdergame.client.model.dto.account.ChangeEmailDto;
    import com.stockholdergame.client.model.dto.account.ChangeUserNameDto;
    import com.stockholdergame.client.model.dto.account.MyAccountDto;
    import com.stockholdergame.client.model.dto.account.OperationTypeDto;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.remote.factory.RemoteServiceFactory;
    import com.stockholdergame.client.ui.MessageResources;
    import com.stockholdergame.client.ui.message.Message;
    import com.stockholdergame.client.ui.message.MessageSeverity;

    import mx.resources.ResourceManager;

    public class AccountServiceProxy extends AbstractProxy {

        public static const NAME:String = 'AccountServiceProxy';

        public function AccountServiceProxy() {
            super(NAME, RemoteServiceFactory.ACCOUNT_SERVICE);
        }

        public function registerNewUser(registrationDto:RegistrationDto, resultHandler:Function):void {
            getService(resultHandler).registerNewUser(registrationDto);
        }

        public function checkUserNameExistence(userName:String, resultHandler:Function):void {
            getService(resultHandler).checkUserNameExistence(userName);
        }

        public function changePassword(passwordChangingDto:PasswordChangingDto, resultHandler:Function):void {
            getService(resultHandler).changePassword(passwordChangingDto);
        }

        public function changeEmail(changeEmailDto:ChangeEmailDto, resultHandler:Function):void {
            getService(resultHandler).changeEmail(changeEmailDto);
        }

        public function resetPassword(userName:String, resultHandler:Function):void {
            getService(resultHandler).resetPassword(userName);
        }

        public function getAccountInfo(resultHandler:Function):void {
            getService(
                    function (result:Object):void {
                        var myAccountDto:MyAccountDto = result as MyAccountDto;
                        if (myAccountDto == null || myAccountDto.userName == null || myAccountDto.email == null) {
                            sendNotification(Notifications.SHOW_POPUP,
                                    new Message(MessageResources.ACCOUNT_INFO_EMPTY, MessageSeverity.ERROR));
                            return;
                        }
                        myAccountDto = AccountInfoAssembler.assemble(myAccountDto);
                        ResourceManager.getInstance().localeChain = [myAccountDto.locale];
                        sessionManager.setAccountInfo(myAccountDto);
                        resultHandler(result);
                    }).getAccountInfo();
        }

        public function confirmAccountStatus(confirmationDto:ConfirmationDto, resultHandler:Function):void {
            getService(resultHandler).confirmAccountStatus(confirmationDto);
        }

        public function removeAccount(resultHandler:Function):void {
            getService(resultHandler).removeAccount();
        }

        public function restoreAccount(resultHandler:Function):void {
            getService(resultHandler).restoreAccount();
        }

        public function confirmNewEmail(confirmationDto:ConfirmationDto, resultHandler:Function):void {
            getService(resultHandler).confirmNewEmail(confirmationDto);
        }

        public function resendConfirmationEmail(operationType:String, resultHandler:Function):void {
            getService(resultHandler).resendConfirmationEmail(new OperationTypeDto(operationType));
        }

        public function cancelAccountOperation(operationType:String, resultHandler:Function):void {
            getService(resultHandler).cancelAccountOperation(new OperationTypeDto(operationType));
        }

        public function changeProfileData(profileDto:ProfileDto, resultCallback:Function):void {
            getService(resultCallback).changeProfileData(profileDto);
        }

        public function checkEmailUsage(email:String, resultHandler:Function):void {
            getService(resultHandler).checkEmailUsage(email);
        }

        public function changeUserName(changeUserNameDto:ChangeUserNameDto, resultHandler:Function):void {
            getService(resultHandler).changeUserName(changeUserNameDto);
        }

        public function changeLanguage(language:String, resultHandler:Function):void {
            getService(resultHandler).changeLanguage(language);
        }

        public function getCaptcha(resultHandler:Function):void {
            getService(resultHandler).getCaptcha();
        }

        public function updateAvatar(update:Boolean, resultHandler:Function):void {
            getService(resultHandler).updateAvatar(update);
        }

        public function setUnauthLanguage(language:String, resultHandler:Function):void {
            getService(resultHandler).setUnauthLanguage(language);
        }
    }
}
