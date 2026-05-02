package com.satyasetu.app.ui.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasetu.app.data.model.ChatMessage
import com.satyasetu.app.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentLanguage: String = "hi"
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState(
        messages = listOf(
            ChatMessage(
                id = "welcome",
                content = "नमस्ते! 🙏 मैं सत्यसेतु सहायक हूँ।\n\n" +
                    "आप मुझसे कुछ भी पूछ सकते हैं:\n" +
                    "• स्वास्थ्य संबंधी सवाल\n" +
                    "• सरकारी योजनाएँ\n" +
                    "• अंधविश्वास का सच\n" +
                    "• डिजिटल सुरक्षा\n" +
                    "• खेती की जानकारी\n\n" +
                    "💡 नीचे दिए सुझावों पर टैप करें या अपना सवाल टाइप करें!",
                isFromUser = false
            )
        )
    ))
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    /**
     * Smart suggestion categories that rotate based on conversation.
     */
    val suggestions: List<List<String>> = listOf(
        // Initial suggestions
        listOf(
            "बुखार में क्या करें?",
            "PM किसान योजना क्या है?",
            "काला जादू सच है?",
            "OTP फ्रॉड से कैसे बचें?"
        ),
        // Health suggestions
        listOf(
            "सांप काटने पर क्या करें?",
            "डेंगू के लक्षण क्या हैं?",
            "साफ पानी क्यों जरूरी?",
            "बच्चों को कौन से टीके लगवाएं?"
        ),
        // Scheme suggestions
        listOf(
            "आयुष्मान भारत क्या है?",
            "मुद्रा लोन कैसे मिलेगा?",
            "छात्रवृत्ति कैसे मिलेगी?",
            "उज्ज्वला योजना के लिए कैसे आवेदन करें?"
        ),
        // Safety suggestions
        listOf(
            "फेक न्यूज़ कैसे पहचानें?",
            "बाल विवाह गैरकानूनी है?",
            "मच्छरों से कैसे बचें?",
            "पासवर्ड कैसे सुरक्षित रखें?"
        )
    )

    /**
     * Get the current suggestion set based on message count.
     */
    fun getCurrentSuggestions(): List<String> {
        val messageCount = _uiState.value.messages.size
        val index = (messageCount / 3) % suggestions.size
        return suggestions[index]
    }

    fun updateInput(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun sendMessage() {
        val text = _uiState.value.inputText.trim()
        if (text.isBlank()) return

        // Get history BEFORE adding the new message
        val history = _uiState.value.messages.filter { !it.isLoading && it.id != "welcome" }

        val userMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            content = text,
            isFromUser = true
        )

        val loadingMessage = ChatMessage(
            id = "loading",
            content = "",
            isFromUser = false,
            isLoading = true
        )

        _uiState.update {
            it.copy(
                messages = it.messages + userMessage + loadingMessage,
                inputText = "",
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            chatRepository.getAIResponse(text, history)
                .onSuccess { response ->
                    val botMessage = ChatMessage(
                        id = UUID.randomUUID().toString(),
                        content = response,
                        isFromUser = false
                    )
                    _uiState.update {
                        it.copy(
                            messages = it.messages.filter { m -> !m.isLoading } + botMessage,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { e ->
                    val errorMessage = ChatMessage(
                        id = UUID.randomUUID().toString(),
                        content = "माफ़ कीजिए, कुछ गड़बड़ हो गई। कृपया दोबारा कोशिश करें। 🙏\n\n" +
                            "💡 कुछ सुझाव:\n" +
                            "• \"बुखार\" - स्वास्थ्य\n" +
                            "• \"योजना\" - सरकारी स्कीम\n" +
                            "• \"फ्रॉड\" - डिजिटल सुरक्षा",
                        isFromUser = false,
                        isError = true
                    )
                    _uiState.update {
                        it.copy(
                            messages = it.messages.filter { m -> !m.isLoading } + errorMessage,
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
        }
    }

    fun useSuggestion(suggestion: String) {
        _uiState.update { it.copy(inputText = suggestion) }
        sendMessage()
    }

    /**
     * Retry the last failed message.
     */
    fun retryLastMessage() {
        val messages = _uiState.value.messages
        val lastUserMessage = messages.lastOrNull { it.isFromUser }
        if (lastUserMessage != null) {
            // Remove the error message
            _uiState.update {
                it.copy(
                    messages = it.messages.filter { m -> !m.isError },
                    inputText = lastUserMessage.content
                )
            }
            // Remove the user message too since sendMessage will re-add it
            _uiState.update {
                it.copy(
                    messages = it.messages.dropLast(1)
                )
            }
            sendMessage()
        }
    }

    /**
     * Clear chat history, keeping only the welcome message.
     */
    fun clearChat() {
        _uiState.update {
            it.copy(
                messages = listOf(it.messages.first()),
                error = null
            )
        }
    }
}
