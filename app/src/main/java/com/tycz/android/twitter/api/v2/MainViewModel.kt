package com.tycz.android.twitter.api.v2

import androidx.lifecycle.*
import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.authentication.*
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.authentication.oauth.OAuth2Bearer
import com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
import com.tycz.tweedle.lib.dtos.tweet.rules.Rule
import com.tycz.tweedle.lib.dtos.tweet.SingleTweetPayload
import com.tycz.tweedle.lib.dtos.tweet.rules.DeleteRule
import com.tycz.tweedle.lib.dtos.tweet.rules.DeleteRuleResponse
import com.tycz.tweedle.lib.dtos.tweet.rules.RuleResponse
import com.tycz.tweedle.lib.dtos.user.Payload
import com.tycz.tweedle.lib.dtos.user.UserPayload
import com.tycz.tweedle.lib.tweets.lookup.TweetsLookup
import com.tycz.tweedle.lib.user.UserEndpoints
import com.tycz.tweedle.lib.tweets.stream.TweetsStream
import kotlinx.coroutines.launch

@ExperimentalApi
class MainViewModel: ViewModel() {

    private lateinit var _tweetLookup:TweetsLookup
    private lateinit var _userLookup: UserEndpoints
    private lateinit var _tweetStream:TweetsStream
    private lateinit var _authentication:Authentication1
    private lateinit var _authentication2: Authentication2

    var authOAuth: OAuth1? = null
            set(value) {
                value?.let {
                    _authentication = Authentication1(value)
                }
            }

    var oAuth1: OAuth1? = null
        set(value) {
            value?.let {
                _tweetLookup = TweetsLookup(value)
                _userLookup = UserEndpoints(value)
            }
        }
    var oAuth2: OAuth2Bearer? = null
        set(value) {
            value?.let {
//                _tweetLookup = TweetsLookup(value)
                _tweetStream = TweetsStream(value)
            }
        }

    fun getTweet(tweetId:Long):LiveData<Response<SingleTweetPayload?>>{
        val liveData:MutableLiveData<Response<SingleTweetPayload?>> = MutableLiveData<Response<SingleTweetPayload?>>()
        viewModelScope.launch{
            val response = _tweetLookup.getTweet(tweetId)
            liveData.postValue(response)
        }
        return liveData
    }

    fun getMultipleTweets(tweetIds:List<Long>):LiveData<Response<MultipleTweetPayload?>>{
        val liveData:MutableLiveData<Response<MultipleTweetPayload?>> = MutableLiveData<Response<MultipleTweetPayload?>>()
        viewModelScope.launch{
            val response = _tweetLookup.getMultipleTweets(tweetIds)
            liveData.postValue(response)
        }
        return liveData
    }

    fun getUser(userId:Long):LiveData<Response<UserPayload?>>{
        val liveData:MutableLiveData<Response<UserPayload?>> = MutableLiveData<Response<UserPayload?>>()
        viewModelScope.launch {
            val response = _userLookup.getUserById(userId)
            liveData.postValue(response)
        }
        return liveData
    }

    fun getUsers(userIds:List<Long>):LiveData<Response<Payload?>>{
        val liveData:MutableLiveData<Response<Payload?>> = MutableLiveData<Response<Payload?>>()
        viewModelScope.launch {
            val response = _userLookup.getUsersByIds(userIds)
            liveData.postValue(response)
        }
        return liveData
    }

    fun getUserByUsername(username:String):LiveData<Response<UserPayload?>>{
        val liveData:MutableLiveData<Response<UserPayload?>> = MutableLiveData<Response<UserPayload?>>()
        viewModelScope.launch {
            val response = _userLookup.getUserByUsername(username)
            liveData.postValue(response)
        }
        return liveData
    }

    fun getUsersByUsernames(usernames:List<String>):LiveData<Response<Payload?>>{
        val liveData:MutableLiveData<Response<Payload?>> = MutableLiveData<Response<Payload?>>()
        viewModelScope.launch {
            val response = _userLookup.getUsersByUsernames(usernames)
            liveData.postValue(response)
        }
        return liveData
    }

    fun deleteRule(deleteRule:DeleteRule):LiveData<Response<DeleteRuleResponse?>>{
        val liveData:MutableLiveData<Response<DeleteRuleResponse?>> = MutableLiveData<Response<DeleteRuleResponse?>>()
        viewModelScope.launch {
            val response = _tweetStream.deleteRule(deleteRule)
            liveData.postValue(response)
        }
        return liveData
    }

    fun addRule(rule: Rule):LiveData<Response<RuleResponse?>>{
        val liveData:MutableLiveData<Response<RuleResponse?>> = MutableLiveData<Response<RuleResponse?>>()
        viewModelScope.launch {
            val response = _tweetStream.addRules(rule)
            liveData.postValue(response)
        }
        return liveData
    }

    fun getStreamTweets():LiveData<Response<SingleTweetPayload>>{
        return _tweetStream.startTweetStream().asLiveData(viewModelScope.coroutineContext)
    }

    fun getRecentTweets(query: String, additionalParameters:Map<String,String>):LiveData<Response<MultipleTweetPayload?>>{
        val liveData:MutableLiveData<Response<MultipleTweetPayload?>> = MutableLiveData<Response<MultipleTweetPayload?>>()
        viewModelScope.launch {
            val response = _tweetLookup.getRecentTweets(query, additionalParameters)
            liveData.postValue(response)
        }
        return liveData
    }

    fun closeStream(){
        _tweetStream.closeStream()
    }

    fun authenticate(callbackUrl: String):LiveData<TokenResponse>{
        val liveData:MutableLiveData<TokenResponse> = MutableLiveData<TokenResponse>()
        viewModelScope.launch {
            val token = _authentication.requestToken(callbackUrl)
            liveData.postValue(token)
        }
        return liveData
    }

    fun getAccessToken(oauthToken: String, oauthVerifier: String):LiveData<AccessTokenResponse>{
        val liveData:MutableLiveData<AccessTokenResponse> = MutableLiveData<AccessTokenResponse>()
        viewModelScope.launch {
            val accessToken = _authentication.getAccessToken(oauthToken, oauthVerifier)
            liveData.postValue(accessToken)
        }
        return liveData
    }

    fun getOAuth2AccessToken(code: String, challenge: String, clientId: String, callbackUrl: String):LiveData<Response<OAuth2PKCEResponse?>>{
        _authentication2 = Authentication2(code, clientId)
        val liveData:MutableLiveData<Response<OAuth2PKCEResponse?>> = MutableLiveData<Response<OAuth2PKCEResponse?>>()
        viewModelScope.launch {
            val response = _authentication2.getAccessToken(callbackUrl, challenge)
            liveData.postValue(response)
        }
        return liveData
    }


}