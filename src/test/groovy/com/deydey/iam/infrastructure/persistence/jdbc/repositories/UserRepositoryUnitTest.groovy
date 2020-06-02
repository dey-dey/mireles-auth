package com.deydey.iam.infrastructure.persistence.jdbc.repositories


import com.deydey.iam.domain.identity.user.User
import com.deydey.iam.domain.identity.user.UserId
import com.deydey.iam.domain.identity.user.UserRepository
import com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders.JdbcMapParameterSource
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

import static com.deydey.iam.testFactory.UUIDGenerator.uuid
import static com.deydey.iam.testFactory.MemberTestFactory.aMember
import static com.deydey.iam.testFactory.UserTestFactory.aUserOf
import static java.util.Arrays.asList

class UserRepositoryUnitTest extends Specification {

    UserRepository objectUnderTest
    NamedParameterJdbcTemplate jdbcTemplate

    def setup() {
        jdbcTemplate = Mock(NamedParameterJdbcTemplate)
        objectUnderTest = new UserJdbcRepository(jdbcTemplate)
    }

    def "user repository gets user by user id" () {
        given: "a user"
            User expected = aUserOf([
                userId: new UserId(uuid())
            ])
        and: "a parameter query source"
            MapSqlParameterSource parameters = new JdbcMapParameterSource()
            parameters.addValue("userId", expected.getId().getValue())
        when:
            User actual = objectUnderTest.getBy(expected.getId())
        then:
            1 * jdbcTemplate.query(_ as String,
                    _ as MapSqlParameterSource,
                    _ as RowMapper) >> asList(expected)
            1 * jdbcTemplate.query(_ as String,
                    _ as MapSqlParameterSource,
                    _ as RowMapper) >> asList(aMember([userId: expected.getId()]))
            actual.id == expected.getId()
            actual.members.size() == 1

    }


    def "user repository throws exception for if user does not exist" () {
        given: "a user"
        User expected = aUserOf([
                userId: new UserId(uuid())
        ])
        and: "a parameter query source"
            MapSqlParameterSource parameters = new JdbcMapParameterSource()
            parameters.addValue("userId", expected.getId().getValue())
        when:
            User actual = objectUnderTest.getBy(expected.getId())
        then:
            1 * jdbcTemplate.query(_ as String,
                    _ as MapSqlParameterSource,
                    _ as RowMapper) >> asList()
            1 * jdbcTemplate.query(_ as String,
                    _ as MapSqlParameterSource,
                    _ as RowMapper) >> asList()
            thrown(EntityNotFoundException)
    }
}
