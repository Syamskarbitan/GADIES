package com.gadies.suzuki.service;

import android.content.Context;
import com.gadies.suzuki.data.model.*;
import com.google.gson.Gson;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000  2\u00020\u0001:\u0001 B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J8\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u000fH\u0002J\u0010\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u000fH\u0002J\u0010\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u000fH\u0002JP\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\n2\u0006\u0010\u001a\u001a\u00020\u000f2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\b\b\u0002\u0010\u0015\u001a\u00020\u000f2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006!"}, d2 = {"Lcom/gadies/suzuki/service/AiService;", "", "context", "Landroid/content/Context;", "gson", "Lcom/google/gson/Gson;", "(Landroid/content/Context;Lcom/google/gson/Gson;)V", "httpClient", "Lokhttp3/OkHttpClient;", "analyzeVehicleHealth", "Lkotlin/Result;", "Lcom/gadies/suzuki/data/model/AiAnalysisResponse;", "request", "Lcom/gadies/suzuki/data/model/AiAnalysisRequest;", "apiKey", "", "model", "analyzeVehicleHealth-BWLJW6A", "(Lcom/gadies/suzuki/data/model/AiAnalysisRequest;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "buildAnalysisPrompt", "buildChatSystemPrompt", "language", "buildSystemPrompt", "parseAnalysisResponse", "content", "sendChatMessage", "message", "chatHistory", "", "Lcom/gadies/suzuki/data/model/ChatMessage;", "sendChatMessage-hUnOzRk", "(Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class AiService {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String OPENROUTER_BASE_URL = "https://openrouter.ai/api/v1/chat/completions";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String DEFAULT_API_KEY = "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";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String DEFAULT_MODEL = "deepseek/deepseek-r1";
    @org.jetbrains.annotations.NotNull
    private final okhttp3.OkHttpClient httpClient = null;
    @org.jetbrains.annotations.NotNull
    public static final com.gadies.suzuki.service.AiService.Companion Companion = null;
    
    @javax.inject.Inject
    public AiService(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.google.gson.Gson gson) {
        super();
    }
    
    private final java.lang.String buildSystemPrompt(java.lang.String language) {
        return null;
    }
    
    private final java.lang.String buildChatSystemPrompt(java.lang.String language) {
        return null;
    }
    
    private final java.lang.String buildAnalysisPrompt(com.gadies.suzuki.data.model.AiAnalysisRequest request) {
        return null;
    }
    
    private final com.gadies.suzuki.data.model.AiAnalysisResponse parseAnalysisResponse(java.lang.String content) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/gadies/suzuki/service/AiService$Companion;", "", "()V", "DEFAULT_API_KEY", "", "DEFAULT_MODEL", "OPENROUTER_BASE_URL", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}