package com.y.app.features.post.utils

import com.google.gson.Gson
import com.y.app.features.post.data.Comment
import com.y.app.features.post.data.CommentWsMessage
import okhttp3.OkHttpClient
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.subscribeText
import org.hildan.krossbow.stomp.use
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import java.time.Duration

class CommentWebSocketManager(private val url: String) {
    private val okHttpClient = OkHttpClient.Builder().callTimeout(Duration.ofMinutes(1))
        .pingInterval(Duration.ofSeconds(10)).build()
    private val wsClient = OkHttpWebSocketClient(okHttpClient)
    private val stompClient = StompClient(wsClient)

    suspend fun start(
        postId: Int,
        onCommentReceived: suspend (Comment) -> Unit,
        onFailure: suspend (Exception) -> Unit
    ) {
        try {
            val session = stompClient.connect(url)
            session.use {
                it.subscribeText("/topic/comments").collect { message ->
                    val commentWsMessage = Gson().fromJson(message, CommentWsMessage::class.java)
                    if (commentWsMessage.postId == postId) onCommentReceived(commentWsMessage.commentResponse)
                }
            }
        } catch (e: Exception) {
            onFailure(e)
        }
    }

}