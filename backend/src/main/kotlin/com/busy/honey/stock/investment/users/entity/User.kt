package com.busy.honey.stock.investment.users.entity


import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name="users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var userId: Long?,
    var email: String,
    var username: String,
    var password: String,
    var accountId: Long,
    var userType: String,
    var createdAt: LocalDateTime
)