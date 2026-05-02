package com.satyasetu.app.ui.chatbot;

import androidx.lifecycle.ViewModel;
import com.satyasetu.app.data.model.ChatMessage;
import com.satyasetu.app.data.repository.ChatRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import java.util.UUID;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0011\u001a\u00020\u0012J\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u0006\u0010\u0014\u001a\u00020\u0012J\u0006\u0010\u0015\u001a\u00020\u0012J\u000e\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\nJ\u000e\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\nR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001a"}, d2 = {"Lcom/satyasetu/app/ui/chatbot/ChatViewModel;", "Landroidx/lifecycle/ViewModel;", "chatRepository", "Lcom/satyasetu/app/data/repository/ChatRepository;", "(Lcom/satyasetu/app/data/repository/ChatRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/satyasetu/app/ui/chatbot/ChatUiState;", "suggestions", "", "", "getSuggestions", "()Ljava/util/List;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearChat", "", "getCurrentSuggestions", "retryLastMessage", "sendMessage", "updateInput", "text", "useSuggestion", "suggestion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ChatViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.satyasetu.app.data.repository.ChatRepository chatRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.satyasetu.app.ui.chatbot.ChatUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.chatbot.ChatUiState> uiState = null;
    
    /**
     * Smart suggestion categories that rotate based on conversation.
     */
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.util.List<java.lang.String>> suggestions = null;
    
    @javax.inject.Inject()
    public ChatViewModel(@org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.repository.ChatRepository chatRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.chatbot.ChatUiState> getUiState() {
        return null;
    }
    
    /**
     * Smart suggestion categories that rotate based on conversation.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.util.List<java.lang.String>> getSuggestions() {
        return null;
    }
    
    /**
     * Get the current suggestion set based on message count.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getCurrentSuggestions() {
        return null;
    }
    
    public final void updateInput(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void sendMessage() {
    }
    
    public final void useSuggestion(@org.jetbrains.annotations.NotNull()
    java.lang.String suggestion) {
    }
    
    /**
     * Retry the last failed message.
     */
    public final void retryLastMessage() {
    }
    
    /**
     * Clear chat history, keeping only the welcome message.
     */
    public final void clearChat() {
    }
}