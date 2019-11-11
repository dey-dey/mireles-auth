package com.deydey.configuration

import org.springframework.test.context.support.DefaultActiveProfilesResolver
import org.springframework.util.StringUtils

class EnvironmentAwareActiveProfilesResolver extends DefaultActiveProfilesResolver{

    @Override
    String[] resolve(Class<?> testClass) {
        Set<String> activeProfiles = super.resolve(testClass)
        activeProfiles.addAll(parseProfiles(System.getProperty('spring.profiles.active')))
        activeProfiles.addAll(parseProfiles(System.getenv('SPRING_PROFILES_ACTIVE')))
        return activeProfiles
    }

    private static String[] parseProfiles(String profiles) {
        StringUtils.isEmpty(profiles) ? [] : profiles.split(',')
    }
}
