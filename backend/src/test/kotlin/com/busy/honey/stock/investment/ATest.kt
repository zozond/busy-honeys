package com.busy.honey.stock.investment

import io.kotest.core.spec.BeforeTest
import io.kotest.core.spec.style.WordSpec


val startTest: BeforeTest = {
    println("Starting a test $it")
}

class TestSpec : WordSpec({

    // used once
    beforeTest(startTest)

    "this test" should {
        "be alive" {
            println("Johnny5 is alive!")
        }
    }
})
