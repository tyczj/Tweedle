package com.tycz.twitter.api.sharedmodule

import com.github.scribejava.apis.TwitterApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.tweets.stream.filter.Filter
import kotlin.test.Test

class OAuthTesting {

    @Test
    fun testApi(){
        val service = ServiceBuilder("26BVvThkUuerm0IauIyK6kiUA")
            .apiSecret("YBPScGOH8mqdKqW2GYIiRV7M3ocxvWMk3ntEEVMGC45Q870NxH")
            .build(TwitterApi.instance())

        val map = HashMap<String, String>()
        map["tweet.fields"] = "lang"
        map["expansions"] = "attachments.media_keys"
        map["media.fields"] = "preview_image_url,url"

        val filter: Filter = Filter.Builder()
            .addOperator("from:TwitterDev")
            .build()

        val accessToken = OAuth1AccessToken("146633079-AuSo7Xuc5sT63uDopTT8yWRhCbYCkbq1u3eNOGPI", "MRagFqoWl9QDzhg6dkkIqJunVBhxG81oSDjLnnmLtnU9w")
        val request = OAuthRequest(Verb.GET, "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/search/recent")
        request.queryStringParams.add("query", filter.filter)

        for (additionalParameter in map) {
            request.queryStringParams.add(additionalParameter.key, additionalParameter.value)
        }


        service.signRequest(accessToken, request)
        val headers = request.headers
        val parameters = request.oauthParameters

        val response = service.execute(request)
        print(response.body)
    }
}