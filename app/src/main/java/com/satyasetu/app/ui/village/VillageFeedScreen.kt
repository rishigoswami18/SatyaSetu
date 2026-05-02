package com.satyasetu.app.ui.village

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.satyasetu.app.data.model.Post
import com.satyasetu.app.ui.theme.*
import com.satyasetu.app.util.toRelativeTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VillageFeedScreen(
    onNavigateToMap: () -> Unit,
    viewModel: VillageFeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showCreatePost by remember { mutableStateOf(false) }
    var showSearch by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("मेरा गाँव 🏡", fontWeight = FontWeight.Bold, color = TextWhite)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, null, tint = SaffronLight, modifier = Modifier.size(12.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(
                                uiState.villageName,
                                style = MaterialTheme.typography.bodySmall,
                                color = SaffronLight
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleLanguage() }) {
                        Icon(Icons.Default.Language, "Change Language", tint = TextLight)
                    }
                    IconButton(onClick = { showSearch = !showSearch }) {
                        Icon(Icons.Default.Search, "Search", tint = TextLight)
                    }
                    TextButton(onClick = onNavigateToMap) {
                        Text("गाँव बदलें", color = SaffronLight)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        },
        floatingActionButton = {
            if (uiState.villageId != null) {
                FloatingActionButton(
                    onClick = { showCreatePost = true },
                    containerColor = SaffronPrimary,
                    contentColor = TextWhite,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, "Create Post")
                }
            }
        },
        containerColor = DarkBackground
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Brush.verticalGradient(listOf(DarkBackground, DarkSurface)))
        ) {
            if (uiState.isLoading) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = SaffronPrimary)
                    if (uiState.isSeeding) {
                        Spacer(Modifier.height(12.dp))
                        Text("डेटा तैयार हो रहा है... 🚀", color = SaffronLight, fontSize = 14.sp)
                    }
                }
            } else if (uiState.villageId == null) {
                // Not mapped to a village yet
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("📍", fontSize = 64.sp)
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "अभी आपने अपना गाँव नहीं चुना है।",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "अपने गाँव की खबरें और जानकारी पाने के लिए अपना स्थान सेट करें।",
                        color = TextLight,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = onNavigateToMap,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FactCardGreen)
                    ) {
                        Text("अपना गाँव चुनें", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            } else if (uiState.posts.isEmpty()) {
                // Empty Feed
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("📰", fontSize = 64.sp)
                    Spacer(Modifier.height(16.dp))
                    Text("अभी कोई अपडेट नहीं है", color = TextLight, fontSize = 16.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("पहली पोस्ट करने के लिए + दबाएं", color = SaffronLight, fontSize = 14.sp)
                }
            } else {
                // Feed List with filters
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Search bar (animated)
                    if (showSearch) {
                        item {
                            OutlinedTextField(
                                value = uiState.searchQuery,
                                onValueChange = { viewModel.searchPosts(it) },
                                placeholder = { Text("पोस्ट खोजें...", color = TextMuted) },
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
                    }

                    // Filter chips
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val filters = listOf(
                                null to "सभी",
                                Post.TYPE_AWARENESS to "🧠 जागरूकता",
                                Post.TYPE_ISSUE to "⚠️ समस्या",
                                Post.TYPE_NEWS to "📢 समाचार"
                            )
                            items(filters) { (type, label) ->
                                FilterChip(
                                    selected = uiState.selectedFilter == type,
                                    onClick = { viewModel.setFilter(type) },
                                    label = {
                                        Text(
                                            label,
                                            fontSize = 13.sp,
                                            fontWeight = if (uiState.selectedFilter == type) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = DarkCard,
                                        labelColor = TextLight,
                                        selectedContainerColor = SaffronPrimary.copy(alpha = 0.2f),
                                        selectedLabelColor = SaffronLight
                                    ),
                                    border = FilterChipDefaults.filterChipBorder(
                                        enabled = true,
                                        selected = uiState.selectedFilter == type,
                                        borderColor = GlassBorder,
                                        selectedBorderColor = SaffronPrimary.copy(alpha = 0.5f)
                                    )
                                )
                            }
                        }
                    }

                    // Trending section
                    if (uiState.trendingPosts.isNotEmpty() && uiState.selectedFilter == null) {
                        item {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.TrendingUp, null, tint = StatusRejected, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("🔥 ट्रेंडिंग", color = StatusRejected, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                        }

                        items(uiState.trendingPosts.take(2)) { post ->
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                TrendingPostCard(post = post, onLike = { viewModel.likePost(post.postId) })
                            }
                        }

                        item {
                            Divider(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                color = GlassBorder,
                                thickness = 0.5.dp
                            )
                        }
                    }

                    // Main feed or search results
                    val displayPosts = if (uiState.searchQuery.isNotBlank()) {
                        uiState.searchResults
                    } else {
                        uiState.posts
                    }

                    if (uiState.searchQuery.isNotBlank() && displayPosts.isEmpty()) {
                        item {
                            Text(
                                "कोई परिणाम नहीं मिला 🔍",
                                color = TextMuted,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    items(displayPosts) { post ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            VillagePostCard(
                                post = post,
                                onLike = { viewModel.likePost(post.postId) }
                            )
                        }
                    }
                }
            }

            if (showCreatePost) {
                CreatePostBottomSheet(onDismiss = { showCreatePost = false })
            }
        }
    }
}

@Composable
fun TrendingPostCard(post: Post, onLike: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = StatusRejected.copy(alpha = 0.08f)
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.horizontalGradient(
                listOf(StatusRejected.copy(alpha = 0.3f), SaffronPrimary.copy(alpha = 0.3f))
            )
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🔥", fontSize = 14.sp)
                Spacer(Modifier.width(6.dp))
                Text(
                    post.userName,
                    color = TextWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Text("${post.likes} ❤️", color = TextMuted, fontSize = 12.sp)
            }
            Spacer(Modifier.height(6.dp))
            Text(
                post.description,
                color = TextLight,
                fontSize = 13.sp,
                lineHeight = 20.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun VillagePostCard(post: Post, onLike: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = LightBlue,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(post.userName.firstOrNull()?.toString() ?: "👤", color = TextWhite, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(post.userName, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(post.timestamp.toRelativeTime(), color = TextMuted, fontSize = 12.sp)
                    }
                }
                
                // Type Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = when (post.type) {
                        Post.TYPE_ISSUE -> StatusRejected.copy(alpha = 0.2f)
                        Post.TYPE_NEWS -> LightBlue.copy(alpha = 0.2f)
                        else -> FactCardGreen.copy(alpha = 0.2f)
                    }
                ) {
                    Text(
                        Post.getTypeDisplayName(post.type),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = when (post.type) {
                            Post.TYPE_ISSUE -> StatusRejected
                            Post.TYPE_NEWS -> LightBlue
                            else -> FactCardGreen
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Content
            if (post.description.isNotBlank()) {
                Text(
                    text = post.description,
                    color = TextWhite,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )
                Spacer(Modifier.height(12.dp))
            }

            // Media
            if (post.mediaUrl.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    AsyncImage(
                        model = post.mediaUrl,
                        contentDescription = "Post Media",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    if (post.isVideo) {
                        Icon(
                            imageVector = Icons.Default.PlayCircleFilled,
                            contentDescription = "Play Video",
                            tint = TextWhite.copy(alpha = 0.8f),
                            modifier = Modifier
                                .size(64.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
            }

            // Footer (Likes)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onLike) {
                    Icon(
                        if (post.likes > 0) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        "Like",
                        tint = if (post.likes > 0) StatusRejected else TextMuted
                    )
                }
                Text("${post.likes}", color = TextMuted, fontSize = 14.sp)
            }
        }
    }
}
