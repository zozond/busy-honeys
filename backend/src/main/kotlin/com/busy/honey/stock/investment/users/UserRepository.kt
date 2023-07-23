package com.busy.honey.stock.investment.users;

import com.busy.honey.stock.investment.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmailAndPassword(email: String, password: String): List<User?>
}
