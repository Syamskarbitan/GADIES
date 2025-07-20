package com.gadies.suzuki.data.model;

import android.os.Parcelable;
import kotlinx.parcelize.Parcelize;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\tH\u00c6\u0003J1\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u001cH\u00d6\u0001J\t\u0010\u001d\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001e"}, d2 = {"Lcom/gadies/suzuki/data/model/PidAlert;", "", "pidData", "Lcom/gadies/suzuki/data/model/PidData;", "level", "Lcom/gadies/suzuki/data/model/AlertLevel;", "message", "", "timestamp", "", "(Lcom/gadies/suzuki/data/model/PidData;Lcom/gadies/suzuki/data/model/AlertLevel;Ljava/lang/String;J)V", "getLevel", "()Lcom/gadies/suzuki/data/model/AlertLevel;", "getMessage", "()Ljava/lang/String;", "getPidData", "()Lcom/gadies/suzuki/data/model/PidData;", "getTimestamp", "()J", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class PidAlert {
    @org.jetbrains.annotations.NotNull
    private final com.gadies.suzuki.data.model.PidData pidData = null;
    @org.jetbrains.annotations.NotNull
    private final com.gadies.suzuki.data.model.AlertLevel level = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String message = null;
    private final long timestamp = 0L;
    
    public PidAlert(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.PidData pidData, @org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.AlertLevel level, @org.jetbrains.annotations.NotNull
    java.lang.String message, long timestamp) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.model.PidData getPidData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.model.AlertLevel getLevel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getMessage() {
        return null;
    }
    
    public final long getTimestamp() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.model.PidData component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.model.AlertLevel component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    public final long component4() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.model.PidAlert copy(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.PidData pidData, @org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.AlertLevel level, @org.jetbrains.annotations.NotNull
    java.lang.String message, long timestamp) {
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