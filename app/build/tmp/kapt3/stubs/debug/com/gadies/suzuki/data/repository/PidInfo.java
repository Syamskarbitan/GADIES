package com.gadies.suzuki.data.repository;

import android.content.Context;
import com.gadies.suzuki.data.model.*;
import com.google.gson.Gson;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b%\b\u0086\b\u0018\u00002\u00020\u0001Bi\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\u0002\u0010\u0011J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010%\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010&\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001eJ\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\tH\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u0082\u0001\u0010/\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00c6\u0001\u00a2\u0006\u0002\u00100J\u0013\u00101\u001a\u00020\u00102\b\u00102\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00103\u001a\u00020\tH\u00d6\u0001J\t\u00104\u001a\u00020\u0003H\u00d6\u0001R\u0015\u0010\u000e\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u0015\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\n\n\u0002\u0010\u001f\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0018\u00a8\u00065"}, d2 = {"Lcom/gadies/suzuki/data/repository/PidInfo;", "", "pid", "", "mode", "name", "unit", "formula", "bytes", "", "category", "ui_type", "threshold", "Lcom/gadies/suzuki/data/repository/ThresholdInfo;", "address_offset", "suzuki_specific", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Lcom/gadies/suzuki/data/repository/ThresholdInfo;Ljava/lang/Integer;Ljava/lang/Boolean;)V", "getAddress_offset", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getBytes", "()I", "getCategory", "()Ljava/lang/String;", "getFormula", "getMode", "getName", "getPid", "getSuzuki_specific", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getThreshold", "()Lcom/gadies/suzuki/data/repository/ThresholdInfo;", "getUi_type", "getUnit", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Lcom/gadies/suzuki/data/repository/ThresholdInfo;Ljava/lang/Integer;Ljava/lang/Boolean;)Lcom/gadies/suzuki/data/repository/PidInfo;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class PidInfo {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String pid = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String mode = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String unit = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String formula = null;
    private final int bytes = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String category = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String ui_type = null;
    @org.jetbrains.annotations.Nullable
    private final com.gadies.suzuki.data.repository.ThresholdInfo threshold = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer address_offset = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Boolean suzuki_specific = null;
    
    public PidInfo(@org.jetbrains.annotations.NotNull
    java.lang.String pid, @org.jetbrains.annotations.NotNull
    java.lang.String mode, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    java.lang.String unit, @org.jetbrains.annotations.NotNull
    java.lang.String formula, int bytes, @org.jetbrains.annotations.NotNull
    java.lang.String category, @org.jetbrains.annotations.NotNull
    java.lang.String ui_type, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.repository.ThresholdInfo threshold, @org.jetbrains.annotations.Nullable
    java.lang.Integer address_offset, @org.jetbrains.annotations.Nullable
    java.lang.Boolean suzuki_specific) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPid() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getUnit() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFormula() {
        return null;
    }
    
    public final int getBytes() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getUi_type() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.repository.ThresholdInfo getThreshold() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getAddress_offset() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Boolean getSuzuki_specific() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Boolean component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.repository.ThresholdInfo component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.repository.PidInfo copy(@org.jetbrains.annotations.NotNull
    java.lang.String pid, @org.jetbrains.annotations.NotNull
    java.lang.String mode, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    java.lang.String unit, @org.jetbrains.annotations.NotNull
    java.lang.String formula, int bytes, @org.jetbrains.annotations.NotNull
    java.lang.String category, @org.jetbrains.annotations.NotNull
    java.lang.String ui_type, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.repository.ThresholdInfo threshold, @org.jetbrains.annotations.Nullable
    java.lang.Integer address_offset, @org.jetbrains.annotations.Nullable
    java.lang.Boolean suzuki_specific) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}