package com.tycz.android.twitter.api.v2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.tycz.tweedle.lib.authentication.Authentication2
import com.tycz.tweedle.lib.authentication.oauth.OAuthScope
import java.net.URLDecoder

class TwitterOAuth2Activity: AppCompatActivity() {
    private val callbackUrl = ""

    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    val _state = (1..10)
        .map { allowedChars.random() }
        .joinToString("")

    val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9') + '-' + '.' + '_' + '~'

    val challenge = (1..44)
        .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.twitter_auth_activity_layout)

        val clientId = intent.getStringExtra("clientId")!!
        val url = Authentication2.generateAuthenticationUrl(clientId,
            listOf(
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
            ), callbackUrl, _state, challenge)

        val webView: WebView = findViewById(R.id.webview)
        val webSettings = webView.settings
        webSettings.setJavaScriptEnabled(true)
        webView.webViewClient = webViewClient
        webView.loadUrl(url)
    }

    private val webViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (request!!.url.toString().startsWith(callbackUrl)) {
                val decodedUrl = URLDecoder.decode(request.url.toString(), "UTF-8")
                if (decodedUrl.contains("code=")) {
                    val uri = Uri.parse(decodedUrl)
                    val code = uri.getQueryParameter("code")
                    val state = uri.getQueryParameter("state")

                    //Check to make sure the state generated matches the one returned in the url
                    if(state == _state){
                        val data = Intent()
                        data.putExtra("code", code)
                        data.putExtra("challenge", challenge)
                        setResult(AppCompatActivity.RESULT_OK, data)
                        finish()
                    }else{
                        //Tokens don't match, do not proceed
                        setResult(AppCompatActivity.RESULT_CANCELED)
                        finish()
                    }
                }
                return true
            }
            return false
        }
    }
}