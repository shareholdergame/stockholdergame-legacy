package com.stockholdergame.client.mvc.mediator {
    import com.stockholdergame.client.model.dto.account.AccountOperationDto;
    import com.stockholdergame.client.model.dto.account.MyAccountDto;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.components.dialog.ConfirmationDialog;
    import com.stockholdergame.client.ui.events.BusinessActions;

    import mx.collections.ArrayCollection;
    import mx.managers.PopUpManager;

    public class ConfirmationDialogMediator extends ProxyAwareMediator {

        public static const NAME:String = "com.stockholdergame.client.mvc.mediator.ConfirmationDialogMediator";

        public function ConfirmationDialogMediator(viewComponent:ConfirmationDialog) {
            super(NAME, viewComponent);
            registerNotificationHandler(Notifications.OPERATION_CONFIRMED, handleOperationConfirmed);
            registerNotificationHandler(Notifications.ACCOUNT_DATA_REFRESHED, handleAccountDataRefreshed);
        }

        override public function onRegister():void {
            registerAction(BusinessActions.CONFIRM_OPERATION);
            registerAction(BusinessActions.RESEND_VERIFICATION_EMAIL);
            registerAction(BusinessActions.CANCEL_ACCOUNT_OPERATION);
        }

        private function handleOperationConfirmed(operation:AccountOperationDto):void {
            var operations:ArrayCollection = sessionManager.getAccountInfo().accountOperations;
            for each (var accountOperationDto:AccountOperationDto in operations) {
                if (accountOperationDto.operationType == operation.operationType) {
                    accountOperationDto.confirmed = true;
                }
            }
            confirmationDialog.accountOperations = operations;
        }

        private function handleAccountDataRefreshed(account:MyAccountDto):void {
            if (confirmationDialog != null && confirmationDialog.visible) {
                if (account.accountOperations.length == 0) {
                    PopUpManager.removePopUp(confirmationDialog);
                }
                confirmationDialog.accountOperations = account.accountOperations;
            }
        }

        private function get confirmationDialog():ConfirmationDialog {
            return ConfirmationDialog(viewComponent);
        }
    }
}
