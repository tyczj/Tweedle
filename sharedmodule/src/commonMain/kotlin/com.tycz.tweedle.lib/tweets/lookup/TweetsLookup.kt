package com.tycz.tweedle.lib.tweets.lookup

import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
import com.tycz.tweedle.lib.dtos.tweet.SingleTweetPayload
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.flow

class TweetsLookup {

    private val _client = TwitterClient.instance

    /**
     * Gets a single tweet by the tweet id
     * @param token Authorization token
     * @param tweetId The if of the tweet to get
     *
     * @return Returns a flow that returns the response with the tweet payload
     */
    fun getTweet(token:String, tweetId:Long) = flow{

        val result = try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/$tweetId"
            val payload = _client.get<SingleTweetPayload>(token, url)
            Response.Success(payload)
        }catch (e:Exception){
            Response.Error(e)
        }

        emit(result)
    }

    /**
     * Gets a lost of tweets for the given tweet ids
     * @param token Authorization token
     * @param tweetIds List of tweet id's to get the tweets for
     *
     * @return Returns a flow that returns the response with the payload
     */
    fun getMultipleTweets(token:String, tweetIds:List<Long>) = flow {

        val result = try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}?ids=${tweetIds.joinToString(",","","",100)}"
            val tweets = _client.get<List<MultipleTweetPayload>>(token, url)
            Response.Success(tweets)
        }catch (e:Exception){
            Response.Error(e)
        }

        emit(result)
    }

    fun hideTweet(token:String, tweetId:Long) = flow {

        val result = try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/$tweetId/hidden"
            val response = _client.put<HttpResponse>(token, url, "{\\n    \\\"hidden\\\": true\\n}")
            Response.Success(response)
        }catch (e:Exception){
            Response.Error(e)
        }

        emit(result)
    }

    fun unhideTweet(token:String, tweetId:Long) = flow {

        val result = try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/$tweetId/hidden"
            val response = _client.put<HttpResponse>(token, url, "{\\n    \\\"hidden\\\": false\\n}")
            Response.Success(response)
        }catch (e:Exception){
            Response.Error(e)
        }

        emit(result)
    }

    /**
     * Gets a list of tweets posted over the last week
     * @see <a href="https://developer.twitter.com/en/docs/twitter-api/tweets/search/introduction">Recent Search</a>
     *
     * @param token Authorization token
     * @param query Search query to submit to the recent search endpoint
     * @param additionalParameters Additional fields to be returned for extra information not in the query
     *
     * @return Returns a flow with the response of the tweets payload
     */
    fun getRecentTweets(token:String, query:String, additionalParameters:Map<String,String>) = flow{

        val result = try{
            val builder = StringBuilder()

            val url = "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/search/recent?query=$query"
            builder.append(url)

            for (additionalParameter in additionalParameters) {
                builder.append("&${additionalParameter.key}=${additionalParameter.value}")
            }
            val response = _client.get<MultipleTweetPayload>(token, builder.toString())
            Response.Success(response)
        }catch (e:Exception){
            Response.Error(e)
        }

        emit(result)
    }
}