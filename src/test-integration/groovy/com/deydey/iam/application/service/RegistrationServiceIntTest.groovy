package com.deydey.iam.application.service

import com.deydey.common.infrastructure.spring.ApplicationConfig
import com.deydey.configuration.DatabaseIntegrationTest
import com.deydey.iam.api.dto.RegistrationDto
import com.deydey.iam.application.command.registration.CreateRegistrationCommand
import com.deydey.iam.domain.access.authorization.RoleService
import com.deydey.iam.domain.identity.tenant.TenantId
import com.deydey.iam.domain.identity.tenant.TenantRepository
import com.deydey.iam.domain.identity.user.Member
import com.deydey.iam.domain.identity.user.MemberId
import com.deydey.iam.domain.identity.user.MemberRepository
import com.deydey.iam.domain.identity.user.User
import com.deydey.iam.domain.identity.user.UserId
import com.deydey.iam.domain.identity.user.UserRepository
import com.deydey.iam.infrastructure.persistence.jdbc.repositories.RoleJdbcRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Slf4j
class RegistrationServiceIntTest extends DatabaseIntegrationTest {

    @Autowired
    private ApplicationConfig applicationConfig
    @Autowired
    private UserRepository userRepository
    @Autowired
    MemberRepository memberRepository
    @Autowired
    private TenantRepository tenantRepository
    @Autowired
    private RoleJdbcRepository roleRepository

    private RegistrationService objectUnderTest

    def setup() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder()
        RoleService roleService = new RoleService(roleRepository)
        objectUnderTest = new RegistrationService(bCryptPasswordEncoder,
                applicationConfig,
                userRepository,
                tenantRepository,
                roleService,
                memberRepository)
    }

    def "registers a personal admin" () {
        given:
            def expectedEmail = "yo@yo.com"
            CreateRegistrationCommand createRegistrationCommand = CreateRegistrationCommand.builder()
                .email(expectedEmail)
                .password("yo")
                .build()
        when:
            RegistrationDto actualResult = objectUnderTest.registerUserAsTenant(createRegistrationCommand)
            User savedUser = userRepository.getBy(new UserId(actualResult.getUserId()))
            Member savedMember = memberRepository.getBy(new UserId(actualResult.getUserId()),
                    new TenantId(actualResult.getTenantId()))
        then:
            actualResult.getUserId() == savedUser.getId().getValue()
            actualResult.getTenantId() == savedMember.getTenantId().getValue()
            actualResult.getEmail() == expectedEmail
    }
}
