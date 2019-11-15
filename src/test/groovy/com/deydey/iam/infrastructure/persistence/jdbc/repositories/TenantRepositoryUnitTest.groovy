package com.deydey.iam.infrastructure.persistence.jdbc.repositories

import com.deydey.iam.domain.identity.tenant.Tenant
import com.deydey.iam.domain.identity.tenant.TenantId
import com.deydey.iam.domain.identity.tenant.TenantRepository
import com.deydey.iam.infrastructure.persistence.jdbc.mappers.TenantMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

import static com.deydey.iam.testFactory.TenantTestFactory.aPersonalTenant
import static com.deydey.iam.testFactory.UUIDGenerator.uuid

class TenantRepositoryUnitTest extends Specification {
    TenantRepository tenantRepository
    NamedParameterJdbcTemplate jdbcTemplate

    def setup() {
        jdbcTemplate = Mock(NamedParameterJdbcTemplate)
        tenantRepository = new TenantJdbcRepository(jdbcTemplate)
    }

    def "gets tenant by id" () {
        given:
            TenantId tenantId = new TenantId(uuid())
            Tenant expected = aPersonalTenant([tenantId: tenantId])
        when:
            Tenant actual = tenantRepository.getBy(expected.getTenantId())
        then:
            1 * jdbcTemplate.query(_ as String, _ as MapSqlParameterSource, _ as TenantMapper) >> Arrays.asList(expected)
            actual.tenantId == tenantId
    }

    def "throws if tenant doesn't exist by id"() {
        given:
            TenantId tenantId = new TenantId(uuid())
            Tenant expected = aPersonalTenant([tenantId: tenantId])
        when:
            Tenant actual = tenantRepository.getBy(expected.getTenantId())
        then:
            1 * jdbcTemplate.query(_ as String, _ as MapSqlParameterSource, _ as TenantMapper) >> []
            thrown(EntityNotFoundException)
    }
}
