# Tweedle

***Library is currently in alpha and the API might change as the Twitter v2 API changes***

[![v2](https://img.shields.io/endpoint?url=https%3A%2F%2Ftwbadges.glitch.me%2Fbadges%2Fv2)](https://developer.twitter.com/en/docs/twitter-api)

Tweedle is an Android library built around the [Twitter v2 API](https://developer.twitter.com/en/docs/twitter-api/early-access) built fully in Kotlin using Kotlin Coroutines

Usage

### ViewModel

```kotlin
class MainViewModel: ViewModel() {

    val oauth2 = OAuth2(token)
    private val _tweetLookup:TweetsLookup = TweetsLookup(oauth2)
    
    fun getTweet(tweetId:Long):LiveData<Response<SingleTweetPayload?>>{
            val liveData:MutableLiveData<Response<SingleTweetPayload?>> = MutableLiveData<Response<SingleTweetPayload?>>()
            viewModelScope.launch{
                val response = _tweetLookup.getTweet(tweetId)
                liveData.postValue(response)
            }
            return liveData
        }
}
```

### Activity

```kotlin
_viewModel.getTweet(1299418846990921728).observe(this, Observer {
    when(it){
        is Response.Error -> {it.exception}
        is Response.Success -> it.data
    }
})
```

## Streaming

Tweedle also supports streaming in real time of current tweets based on filters/rules applied

Creating a filter

You can easily create a filter with the filter builder

Say we want to stream tweets that come in for the hashtag `#SundayMorning` and only in english. That filter would look like this

```kotlin
val filters:MutableList<Add> = mutableListOf()
        val filter: Filter = Filter.Builder()
            .addOperator("#SundayMorning")
            .and()
            .setLanguage(Filter.ENGLISH)
            .build()
```

See additional examples [here](https://github.com/tyczj/Tweedle/blob/master/lib/src/test/java/com/tycz/tweedle/lib/FilterBuilderTest.kt) as well as the twiter documentation about creating filters [here](https://developer.twitter.com/en/docs/twitter-api/tweets/filtered-stream/integrate/build-a-rule)

### Adding a filter/rule 

(Streaming only supports OAuth2)

```kotlin
val oAuth2 = OAuth2(token)
val _tweetStream = TweetsStream(oAuth2)
val addRule = Add(filter.filter, "Sunday Morning")
filters.add(addRule)
val rule = Rule(filters)

_tweetStream.addRules(token, rule)
```

Currently Twitter only supports 25 filters/rules per API key

To start streaming tweets call the `startTweetStream` endpoint

```kotlin
_tweetStream.startTweetStream().collect {
    when(it){
         is Response.Success -> Log.d("TWEET", it.data.data.text)
    }
}
```

Collect is called every time a new tweet is received

### User Authentication (Experimental not considered stable yet)

To have a user login through their account and don things on their behalf you can start the login flow through the Authentication API

```kotlin
    val _authenticationOauth = AuthenticationOAuth(apiKey, apiSecret)
    val _authentication:Authentication = Authentication(authenticationOauth)
    val token = _authentication.requestToken(callbackUrl)
```

an `AccessTokenResponse` object is returned and you use that to load a login page in a webview for the user to login

```kotlin
    val url = "https://api.twitter.com/oauth/authorize?oauth_token=${token.oauthToken}&force_login=true"

    val webView: WebView = findViewById(R.id.webview)
    webView.webViewClient = webViewClient
    webView.loadUrl(url)
```

Create a WebViewClient and override `shouldOverrideUrlLoading` to watch for then your callback url gets called so you can grab the 
credentials if the user logs in successfully.

```kotlin
    private val webViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (request!!.url.toString().startsWith(callbackUrl)) {
                return true
            }
            return false
        }
    }
```

When you see your callback url called you need to get the tokens from the url to save them

```kotlin
if (request!!.url.toString().startsWith(callbackUrl)){
    val decodedUrl = URLDecoder.decode(request.url.toString(), "UTF-8")
    if (decodedUrl.contains("oauth_token=")) {
        val uri = Uri.parse(decodedUrl)
        val authToken = uri.getQueryParameter("oauth_token")
        val authVerifier = uri.getQueryParameter("oauth_verifier")

        //Check to make sure oauth tokens match
        if(oauthToken == authToken){
            //Tokens match you can proceed to the next step
        }else{
            //Tokens don't match, do not proceed
        }
    }
}
```

With those tokens now you can get the access tokens by calling the request token api

```kotlin
val accessToken = _authentication.getAccessToken(oauthToken, oauthVerifier)
```

### OAuth1 Authorization (Experimental not considered stable yet)

With the user access token you can now create an `OAuth1` object to use to make API calls with on behalf of the user

```kotlin
val oauth1 = OAuth1(apiKey, apiSecret, accessToken.oauthToken!!, accessToken.oauthTokenSecret!!)
private val _tweetLookup:TweetsLookup = TweetsLookup(oauth1)
val response = _tweetLookup.getTweet(tweetId)
```

To start using Tweedle, include the dependency in your `build.gradle`

Common
```kotlin
implementation("com.tycz:tweedle:0.2.2")
```

Android
```kotlin
implementation("com.tycz:tweedle-android:0.2.2")
```
