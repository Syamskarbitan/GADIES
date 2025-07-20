package com.gadies.suzuki.ui.screen;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.navigation.NavController;
import com.gadies.suzuki.data.model.AlertLevel;
import com.gadies.suzuki.data.model.ConnectionStatus;
import com.gadies.suzuki.data.model.PidData;
import com.gadies.suzuki.ui.theme.*;
import com.gadies.suzuki.ui.viewmodel.MainViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000p\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\b\u0010\b\u001a\u00020\u0001H\u0007\u001aJ\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000fH\u0007\u001a\u001e\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\u0018\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0007\u001a \u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\n\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007\u001a^\u0010 \u001a\u00020\u00012\b\b\u0002\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001d2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000fH\u0007\u001a\b\u0010&\u001a\u00020\u0001H\u0007\u001a8\u0010\'\u001a\u00020\u00012\b\b\u0002\u0010!\u001a\u00020\"2\u0006\u0010(\u001a\u00020)2\u0006\u0010#\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020\u001d2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\u0010\u0010+\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018H\u0007\u001a$\u0010,\u001a\u00020\u00012\f\u0010-\u001a\b\u0012\u0004\u0012\u00020%0.2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u00a8\u00060"}, d2 = {"AlertBanner", "", "alertCount", "", "latestAlert", "Lcom/gadies/suzuki/data/model/PidAlert;", "onViewAlerts", "Lkotlin/Function0;", "AuthorSection", "CircularGauge", "value", "", "minValue", "maxValue", "normalRange", "Lkotlin/ranges/ClosedFloatingPointRange;", "cautionRange", "dangerRange", "ConnectionStatusIndicator", "status", "Lcom/gadies/suzuki/data/model/ConnectionStatus;", "onClick", "DashboardScreen", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/gadies/suzuki/ui/viewmodel/MainViewModel;", "DataRow", "name", "", "timestamp", "", "GaugeCard", "modifier", "Landroidx/compose/ui/Modifier;", "title", "pidData", "Lcom/gadies/suzuki/data/model/PidData;", "OfflineBanner", "QuickActionButton", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "subtitle", "QuickActionsSection", "RecentDataSummary", "dashboardPids", "", "onViewAll", "app_debug"})
public final class DashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void DashboardScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.gadies.suzuki.ui.viewmodel.MainViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ConnectionStatusIndicator(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.ConnectionStatus status, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void OfflineBanner() {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AlertBanner(int alertCount, @org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.PidAlert latestAlert, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onViewAlerts) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void GaugeCard(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.PidData pidData, double minValue, double maxValue, @org.jetbrains.annotations.NotNull
    kotlin.ranges.ClosedFloatingPointRange<java.lang.Double> normalRange, @org.jetbrains.annotations.NotNull
    kotlin.ranges.ClosedFloatingPointRange<java.lang.Double> cautionRange, @org.jetbrains.annotations.NotNull
    kotlin.ranges.ClosedFloatingPointRange<java.lang.Double> dangerRange) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CircularGauge(double value, double minValue, double maxValue, @org.jetbrains.annotations.NotNull
    kotlin.ranges.ClosedFloatingPointRange<java.lang.Double> normalRange, @org.jetbrains.annotations.NotNull
    kotlin.ranges.ClosedFloatingPointRange<java.lang.Double> cautionRange, @org.jetbrains.annotations.NotNull
    kotlin.ranges.ClosedFloatingPointRange<java.lang.Double> dangerRange) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void QuickActionsSection(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void QuickActionButton(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void RecentDataSummary(@org.jetbrains.annotations.NotNull
    java.util.List<com.gadies.suzuki.data.model.PidData> dashboardPids, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onViewAll) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void DataRow(@org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    java.lang.String value, long timestamp) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AuthorSection() {
    }
}