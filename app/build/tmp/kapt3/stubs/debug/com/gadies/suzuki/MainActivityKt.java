package com.gadies.suzuki;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import androidx.activity.ComponentActivity;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.core.content.ContextCompat;
import com.gadies.suzuki.service.NotificationService;
import com.gadies.suzuki.service.ObdService;
import com.gadies.suzuki.ui.screen.*;
import com.gadies.suzuki.ui.viewmodel.MainViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u00a8\u0006\u0006"}, d2 = {"GadiesApp", "", "activity", "Lcom/gadies/suzuki/MainActivity;", "mainViewModel", "Lcom/gadies/suzuki/ui/viewmodel/MainViewModel;", "app_debug"})
public final class MainActivityKt {
    
    @androidx.compose.runtime.Composable
    public static final void GadiesApp(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.MainActivity activity, @org.jetbrains.annotations.NotNull
    com.gadies.suzuki.ui.viewmodel.MainViewModel mainViewModel) {
    }
}