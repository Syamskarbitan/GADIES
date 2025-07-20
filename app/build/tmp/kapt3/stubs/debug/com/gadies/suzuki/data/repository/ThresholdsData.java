package com.gadies.suzuki.data.repository;

import android.content.Context;
import com.gadies.suzuki.data.model.*;
import com.google.gson.Gson;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003JC\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\r\u00a8\u0006\u001c"}, d2 = {"Lcom/gadies/suzuki/data/repository/ThresholdsData;", "", "metadata", "Lcom/gadies/suzuki/data/repository/MetadataInfo;", "standard_obdii_pids", "", "Lcom/gadies/suzuki/data/repository/PidInfo;", "suzuki_custom_pids", "suzuki_d13a_02_pids", "(Lcom/gadies/suzuki/data/repository/MetadataInfo;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getMetadata", "()Lcom/gadies/suzuki/data/repository/MetadataInfo;", "getStandard_obdii_pids", "()Ljava/util/List;", "getSuzuki_custom_pids", "getSuzuki_d13a_02_pids", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class ThresholdsData {
    @org.jetbrains.annotations.NotNull
    private final com.gadies.suzuki.data.repository.MetadataInfo metadata = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.gadies.suzuki.data.repository.PidInfo> standard_obdii_pids = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.gadies.suzuki.data.repository.PidInfo> suzuki_custom_pids = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.gadies.suzuki.data.repository.PidInfo> suzuki_d13a_02_pids = null;
    
    public ThresholdsData(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.repository.MetadataInfo metadata, @org.jetbrains.annotations.NotNull
    java.util.List<com.gadies.suzuki.data.repository.PidInfo> standard_obdii_pids, @org.jetbrains.annotations.NotNull
    java.util.List<com.gadies.suzuki.data.repository.PidInfo> suzuki_custom_pids, @org.jetbrains.annotations.NotNull
    java.util.List<com.gadies.suzuki.data.repository.PidInfo> suzuki_d13a_02_pids) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.repository.MetadataInfo getMetadata() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.gadies.suzuki.data.repository.PidInfo> getStandard_obdii_pids() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.gadies.suzuki.data.repository.PidInfo> getSuzuki_custom_pids() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.gadies.suzuki.data.repository.PidInfo> getSuzuki_d13a_02_pids() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.repository.MetadataInfo component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.gadies.suzuki.data.repository.PidInfo> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.gadies.suzuki.data.repository.PidInfo> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.gadies.suzuki.data.repository.PidInfo> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.repository.ThresholdsData copy(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.repository.MetadataInfo metadata, @org.jetbrains.annotations.NotNull
    java.util.List<com.gadies.suzuki.data.repository.PidInfo> standard_obdii_pids, @org.jetbrains.annotations.NotNull
    java.util.List<com.gadies.suzuki.data.repository.PidInfo> suzuki_custom_pids, @org.jetbrains.annotations.NotNull
    java.util.List<com.gadies.suzuki.data.repository.PidInfo> suzuki_d13a_02_pids) {
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