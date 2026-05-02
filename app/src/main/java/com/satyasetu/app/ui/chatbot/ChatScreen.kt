package com.satyasetu.app.ui.chatbot

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.satyasetu.app.data.model.ChatMessage
import com.satyasetu.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    // Auto-scroll to bottom
    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    val currentSuggestions = viewModel.getCurrentSuggestions()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBackground, DarkSurface)))
    ) {
        // Header
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = SaffronPrimary,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🤖", fontSize = 20.sp)
                        }
                    }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text("सत्यसेतु सहायक", fontWeight = FontWeight.Bold,
                            color = TextWhite, fontSize = 16.sp)
                        Text(
                            if (uiState.isLoading) "टाइप कर रहा है..." else "ऑनलाइन • ऑफलाइन भी काम करता है",
                            color = if (uiState.isLoading) SaffronLight else FactCardGreen,
                            fontSize = 11.sp
                        )
                    }
                }
            },
            actions = {
                // Clear chat button
                if (uiState.messages.size > 1) {
                    IconButton(onClick = { viewModel.clearChat() }) {
                        Icon(Icons.Default.DeleteSweep, "Clear Chat", tint = TextMuted)
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
        )

        // Messages
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.messages) { message ->
                ChatBubble(
                    message = message,
                    onRetry = if (message.isError) {
                        { viewModel.retryLastMessage() }
                    } else null
                )
            }

            // Suggestion chips (show when few messages or after errors)
            if (uiState.messages.size <= 2 || uiState.messages.lastOrNull()?.isError == true) {
                item {
                    Spacer(Modifier.height(8.dp))
                    Text("💡 सुझाव:", color = TextMuted, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 4.dp))
                    Spacer(Modifier.height(6.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(currentSuggestions) { suggestion ->
                            SuggestionChip(
                                onClick = { viewModel.useSuggestion(suggestion) },
                                label = { Text(suggestion, fontSize = 13.sp) },
                                shape = RoundedCornerShape(12.dp),
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = DarkCard,
                                    labelColor = SaffronLight
                                ),
                                border = SuggestionChipDefaults.suggestionChipBorder(
                                    enabled = true,
                                    borderColor = SaffronPrimary.copy(alpha = 0.3f)
                                )
                            )
                        }
                    }
                }
            }
        }

        // Disclaimer
        Text(
            "⚠ यह AI सहायक है। गंभीर समस्या के लिए विशेषज्ञ से मिलें।",
            color = TextMuted,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
        )

        // Input bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkCard,
            tonalElevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = uiState.inputText,
                    onValueChange = { viewModel.updateInput(it) },
                    placeholder = { Text("अपना सवाल पूछें...", fontSize = 15.sp) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SaffronPrimary,
                        unfocusedBorderColor = GlassBorder
                    )
                )
                Spacer(Modifier.width(8.dp))
                // Send button
                FloatingActionButton(
                    onClick = { viewModel.sendMessage() },
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    containerColor = if (uiState.isLoading) DarkCard else SaffronPrimary,
                    contentColor = TextWhite
                ) {
                    Icon(
                        if (uiState.isLoading) Icons.Default.HourglassTop
                        else Icons.Default.Send,
                        contentDescription = "Send",
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage, onRetry: (() -> Unit)? = null) {
    val isUser = message.isFromUser

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) {
            // Bot avatar
            Surface(
                shape = CircleShape,
                color = SaffronPrimary.copy(alpha = 0.2f),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Bottom)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("🤖", fontSize = 16.sp)
                }
            }
            Spacer(Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
        ) {
            Surface(
                shape = RoundedCornerShape(
                    topStart = 18.dp, topEnd = 18.dp,
                    bottomStart = if (isUser) 18.dp else 4.dp,
                    bottomEnd = if (isUser) 4.dp else 18.dp
                ),
                color = when {
                    message.isLoading -> DarkCard
                    message.isError -> StatusRejected.copy(alpha = 0.15f)
                    isUser -> SaffronPrimary
                    else -> DarkCard
                },
                modifier = Modifier.widthIn(max = 300.dp)
            ) {
                if (message.isLoading) {
                    Row(Modifier.padding(16.dp)) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = SaffronPrimary,
                            strokeWidth = 2.dp
                        )
                        Spacer(Modifier.width(10.dp))
                        Text("सोच रहा हूँ...", color = TextMuted, fontSize = 14.sp)
                    }
                } else {
                    Text(
                        text = message.content,
                        modifier = Modifier.padding(14.dp),
                        color = if (isUser) TextWhite else TextLight,
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                }
            }

            // Retry button for error messages
            if (message.isError && onRetry != null) {
                Spacer(Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SaffronPrimary.copy(alpha = 0.15f),
                    modifier = Modifier.clickable { onRetry() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Retry",
                            tint = SaffronLight,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("पुनः प्रयास करें", color = SaffronLight, fontSize = 12.sp)
                    }
                }
            }
        }

        if (isUser) {
            Spacer(Modifier.width(8.dp))
            Surface(
                shape = CircleShape,
                color = LightBlue.copy(alpha = 0.2f),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Bottom)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("👤", fontSize = 16.sp)
                }
            }
        }
    }
}
