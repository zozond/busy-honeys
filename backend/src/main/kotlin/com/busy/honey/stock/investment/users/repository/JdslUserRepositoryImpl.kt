package com.busy.honey.stock.investment.users.repository

import com.busy.honey.stock.investment.users.entity.User
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.stereotype.Repository


@Repository
class JdslUserRepositoryImpl (
    private val queryFactory: SpringDataQueryFactory
) : JdslUserRepository  {
    override fun findByUserType(userType: String): List<User> {
        return queryFactory.listQuery {
            select(entity(User::class))
            from(entity(User::class))
            whereAnd(
                column(User::userType).equal(userType),
            )
        }
    }
    override fun findByEmailAndPassword(email: String, password: String): List<User> {
        return queryFactory.listQuery {
            select(entity(User::class))
            from(entity(User::class))
            whereAnd(
                column(User::email).equal(email),
                column(User::password).equal(password),
            )
        }
    }
}