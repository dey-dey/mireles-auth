package com.deydey.iam.infrastructure.jdbc

import com.deydey.configuration.DatabaseIntegrationTest
import com.deydey.iam.domain.access.authorization.Role
import com.deydey.iam.domain.access.authorization.RoleRepository
import org.springframework.beans.factory.annotation.Autowired

import static com.deydey.iam.testFactory.RoleTestFactory.aRole

class RoleRepositoryIntTest extends DatabaseIntegrationTest {
    @Autowired
    RoleRepository roleRepository

    def "role repository retrives a role by name" () {
        given: "a saved role"
            Role expectedRole = aRole([:])
            saveRole(expectedRole)
        when:
            Role actualRole = roleRepository.getRoleByName(expectedRole.getRoleName())
        then:
            actualRole.id == expectedRole.id
    }

    private saveRole(Role role) {
        roleRepository.save(role);
    }
}
