package com.satyasetu.app.ui.village

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.satyasetu.app.data.model.Post
import com.satyasetu.app.ui.theme.*
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostBottomSheet(
    onDismiss: () -> Unit,
    viewModel: CreatePostViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Media Picker
    val mediaPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val isVideo = context.contentResolver.getType(uri)?.startsWith("video/") == true
            viewModel.updateMedia(uri, isVideo)
        }
    }

    // Speech Recognizer
    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = results?.get(0) ?: ""
            if (spokenText.isNotEmpty()) {
                val currentText = uiState.description
                val newText = if (currentText.isEmpty()) spokenText else "$currentText $spokenText"
                viewModel.updateDescription(newText)
            }
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            viewModel.resetState()
            onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .padding(bottom = 32.dp) // extra padding for bottom navigation
        ) {
            Text(
                "अपने गाँव में अपडेट साझा करें",
                color = TextWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "यह प्लेटफॉर्म जानकारी और समुदाय सुधार के लिए है।",
                color = SaffronLight,
                fontSize = 12.sp
            )

            Spacer(Modifier.height(24.dp))

            // Post Type Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PostTypeChip(
                    title = Post.getTypeDisplayName(Post.TYPE_AWARENESS),
                    selected = uiState.type == Post.TYPE_AWARENESS,
                    onClick = { viewModel.updateType(Post.TYPE_AWARENESS) }
                )
                PostTypeChip(
                    title = Post.getTypeDisplayName(Post.TYPE_ISSUE),
                    selected = uiState.type == Post.TYPE_ISSUE,
                    onClick = { viewModel.updateType(Post.TYPE_ISSUE) }
                )
                PostTypeChip(
                    title = Post.getTypeDisplayName(Post.TYPE_NEWS),
                    selected = uiState.type == Post.TYPE_NEWS,
                    onClick = { viewModel.updateType(Post.TYPE_NEWS) }
                )
            }

            Spacer(Modifier.height(16.dp))

            // Text Input with Mic
            OutlinedTextField(
                value = uiState.description,
                onValueChange = { viewModel.updateDescription(it) },
                placeholder = { Text("क्या चल रहा है?", color = TextMuted) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SaffronPrimary,
                    unfocusedBorderColor = DarkCard,
                    focusedContainerColor = DarkCard,
                    unfocusedContainerColor = DarkCard
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi-IN")
                                putExtra(RecognizerIntent.EXTRA_PROMPT, "बोलना शुरू करें...")
                            }
                            speechRecognizerLauncher.launch(intent)
                        }
                    ) {
                        Icon(Icons.Default.Mic, "Voice Input", tint = SaffronPrimary)
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            // Selected Media Preview
            if (uiState.mediaUri != null) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (uiState.isVideo) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = DarkCard
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.VideoLibrary, null, tint = SaffronLight, modifier = Modifier.size(48.dp))
                                Text("वीडियो चुना गया", color = TextWhite, modifier = Modifier.align(Alignment.BottomCenter).padding(8.dp))
                            }
                        }
                    } else {
                        AsyncImage(
                            model = uiState.mediaUri,
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    
                    Surface(
                        shape = CircleShape,
                        color = StatusRejected,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(32.dp)
                            .clickable { viewModel.updateMedia(null, false) }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("✕", color = TextWhite, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            // Media Buttons & Submit
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    IconButton(onClick = { mediaPicker.launch("image/*") }) {
                        Icon(Icons.Default.Image, "Add Image", tint = SaffronLight)
                    }
                    IconButton(onClick = { mediaPicker.launch("video/*") }) {
                        Icon(Icons.Default.VideoLibrary, "Add Video", tint = SaffronLight)
                    }
                }

                Button(
                    onClick = { viewModel.submitPost() },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                    enabled = !uiState.isUploading
                ) {
                    if (uiState.isUploading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = TextWhite)
                    } else {
                        Text("पोस्ट करें", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
            
            if (uiState.error != null) {
                Spacer(Modifier.height(8.dp))
                Text(uiState.error!!, color = StatusRejected, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun PostTypeChip(title: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (selected) SaffronPrimary else DarkCard,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = title,
            color = if (selected) TextWhite else TextLight,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
