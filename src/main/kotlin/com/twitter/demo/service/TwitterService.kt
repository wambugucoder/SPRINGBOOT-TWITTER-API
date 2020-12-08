package com.twitter.demo.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.twitter.api.Twitter
import org.springframework.stereotype.Service

@Service
class TwitterService {

    @Autowired
    lateinit var twitter: Twitter

    @Autowired
    lateinit var objectMapper:ObjectMapper



    fun getTweetsByHashtag(hashtag:String): MutableList<String> {
        var filteredTweet= mutableListOf<String>()
        val allTweets=  twitter.searchOperations().search(hashtag)
        if (allTweets != null) {
            for(eachTweet in allTweets.tweets){
                val getTweet = eachTweet.text
                filteredTweet.add(getTweet)
            }

        }
        return filteredTweet

    }
    fun unfollowNonFollowers(): String {
        val getFollowers = twitter.friendOperations().followerIds
        val getFollowing= twitter.friendOperations().friendIds

        val notFollowing = getFollowing.toSet().minus(getFollowers.toSet())
        for(eachUser in notFollowing){
            twitter.friendOperations().unfollow(eachUser)
        }

        return "Done"
    }
}