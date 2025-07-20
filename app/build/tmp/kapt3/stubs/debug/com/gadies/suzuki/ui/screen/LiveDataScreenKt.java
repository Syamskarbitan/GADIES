package com.gadies.suzuki.ui.screen;

import androidx.navigation.NavController;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.gadies.suzuki.data.model.PidCategory;
import com.gadies.suzuki.data.model.PidData;
import com.gadies.suzuki.ui.theme.GadiesColors;
import com.gadies.suzuki.ui.viewmodel.MainViewModel;
import com.gadies.suzuki.data.model.ConnectionStatus;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a\u001a\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001av\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000e0\u00102\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u00152\u0018\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u0017H\u0007\u001a*\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\f2\u0018\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u0017H\u0007\u00a8\u0006\u001a"}, d2 = {"CategoryIcon", "", "category", "Lcom/gadies/suzuki/data/model/PidCategory;", "LiveDataScreen", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/gadies/suzuki/ui/viewmodel/MainViewModel;", "PidCategoryCard", "pids", "", "Lcom/gadies/suzuki/data/model/PidData;", "isExpanded", "", "expandedSubCategories", "", "", "onExpandToggle", "Lkotlin/Function0;", "onSubCategoryExpandToggle", "Lkotlin/Function1;", "onToggleAlert", "Lkotlin/Function2;", "PidDataRow", "pidData", "app_debug"})
public final class LiveDataScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void LiveDataScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.gadies.suzuki.ui.viewmodel.MainViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void PidCategoryCard(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.PidCategory category, @org.jetbrains.annotations.NotNull
    java.util.List<com.gadies.suzuki.data.model.PidData> pids, boolean isExpanded, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, java.lang.Boolean> expandedSubCategories, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onExpandToggle, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSubCategoryExpandToggle, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit> onToggleAlert) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void PidDataRow(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.PidData pidData, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit> onToggleAlert) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CategoryIcon(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.PidCategory category) {
    }
}