package com.deydey.iam.infrastructure.persistence.jdbc.repositories


import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import spock.lang.Specification
import com.deydey.iam.domain.identity.tenant.Tenant
import com.deydey.iam.domain.identity.user.Member
import com.deydey.iam.domain.identity.user.MemberRepository
import com.deydey.iam.domain.identity.user.User
import com.deydey.iam.domain.identity.user.UserId

import static java.util.Arrays.asList
import static com.deydey.iam.testFactory.UUIDGenerator.uuid
import static com.deydey.iam.testFactory.MemberTestFactory.aMember
import static com.deydey.iam.testFactory.UserTestFactory.aUser
import static com.deydey.iam.testFactory.UserTestFactory.aUserOf
import static com.deydey.iam.testFactory.TenantTestFactory.aPersonalTenant

class MemberRepositoryUnitTest extends Specification {
    MemberRepository objectUnderTest
    NamedParameterJdbcTemplate jdbcTemplate

    def setup() {
        jdbcTemplate = Mock(NamedParameterJdbcTemplate)
        objectUnderTest = new MemberJdbcRepository(jdbcTemplate)
    }

    def "member repository gets a member and hydrates the user and tenant id" () {
        given: "a user and a tenant"
            Tenant expectedTenant = aPersonalTenant()
            User expectedUser = aUserOf([
                    userId: new UserId(uuid()),
            ])
        and: "an expected member without tenant and user id"
            Member expected = aMember()
            expected.setTenantId(null)
            expected.setUserId(null)
        when:
            Member actual = objectUnderTest.getBy(expectedUser.getId(), expectedTenant.getTenantId())
        then:
            1 * jdbcTemplate.query(_ as String,
                    _ as MapSqlParameterSource,
                    _ as RowMapper) >> asList(expected)
            actual.id == expected.getId()
            actual.tenantId == expected.getTenantId()
            actual.userId == expected.getUserId()
    }
}
