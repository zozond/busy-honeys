package com.busy.honey.stock.investment.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Utils {
    companion object{
        private val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        fun toDateString(date: LocalDateTime): String {
            val year = date.year
            var monthStr = "" + date.monthValue
            var dayStr = "" + date.dayOfMonth

            if (date.monthValue < 10){
                monthStr = "0${monthStr}"
            }
            if (date.dayOfMonth < 10){
                dayStr = "0${dayStr}"
            }
            return "$year-$monthStr-$dayStr"
        }

        fun getTodayStartDateTime(): LocalDateTime {
            val now = LocalDateTime.now()
            val yyyymmdd = toDateString(now)
            return LocalDateTime.parse("$yyyymmdd 00:00:00", pattern)
        }

        fun getTodayEndDateTime(): LocalDateTime {
            val now = LocalDateTime.now()
            val yyyymmdd = toDateString(now)
            return LocalDateTime.parse("$yyyymmdd 23:59:59", pattern)
        }

        fun getStartDateTime(yyyymmdd: String): LocalDateTime {
            return LocalDateTime.parse("$yyyymmdd 00:00:00", pattern)
        }

        fun getEndDateTime(yyyymmdd: String): LocalDateTime {
            return LocalDateTime.parse("$yyyymmdd 23:59:59", pattern)
        }

        fun getOneMonthDateRange(): List<String>{
            val result = mutableListOf<String>()

            var monthAgo = LocalDateTime.now().minusMonths(1)

            while(monthAgo.compareTo(LocalDateTime.now()) < 0){
                result.add(toDateString(monthAgo))
                monthAgo = monthAgo.plusDays(1)
            }

            return result
        }
    }
}