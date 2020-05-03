package com.stockholdergame.server.dao.typehandler;

import com.stockholdergame.server.model.game.GameStatus;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 *
 */
public class GameStatusTypeHandler extends BaseTypeHandler {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType)
        throws SQLException {
        throw new SQLException("operation not supported");
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return GameStatus.values()[resultSet.getInt(columnName)].name();
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return GameStatus.values()[resultSet.getInt(i)].name();
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return GameStatus.values()[callableStatement.getInt(columnIndex)].name();
    }
}
