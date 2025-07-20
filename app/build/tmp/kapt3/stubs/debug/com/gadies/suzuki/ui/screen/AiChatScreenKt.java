package com.gadies.suzuki.ui.screen;

import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.foundation.ExperimentalFoundationApi;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.LocalSoftwareKeyboardController;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.style.TextAlign;
import androidx.navigation.NavController;
import com.gadies.suzuki.data.model.ChatMessage;
import com.gadies.suzuki.ui.theme.*;
import com.gadies.suzuki.ui.viewmodel.MainViewModel;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0007\u001aB\u0010\t\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0007\u001a\b\u0010\u0012\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0013\u001a\u00020\u0001H\u0007\u001a\u001e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\nH\u0007\u00a8\u0006\u0019"}, d2 = {"AiChatScreen", "", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/gadies/suzuki/ui/viewmodel/MainViewModel;", "ChatMessageBubble", "message", "Lcom/gadies/suzuki/data/model/ChatMessage;", "MessageInputBar", "", "onMessageChange", "Lkotlin/Function1;", "onSendMessage", "Lkotlin/Function0;", "isLoading", "", "aiEnabled", "TypingIndicator", "WelcomeMessage", "animateFloatAsState", "Landroidx/compose/runtime/State;", "", "targetValue", "label", "app_debug"})
public final class AiChatScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable
    public static final void AiChatScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.gadies.suzuki.ui.viewmodel.MainViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void WelcomeMessage() {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ChatMessageBubble(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.ChatMessage message) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void TypingIndicator() {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void MessageInputBar(@org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onMessageChange, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onSendMessage, boolean isLoading, boolean aiEnabled) {
    }
    
    @androidx.compose.runtime.Composable
    @org.jetbrains.annotations.NotNull
    public static final androidx.compose.runtime.State<java.lang.Float> animateFloatAsState(float targetValue, @org.jetbrains.annotations.NotNull
    java.lang.String label) {
        return null;
    }
}