package com.gadies.suzuki.data.model;

import android.os.Parcelable;
import kotlinx.parcelize.Parcelize;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b9\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u00bb\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0017\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0017\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0017\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0017\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u001dJ\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003J\t\u0010;\u001a\u00020\u0012H\u00c6\u0003J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\t\u0010=\u001a\u00020\u0015H\u00c6\u0003J\t\u0010>\u001a\u00020\u0017H\u00c6\u0003J\t\u0010?\u001a\u00020\u0017H\u00c6\u0003J\u0010\u0010@\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\t\u0010A\u001a\u00020\u0017H\u00c6\u0003J\t\u0010B\u001a\u00020\u0017H\u00c6\u0003J\u000b\u0010C\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010D\u001a\u00020\u0003H\u00c6\u0003J\t\u0010E\u001a\u00020\u0003H\u00c6\u0003J\t\u0010F\u001a\u00020\u0003H\u00c6\u0003J\t\u0010G\u001a\u00020\u0003H\u00c6\u0003J\t\u0010H\u001a\u00020\tH\u00c6\u0003J\t\u0010I\u001a\u00020\u000bH\u00c6\u0003J\t\u0010J\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010K\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\u00d4\u0001\u0010L\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00172\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\u001a\u001a\u00020\u00172\b\b\u0002\u0010\u001b\u001a\u00020\u00172\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010MJ\t\u0010N\u001a\u00020\tH\u00d6\u0001J\u0013\u0010O\u001a\u00020\u00172\b\u0010P\u001a\u0004\u0018\u00010QH\u00d6\u0003J\t\u0010R\u001a\u00020\tH\u00d6\u0001J\t\u0010S\u001a\u00020\u0003H\u00d6\u0001J\u0019\u0010T\u001a\u00020U2\u0006\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020\tH\u00d6\u0001R\u0015\u0010\u0019\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010*R\u0011\u0010\u001b\u001a\u00020\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\"R\u0011\u0010\u0016\u001a\u00020\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\"R\u0011\u0010\u001a\u001a\u00020\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\"R\u0011\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010*R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010*R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010*R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010*R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010*R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010*\u00a8\u0006Y"}, d2 = {"Lcom/gadies/suzuki/data/model/PidData;", "Landroid/os/Parcelable;", "pid", "", "mode", "name", "unit", "formula", "bytes", "", "category", "Lcom/gadies/suzuki/data/model/PidCategory;", "uiType", "subCategory", "Lcom/gadies/suzuki/data/model/PidSubCategory;", "threshold", "Lcom/gadies/suzuki/data/model/Threshold;", "currentValue", "", "rawHexValue", "lastUpdated", "", "isEnabled", "", "alertsEnabled", "addressOffset", "isSuzukiSpecific", "hasData", "errorMessage", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/gadies/suzuki/data/model/PidCategory;Ljava/lang/String;Lcom/gadies/suzuki/data/model/PidSubCategory;Lcom/gadies/suzuki/data/model/Threshold;DLjava/lang/String;JZZLjava/lang/Integer;ZZLjava/lang/String;)V", "getAddressOffset", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getAlertsEnabled", "()Z", "getBytes", "()I", "getCategory", "()Lcom/gadies/suzuki/data/model/PidCategory;", "getCurrentValue", "()D", "getErrorMessage", "()Ljava/lang/String;", "getFormula", "getHasData", "getLastUpdated", "()J", "getMode", "getName", "getPid", "getRawHexValue", "getSubCategory", "()Lcom/gadies/suzuki/data/model/PidSubCategory;", "getThreshold", "()Lcom/gadies/suzuki/data/model/Threshold;", "getUiType", "getUnit", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/gadies/suzuki/data/model/PidCategory;Ljava/lang/String;Lcom/gadies/suzuki/data/model/PidSubCategory;Lcom/gadies/suzuki/data/model/Threshold;DLjava/lang/String;JZZLjava/lang/Integer;ZZLjava/lang/String;)Lcom/gadies/suzuki/data/model/PidData;", "describeContents", "equals", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_debug"})
@kotlinx.parcelize.Parcelize
public final class PidData implements android.os.Parcelable {
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
    private final com.gadies.suzuki.data.model.PidCategory category = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String uiType = null;
    @org.jetbrains.annotations.Nullable
    private final com.gadies.suzuki.data.model.PidSubCategory subCategory = null;
    @org.jetbrains.annotations.Nullable
    private final com.gadies.suzuki.data.model.Threshold threshold = null;
    private final double currentValue = 0.0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String rawHexValue = null;
    private final long lastUpdated = 0L;
    private final boolean isEnabled = false;
    private final boolean alertsEnabled = false;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer addressOffset = null;
    private final boolean isSuzukiSpecific = false;
    private final boolean hasData = false;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String errorMessage = null;
    
    public PidData(@org.jetbrains.annotations.NotNull
    java.lang.String pid, @org.jetbrains.annotations.NotNull
    java.lang.String mode, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    java.lang.String unit, @org.jetbrains.annotations.NotNull
    java.lang.String formula, int bytes, @org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.PidCategory category, @org.jetbrains.annotations.NotNull
    java.lang.String uiType, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.PidSubCategory subCategory, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.Threshold threshold, double currentValue, @org.jetbrains.annotations.NotNull
    java.lang.String rawHexValue, long lastUpdated, boolean isEnabled, boolean alertsEnabled, @org.jetbrains.annotations.Nullable
    java.lang.Integer addressOffset, boolean isSuzukiSpecific, boolean hasData, @org.jetbrains.annotations.Nullable
    java.lang.String errorMessage) {
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
    public final com.gadies.suzuki.data.model.PidCategory getCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getUiType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.model.PidSubCategory getSubCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.model.Threshold getThreshold() {
        return null;
    }
    
    public final double getCurrentValue() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRawHexValue() {
        return null;
    }
    
    public final long getLastUpdated() {
        return 0L;
    }
    
    public final boolean isEnabled() {
        return false;
    }
    
    public final boolean getAlertsEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getAddressOffset() {
        return null;
    }
    
    public final boolean isSuzukiSpecific() {
        return false;
    }
    
    public final boolean getHasData() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.model.Threshold component10() {
        return null;
    }
    
    public final double component11() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component12() {
        return null;
    }
    
    public final long component13() {
        return 0L;
    }
    
    public final boolean component14() {
        return false;
    }
    
    public final boolean component15() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component16() {
        return null;
    }
    
    public final boolean component17() {
        return false;
    }
    
    public final boolean component18() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component19() {
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
    public final com.gadies.suzuki.data.model.PidCategory component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.model.PidSubCategory component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.model.PidData copy(@org.jetbrains.annotations.NotNull
    java.lang.String pid, @org.jetbrains.annotations.NotNull
    java.lang.String mode, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    java.lang.String unit, @org.jetbrains.annotations.NotNull
    java.lang.String formula, int bytes, @org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.PidCategory category, @org.jetbrains.annotations.NotNull
    java.lang.String uiType, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.PidSubCategory subCategory, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.Threshold threshold, double currentValue, @org.jetbrains.annotations.NotNull
    java.lang.String rawHexValue, long lastUpdated, boolean isEnabled, boolean alertsEnabled, @org.jetbrains.annotations.Nullable
    java.lang.Integer addressOffset, boolean isSuzukiSpecific, boolean hasData, @org.jetbrains.annotations.Nullable
    java.lang.String errorMessage) {
        return null;
    }
    
    @java.lang.Override
    public int describeContents() {
        return 0;
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
    
    @java.lang.Override
    public void writeToParcel(@org.jetbrains.annotations.NotNull
    android.os.Parcel parcel, int flags) {
    }
}