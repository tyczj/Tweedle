# Tweedle

[![v2](https://img.shields.io/endpoint?url=https%3A%2F%2Ftwbadges.glitch.me%2Fbadges%2Fv2)](https://developer.twitter.com/en/docs/twitter-api)
![version](https://img.shields.io/badge/version-0.5.1-blue)
<a href="https://twitter.com/tyczj" alt="Twitter">
<img src="https://img.shields.io/twitter/follow/tyczj?style=social" /></a>

***Library is currently in alpha and the API might change as the Twitter v2 API changes***

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

To have a user login through their account and do things on their behalf you can start the login flow through the Authentication API

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

### OAuth2 Authorization PKCE (Proof Key for Code Exchange)

An alternative to `OAuth1` is `OAuth2 PKCE` which allows for greater user control over access with scopes.
OAuth2 with PKCE usage is the same as using the Bearer token found in your developer account.

OAuth2 PKCE is recommended over using OAuth1 because OAuth1 currently will only perform GET operations

```kotlin
val oauth2 = OAuth2Bearer(bearerToken)
private val _tweetLookup:TweetsLookup = TweetsLookup(oauth2)
val response = _tweetLookup.getTweet(tweetId)
```

The main difference is how the token is created. An example of the OAuth2 PKCE flow is available in the sample app code but the majority
of the code can be found in [TwitterOAuth2Activity](https://github.com/tyczj/Tweedle/blob/oauth2-pkce/app/src/main/java/com/tycz/android/twitter/api/v2/TwitterOAuth2Activity.kt)

Start the login flow by creating authentication url and passing it to the webview

```kotlin
//Scopes to request 
val scopes = listOf(
    OAuthScope.OfflineAccessScope,
    OAuthScope.TweetReadScope,
    OAuthScope.TweetWriteScope,
    OAuthScope.TweetModerateWrite,
    OAuthScope.UserBlockReadScope,
    OAuthScope.UserBlockWriteScope,
    OAuthScope.UserFollowsReadScope,
    OAuthScope.UserFollowsWriteScope,
    OAuthScope.UserMuteReadScope,
    OAuthScope.UserMuteWriteScope,
    OAuthScope.UserReadScope
)

//Client ID is found in your developer account
//Callback url is what you have in your developer account

//State is a random set of characters to verify against CSRF attacks.  The length of this string can be up to 500 characters.
val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
val state = (1..10)
    .map { allowedChars.random() }
    .joinToString("")

//Challenge is a cryptographically random string using the characters A-Z, a-z, 0-9, and the punctuation characters -._~ (hyphen, period, underscore, and tilde), between 43 and 128 characters long.

val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9') + '-' + '.' + '_' + '~'

val challenge = (1..44)
    .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
    .map(charPool::get)
    .joinToString("")

val url = Authentication2.generateAuthenticationUrl(clientId,scopes,callbackurl,state, challenge)

val webView: WebView = findViewById(R.id.webview)
val webSettings = webView.settings
webSettings.setJavaScriptEnabled(true)
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
    if (decodedUrl.contains("code=")) {
        val uri = Uri.parse(decodedUrl)
        val code = uri.getQueryParameter("code")
        val state = uri.getQueryParameter("state")

        //Check to make sure the state perameter matches what you sent in the authorization url
        if(state == savedState){
            //States match you can proceed to the next step
        }else{
            //States don't match, do not proceed
        }
    }
}
```

With those the code and your challenge now you can get the access tokens by calling the request token api

```kotlin
val authentication2 = Authentication2(code, clientId)
val accessToken = authentication2.getAccessToken(callbackUrl, challenge)
```

To start using Tweedle, include the dependency in your `build.gradle`

Common
```kotlin
implementation("io.github.tyczj:tweedle:0.5.1")
```

Android
```kotlin
android{
    buildTypes {
        debug { matchingFallbacks = ['release'] } 
    }
}

implementation 'io.github.tyczj:tweedle-android:0.5.1'
```
