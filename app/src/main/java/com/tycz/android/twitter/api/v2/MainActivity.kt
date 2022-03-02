package com.tycz.android.twitter.api.v2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.authentication.oauth.OAuth2Bearer
import com.tycz.tweedle.lib.tweets.stream.filter.Filter

class MainActivity : AppCompatActivity() {

    @ExperimentalApi
    val model: MainViewModel by viewModels()

    @ExperimentalApi
    val a = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            model.getAccessToken(it.data!!.getStringExtra("oauth_token")!!,
                it.data!!.getStringExtra("oauth_verifier")!!).observe(this, Observer {accessTokenResponse ->
                    Log.d("Response",accessTokenResponse.toString())

                //Securely store access token

                val apiKey = ""
                val apiSecret = ""

                model.oAuth1 = OAuth1(apiKey, apiSecret, accessTokenResponse.oauthToken!!, accessTokenResponse.oauthTokenSecret!!)

                val filter: Filter = Filter.Builder()
                    .addOperator("from:TwitterDev")
                    .build()

                val map = HashMap<String, String>()
                map["tweet.fields"] = "lang"
                map["expansions"] = "attachments.media_keys"
                map["media.fields"] = "preview_image_url,url"

                model.getRecentTweets(
                    filter.filter,
                    map
                ).observe(this, Observer {resp ->
                    when (resp) {
                        is Response.Error -> {
                            resp.exception
                        }
                        is Response.Success -> resp.data!!.data?.get(0)
                    }
                })

//                model.getTweet(1299418846990921728).observe(this, Observer {response ->
//                    Log.d("Response2",response.toString())
//                    when(response){
//                        is Response.Error -> {response.exception}
//                        is Response.Success -> response.data
//                    }
//                })
            })
        }else{
            //Error with authorizing
        }
    }

    @OptIn(ExperimentalApi::class)
    val b = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            model.getOAuth2AccessToken(it.data!!.getStringExtra("code")!!, it.data!!.getStringExtra("challenge")!!).observe(this) { oAuth2Response ->
                Log.d("Response",oAuth2Response.toString())
                if(oAuth2Response is Response.Success){
                    //Save token information
                    model.oAuth2 = OAuth2Bearer(oAuth2Response.data!!.access_token)
                }
            }
        }else{
            //Error with authorizing
        }
    }

    @ExperimentalApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add your own Authorization token then uncomment the different sections to see example usage
        val token = ""
        val apiKey = ""
        val apiSecret = ""
        model.oAuth2 = OAuth2(token)
        val oauthToken = ""
        val oauthTokenSecret = ""
        model.oAuth1 = OAuth1(apiKey,apiSecret,oauthToken, oauthTokenSecret)

//        model.authOAuth = AuthenticationOAuth(apiKey, apiSecret)
        //region Get Single Tweet
//        model.getTweet(1299418846990921728).observe(this, Observer {
//            when(it){
//                is Response.Error -> {it.exception}
//                is Response.Success -> it.data
//            }
//        })
        //endregion

        //region Get multiple tweets
//        model.getMultipleTweets(mutableListOf(1299418846990921728, 1387852271938150408)).observe(this, Observer {
//            when(it){
//                is Response.Error -> {it.exception}
//                is Response.Success -> it.data
//            }
//        })
        //endregion

        //region Add Rule
//        val filters:MutableList<Add> = mutableListOf()
//        val filter: Filter = Filter.Builder()
//            .addOperator("#SundayMorning")
//            .and()
//            .setLanguage(Filter.ENGLISH)
//            .build()
//
//        val addRule = Add(filter.filter, "Sunday Morning")
//        filters.add(addRule)
//        val rule = Rule(filters)
//
//        model.addRule(rule).observe(this, Observer {
//            when(it){
//                is Response.Error -> {it.exception}
//                is Response.Success -> it.data
//            }
//        })
        //endregion

        //region Delete Rule
//        val filter: Filter = Filter.Builder()
//            .addOperator("#SundayMorning")
//            .and()
//            .setLanguage(Filter.ENGLISH)
//            .build()
//        val list = mutableListOf<String>(filter.filter)
//        val delete = Delete(list)
//        val deleteRule = DeleteRule(delete)
//
//        model.deleteRule(
//            deleteRule).observe(this, Observer {
//            when(it){
//                is Response.Error -> {it.exception}
//                is Response.Success -> it.data
//            }
//        })
        //endregion

        //region Streaming
//        model.getStreamTweets().observe(this, Observer {
//            when(it){
//                is Response.Success -> Log.d("TWEET", it.data.data.text)
//                is Response.Error -> {
//                    Log.d("Error", it.exception.message!!)
//                }
//            }
//        })
//
//        //Close the stream in 40 seconds
//        Handler().postDelayed({
//            model.closeStream()
//        }, 40000)
        //endregion

        //region Recent Tweets
//        val map = HashMap<String, String>()
//        map["tweet.fields"] = "lang"
//        map["expansions"] = "attachments.media_keys"
//        map["media.fields"] = "preview_image_url,url"
//
//        val filter: Filter = Filter.Builder()
//            .addOperator("from:TwitterDev")
//            .build()
//
//        model.getRecentTweets(
//            filter.filter,
//            map
//        ).observe(this, Observer {
//            when (it) {
//                is Response.Error -> {
//                    it.exception
//                }
//                is Response.Success -> it.data!!.data?.get(0)
//            }
//        })
        //endregion

        //region Authentication
//        val callbackUrl = ""
//        model.authenticate(callbackUrl).observe(this, Observer {
//            if(it.error == null){
//                val intent = Intent(this, TwitterAuthActivity::class.java)
//                intent.putExtra("oauthToken", it.oauthToken)
//                a.launch(intent)
//            }else{
//                //Check error
//            }
//
//        })
        //endregion

        //region OAuth2 PKCE
        val intent = Intent(this, TwitterOAuth2Activity::class.java)
        intent.putExtra("clientId", "")
        b.launch(intent)
        //endregion

    }
}