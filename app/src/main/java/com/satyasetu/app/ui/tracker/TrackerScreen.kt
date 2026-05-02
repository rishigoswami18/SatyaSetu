package com.satyasetu.app.ui.tracker

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.satyasetu.app.data.model.DevelopmentItem
import com.satyasetu.app.ui.theme.*
import com.satyasetu.app.util.toFormattedDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerScreen(viewModel: TrackerViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBackground, DarkSurface)))
    ) {
        TopAppBar(
            title = {
                Column {
                    Text("विकास ट्रैकर 📊", fontWeight = FontWeight.Bold, color = TextWhite)
                    Text("आपके क्षेत्र का विकास", style = MaterialTheme.typography.bodySmall,
                        color = TextMuted)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
        )

        // Disclaimer
        Surface(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            color = LightBlue.copy(alpha = 0.1f)
        ) {
            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("ℹ️", fontSize = 18.sp)
                Spacer(Modifier.width(8.dp))
                Text(
                    "यह जानकारी तटस्थ और तथ्यात्मक है। किसी राजनीतिक दल से संबंधित नहीं।",
                    color = LightBlue, fontSize = 12.sp
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // Search
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { viewModel.updateSearch(it) },
            placeholder = { Text("क्षेत्र खोजें...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = SaffronPrimary)
            }
        } else if (uiState.items.isEmpty()) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("📋", fontSize = 64.sp)
                Spacer(Modifier.height(16.dp))
                Text("अभी कोई डेटा नहीं है", color = TextLight)
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.seedData() },
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) { Text("सैंपल डेटा लोड करें") }
            }
        } else {
            val filtered = if (uiState.searchQuery.isNotBlank()) {
                uiState.items.filter {
                    it.area.contains(uiState.searchQuery, ignoreCase = true) ||
                    it.district.contains(uiState.searchQuery, ignoreCase = true)
                }
            } else uiState.items

            // Summary bar
            val completed = filtered.count { it.status }
            val total = filtered.size
            Surface(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                color = DarkCard
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatItem("कुल", total.toString(), LightBlue)
                    StatItem("पूरे ✅", completed.toString(), FactCardGreen)
                    StatItem("बाकी ❌", (total - completed).toString(), StatusRejected)
                }
            }

            Spacer(Modifier.height(8.dp))

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filtered) { item ->
                    DevelopmentCard(item)
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String, color: androidx.compose.ui.graphics.Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = color, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(label, color = TextMuted, fontSize = 12.sp)
    }
}

@Composable
fun DevelopmentCard(item: DevelopmentItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Text(
                DevelopmentItem.getCategoryIcon(item.category),
                fontSize = 32.sp
            )
            Spacer(Modifier.width(14.dp))

            // Content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    DevelopmentItem.getCategoryDisplayName(item.category),
                    color = SaffronLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    item.title,
                    color = TextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "${item.area}, ${item.district}",
                    color = TextMuted,
                    fontSize = 13.sp
                )
                if (item.source.isNotBlank()) {
                    Text(
                        "स्रोत: ${item.source}",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }
            }

            // Status indicator (YES / NO)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (item.status) FactCardGreen.copy(alpha = 0.2f)
                else StatusRejected.copy(alpha = 0.2f)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        if (item.status) "हाँ" else "नहीं",
                        color = if (item.status) FactCardGreen else StatusRejected,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        if (item.status) "उपलब्ध" else "अनुपलब्ध",
                        color = if (item.status) FactCardGreen else StatusRejected,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}
