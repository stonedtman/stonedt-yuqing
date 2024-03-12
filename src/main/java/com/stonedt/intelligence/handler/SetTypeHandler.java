package com.stonedt.intelligence.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class SetTypeHandler extends BaseTypeHandler<Set<?>> {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .disable(com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .disable(com.fasterxml.jackson.databind.MapperFeature.DEFAULT_VIEW_INCLUSION)
            .disable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

//    private final CollectionType collectionType = TypeFactory.defaultInstance().constructCollectionType(Set.class, String.class);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<?> parameter, JdbcType jdbcType) throws SQLException {
        String json;
        try {
            json = objectMapper.writeValueAsString(parameter);
        } catch (JsonProcessingException e) {
            throw new SQLException(e);
        }
        ps.setString(i, json);
    }

    @Override
    public Set<?> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toSet(rs.getString(columnName));
    }

    @Override
    public Set<?> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toSet(rs.getString(columnIndex));
    }

    @Override
    public Set<?> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toSet(cs.getString(columnIndex));
    }

    private Set<?> toSet(String json) throws SQLException {
        try {
            return json == null ? null : objectMapper.readValue(json, Set.class);
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }
}