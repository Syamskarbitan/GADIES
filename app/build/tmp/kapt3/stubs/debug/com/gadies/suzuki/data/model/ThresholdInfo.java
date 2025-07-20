package com.gadies.suzuki.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\tJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJ\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003JJ\u0010\u0017\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u0010\u0010\u000eR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000b\u00a8\u0006 "}, d2 = {"Lcom/gadies/suzuki/data/model/ThresholdInfo;", "", "min", "", "max", "normal", "Lcom/gadies/suzuki/data/model/DoubleRange;", "caution", "danger", "(Ljava/lang/Double;Ljava/lang/Double;Lcom/gadies/suzuki/data/model/DoubleRange;Lcom/gadies/suzuki/data/model/DoubleRange;Lcom/gadies/suzuki/data/model/DoubleRange;)V", "getCaution", "()Lcom/gadies/suzuki/data/model/DoubleRange;", "getDanger", "getMax", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getMin", "getNormal", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/Double;Ljava/lang/Double;Lcom/gadies/suzuki/data/model/DoubleRange;Lcom/gadies/suzuki/data/model/DoubleRange;Lcom/gadies/suzuki/data/model/DoubleRange;)Lcom/gadies/suzuki/data/model/ThresholdInfo;", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class ThresholdInfo {
    @org.jetbrains.annotations.Nullable
    private final java.lang.Double min = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Double max = null;
    @org.jetbrains.annotations.Nullable
    private final com.gadies.suzuki.data.model.DoubleRange normal = null;
    @org.jetbrains.annotations.Nullable
    private final com.gadies.suzuki.data.model.DoubleRange caution = null;
    @org.jetbrains.annotations.Nullable
    private final com.gadies.suzuki.data.model.DoubleRange danger = null;
    
    public ThresholdInfo(@org.jetbrains.annotations.Nullable
    java.lang.Double min, @org.jetbrains.annotations.Nullable
    java.lang.Double max, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.DoubleRange normal, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.DoubleRange caution, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.DoubleRange danger) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Double getMin() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Double getMax() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.model.DoubleRange getNormal() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.model.DoubleRange getCaution() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.model.DoubleRange getDanger() {
        return null;
    }
    
    public ThresholdInfo() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Double component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Double component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.model.DoubleRange component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.model.DoubleRange component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.model.DoubleRange component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.model.ThresholdInfo copy(@org.jetbrains.annotations.Nullable
    java.lang.Double min, @org.jetbrains.annotations.Nullable
    java.lang.Double max, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.DoubleRange normal, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.DoubleRange caution, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.DoubleRange danger) {
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