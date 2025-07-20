package com.gadies.suzuki.data.model;

/**
 * Comprehensive PID mapping for all 74 OBD-II PIDs from thresholds.json
 * Maps each PID to its appropriate sub-category for organized display
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e2\u0006\u0010\u0012\u001a\u00020\nJ\u0018\u0010\u0013\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0005J\u0016\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0005R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0018"}, d2 = {"Lcom/gadies/suzuki/data/model/PidMapping;", "", "()V", "dashboardPids", "", "", "getDashboardPids", "()Ljava/util/Set;", "pidSubCategoryMap", "", "Lcom/gadies/suzuki/data/model/PidSubCategory;", "getPidSubCategoryMap", "()Ljava/util/Map;", "getPidsForCategory", "", "category", "Lcom/gadies/suzuki/data/model/PidCategory;", "getPidsForSubCategory", "subCategory", "getSubCategory", "mode", "pid", "isDashboardPid", "", "app_debug"})
public final class PidMapping {
    
    /**
     * Maps PID identifiers to their sub-categories
     * Format: "mode_pid" -> PidSubCategory
     */
    @org.jetbrains.annotations.NotNull
    private static final java.util.Map<java.lang.String, com.gadies.suzuki.data.model.PidSubCategory> pidSubCategoryMap = null;
    
    /**
     * Dashboard PIDs - highlighted on main dashboard
     */
    @org.jetbrains.annotations.NotNull
    private static final java.util.Set<java.lang.String> dashboardPids = null;
    @org.jetbrains.annotations.NotNull
    public static final com.gadies.suzuki.data.model.PidMapping INSTANCE = null;
    
    private PidMapping() {
        super();
    }
    
    /**
     * Maps PID identifiers to their sub-categories
     * Format: "mode_pid" -> PidSubCategory
     */
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, com.gadies.suzuki.data.model.PidSubCategory> getPidSubCategoryMap() {
        return null;
    }
    
    /**
     * Gets the sub-category for a given PID
     */
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.model.PidSubCategory getSubCategory(@org.jetbrains.annotations.NotNull
    java.lang.String mode, @org.jetbrains.annotations.NotNull
    java.lang.String pid) {
        return null;
    }
    
    /**
     * Gets all PIDs for a specific category
     */
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getPidsForCategory(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.PidCategory category) {
        return null;
    }
    
    /**
     * Gets all PIDs for a specific sub-category
     */
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getPidsForSubCategory(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.PidSubCategory subCategory) {
        return null;
    }
    
    /**
     * Dashboard PIDs - highlighted on main dashboard
     */
    @org.jetbrains.annotations.NotNull
    public final java.util.Set<java.lang.String> getDashboardPids() {
        return null;
    }
    
    /**
     * Checks if a PID should be displayed on the main dashboard
     */
    public final boolean isDashboardPid(@org.jetbrains.annotations.NotNull
    java.lang.String mode, @org.jetbrains.annotations.NotNull
    java.lang.String pid) {
        return false;
    }
}