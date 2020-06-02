package com.deydey.iam.infrastructure.jdbc

import com.deydey.configuration.DatabaseIntegrationTest
import com.deydey.iam.domain.identity.tenant.TenantId
import com.deydey.iam.domain.identity.tenant.TenantRepository
import com.deydey.iam.domain.identity.user.Member
import com.deydey.iam.domain.identity.user.MemberRepository
import com.deydey.iam.domain.identity.user.UserId
import com.deydey.iam.domain.identity.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired

import static com.deydey.iam.testFactory.MemberTestFactory.aMember
import static com.deydey.iam.testFactory.TenantTestFactory.aPersonalTenant
import static com.deydey.iam.testFactory.UUIDGenerator.uuid
import static com.deydey.iam.testFactory.UserTestFactory.aUserOf

class MemberRepositoryIntTest extends DatabaseIntegrationTest {

    @Autowired
    MemberRepository objectUnderTest
    @Autowired
    TenantRepository tenantRepository
    @Autowired
    UserRepository userRepository

    TenantId tenantId = new TenantId(uuid())
    UserId userId = new UserId(uuid())

    def setup() {
        saveATenant()
        saveAUser()
    }

    def "can save and retrieve a member from a user id and tenant id" () {
        given: "a tenant and user id and a new member object"
            def aMemberInstance = aMember([
                    userId: userId,
                    tenantId: tenantId
            ])
        when: "saving a member"
            objectUnderTest.save(aMemberInstance)
        and: "retrieves a member from the user and tenant id"
            Member actualResult = objectUnderTest.getBy(userId, tenantId)
        then:
            actualResult.getTenantId() == tenantId
            actualResult.getUserId() == userId
    }

    def saveATenant() {
        tenantRepository.save(aPersonalTenant([tenantId: tenantId]))
    }

    def saveAUser() {
        userRepository.save(aUserOf([userId: userId]))
    }
}
