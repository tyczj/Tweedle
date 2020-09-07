package com.tycz.android.twitter.api.v2

import androidx.lifecycle.*
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
import com.tycz.tweedle.lib.dtos.tweet.rules.Rule
import com.tycz.tweedle.lib.dtos.tweet.SingleTweetPayload
import com.tycz.tweedle.lib.dtos.tweet.rules.DeleteRule
import com.tycz.tweedle.lib.dtos.tweet.rules.DeleteRuleResponse
import com.tycz.tweedle.lib.dtos.tweet.rules.RuleResponse
import com.tycz.tweedle.lib.dtos.user.Payload
import com.tycz.tweedle.lib.tweets.lookup.TweetsLookup
import com.tycz.tweedle.lib.tweets.stream.TweetsStream
import com.tycz.tweedle.lib.lookup.UserLookup

class MainViewModel: ViewModel() {

    private val _tweetLookup:TweetsLookup = TweetsLookup()
    private val _userLookup:UserLookup = UserLookup()
    private val _tweetStream:TweetsStream = TweetsStream()

    fun getTweet(token:String, tweetId:Long):LiveData<Response<SingleTweetPayload>>{
        return _tweetLookup.getTweet(token, tweetId).asLiveData(viewModelScope.coroutineContext)
    }

    fun getMultipleTweets(token:String, tweetIds:List<Long>):LiveData<Response<List<MultipleTweetPayload>>>{
        return _tweetLookup.getMultipleTweets(token, tweetIds).asLiveData(viewModelScope.coroutineContext)
    }

    fun getUser(token:String, userId:Long):LiveData<Response<Payload>>{
        return _userLookup.getUserById(token, userId).asLiveData(viewModelScope.coroutineContext)
    }

    fun getUsers(token:String, userIds:List<Long>):LiveData<Response<List<Payload>>>{
        return _userLookup.getUsersByIds(token, userIds).asLiveData(viewModelScope.coroutineContext)
    }

    fun getUserByUsername(token:String, username:String):LiveData<Response<Payload>>{
        return _userLookup.getUserByUsername(token, username).asLiveData(viewModelScope.coroutineContext)
    }

    fun getUsersByUsernames(token:String, usernames:List<String>):LiveData<Response<List<Payload>>>{
        return _userLookup.getUsersByUsernames(token, usernames).asLiveData(viewModelScope.coroutineContext)
    }

    fun deleteRule(token: String, deleteRule:DeleteRule):LiveData<Response<DeleteRuleResponse>>{
        return _tweetStream.deleteRule(token, deleteRule).asLiveData(viewModelScope.coroutineContext)
    }

    fun addRule(token: String, rule: Rule):LiveData<Response<RuleResponse>>{
        return _tweetStream.addRules(token, rule).asLiveData(viewModelScope.coroutineContext)
    }

    fun getStreamTweets(token:String):LiveData<Response<SingleTweetPayload>>{
        return _tweetStream.startTweetStream(token).asLiveData(viewModelScope.coroutineContext)
    }

    fun getRecentTweets(token:String, query:String, additionalParameters:Map<String,String>):LiveData<Response<MultipleTweetPayload>>{
        return _tweetLookup.getRecentTweets(token, query, additionalParameters).asLiveData(viewModelScope.coroutineContext)
    }
}