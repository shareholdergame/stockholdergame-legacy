package com.stockholdergame.client.model.assembler {
    import com.stockholdergame.client.model.dto.ProfileDto;
    import com.stockholdergame.client.model.dto.account.AccountOperationDto;
    import com.stockholdergame.client.model.dto.account.MyAccountDto;

    import mx.collections.ArrayCollection;

    public class AccountInfoAssembler {

        public static function assemble(myAccountDto:MyAccountDto):MyAccountDto {
            if (myAccountDto.profile != null) {
                myAccountDto.profile.sexKey = getSexKey(myAccountDto.profile.sex);
            }
            assembleAccountOperations(myAccountDto);
            return myAccountDto;
        }

        private static function getSexKey(sex:String):String {
            if (sex == ProfileDto.MALE) {
                return "sex.male";
            } else if (sex == ProfileDto.FEMALE) {
                return "sex.female";
            } else {
                return "sex.unknown";
            }
        }

        private static function assembleAccountOperations(myAccountDto:MyAccountDto):void {
            var _accountOperations:ArrayCollection = myAccountDto.accountOperations;
            for each (var accountOperation:AccountOperationDto in _accountOperations) {
                accountOperation.operationDescriptionKey = getOperationDescriptionKey(accountOperation);
                if (accountOperation.operationType == AccountOperationDto.OPER_TYPE_EMAIL_CHANGED) {
                    myAccountDto.isEmailChangeOperationExists = true;
                    myAccountDto.newEmail = accountOperation.newValue;
                }else if (accountOperation.operationType == AccountOperationDto.OPER_TYPE_STATUS_CHANGED) {
                    myAccountDto.isStatusChangeOperationExists = true;
                    myAccountDto.newStatus = accountOperation.newValue;
                }
            }
        }

        private static function getOperationDescriptionKey(operation:AccountOperationDto):String {
            var key:String;
            if (operation.operationType == AccountOperationDto.OPER_TYPE_STATUS_CHANGED) {
                if (operation.newValue == MyAccountDto.ACCOUNT_STATUS_ACTIVE) {
                    if (operation.oldValue == MyAccountDto.ACCOUNT_STATUS_NEW) {
                        key = "confirm.registration.label";
                    } else if (operation.oldValue == MyAccountDto.ACCOUNT_STATUS_REMOVED) {
                        key = "confirm.restoring.label";
                    }
                } else if (operation.newValue == MyAccountDto.ACCOUNT_STATUS_REMOVED) {
                    key = "confirm.removing.label";
                }
            } else if (operation.operationType == AccountOperationDto.OPER_TYPE_EMAIL_CHANGED) {
                key = "email.changed.label";
            } else {
                key = "unknown.operation.label";
            }
            return key;
        }
    }
}
