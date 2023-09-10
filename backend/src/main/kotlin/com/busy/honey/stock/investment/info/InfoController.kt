package com.busy.honey.stock.investment.info

import com.busy.honey.stock.investment.response.RestApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class InfoController {

    @GetMapping
    fun getInfo(): RestApiResponse{
        return RestApiResponse(
            status = "OK",
            description = "모의 주식 거래 서버 v1.0.0 입니다.",
            data = null
        )
    }
}