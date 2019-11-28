package com.deydey.iam.domain.identity.tenant

import com.deydey.iam.domain.access.authorization.Role
import com.deydey.iam.domain.identity.user.Member
import spock.lang.Specification

import static com.deydey.iam.testFactory.MemberTestFactory.aMember
import static com.deydey.iam.testFactory.RoleTestFactory.aRole
import static com.deydey.iam.testFactory.TenantTestFactory.aPersonalTenant

class TenantUnitTest extends Specification {

    def "a tenant can register a member with roles" () {
        given:
            Tenant tenant = aPersonalTenant()
            Member member = aMember()
            member.setTenantId(tenant.getTenantId())
            Set<Role> roles = Set.of(aRole())
        when: "registering one member"
            tenant.registerMemberWithRole(member.getId(), roles)
        then: "the membership size increases"
            tenant.tenantMemberships.size() == 1
        when: "deactivating a member"
            TenantMember registeredTenant = tenant.tenantMemberships.first()
            tenant.retireTenantMember(member.getId())
        then:
            tenant.tenantMemberships.size() == 0
            registeredTenant.retiredOn != null
    }

    def "a tenant member invariants are maintained on creation" () {
        given: "a tenant member and some roles"
            Tenant tenant = aPersonalTenant()
            Member member = aMember()
            member.setTenantId(tenant.getTenantId())
            Set<Role> roles = Set.of(aRole())
        when:
            TenantMember actual = TenantMember.of(tenant.getTenantId(), member.getId(), roles)
        then:
            actual.startingOn != null
            actual.memberId == member.getId()
            actual.tenantId == tenant.getTenantId()
            actual.roles.first().roleId == roles.first().id
    }

    def "a tenant member role can be retired" () {
        given:
            TenantMemberRole tenantMemberRole = TenantMemberRole.builder().build()
        when:
            tenantMemberRole.retire()
        then:
            tenantMemberRole.retiredOn != null
    }
}
