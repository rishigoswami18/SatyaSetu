package com.satyasetu.app.ui.report

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.satyasetu.app.R
import com.satyasetu.app.data.model.Report
import com.satyasetu.app.ui.theme.*
import com.satyasetu.app.util.toRelativeTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    onNavigateBack: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showForm by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBackground, DarkSurface)))
    ) {
        TopAppBar(
            title = { 
                Text(
                    stringResource(R.string.report_title), 
                    fontWeight = FontWeight.Bold, 
                    color = TextWhite 
                ) 
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = TextWhite)
                }
            },
            actions = {
                TextButton(onClick = { showForm = !showForm }) {
                    Text(
                        if (showForm) stringResource(R.string.report_my_reports) 
                        else stringResource(R.string.report_new_report), 
                        color = SaffronLight
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
        )

        // Disclaimer
        Surface(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            color = StatusPending.copy(alpha = 0.15f)
        ) {
            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("⚠️", fontSize = 20.sp)
                Spacer(Modifier.width(8.dp))
                Text(
                    stringResource(R.string.report_disclaimer),
                    color = StatusPending, 
                    fontSize = 12.sp
                )
            }
        }

        if (uiState.isSubmitted) {
            // Success State
            Column(
                Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("✅", fontSize = 64.sp)
                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(R.string.report_success), 
                    color = FactCardGreen,
                    fontSize = 20.sp, 
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Text(stringResource(R.string.report_check_pending), color = TextLight)
                Spacer(Modifier.height(24.dp))
                Button(onClick = { viewModel.resetForm() },
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) { Text(stringResource(R.string.report_new_report)) }
            }
        } else if (showForm) {
            // Report Form
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    // Category selector
                    Text(
                        stringResource(R.string.report_select_cat), 
                        color = TextLight, 
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(Report.CATEGORY_FAKE_BABA, Report.CATEGORY_FRAUD_SCHEME,
                            Report.CATEGORY_SUPERSTITION).forEach { cat ->
                            FilterChip(
                                selected = uiState.category == cat,
                                onClick = { viewModel.updateCategory(cat) },
                                label = { Text(stringResource(Report.getCategoryResId(cat)), fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SaffronPrimary,
                                    selectedLabelColor = TextWhite)
                            )
                        }
                    }
                }
                item {
                    OutlinedTextField(
                        value = uiState.title,
                        onValueChange = { viewModel.updateTitle(it) },
                        label = { Text(stringResource(R.string.report_title_label)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp), singleLine = true
                    )
                }
                item {
                    OutlinedTextField(
                        value = uiState.description,
                        onValueChange = { viewModel.updateDescription(it) },
                        label = { Text(stringResource(R.string.report_desc_label)) },
                        modifier = Modifier.fillMaxWidth().height(150.dp),
                        shape = RoundedCornerShape(16.dp), maxLines = 6
                    )
                }
                item {
                    OutlinedTextField(
                        value = uiState.location,
                        onValueChange = { viewModel.updateLocation(it) },
                        label = { Text(stringResource(R.string.report_location_label)) },
                        leadingIcon = { Icon(Icons.Default.LocationOn, null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp), singleLine = true
                    )
                }
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(uiState.isAnonymous, onCheckedChange = { viewModel.toggleAnonymous() },
                            colors = CheckboxDefaults.colors(checkedColor = SaffronPrimary))
                        Text(stringResource(R.string.report_anonymous), color = TextLight)
                    }
                }
                item {
                    uiState.error?.let {
                        Text(it, color = StatusRejected, fontSize = 14.sp)
                    }
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { viewModel.submitReport() },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                        enabled = !uiState.isLoading
                    ) {
                        if (uiState.isLoading) CircularProgressIndicator(Modifier.size(24.dp), color = TextWhite)
                        else Text(stringResource(R.string.report_submit_btn), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            // My Reports List
            if (uiState.myReports.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.report_no_reports), color = TextMuted)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.myReports) { report ->
                        ReportStatusCard(report)
                    }
                }
            }
        }
    }
}

@Composable
fun ReportStatusCard(report: Report) {
    val statusColor = when (report.status) {
        Report.STATUS_APPROVED -> FactCardGreen
        Report.STATUS_REJECTED -> StatusRejected
        else -> StatusPending
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(report.title, color = TextWhite, fontWeight = FontWeight.SemiBold, fontSize = 16.sp,
                    modifier = Modifier.weight(1f))
                Surface(shape = RoundedCornerShape(8.dp), color = statusColor.copy(alpha = 0.2f)) {
                    Text(
                        stringResource(Report.getStatusResId(report.status)),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = statusColor, fontSize = 12.sp
                    )
                }
            }
            Spacer(Modifier.height(6.dp))
            Text(report.description, color = TextMuted, maxLines = 2, fontSize = 14.sp)
            Spacer(Modifier.height(6.dp))
            Text(report.createdAt.toRelativeTime(), color = TextMuted, fontSize = 12.sp)
        }
    }
}
