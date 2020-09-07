package com.tycz.android.twitter.api.lib

import com.tycz.tweedle.lib.tweets.stream.filter.Filter
import org.junit.Assert
import org.junit.Test

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

        Assert.assertEquals(expectedText, filter.filter)
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

        Assert.assertEquals(expected, filter.filter)
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

        Assert.assertEquals(expected, filter.filter)
    }
}