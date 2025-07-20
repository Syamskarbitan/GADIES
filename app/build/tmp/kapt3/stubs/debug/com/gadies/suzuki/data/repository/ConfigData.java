package com.gadies.suzuki.data.repository;

import android.content.Context;
import com.gadies.suzuki.data.model.*;
import com.google.gson.Gson;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u001b\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0006H\u00c6\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0003JE\u0010\u0012\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u001a\b\u0002\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0004H\u00d6\u0001R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR#\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b\u00a8\u0006\u0019"}, d2 = {"Lcom/gadies/suzuki/data/repository/ConfigData;", "", "dashboard_highlight", "", "", "categories", "", "ai_analysis_questions", "Lcom/gadies/suzuki/data/model/AiQuestion;", "(Ljava/util/List;Ljava/util/Map;Ljava/util/List;)V", "getAi_analysis_questions", "()Ljava/util/List;", "getCategories", "()Ljava/util/Map;", "getDashboard_highlight", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class ConfigData {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> dashboard_highlight = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Map<java.lang.String, java.util.List<java.lang.String>> categories = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.gadies.suzuki.data.model.AiQuestion> ai_analysis_questions = null;
    
    public ConfigData(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> dashboard_highlight, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, ? extends java.util.List<java.lang.String>> categories, @org.jetbrains.annotations.NotNull
    java.util.List<com.gadies.suzuki.data.model.AiQuestion> ai_analysis_questions) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getDashboard_highlight() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.util.List<java.lang.String>> getCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.gadies.suzuki.data.model.AiQuestion> getAi_analysis_questions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.util.List<java.lang.String>> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.gadies.suzuki.data.model.AiQuestion> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.repository.ConfigData copy(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> dashboard_highlight, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, ? extends java.util.List<java.lang.String>> categories, @org.jetbrains.annotations.NotNull
    java.util.List<com.gadies.suzuki.data.model.AiQuestion> ai_analysis_questions) {
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