package com.tycz.tweedle.lib.tweets.lookup

import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterApi
import com.tycz.tweedle.lib.api.interfaces.ITweetLookup
import kotlinx.coroutines.flow.flow

/**
 * Class with various methods to get tweets from the Twitter API
 */
class TweetsLookup {

    private val _lookup = TwitterApi.httpClient.create(ITweetLookup::class.java)

    /**
     * Gets a single tweet by the tweet id
     * @param token Authorization token
     * @param tweetId The if of the tweet to get
     *
     * @return Returns a flow that returns the response with the tweet payload
     */
    fun getTweet(token:String, tweetId:Long) = flow{

        val result = try{
            val payload = _lookup.getSingleTweet("Bearer $token", tweetId)
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
            val tweets = _lookup.getMultipleTweets("Bearer $token",tweetIds.joinToString(",","","",100))
            Response.Success(tweets)
        }catch (e:Exception){
            Response.Error(e)
        }

        emit(result)
    }

    fun hideTweet(token:String, tweetId:Long) = flow {

        val result = try{
            val response = _lookup.hideTweet("Bearer $token", tweetId)
            Response.Success(response)
        }catch (e:Exception){
            Response.Error(e)
        }

        emit(result)
    }

    fun unhideTweet(token:String, tweetId:Long) = flow {

        val result = try{
            val response = _lookup.unhideTweet("Bearer $token", tweetId)
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
            val response = _lookup.getRecentTweets("Bearer $token", query, additionalParameters)
            Response.Success(response)
        }catch (e:Exception){
            Response.Error(e)
        }

        emit(result)
    }
}