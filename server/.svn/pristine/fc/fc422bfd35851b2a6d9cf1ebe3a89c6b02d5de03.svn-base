package com.stockholdergame.server.dao;

import com.stockholdergame.server.model.account.AccountOperation;
import com.stockholdergame.server.model.account.OperationType;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Savin
 */
public interface AccountOperationDao extends GenericDao<AccountOperation, Long> {

    List<AccountOperation> findUncompletedOperationsWithExpiredTerm(Date expiredDate);

    List<AccountOperation> findUncompletedOperations(Long gamerId);

    AccountOperation findUncompletedOperationByType(Long gamerId, OperationType operationType);
}
