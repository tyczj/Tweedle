package com.tycz.tweedle.lib

import com.github.scribejava.apis.TwitterApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.tweets.stream.filter.Filter
import io.ktor.client.engine.android.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.Base64.getEncoder
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

actual val engine by lazy { Android.create() }

actual fun epochMillis() = System.currentTimeMillis()

actual fun epochSeconds() = System.currentTimeMillis() / 1000

actual fun urlEncodeString(stringToEncode: String): String{
    return URLEncoder.encode(stringToEncode, StandardCharsets.UTF_8.toString())
}

actual fun getRandomInteger() = Random().nextInt()

actual fun generateHmacSha1Signature(key: String, value: String): String{
    val type = "HmacSHA1"
    val spec = SecretKeySpec(key.toByteArray(), type)
    val mac = Mac.getInstance(type)
    mac.init(spec)
    val bytes = mac.doFinal(value.toByteArray())
    return getEncoder().encodeToString(bytes).replace("\r\n", "")
}

actual fun testApi(): String{
    val service = ServiceBuilder("26BVvThkUuerm0IauIyK6kiUA")
        .apiSecret("YBPScGOH8mqdKqW2GYIiRV7M3ocxvWMk3ntEEVMGC45Q870NxH")
        .build(TwitterApi.instance())

    val map = HashMap<String, String>()
    map["tweet.fields"] = "lang"
    map["expansions"] = "attachments.media_keys"
    map["media.fields"] = "preview_image_url,url"

    map.toSortedMap()

    val filter: Filter = Filter.Builder()
        .addOperator("from:TwitterDev")
        .build()

    val accessToken = OAuth1AccessToken("146633079-AuSo7Xuc5sT63uDopTT8yWRhCbYCkbq1u3eNOGPI", "MRagFqoWl9QDzhg6dkkIqJunVBhxG81oSDjLnnmLtnU9w")
    val request = OAuthRequest(Verb.GET, "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/search/recent")
    request.addParameter("query", filter.filter)

    for (additionalParameter in map) {
        request.addParameter(additionalParameter.key, additionalParameter.value)
    }

    service.signRequest(accessToken, request)
    val headers = request.headers
    val parameters = request.oauthParameters

    val response = service.execute(request)
//    print(response.body)
    return response.body
}