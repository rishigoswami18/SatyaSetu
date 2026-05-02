package com.satyasetu.app.data.remote;

import com.satyasetu.app.data.model.ChatRequest;
import com.satyasetu.app.data.model.ChatResponse;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Retrofit service interface for OpenAI API
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/satyasetu/app/data/remote/OpenAIService;", "", "getChatCompletion", "Lcom/satyasetu/app/data/model/ChatResponse;", "authorization", "", "request", "Lcom/satyasetu/app/data/model/ChatRequest;", "(Ljava/lang/String;Lcom/satyasetu/app/data/model/ChatRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface OpenAIService {
    
    @retrofit2.http.POST(value = "chat/completions")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getChatCompletion(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String authorization, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.model.ChatRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.satyasetu.app.data.model.ChatResponse> $completion);
}