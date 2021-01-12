package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.AccountOperationDao;
import com.stockholdergame.server.model.account.AccountOperation;

import com.stockholdergame.server.model.account.OperationType;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author Alexander Savin
 */
@Repository
public class AccountOperationDaoImpl extends BaseDao<AccountOperation, Long> implements AccountOperationDao {

    public List<AccountOperation> findUncompletedOperationsWithExpiredTerm(Date expiredDate) {
        return findList("AccountOperation.findUncompletedOperationsWithExpiredTerm",
            expiredDate);
    }

    public List<AccountOperation> findUncompletedOperations(Long gamerId) {
        return findList("AccountOperation.findUncompletedOperations", gamerId);
    }

    public AccountOperation findUncompletedOperationByType(Long gamerId, OperationType operationType) {
        return findSingleObject("AccountOperation.findUncompletedOperationsByType", gamerId, operationType);
    }

    @Override
    public AccountOperation findUncompletedOperationByCode(String verificationCode) {
        return findSingleObject("AccountOperation.findUncompletedOperationByCode", verificationCode);
    }
}
