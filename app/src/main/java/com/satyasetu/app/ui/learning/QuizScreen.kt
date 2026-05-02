package com.satyasetu.app.ui.learning

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.satyasetu.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    lessonId: String,
    onNavigateBack: () -> Unit,
    viewModel: LearningViewModel = hiltViewModel()
) {
    val quizState by viewModel.quizState.collectAsState()

    LaunchedEffect(lessonId) {
        viewModel.loadQuiz(lessonId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBackground, DarkSurface)))
    ) {
        TopAppBar(
            title = {
                Text(
                    quizState.lesson?.title ?: "क्विज़",
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    maxLines = 1
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = TextWhite)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
        )

        if (quizState.isComplete) {
            // Quiz Complete - Score Screen
            QuizResultScreen(
                score = quizState.score,
                correctCount = quizState.correctCount,
                totalQuestions = quizState.questions.size,
                onGoBack = onNavigateBack
            )
        } else if (quizState.questions.isNotEmpty()) {
            val currentQuestion = quizState.questions[quizState.currentIndex]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Progress bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "प्रश्न ${quizState.currentIndex + 1}/${quizState.questions.size}",
                            color = SaffronLight,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Text(
                            "✅ ${quizState.correctCount} सही",
                            color = FactCardGreen,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = {
                            (quizState.currentIndex + 1).toFloat() / quizState.questions.size
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = SaffronPrimary,
                        trackColor = DarkCard
                    )

                    Spacer(Modifier.height(32.dp))

                    // Question
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkCard)
                    ) {
                        Text(
                            text = currentQuestion.question,
                            modifier = Modifier.padding(24.dp),
                            color = TextWhite,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 30.sp
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    // Options
                    currentQuestion.options.forEachIndexed { index, option ->
                        val isSelected = quizState.selectedAnswer == index
                        val isCorrect = index == currentQuestion.correctOptionIndex
                        val bgColor = when {
                            !quizState.isAnswered -> DarkCard
                            isCorrect -> FactCardGreen.copy(alpha = 0.2f)
                            isSelected && !isCorrect -> StatusRejected.copy(alpha = 0.2f)
                            else -> DarkCard
                        }
                        val borderColor = when {
                            !quizState.isAnswered && isSelected -> SaffronPrimary
                            quizState.isAnswered && isCorrect -> FactCardGreen
                            quizState.isAnswered && isSelected && !isCorrect -> StatusRejected
                            else -> Color.Transparent
                        }

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .border(2.dp, borderColor, RoundedCornerShape(16.dp))
                                .clickable(enabled = !quizState.isAnswered) {
                                    viewModel.selectAnswer(index)
                                },
                            shape = RoundedCornerShape(16.dp),
                            color = bgColor
                        ) {
                            Row(
                                modifier = Modifier.padding(18.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Option letter
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSelected && !quizState.isAnswered) SaffronPrimary
                                    else GlassWhite,
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = ('अ' + index).toString(),
                                            color = TextWhite,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                                Spacer(Modifier.width(14.dp))
                                Text(
                                    text = option,
                                    color = TextWhite,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                if (quizState.isAnswered) {
                                    if (isCorrect) {
                                        Icon(Icons.Default.CheckCircle, null,
                                            tint = FactCardGreen, modifier = Modifier.size(24.dp))
                                    } else if (isSelected) {
                                        Icon(Icons.Default.Cancel, null,
                                            tint = StatusRejected, modifier = Modifier.size(24.dp))
                                    }
                                }
                            }
                        }
                    }

                    // Explanation after answering
                    AnimatedVisibility(visible = quizState.isAnswered) {
                        Column {
                            Spacer(Modifier.height(16.dp))
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = LightBlue.copy(alpha = 0.1f)
                            ) {
                                Row(Modifier.padding(14.dp)) {
                                    Text("💡", fontSize = 20.sp)
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        currentQuestion.explanation,
                                        color = TextLight,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Next button
                if (quizState.isAnswered) {
                    Button(
                        onClick = { viewModel.nextQuestion() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
                    ) {
                        Text(
                            if (quizState.currentIndex + 1 >= quizState.questions.size)
                                "नतीजे देखें" else "अगला प्रश्न →",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        } else {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("इस पाठ में कोई क्विज़ नहीं है", color = TextMuted)
            }
        }
    }
}

@Composable
fun QuizResultScreen(
    score: Int,
    correctCount: Int,
    totalQuestions: Int,
    onGoBack: () -> Unit
) {
    val emoji = when {
        score >= 80 -> "🏆"
        score >= 60 -> "👏"
        score >= 40 -> "💪"
        else -> "📖"
    }
    val message = when {
        score >= 80 -> "बहुत बढ़िया! आप तो एक्सपर्ट हैं!"
        score >= 60 -> "अच्छा प्रदर्शन! थोड़ा और अभ्यास करें।"
        score >= 40 -> "ठीक है! पाठ दोबारा पढ़ें और फिर कोशिश करें।"
        else -> "कोई बात नहीं! पाठ पढ़ें और दोबारा कोशिश करें।"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(emoji, fontSize = 80.sp)
        Spacer(Modifier.height(24.dp))
        Text(
            "$score%",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            color = when {
                score >= 60 -> FactCardGreen
                score >= 40 -> StatusPending
                else -> StatusRejected
            }
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "$correctCount / $totalQuestions सही जवाब",
            color = TextLight,
            fontSize = 18.sp
        )
        Spacer(Modifier.height(16.dp))
        Text(
            message,
            color = TextWhite,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(40.dp))
        Button(
            onClick = onGoBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
        ) {
            Text("वापस जाएँ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
