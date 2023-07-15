package com.busy.honey.stock.investment.users.entity

import java.time.LocalDateTime


class User(
    var userId: Long,
    var email: String,
    var userName: String,
    var password: String,
    var createdAt: LocalDateTime?
) {

}