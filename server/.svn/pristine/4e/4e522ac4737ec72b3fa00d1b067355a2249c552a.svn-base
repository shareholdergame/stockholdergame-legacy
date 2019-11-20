package com.stockholdergame.server.util;

import com.stockholdergame.server.helpers.MD5Helper;
import com.stockholdergame.server.model.account.AccountStatus;
import java.util.Date;
import org.apache.commons.lang.time.DateUtils;

/**
 * @author Alexander Savin
 *         Date: 11.1.12 21.46
 */
public final class AccountUtils {

    private AccountUtils() {
    }

    public static String createHashWithSalt(String string) {
        return MD5Helper.generateMD5hashWithSalt(string);
    }

    public static Date calculateOperationExpirationDate(Date date) {
        return DateUtils.addDays(date, 3);
    }

    public static Date calculateRemovalDate(Date date) {
        return DateUtils.addDays(date, 30);
    }

    public static boolean isRemoved(AccountStatus status) {
        return AccountStatus.REMOVED.equals(status) || AccountStatus.REMOVED_COMPLETELY.equals(status);
    }
}
