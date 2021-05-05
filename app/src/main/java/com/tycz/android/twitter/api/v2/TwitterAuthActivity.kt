package com.tycz.android.twitter.api.v2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import java.net.URLDecoder

class TwitterAuthActivity: AppCompatActivity() {
    lateinit var oauthToken: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.twitter_auth_activity_layout)

        oauthToken = intent.getStringExtra("oauthToken")!!
        val url = "https://api.twitter.com/oauth/authorize?oauth_token=$oauthToken&force_login=true"

        val webView: WebView = findViewById(R.id.webview)
        webView.webViewClient = webViewClient
        webView.loadUrl(url)
    }

    private val webViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (request!!.url.toString().startsWith("https://inlighten.net")) {
                val decodedUrl = URLDecoder.decode(request.url.toString(), "UTF-8")
                if (decodedUrl.contains("oauth_token=")) {
                    val uri = Uri.parse(decodedUrl)
                    val authToken = uri.getQueryParameter("oauth_token")
                    val authVerifier = uri.getQueryParameter("oauth_verifier")

                    //Check to make sure oauth tokens match
                    if(oauthToken == authToken){
                        val data = Intent()
                        data.putExtra("oauth_token", authToken)
                        data.putExtra("oauth_verifier", authVerifier)
                        setResult(RESULT_OK, data)
                        finish()
                    }else{
                        //Tokens don't match, do not proceed
                        setResult(RESULT_CANCELED)
                        finish()
                    }
                }
                return true
            }
            return false
        }
    }
}