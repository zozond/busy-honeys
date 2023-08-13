package com.busy.honey.stock.investment.users.repository

import com.busy.honey.stock.investment.users.entity.User
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

interface JdslUserRepository {
    fun findByUserType(userType: String): List<User>
    fun findByEmailAndPassword(email:String, password:String): List<User>
}

