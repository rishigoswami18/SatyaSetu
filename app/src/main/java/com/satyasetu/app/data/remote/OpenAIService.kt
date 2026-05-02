package com.satyasetu.app.data.remote

import com.satyasetu.app.data.model.ChatRequest
import com.satyasetu.app.data.model.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Retrofit service interface for OpenAI API
 */
interface OpenAIService {

    @POST("chat/completions")
    suspend fun getChatCompletion(
        @Header("Authorization") authorization: String,
        @Body request: ChatRequest
    ): ChatResponse
}
