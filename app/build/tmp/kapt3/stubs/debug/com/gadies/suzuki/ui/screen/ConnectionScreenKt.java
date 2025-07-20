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
import com.gadies.suzuki.data.model.*;
import com.gadies.suzuki.ui.theme.*;
import com.gadies.suzuki.ui.viewmodel.MainViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000H\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\u001aV\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\u0018\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007\u001a\u001e\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a.\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00062\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\u0016\u0010\u001a\u001a\u00020\u00012\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\b\u0010\u001c\u001a\u00020\u0001H\u0007\u001a*\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0018\u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00010\u001fH\u0007\u00a8\u0006\""}, d2 = {"BluetoothTab", "", "availableDevices", "", "Lcom/gadies/suzuki/data/model/ObdDevice;", "isScanning", "", "connectionState", "Lcom/gadies/suzuki/data/model/ConnectionState;", "onScanDevices", "Lkotlin/Function0;", "onConnectDevice", "Lkotlin/Function1;", "onStopScan", "ConnectionScreen", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/gadies/suzuki/ui/viewmodel/MainViewModel;", "ConnectionStatusSection", "onDisconnect", "DeviceItem", "device", "isConnected", "isConnecting", "onConnect", "EmptyDeviceList", "onScan", "ScanningIndicator", "WiFiTab", "onConnectWiFi", "Lkotlin/Function2;", "", "", "app_debug"})
public final class ConnectionScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void ConnectionScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.gadies.suzuki.ui.viewmodel.MainViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ConnectionStatusSection(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.ConnectionState connectionState, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDisconnect) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void BluetoothTab(@org.jetbrains.annotations.NotNull
    java.util.List<com.gadies.suzuki.data.model.ObdDevice> availableDevices, boolean isScanning, @org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.ConnectionState connectionState, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onScanDevices, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.gadies.suzuki.data.model.ObdDevice, kotlin.Unit> onConnectDevice, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onStopScan) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void WiFiTab(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.ConnectionState connectionState, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Integer, kotlin.Unit> onConnectWiFi) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void DeviceItem(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.ObdDevice device, boolean isConnected, boolean isConnecting, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onConnect) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ScanningIndicator() {
    }
    
    @androidx.compose.runtime.Composable
    public static final void EmptyDeviceList(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onScan) {
    }
}