package com.tycz.twitter.api.sharedmodule

import com.tycz.tweedle.lib.tweets.stream.filter.Filter
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterBuilderTest {

    @Test
    fun testAnd(){
        val expectedText:String = "snow day #NoSchool"

        val filter = Filter.Builder()
            .addOperator("snow")
            .and()
            .addOperator("day")
            .and()
            .addOperator("#NoSchool")
            .build()

        assertEquals(expectedText, filter.filter)
    }

    @Test
    fun testOr(){
        val expected:String = "snow OR day OR #NoSchool"

        val filter = Filter.Builder()
            .addOperator("snow")
            .or()
            .addOperator("day")
            .or()
            .addOperator("#NoSchool")
            .build()

        assertEquals(expected, filter.filter)
    }

    @Test
    fun testNot(){
        val expected:String = "snow #NoSchool -day"

        val filter = Filter.Builder()
            .addOperator("snow")
            .and()
            .addOperator("#NoSchool")
            .and()
            .not()
            .addOperator("day")
            .build()

        assertEquals(expected, filter.filter)
    }

    @Test
    fun testLanguage(){
        val expected:String = "#SundayMorning lang: en"

        val filter = Filter.Builder()
            .addOperator("#SundayMorning")
            .and()
            .setLanguage(Filter.ENGLISH)
            .build()

        assertEquals(expected, filter.filter)
    }
}