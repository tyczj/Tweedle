package com.tycz.tweedle.lib.tweets.stream.filter

class Filter private constructor(private val builder:Builder){

    companion object{
        const val AMHARIC = "am"
        const val ARABIC = "ar"
        const val ARMENIAN = "hy"
        const val BENGALI = "bn"
        const val BULGARIAN = "bg"
        const val BURMESE = "my"
        const val CHINESE = "zh"
        const val CZECH = "cs"
        const val DANISH = "da"
        const val DUTCH = "nl"
        const val ENGLISH = "en"
        const val ESTONIAN = "et"
        const val FINNISH = "fi"
        const val FRENCH = "fr"
        const val GEORGIAN = "ka"
        const val GREEK = "el"
        const val Gujarati = "gu"
        const val HAITIAN = "ht"
        const val HEBREW = "iw"
        const val HINDI = "hi"
        const val HUNGARIAN = "hu"
        const val ICELANDIC = "is"
        const val INDONESIAN = "in"
        const val ITALIAN = "it"
        const val JAPANESE = "ja"
        const val KANNADA = "kn"
        const val KHMER = "km"
        const val KOREAN = "ko"
        const val LAO = "lo"
        const val LATVIAN = "lv"
        const val MALAYALAM = "ml"
        const val MALDIVIAN = "dv"
        const val MARATHI = "mr"
        const val NEPALI = "ne"
        const val NORWEGIAN = "no"
        const val ORIYA = "or"
        const val PANJABI = "pa"
        const val PASHTO = "ps"
        const val PERSIAN = "fa"
        const val POLISH = "pl"
        const val PORTUGUESE = "pt"
        const val ROMANIAN = "ro"
        const val RUSSIAN = "ru"
        const val SERBIAN = "sr"
        const val SINDHI = "sd"
        const val SLOVAK = "sk"
        const val SLOVENIAN = "sl"
        const val SORANI_KURDISH = "ckb"
        const val SPANISH = "es"
        const val SWEDISH = "sv"
        const val TAGALOG = "tl"
        const val TAMIL = "ta"
        const val TELUGU = "te"
        const val THAI = "th"
        const val TIBETAN = "bo"
        const val TURKISH = "tr"
        const val UKRAINIAN = "uk"
        const val URDU = "ur"
        const val UYGHUR = "ug"
        const val VIETNAMESE = "vi"
        const val WELSH = "cy"
    }

    val filter:String
        get() = builder.tweetQuery

    /**
     * Builder class for creating a filter rule for tweet streaming
     */
    data class Builder(private val filter:String? = null){

        private val OR:String = " OR "
        private val AND:String = " "
        private val NOT:String = "-"
        private val RETWEET:String = "is:retweet"
        private val NOT_RETWEET:String = "${NOT}${RETWEET}"
        private val HAS_IMAGES:String = "has:images"
        private val IS_REPLY:String = "is:reply"
        private val IS_QUOTE:String = "is:quote"
        private val IS_VERIFIED:String = "is:verified"
        private val HAS_HASHTAG:String = "has:hashtag"
        private val HAS_LINKS:String = "has:links"
        private val HAS_MENTIONS:String = "has:mentions"
        private val HAS_MEDIA:String = "has:media"
        private val HAS_VIDEOS:String = "has:videos"

        private val _filter:StringBuilder = StringBuilder()

        val tweetQuery:String
            get() = _filter.toString()

        init {
            filter?.let {
                _filter.append(filter)
            }
        }

        /**
         * Adds a single operator to the filter. Operators are trimmed of any whitespace at the beginning or end of the string
         * @param operator: The operator to add the to stream filter
         */
        fun addOperator(operator:String):Builder{
            _filter.append(operator.trim())
            return this
        }

        /**
         * Appends an OR between two operators
         * Example: builder.addOperator("snow").or().addOperator("day")
         */
        fun or():Builder{
            _filter.append(OR)
            return this
        }

        /**
         * Appends an AND between two operators
         * Example: builder.addOperator("snow").and().addOperator("day")
         */
        fun and():Builder{
            _filter.append(AND)
            return this
        }

        /**
         * Negates an operator
         * Example builder.addOperator("snow").and().not().addOperator("day") would be "snow and not day"
         */
        fun not():Builder{
            _filter.append(NOT)
            return this
        }

        /**
         * Adds a custom built filter string
         * Example usage builder.addRawFilter("snow OR day OR #NoSchool")
         * @see <a href="https://developer.twitter.com/en/docs/twitter-api/tweets/filtered-stream/integrate/build-a-rule">Twitter Filter Rules</a>
         * @param rawFilter: Custom filter string
         */
        fun addRawFilter(rawFilter:String):Builder{
            _filter.append(rawFilter.trim())
            return this
        }

        //region Non-standalone operators

        /**
         * Deliver only explicit replies that match a rule
         */
        fun isReply():Builder{
            _filter.append(IS_REPLY)
            return this
        }

        /**
         * Delivers only explicit Retweets with comments (also known as Quoted Tweets) that match the rest of the specified rule.
         */
        fun isQuote():Builder{
            _filter.append(IS_QUOTE)
            return this
        }

        /**
         * Deliver only Tweets whose authors are verified by Twitter.
         */
        fun isVerified():Builder{
            _filter.append(IS_VERIFIED)
            return this
        }

        /**
         * Matches Tweets that contain at least one hashtag.
         */
        fun hasHashtag():Builder{
            _filter.append(HAS_HASHTAG)
            return this
        }

        /**
         * This operator matches Tweets which contain links in the Tweet body.
         */
        fun hasLinks():Builder{
            _filter.append(HAS_LINKS)
            return this
        }

        /**
         * Matches Tweets that mention another Twitter user.
         */
        fun hasMentions():Builder{
            _filter.append(HAS_MENTIONS)
            return this
        }

        /**
         * Matches Tweets that contain a media URL recognized by Twitter.
         */
        fun hasMedia():Builder{
            _filter.append(HAS_MEDIA)
            return this
        }

        /**
         * Matches Tweets that contain native Twitter videos, uploaded directly to Twitter.
         * This will not match on videos created with Periscope, or Tweets with links to other video hosting sites.
         */
        fun hasVideos():Builder{
            _filter.append(HAS_VIDEOS)
            return this
        }

        /**
         * Matches on Retweets that match the rest of the specified rule. This operator looks only for true Retweets
         * (for example, those generated using the Retweet button). Tweets with comments (also known as Quoted Tweets)
         * and modified Tweets will not be matched by this operator
         */
        fun isRetweet():Builder{
            _filter.append(RETWEET)
            return this
        }

        /**
         * Excludes retweets of the specified rule
         */
        fun isNotRetweet():Builder{
            _filter.append(NOT_RETWEET)
            return this
        }

        /**
         * Matches Tweets that contain a recognized URL to an image.
         */
        fun hasImages():Builder{
            _filter.append(HAS_IMAGES)
            return this
        }

        //endregion

        /**
         * Sets the specific language to filter tweets by. Default is no language returning tweets in all languages
         */
        fun setLanguage(language:String):Builder{
            _filter.append("lang: $language")
            return this
        }

        fun build():Filter{
            return Filter(this)
        }
    }
}