package com.satyasetu.app.ui.schemes

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satyasetu.app.data.model.GovernmentScheme
import com.satyasetu.app.data.repository.SchemeRepository
import com.satyasetu.app.ui.theme.*

/**
 * Government Schemes Screen — Displays curated government scheme information.
 * Users can browse, search, and learn how to apply for schemes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchemesScreen(
    onNavigateBack: () -> Unit
) {
    val schemeRepository = remember { SchemeRepository() }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var expandedSchemeId by remember { mutableStateOf<String?>(null) }

    val schemes = remember(selectedCategory, searchQuery) {
        schemeRepository.getSchemes(
            query = searchQuery.takeIf { it.isNotBlank() },
            category = selectedCategory
        )
    }
    val categories = remember { schemeRepository.getSchemeCategories() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("सरकारी योजनाएँ 📋", fontWeight = FontWeight.Bold, color = TextWhite)
                        Text(
                            "${schemes.size} योजनाएँ उपलब्ध",
                            color = SaffronLight,
                            fontSize = 12.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = TextWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        },
        containerColor = DarkBackground
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Brush.verticalGradient(listOf(DarkBackground, DarkSurface))),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Search
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("योजना खोजें...", color = TextMuted) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Search, null, tint = SaffronLight)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SaffronPrimary,
                        unfocusedBorderColor = DarkCard,
                        focusedContainerColor = DarkCard,
                        unfocusedContainerColor = DarkCard
                    )
                )
            }

            // Category Filter Chips
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedCategory == null,
                            onClick = { selectedCategory = null },
                            label = { Text("सभी", fontSize = 13.sp) },
                            shape = RoundedCornerShape(12.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = DarkCard,
                                labelColor = TextLight,
                                selectedContainerColor = SaffronPrimary.copy(alpha = 0.2f),
                                selectedLabelColor = SaffronLight
                            )
                        )
                    }
                    items(categories) { (category, count) ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = {
                                selectedCategory = if (selectedCategory == category) null else category
                            },
                            label = {
                                Text(
                                    "${GovernmentScheme.getCategoryDisplayNameHindi(category)} ($count)",
                                    fontSize = 13.sp
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = DarkCard,
                                labelColor = TextLight,
                                selectedContainerColor = SaffronPrimary.copy(alpha = 0.2f),
                                selectedLabelColor = SaffronLight
                            )
                        )
                    }
                }
            }

            // Scheme Cards
            items(schemes) { scheme ->
                SchemeCard(
                    scheme = scheme,
                    isExpanded = expandedSchemeId == scheme.id,
                    onToggle = {
                        expandedSchemeId = if (expandedSchemeId == scheme.id) null else scheme.id
                    }
                )
            }

            if (schemes.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("कोई योजना नहीं मिली 🔍", color = TextMuted, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun SchemeCard(
    scheme: GovernmentScheme,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onToggle() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category icon
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SaffronPrimary.copy(alpha = 0.15f),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            when (scheme.category) {
                                GovernmentScheme.CATEGORY_FARMING -> "🌾"
                                GovernmentScheme.CATEGORY_HEALTH -> "🏥"
                                GovernmentScheme.CATEGORY_EDUCATION -> "📚"
                                GovernmentScheme.CATEGORY_HOUSING -> "🏠"
                                GovernmentScheme.CATEGORY_FINANCIAL -> "💰"
                                GovernmentScheme.CATEGORY_WOMEN -> "👩"
                                else -> "📋"
                            },
                            fontSize = 22.sp
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        scheme.nameHindi,
                        color = TextWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        scheme.nameEnglish,
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }
                Icon(
                    if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = "Expand",
                    tint = SaffronLight
                )
            }

            Spacer(Modifier.height(8.dp))

            // Description (always visible)
            Text(
                scheme.descriptionHindi,
                color = TextLight,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis
            )

            // Expanded details
            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Spacer(Modifier.height(16.dp))
                    Divider(color = GlassBorder, thickness = 0.5.dp)
                    Spacer(Modifier.height(12.dp))

                    // Eligibility
                    SchemeDetailSection(
                        icon = "👥",
                        title = "पात्रता:",
                        content = scheme.eligibilityHindi
                    )

                    // Benefits
                    SchemeDetailSection(
                        icon = "🎁",
                        title = "लाभ:",
                        content = scheme.benefitHindi
                    )

                    // How to apply
                    SchemeDetailSection(
                        icon = "📝",
                        title = "आवेदन कैसे करें:",
                        content = scheme.howToApplyHindi
                    )

                    // Website link
                    if (scheme.websiteUrl.isNotBlank()) {
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {
                                try {
                                    uriHandler.openUri(scheme.websiteUrl)
                                } catch (_: Exception) { }
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.OpenInNew, null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("वेबसाइट पर जाएं", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SchemeDetailSection(icon: String, title: String, content: String) {
    if (content.isNotBlank()) {
        Spacer(Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.Top) {
            Text(icon, fontSize = 16.sp)
            Spacer(Modifier.width(8.dp))
            Column {
                Text(title, color = SaffronLight, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.height(2.dp))
                Text(content, color = TextLight, fontSize = 13.sp, lineHeight = 20.sp)
            }
        }
    }
}
