package com.deydey.iam.testFactory

import com.deydey.iam.application.command.registration.CreateRegistrationCommand
import com.deydey.iam.domain.identity.tenant.Tenant
import com.deydey.iam.domain.identity.tenant.TenantId

import static com.deydey.iam.testFactory.CreateRegistrationCommendTestFactory.aCreateRegistrationCommand

class TenantTestFactory {
    static Tenant aPersonalTenant(Map params = [:]) {
        Tenant tenant = Tenant.newPersonalTenant(
                params.createRegistrationCommand as CreateRegistrationCommand ?: aCreateRegistrationCommand()
        )
        if (params.tenantId) {
            tenant.setTenantId(params.tenantId as TenantId)
        }
        return tenant
    }
}
