package com.satyasetu.app.ui.admin

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasetu.app.data.model.Report
import com.satyasetu.app.data.repository.AuthRepository
import com.satyasetu.app.data.repository.ReportRepository
import com.satyasetu.app.ui.theme.*
import com.satyasetu.app.util.toRelativeTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─── ViewModel ───────────────────────────────────────────────────

data class AdminUiState(
    val pendingReports: List<Report> = emptyList(),
    val isLoading: Boolean = true,
    val actionMessage: String? = null
)

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminUiState())
    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()

    init { loadPendingReports() }

    private fun loadPendingReports() {
        viewModelScope.launch {
            reportRepository.observePendingReports().catch { e ->
                _uiState.update { it.copy(isLoading = false) }
            }.collect { reports ->
                _uiState.update { it.copy(pendingReports = reports, isLoading = false) }
            }
        }
    }

    fun approveReport(reportId: String, note: String) {
        val adminId = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            reportRepository.approveReport(reportId, note, adminId)
                .onSuccess {
                    _uiState.update { it.copy(actionMessage = "रिपोर्ट स्वीकृत ✅") }
                }
                .onFailure {
                    _uiState.update { it.copy(actionMessage = "कुछ गड़बड़ हो गई") }
                }
        }
    }

    fun rejectReport(reportId: String, note: String) {
        val adminId = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            reportRepository.rejectReport(reportId, note, adminId)
                .onSuccess {
                    _uiState.update { it.copy(actionMessage = "रिपोर्ट अस्वीकृत ❌") }
                }
                .onFailure {
                    _uiState.update { it.copy(actionMessage = "कुछ गड़बड़ हो गई") }
                }
        }
    }

    fun clearMessage() { _uiState.update { it.copy(actionMessage = null) } }
}

// ─── Screen ──────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onNavigateBack: () -> Unit,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Snackbar for action messages
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(uiState.actionMessage) {
        uiState.actionMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = DarkBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Brush.verticalGradient(listOf(DarkBackground, DarkSurface)))
        ) {
            TopAppBar(
                title = {
                    Column {
                        Text("एडमिन डैशबोर्ड 🛡️", fontWeight = FontWeight.Bold, color = TextWhite)
                        Text(
                            "${uiState.pendingReports.size} रिपोर्ट समीक्षा बाकी",
                            style = MaterialTheme.typography.bodySmall,
                            color = StatusPending
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

            // Stats bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard("समीक्षा बाकी", uiState.pendingReports.size.toString(),
                    StatusPending, Modifier.weight(1f))
                StatCard("आज के", uiState.pendingReports.count {
                    System.currentTimeMillis() - it.createdAt < 86400000
                }.toString(), LightBlue, Modifier.weight(1f))
            }

            Spacer(Modifier.height(12.dp))

            if (uiState.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = SaffronPrimary)
                }
            } else if (uiState.pendingReports.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("✅", fontSize = 64.sp)
                        Spacer(Modifier.height(16.dp))
                        Text("सभी रिपोर्ट की समीक्षा हो चुकी है!", color = FactCardGreen,
                            fontSize = 18.sp)
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(uiState.pendingReports) { report ->
                        AdminReportCard(
                            report = report,
                            onApprove = { note -> viewModel.approveReport(report.reportId, note) },
                            onReject = { note -> viewModel.rejectReport(report.reportId, note) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, color: androidx.compose.ui.graphics.Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, color = color, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(label, color = TextMuted, fontSize = 12.sp)
        }
    }
}

@Composable
fun AdminReportCard(
    report: Report,
    onApprove: (String) -> Unit,
    onReject: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var adminNote by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = StatusPending.copy(alpha = 0.2f)
                    ) {
                        Text(
                            androidx.compose.ui.res.stringResource(Report.getCategoryResId(report.category)),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            color = StatusPending, fontSize = 11.sp
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(report.title, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                }
                Text(report.createdAt.toRelativeTime(), color = TextMuted, fontSize = 11.sp)
            }

            Spacer(Modifier.height(8.dp))

            // Description
            Text(report.description, color = TextLight, fontSize = 14.sp, maxLines = if (expanded) Int.MAX_VALUE else 3)

            // Meta info
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (report.location.isNotBlank()) {
                    Text("📍 ${report.location}", color = TextMuted, fontSize = 12.sp)
                }
                Text(
                    if (report.isAnonymous) "👤 गुमनाम" else "👤 ${report.userName}",
                    color = TextMuted, fontSize = 12.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            // Expand / Collapse
            TextButton(onClick = { expanded = !expanded }) {
                Text(
                    if (expanded) "कम देखें ▲" else "विस्तार से देखें ▼",
                    color = SaffronLight, fontSize = 13.sp
                )
            }

            // Action area (visible when expanded)
            AnimatedVisibility(visible = expanded) {
                Column {
                    OutlinedTextField(
                        value = adminNote,
                        onValueChange = { adminNote = it },
                        label = { Text("एडमिन नोट (वैकल्पिक)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 2
                    )

                    Spacer(Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Reject
                        OutlinedButton(
                            onClick = { onReject(adminNote) },
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = StatusRejected
                            )
                        ) {
                            Icon(Icons.Default.Close, null, Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("अस्वीकार", fontWeight = FontWeight.SemiBold)
                        }
                        // Approve
                        Button(
                            onClick = { onApprove(adminNote) },
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = FactCardGreen)
                        ) {
                            Icon(Icons.Default.Check, null, Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("स्वीकार", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}
