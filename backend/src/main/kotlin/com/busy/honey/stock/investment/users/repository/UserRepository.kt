package com.busy.honey.stock.investment.users.repository;

import com.busy.honey.stock.investment.users.entity.User;
import com.linecorp.kotlinjdsl.querydsl.expression.column
import org.springframework.stereotype.Repository;
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.data.jpa.repository.JpaRepository


@Repository
interface UserRepository : JpaRepository<User, Long> {

}

