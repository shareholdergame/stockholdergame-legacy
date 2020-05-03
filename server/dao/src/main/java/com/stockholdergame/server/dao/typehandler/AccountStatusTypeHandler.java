package com.stockholdergame.server.dao.typehandler;

import com.stockholdergame.server.model.account.AccountStatus;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * @author Alexander Savin
 *         Date: 1.11.11 21.15
 */
public class AccountStatusTypeHandler extends BaseTypeHandler {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        throw new SQLException("operation not supported");
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return AccountStatus.values()[resultSet.getInt(columnName)].name();
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return AccountStatus.values()[resultSet.getInt(i)].name();
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return AccountStatus.values()[callableStatement.getInt(columnIndex)].name();
    }
}
