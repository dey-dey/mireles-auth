package com.deydey.testFactory

import com.deydey.iam.application.command.registration.CreateRegistrationCommand

class CreateRegistrationCommendTestFactory {
    static CreateRegistrationCommand aCreateRegistrationCommand(Map params = [:]) {
        return CreateRegistrationCommand.builder()
                .email("yo@yo.com")
                .password("awefAWEF1234")
                .build()
    }
}
