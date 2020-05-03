package com.stockholdergame.server.dao.typehandler;

import com.stockholdergame.server.model.game.Game;
import com.stockholdergame.server.model.game.GameInitiationMethod;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public class GameInitiationMethodTypeHandler extends BaseTypeHandler {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        throw new SQLException("operation not supported");
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return GameInitiationMethod.values()[rs.getInt(columnName)].name();
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return GameInitiationMethod.values()[resultSet.getInt(i)].name();
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return GameInitiationMethod.values()[cs.getInt(columnIndex)].name();
    }
}
