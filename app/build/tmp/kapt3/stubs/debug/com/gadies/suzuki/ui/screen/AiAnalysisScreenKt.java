package com.gadies.suzuki.ui.screen;

import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.style.TextAlign;
import androidx.navigation.NavController;
import com.gadies.suzuki.data.model.AiAnalysisResponse;
import com.gadies.suzuki.ui.theme.*;
import com.gadies.suzuki.ui.viewmodel.AiAnalysisState;
import com.gadies.suzuki.ui.viewmodel.MainViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\b\u0010\u0006\u001a\u00020\u0001H\u0007\u001a\u0018\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0007\u001a^\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\tH\u0007\u001a\u0016\u0010\u0015\u001a\u00020\u00012\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0007\u001a\u001e\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0007\u001a\u001e\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\r2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0007\u001a\u0010\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020 H\u0007\u00a8\u0006!"}, d2 = {"AiAnalysisScreen", "", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/gadies/suzuki/ui/viewmodel/MainViewModel;", "AiInfoCard", "AiStatusCard", "isConnected", "", "aiEnabled", "AnalysisFormCard", "odometerKm", "", "onOdometerChange", "Lkotlin/Function1;", "lastServiceKm", "onLastServiceChange", "onAnalyze", "Lkotlin/Function0;", "isLoading", "AnalysisLoadingDialog", "onDismiss", "AnalysisResultCard", "result", "Lcom/gadies/suzuki/data/model/AiAnalysisResponse;", "onStartChat", "ErrorCard", "message", "onRetry", "HealthScoreIndicator", "score", "", "app_debug"})
public final class AiAnalysisScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void AiAnalysisScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.gadies.suzuki.ui.viewmodel.MainViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AiStatusCard(boolean isConnected, boolean aiEnabled) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AnalysisFormCard(@org.jetbrains.annotations.NotNull
    java.lang.String odometerKm, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOdometerChange, @org.jetbrains.annotations.NotNull
    java.lang.String lastServiceKm, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onLastServiceChange, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onAnalyze, boolean isLoading, boolean isConnected) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AnalysisResultCard(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.AiAnalysisResponse result, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onStartChat) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void HealthScoreIndicator(int score) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ErrorCard(@org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onRetry) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AiInfoCard() {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AnalysisLoadingDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
}