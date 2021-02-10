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
import com.tycz.tweedle.lib.lookup.UserLookup
import com.tycz.tweedle.lib.tweets.stream.TweetsStream
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _tweetLookup:TweetsLookup = TweetsLookup()
    private val _userLookup:UserLookup = UserLookup()
    private val _tweetStream:TweetsStream = TweetsStream()

    fun getTweet(token:String, tweetId:Long):LiveData<Response<SingleTweetPayload?>>{
        val liveData:MutableLiveData<Response<SingleTweetPayload?>> = MutableLiveData<Response<SingleTweetPayload?>>()
        viewModelScope.launch{
            val response = _tweetLookup.getTweet(token, tweetId)
            liveData.postValue(response)
        }
        return liveData
    }

    fun getMultipleTweets(token:String, tweetIds:List<Long>):LiveData<Response<List<MultipleTweetPayload>?>>{
        val liveData:MutableLiveData<Response<List<MultipleTweetPayload>?>> = MutableLiveData<Response<List<MultipleTweetPayload>?>>()
        viewModelScope.launch{
            val response = _tweetLookup.getMultipleTweets(token, tweetIds)
            liveData.postValue(response)
        }
        return liveData
    }

    fun getUser(token:String, userId:Long):LiveData<Response<Payload?>>{
        val liveData:MutableLiveData<Response<Payload?>> = MutableLiveData<Response<Payload?>>()
        viewModelScope.launch {
            val response = _userLookup.getUserById(token, userId)
            liveData.postValue(response)
        }
        return liveData
    }

    fun getUsers(token:String, userIds:List<Long>):LiveData<Response<List<Payload>?>>{
        val liveData:MutableLiveData<Response<List<Payload>?>> = MutableLiveData<Response<List<Payload>?>>()
        viewModelScope.launch {
            val response = _userLookup.getUsersByIds(token, userIds)
            liveData.postValue(response)
        }
        return liveData
    }

    fun getUserByUsername(token:String, username:String):LiveData<Response<Payload?>>{
        val liveData:MutableLiveData<Response<Payload?>> = MutableLiveData<Response<Payload?>>()
        viewModelScope.launch {
            val response = _userLookup.getUserByUsername(token, username)
            liveData.postValue(response)
        }
        return liveData
    }

    fun getUsersByUsernames(token:String, usernames:List<String>):LiveData<Response<List<Payload>?>>{
        val liveData:MutableLiveData<Response<List<Payload>?>> = MutableLiveData<Response<List<Payload>?>>()
        viewModelScope.launch {
            val response = _userLookup.getUsersByUsernames(token, usernames)
            liveData.postValue(response)
        }
        return liveData
    }

    fun deleteRule(token: String, deleteRule:DeleteRule):LiveData<Response<DeleteRuleResponse?>>{
        val liveData:MutableLiveData<Response<DeleteRuleResponse?>> = MutableLiveData<Response<DeleteRuleResponse?>>()
        viewModelScope.launch {
            val response = _tweetStream.deleteRule(token, deleteRule)
            liveData.postValue(response)
        }
        return liveData
    }

    fun addRule(token: String, rule: Rule):LiveData<Response<RuleResponse?>>{
        val liveData:MutableLiveData<Response<RuleResponse?>> = MutableLiveData<Response<RuleResponse?>>()
        viewModelScope.launch {
            val response = _tweetStream.addRules(token, rule)
            liveData.postValue(response)
        }
        return liveData
    }

    fun getStreamTweets(token:String):LiveData<Response<SingleTweetPayload>>{
        return _tweetStream.startTweetStream(token).asLiveData(viewModelScope.coroutineContext)
    }

    fun getRecentTweets(token:String, query:String, additionalParameters:Map<String,String>):LiveData<Response<MultipleTweetPayload?>>{
        val liveData:MutableLiveData<Response<MultipleTweetPayload?>> = MutableLiveData<Response<MultipleTweetPayload?>>()
        viewModelScope.launch {
            val response = _tweetLookup.getRecentTweets(token, query, additionalParameters)
            liveData.postValue(response)
        }
        return liveData
    }

    fun closeStream(){
        _tweetStream.closeStream()
    }
}