package com.deydey.iam.testFactory

import com.deydey.common.infrastructure.persistence.AuditInformation
import com.deydey.iam.domain.access.authorization.Role
import com.deydey.iam.domain.access.authorization.RoleId
import com.deydey.iam.domain.access.authorization.RoleName

class RoleTestFactory {
    static Role aRole(Map params = [:]) {
        return Role.builder()
                .id(params.roleId as RoleId ?: new RoleId(1L))
                .roleName(params.roleName as RoleName ?: RoleName.ADMIN)
                .description(params as String ?: "a role description")
                .auditInformation(AuditInformation.now())
                .build()
    }
}
