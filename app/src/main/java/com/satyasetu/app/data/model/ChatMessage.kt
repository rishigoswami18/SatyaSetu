package com.satyasetu.app.data.model

/**
 * Chat message model for AI chatbot
 */
data class ChatMessage(
    val id: String = "",
    val content: String = "",
    val isFromUser: Boolean = true,
    val timestamp: Long = System.currentTimeMillis(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

/**
 * OpenAI API request/response models
 */
data class ChatRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<ApiMessage>,
    val max_tokens: Int = 500,
    val temperature: Double = 0.7
)

data class ApiMessage(
    val role: String,
    val content: String
)

data class ChatResponse(
    val id: String?,
    val choices: List<Choice>?
)

data class Choice(
    val message: ApiMessage?,
    val finish_reason: String?
)
