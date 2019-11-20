package com.stockholdergame.server.services.account.impl;

import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.model.account.AccountOperation;
import com.stockholdergame.server.model.account.AccountStatus;
import com.stockholdergame.server.model.account.OperationType;
import com.stockholdergame.server.services.account.AccountAdministrationService;
import com.stockholdergame.server.services.account.AccountLifecycleService;
import com.stockholdergame.server.services.account.AccountOperationsService;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Savin
 */
@Service("accountAdministrationService")
public class AccountAdministrationServiceImpl implements AccountAdministrationService {

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Autowired
    private AccountLifecycleService accountLifecycleService;

    @Autowired
    private AccountOperationsService accountOperationsService;

    public void setGamerAccountDao(GamerAccountDao gamerAccountDao) {
        this.gamerAccountDao = gamerAccountDao;
    }

    public void setAccountLifecycleService(AccountLifecycleService accountLifecycleService) {
        this.accountLifecycleService = accountLifecycleService;
    }

    public void setAccountOperationsService(AccountOperationsService accountOperationsService) {
        this.accountOperationsService = accountOperationsService;
    }

    public void cancelOperationsWithExpiredTerm() {
        Date currentDate = new Date();
        List<AccountOperation> accountOperations =
                accountOperationsService.findUncompletedOperationsWithExpiredTerm(currentDate);
        for (AccountOperation accountOperation : accountOperations) {
            if (OperationType.STATUS_CHANGED.equals(accountOperation.getOperationType())
                    && AccountStatus.NEW.equals(AccountStatus.values()[NumberUtils.toInt(accountOperation.getOldValue())])) {
                accountLifecycleService.removeAccount(accountOperation.getGamerId());
            } else {
                accountOperationsService.cancelUncompletedOperation(accountOperation.getId());
            }
        }
    }

    public void transferRemovedToRemovedCompletely() {
        List<Long> removedGamerIds = gamerAccountDao.findRemovedGamerIdsWithExpiredTerm(new Date());
        for (Long gamerId : removedGamerIds) {
            accountLifecycleService.removeAccount(gamerId);
        }
    }
}
