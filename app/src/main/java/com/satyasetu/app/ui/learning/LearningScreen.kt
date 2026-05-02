package com.satyasetu.app.ui.learning

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.satyasetu.app.data.model.Lesson
import com.satyasetu.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningScreen(
    onNavigateToQuiz: (String) -> Unit,
    viewModel: LearningViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val categories = listOf(
        null to "सभी",
        Lesson.CATEGORY_HEALTH to "🏥 स्वास्थ्य",
        Lesson.CATEGORY_DIGITAL_FRAUD to "🛡️ डिजिटल",
        Lesson.CATEGORY_VOTING to "🗳️ मतदान",
        Lesson.CATEGORY_SCIENCE to "🔬 विज्ञान"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBackground, DarkSurface)))
    ) {
        TopAppBar(
            title = {
                Column {
                    Text("सीखें 📚", fontWeight = FontWeight.Bold, color = TextWhite)
                    Text("ज्ञान ही शक्ति है", style = MaterialTheme.typography.bodySmall,
                        color = TextMuted)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
        )

        // Category filter chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { (cat, label) ->
                FilterChip(
                    selected = uiState.selectedCategory == cat,
                    onClick = { viewModel.selectCategory(cat) },
                    label = { Text(label, fontSize = 13.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SaffronPrimary,
                        selectedLabelColor = TextWhite
                    )
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = SaffronPrimary)
            }
        } else if (uiState.lessons.isEmpty()) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("📖", fontSize = 64.sp)
                Spacer(Modifier.height(16.dp))
                Text("अभी कोई पाठ नहीं है", color = TextLight)
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.seedData() },
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) { Text("सैंपल डेटा लोड करें") }
            }
        } else {
            val filtered = if (uiState.selectedCategory != null) {
                uiState.lessons.filter { it.category == uiState.selectedCategory }
            } else uiState.lessons

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filtered) { lesson ->
                    LessonCard(
                        lesson = lesson,
                        isCompleted = uiState.completedLessons.contains(lesson.lessonId),
                        onStartQuiz = {
                            viewModel.loadQuiz(lesson.lessonId)
                            onNavigateToQuiz(lesson.lessonId)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LessonCard(
    lesson: Lesson,
    isCompleted: Boolean,
    onStartQuiz: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    // Category + time
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            Lesson.getCategoryIcon(lesson.category),
                            fontSize = 20.sp
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            Lesson.getCategoryDisplayName(lesson.category),
                            color = SaffronLight, fontSize = 12.sp
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "⏱ ${lesson.estimatedMinutes} मिनट",
                            color = TextMuted, fontSize = 12.sp
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        lesson.title, color = TextWhite,
                        fontWeight = FontWeight.Bold, fontSize = 18.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        lesson.description, color = TextLight,
                        fontSize = 14.sp, maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (isCompleted) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = FactCardGreen.copy(alpha = 0.2f)
                    ) {
                        Text(
                            "✅ पूरा",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = FactCardGreen, fontSize = 12.sp
                        )
                    }
                }
            }

            // Expandable content
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(Modifier.height(12.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = GlassWhite
                    ) {
                        Text(
                            lesson.content,
                            modifier = Modifier.padding(14.dp),
                            color = TextLight, fontSize = 15.sp,
                            lineHeight = 24.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { expanded = !expanded }) {
                    Icon(
                        if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        null, tint = SaffronLight
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        if (expanded) "बंद करें" else "पाठ पढ़ें",
                        color = SaffronLight
                    )
                }

                if (lesson.quiz.isNotEmpty()) {
                    Button(
                        onClick = onStartQuiz,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentGreen
                        )
                    ) {
                        Icon(Icons.Default.Quiz, null, Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("क्विज़ दें", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
