package com.deydey.iam.application.service

import com.deydey.common.infrastructure.spring.ApplicationConfig
import com.deydey.configuration.DatabaseIntegrationTest
import com.deydey.iam.application.command.registration.CreateRegistrationCommand
import com.deydey.iam.domain.access.authorization.RoleService
import com.deydey.iam.domain.identity.tenant.TenantRepository
import com.deydey.iam.domain.identity.user.MemberRepository
import com.deydey.iam.domain.identity.user.User
import com.deydey.iam.domain.identity.user.UserRepository
import com.deydey.iam.infrastructure.persistence.jdbc.repositories.RoleJdbcRepository
import com.deydey.iam.infrastructure.persistence.jdbc.repositories.TenantJdbcRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import javax.sql.DataSource

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
            CreateRegistrationCommand createRegistrationCommand = CreateRegistrationCommand.builder()
                .email("yo@yo.com")
                .password("yo")
                .build()
        when:
            User actualResult = objectUnderTest.registerUserAsTenant(createRegistrationCommand).get()
            User savedUser = userRepository.getUserBy(actualResult.getId()).get()
        then:
            log.info("actualResult.getId() ${actualResult.getId().value} ${savedUser.getId().value}")
            actualResult.getId() == savedUser.getId()
//            savedUser.userIdentityInformation.defaultEmail == "yo@yo.com"
    }
}
