package com.deydey.iam.infrastructure.persistence.jdbc.mappers;

import com.deydey.iam.domain.access.authorization.Role;
import com.deydey.iam.domain.access.authorization.RoleId;
import com.deydey.iam.domain.access.authorization.RoleName;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements SqlMapper<Role> {
    @Override
    public Role toEntity(ResultSet rs) throws SQLException {
        return Role.builder()
                .id(new RoleId(rs.getLong("id")))
                .roleName(RoleName.valueOf(rs.getString("role")))
                .description(rs.getString("role_description"))
                .build();
    }
}
