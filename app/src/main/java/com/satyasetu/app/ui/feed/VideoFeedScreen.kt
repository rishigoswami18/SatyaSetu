package com.satyasetu.app.ui.feed

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.media3.ui.AspectRatioFrameLayout
import com.satyasetu.app.data.model.Video
import com.satyasetu.app.ui.theme.*
import com.satyasetu.app.util.toCompactString

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun VideoFeedScreen(
    onNavigateToReport: () -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val categories = Video.getAllCategories()

    Box(modifier = Modifier.fillMaxSize().background(DarkBackground)) {
        if (uiState.isLoading && uiState.videos.isEmpty()) {
            // Loading state
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = SaffronPrimary)
                Spacer(modifier = Modifier.height(16.dp))
                Text("वीडियो लोड हो रहे हैं...", color = TextLight)
            }
        } else if (uiState.videos.isEmpty()) {
            // Empty state with seed button
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("📹", fontSize = 64.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text("अभी कोई वीडियो नहीं है", color = TextLight,
                    style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { viewModel.seedData() },
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("सैंपल डेटा लोड करें", fontSize = 16.sp)
                }
            }
        } else {
            // Video Pager (Reels-like)
            val pagerState = rememberPagerState(pageCount = { uiState.videos.size })

            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val video = uiState.videos[page]
                VideoPage(
                    video = video,
                    isCurrentPage = pagerState.currentPage == page,
                    onLike = { viewModel.likeVideo(video.videoId) },
                    onShare = { /* Share intent */ },
                    onReport = onNavigateToReport
                )
            }

            // Track current video view
            LaunchedEffect(pagerState.currentPage) {
                if (uiState.videos.isNotEmpty()) {
                    viewModel.onVideoViewed(uiState.videos[pagerState.currentPage].videoId)
                }
            }
        }

        // Category chips at top
        if (uiState.videos.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.selectedCategory == null,
                    onClick = { viewModel.selectCategory(null) },
                    label = { Text("सभी", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SaffronPrimary,
                        selectedLabelColor = TextWhite
                    )
                )
                categories.take(3).forEach { category ->
                    FilterChip(
                        selected = uiState.selectedCategory == category,
                        onClick = { viewModel.selectCategory(category) },
                        label = { Text(Video.getCategoryDisplayName(category), fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SaffronPrimary,
                            selectedLabelColor = TextWhite
                        )
                    )
                }
            }
        }
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VideoPage(
    video: Video,
    isCurrentPage: Boolean,
    onLike: () -> Unit,
    onShare: () -> Unit,
    onReport: () -> Unit
) {
    val context = LocalContext.current
    var isLiked by remember { mutableStateOf(false) }

    // ExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
            volume = 1f
        }
    }

    // Set media when video changes
    LaunchedEffect(video.url) {
        exoPlayer.setMediaItem(MediaItem.fromUri(video.url))
        exoPlayer.prepare()
    }

    // Play/Pause based on current page
    LaunchedEffect(isCurrentPage) {
        exoPlayer.playWhenReady = isCurrentPage
    }

    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Video Player
        AndroidView(
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Bottom gradient overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                    )
                )
        )

        // Video Info (bottom left)
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 100.dp, end = 80.dp)
        ) {
            // Category chip
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = SaffronPrimary.copy(alpha = 0.8f)
            ) {
                Text(
                    text = Video.getCategoryDisplayName(video.category),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    color = TextWhite,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = video.title,
                color = TextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = video.description,
                color = TextLight,
                fontSize = 14.sp,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "👁 ${video.views.toCompactString()} views",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        // Action buttons (right side)
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Like
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = {
                        if (!isLiked) { isLiked = true; onLike() }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(GlassWhite)
                ) {
                    Icon(
                        if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (isLiked) StatusRejected else TextWhite,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Text(
                    text = (video.likes + if (isLiked) 1 else 0).toCompactString(),
                    color = TextWhite,
                    fontSize = 12.sp
                )
            }

            // Share
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onShare,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(GlassWhite)
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Share",
                        tint = TextWhite, modifier = Modifier.size(28.dp))
                }
                Text("शेयर", color = TextWhite, fontSize = 12.sp)
            }

            // Save
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(GlassWhite)
                ) {
                    Icon(Icons.Outlined.BookmarkBorder, contentDescription = "Save",
                        tint = TextWhite, modifier = Modifier.size(28.dp))
                }
                Text("सेव", color = TextWhite, fontSize = 12.sp)
            }

            // Report
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onReport,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(GlassWhite)
                ) {
                    Icon(Icons.Outlined.Flag, contentDescription = "Report",
                        tint = TextWhite, modifier = Modifier.size(28.dp))
                }
                Text("रिपोर्ट", color = TextWhite, fontSize = 12.sp)
            }
        }
    }
}
