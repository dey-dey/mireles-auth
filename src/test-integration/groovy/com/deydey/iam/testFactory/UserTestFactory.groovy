package com.deydey.iam.testFactory

import com.deydey.iam.application.command.registration.CreateRegistrationCommand
import com.deydey.iam.domain.identity.user.User
import com.deydey.iam.domain.identity.user.UserId

import static com.deydey.iam.testFactory.CreateRegistrationCommendTestFactory.aCreateRegistrationCommand

class UserTestFactory {
    static User aUserOf(Map params = [:]) {
        User user = User.of(
            params.createRegistrationCommand as CreateRegistrationCommand ?: aCreateRegistrationCommand())
        if (params.userId) {
            user.setId(params.userId as UserId)
        }
        return user
    }
}
