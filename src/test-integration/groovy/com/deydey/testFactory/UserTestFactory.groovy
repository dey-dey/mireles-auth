package com.deydey.testFactory

import com.deydey.iam.application.command.registration.CreateRegistrationCommand
import com.deydey.iam.domain.identity.tenant.TenantId
import com.deydey.iam.domain.identity.user.User
import com.deydey.iam.domain.identity.user.UserId

import static com.deydey.testFactory.CreateRegistrationCommendTestFactory.aCreateRegistrationCommand
import static com.deydey.testFactory.UUIDGenerator.uuid

class UserTestFactory {
    static User aUser(Map params = [:]) {
        User user = User.of(params.tenantId as TenantId ?: new TenantId(uuid()),
            params.createRegistrationCommand as CreateRegistrationCommand ?: aCreateRegistrationCommand())
        if (params.userId) {
            user.setId(params.userId as UserId)
        }
        return user
    }
}
