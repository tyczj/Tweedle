# Authentication

Tweedle supports all authentication methods that the Twitter v2 API supports (OAuth1, OAuth2 and OAuth2 PKCE)

### OAuth 2

The easiest authentication is using OAuth2 with a Bearer token found in your developer account. This token does have some limitations compared to others though, you can read about these limitations [here](https://developer.twitter.com/en/docs/authentication/oauth-2-0/application-only)

Example:
```kotlin
launch{
    val oAuth2 = OAuth2(token)
    val tweetLookup = TweetsLookup(oAuth2)
    val response = tweetLookup.getTweet(tweetId)
}
```

### OAuth2 Authorization PKCE (Proof Key for Code Exchange)

An alternative to `OAuth1` is `OAuth2 PKCE` which allows for greater user control over access with scopes.
OAuth2 with PKCE usage is the same as using the Bearer token found in your developer account.
To read more about OAuth2 with PKCE see [here](https://developer.twitter.com/en/docs/authentication/oauth-2-0/authorization-code)

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

### OAuth1 Authorization

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

With the user access token you can now create an `OAuth1` object to use to make API calls with on behalf of the user

```kotlin
val oauth1 = OAuth1(apiKey, apiSecret, accessToken.oauthToken!!, accessToken.oauthTokenSecret!!)
private val _tweetLookup:TweetsLookup = TweetsLookup(oauth1)
val response = _tweetLookup.getTweet(tweetId)
```

* Note OAuth1 currently only works with query calls such as getting tweets for example. API's that are not queries will throw an eception if you try to use them with OAuth1