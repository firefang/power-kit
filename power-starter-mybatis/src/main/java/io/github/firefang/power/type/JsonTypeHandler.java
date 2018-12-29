package io.github.firefang.power.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import io.github.firefang.power.common.util.JsonUtil;

/**
 * JSON类型处理器
 * 
 * @author xinufo
 *
 */
public class JsonTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, JsonUtil.stringify(parameter));
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return JsonUtil.parse(rs.getString(columnName), Object.class);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return JsonUtil.parse(rs.getString(columnIndex), Object.class);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JsonUtil.parse(cs.getString(columnIndex), Object.class);
    }

}
