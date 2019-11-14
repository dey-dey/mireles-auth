package com.deydey.iam.testFactory

import com.deydey.common.infrastructure.spring.ApplicationConfig
import com.deydey.iam.application.command.registration.CreateRegistrationCommand
import com.deydey.iam.domain.identity.tenant.TenantId
import com.deydey.iam.domain.identity.user.Member
import com.deydey.iam.domain.identity.user.UserId
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import static com.deydey.iam.testFactory.CreateRegistrationCommendTestFactory.aCreateRegistrationCommand
import static com.deydey.iam.testFactory.UUIDGenerator.uuid

class MemberTestFactory {
    static aMember(Map params = [:]) {
        return Member.of(
                params.userId as UserId ?: new UserId(uuid()),
                params.tenantId as TenantId ?: new TenantId(uuid()),
                params.createRegistrationCommand as CreateRegistrationCommand ?: aCreateRegistrationCommand(),
                params.bCryptPasswordEncoder as BCryptPasswordEncoder ?: new BCryptPasswordEncoder(),
                params.applicationConfig as ApplicationConfig ?: new ApplicationConfig("asecret"))
    }

}
