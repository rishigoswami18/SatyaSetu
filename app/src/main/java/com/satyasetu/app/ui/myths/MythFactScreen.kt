package com.satyasetu.app.ui.myths

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.satyasetu.app.data.model.Myth
import com.satyasetu.app.ui.theme.*
import com.satyasetu.app.util.toCompactString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MythFactScreen(viewModel: MythViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBackground, DarkSurface)))
    ) {
        // Header
        TopAppBar(
            title = {
                Column {
                    Text("सच या गलत? 🤔", fontWeight = FontWeight.Bold, color = TextWhite)
                    Text("अंधविश्वास vs विज्ञान", style = MaterialTheme.typography.bodySmall,
                        color = TextMuted)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
        )

        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = SaffronPrimary)
            }
        } else if (uiState.myths.isEmpty()) {
            Column(
                Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("🧪", fontSize = 64.sp)
                Spacer(Modifier.height(16.dp))
                Text("अभी कोई डेटा नहीं है", color = TextLight)
                Spacer(Modifier.height(16.dp))
                Button(onClick = { viewModel.seedData() },
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) { Text("सैंपल डेटा लोड करें") }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.myths) { myth ->
                    MythFactCard(
                        myth = myth,
                        onLike = { viewModel.likeMythFact(myth.mythId) }
                    )
                }
            }
        }
    }
}

@Composable
fun MythFactCard(myth: Myth, onLike: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Category badge
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = LightBlue.copy(alpha = 0.2f)
            ) {
                Text(
                    text = Myth.getCategoryDisplayName(myth.category),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    color = LightBlue, fontSize = 12.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            // MYTH section (Red)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MythCardRed.copy(alpha = 0.15f)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text("❌", fontSize = 24.sp)
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text("गलत धारणा:", color = MythCardRed,
                            fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(myth.myth, color = TextWhite, fontSize = 16.sp,
                            fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // FACT section (Green)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = FactCardGreen.copy(alpha = 0.15f)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text("✅", fontSize = 24.sp)
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text("सच्चाई:", color = FactCardGreen,
                            fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(myth.fact, color = TextWhite, fontSize = 16.sp,
                            fontWeight = FontWeight.Medium)
                    }
                }
            }

            // Expandable explanation
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(Modifier.height(12.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = GlassWhite
                    ) {
                        Column(Modifier.padding(14.dp)) {
                            Text("📖 विस्तृत जानकारी:", color = SaffronLight,
                                fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Spacer(Modifier.height(6.dp))
                            Text(myth.explanation, color = TextLight, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Actions row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Expand button
                TextButton(onClick = { expanded = !expanded }) {
                    Icon(
                        if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null, tint = SaffronLight
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        if (expanded) "कम पढ़ें" else "और पढ़ें",
                        color = SaffronLight, fontSize = 14.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        if (!isLiked) { isLiked = true; onLike() }
                    }) {
                        Icon(
                            if (isLiked) Icons.Filled.ThumbUp else Icons.Default.ThumbUpOffAlt,
                            contentDescription = "Like",
                            tint = if (isLiked) SaffronPrimary else TextMuted
                        )
                    }
                    Text(
                        (myth.likes + if (isLiked) 1 else 0).toCompactString(),
                        color = TextMuted, fontSize = 13.sp
                    )
                    Spacer(Modifier.width(8.dp))
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Share, "Share", tint = TextMuted)
                    }
                }
            }
        }
    }
}
