# Tweedle

***Library is currently in alpha and the API might change as the Twitter v2 API changes***

[![v2](https://img.shields.io/endpoint?url=https%3A%2F%2Ftwbadges.glitch.me%2Fbadges%2Fv2)](https://developer.twitter.com/en/docs/twitter-api)

[![Build Status](https://travis-ci.org/tyczj/Tweedle.svg)](https://travis-ci.org/github/tyczj/Tweedle)
[![Download](https://api.bintray.com/packages/tyczj359/Tweedle/lib/images/download.svg) ](https://bintray.com/tyczj359/Tweedle/lib/_latestVersion)

Tweedle is an Android library built around the [Twitter v2 API](https://developer.twitter.com/en/docs/twitter-api/early-access) built fully in Kotlin using Kotlin Coroutines

All API calls use kotlin flow to return data

```
_tweetLookup.getTweet(token, tweetId).collect {tweet ->
    
}
```

it allows for easy quick integration into the Android ViewModel that returns LiveData 

### ViewModel

```
class MainViewModel: ViewModel() {

    private val _tweetLookup:TweetsLookup = TweetsLookup()
    
    fun getTweet(token:String, tweetId:Long):LiveData<Response<SingleTweetPayload>>{
        return _tweetLookup.getTweet(token, tweetId).asLiveData(viewModelScope.coroutineContext)
    }
}
```

### Activity

```
_viewModel.getTweet(token, 1299418846990921728).observe(this, Observer {
    when(it){
        is Response.Error -> {it.exception}
        is Response.Success -> it.data
    }
})
```

## Streaming

Tweedle also supports streaming in real time of current tweets based on filters/rules applied

Creating a filter

You can easily create a filter with the filter builder.

Say we want to stream tweets that come in for the hashtag `#SundayMorning` and only in english. That filter would look like this

```
val filters:MutableList<Add> = mutableListOf()
        val filter: Filter = Filter.Builder()
            .addOperator("#SundayMorning")
            .and()
            .setLanguage(Filter.ENGLISH)
            .build()
```

See additional examples [here](https://github.com/tyczj/Tweedle/blob/master/lib/src/test/java/com/tycz/tweedle/lib/FilterBuilderTest.kt) as well as the twiter documentation about creating filters [here](https://developer.twitter.com/en/docs/twitter-api/tweets/filtered-stream/integrate/build-a-rule)

### Adding a filter/rule

````
val addRule = Add(filter.filter, "Sunday Morning")
filters.add(addRule)
val rule = Rule(filters)

_tweetStream.addRules(token, rule).collect {
    when(it){
        is Response.Error -> {it.exception}
        is Response.Success -> it.data
    }
}
````

Currently Twitter only supports 25 filters/rules per API key

To start streaming tweets call the `startTweetStream` endpoint

```
_tweetStream.startTweetStream(token).collect {
    when(it){
         is Response.Success -> Log.d("TWEET", it.data.data.text)
    }
}
```

Collect is called every time a new tweet is received

To start using Tweedle, include the dependency in your `build.gradle`

```
implementation 'com.tycz:tweedle:0.1.1'
```
