package com.stockholdergame.server.dao.typehandler;

import com.stockholdergame.server.model.game.InvitationStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public class InvitationStatusTypeHandler extends BaseTypeHandler {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        throw new SQLException("opeartion not supported");
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return InvitationStatus.values()[rs.getInt(columnName)].name();
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return InvitationStatus.values()[cs.getInt(columnIndex)].name();
    }
}
