package com.gadies.suzuki.service

import android.content.Context
import com.gadies.suzuki.data.model.*
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    
    companion object {
        private const val OPENROUTER_BASE_URL = "https://openrouter.ai/api/v1/chat/completions"
        private const val DEFAULT_API_KEY = "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"
        private const val DEFAULT_MODEL = "deepseek/deepseek-r1"
    }
    
    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .build()
    
    suspend fun analyzeVehicleHealth(
        request: AiAnalysisRequest,
        apiKey: String = DEFAULT_API_KEY,
        model: String = DEFAULT_MODEL
    ): Result<AiAnalysisResponse> = withContext(Dispatchers.IO) {
        try {
            val systemPrompt = buildSystemPrompt(request.language)
            val userPrompt = buildAnalysisPrompt(request)
            
            val openRouterRequest = OpenRouterRequest(
                model = model,
                messages = listOf(
                    OpenRouterMessage("system", systemPrompt),
                    OpenRouterMessage("user", userPrompt)
                ),
                temperature = 0.7,
                max_tokens = 2000
            )
            
            val requestBody = gson.toJson(openRouterRequest)
                .toRequestBody("application/json".toMediaType())
            
            val httpRequest = Request.Builder()
                .url(OPENROUTER_BASE_URL)
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build()
            
            val response = httpClient.newCall(httpRequest).execute()
            
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                val openRouterResponse = gson.fromJson(responseBody, OpenRouterResponse::class.java)
                val aiResponse = parseAnalysisResponse(openRouterResponse.choices.first().message.content)
                Result.success(aiResponse)
            } else {
                Result.failure(Exception("API request failed: ${response.code} ${response.message}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun sendChatMessage(
        message: String,
        chatHistory: List<ChatMessage>,
        language: String = "id",
        apiKey: String = DEFAULT_API_KEY,
        model: String = DEFAULT_MODEL
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val systemPrompt = buildChatSystemPrompt(language)
            val messages = mutableListOf<OpenRouterMessage>()
            
            messages.add(OpenRouterMessage("system", systemPrompt))
            
            // Add chat history
            chatHistory.takeLast(10).forEach { chatMsg ->
                messages.add(
                    OpenRouterMessage(
                        role = if (chatMsg.isFromUser) "user" else "assistant",
                        content = chatMsg.content
                    )
                )
            }
            
            // Add current message
            messages.add(OpenRouterMessage("user", message))
            
            val openRouterRequest = OpenRouterRequest(
                model = model,
                messages = messages,
                temperature = 0.8,
                max_tokens = 1000
            )
            
            val requestBody = gson.toJson(openRouterRequest)
                .toRequestBody("application/json".toMediaType())
            
            val httpRequest = Request.Builder()
                .url(OPENROUTER_BASE_URL)
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build()
            
            val response = httpClient.newCall(httpRequest).execute()
            
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                val openRouterResponse = gson.fromJson(responseBody, OpenRouterResponse::class.java)
                Result.success(openRouterResponse.choices.first().message.content)
            } else {
                Result.failure(Exception("Chat request failed: ${response.code} ${response.message}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun buildSystemPrompt(language: String): String {
        return if (language == "id") {
            """
            Anda adalah seorang mekanik ahli Suzuki yang berpengalaman dengan kendaraan Suzuki Ertiga Diesel.
            Anda akan menganalisis data OBD-II dari kendaraan dan memberikan diagnosis yang akurat.
            
            Tugas Anda:
            1. Analisis semua parameter OBD-II yang diberikan
            2. Identifikasi masalah atau potensi masalah
            3. Berikan rekomendasi perbaikan atau perawatan
            4. Berikan skor kesehatan kendaraan (0-100)
            5. Sarankan kapan servis berikutnya diperlukan
            
            Jawab dalam format JSON dengan struktur:
            {
                "summary": "Ringkasan kondisi kendaraan",
                "issuesFound": ["Daftar masalah yang ditemukan"],
                "recommendations": ["Daftar rekomendasi"],
                "healthScore": 85,
                "nextServiceKm": 95000
            }
            
            Gunakan bahasa Indonesia yang mudah dipahami.
            """.trimIndent()
        } else {
            """
            You are an expert Suzuki mechanic with extensive experience with Suzuki Ertiga Diesel vehicles.
            You will analyze OBD-II data from the vehicle and provide accurate diagnostics.
            
            Your tasks:
            1. Analyze all provided OBD-II parameters
            2. Identify issues or potential problems
            3. Provide repair or maintenance recommendations
            4. Give a vehicle health score (0-100)
            5. Suggest when the next service is needed
            
            Respond in JSON format with this structure:
            {
                "summary": "Vehicle condition summary",
                "issuesFound": ["List of issues found"],
                "recommendations": ["List of recommendations"],
                "healthScore": 85,
                "nextServiceKm": 95000
            }
            
            Use clear and understandable language.
            """.trimIndent()
        }
    }
    
    private fun buildChatSystemPrompt(language: String): String {
        return if (language == "id") {
            """
            Anda adalah asisten mekanik Suzuki yang ramah dan berpengetahuan luas tentang Suzuki Ertiga Diesel.
            Bantu pengguna dengan pertanyaan tentang kendaraan mereka, masalah teknis, perawatan, dan tips berkendara.
            
            Karakteristik Anda:
            - Ramah dan mudah dipahami
            - Memberikan jawaban praktis dan actionable
            - Fokus pada keselamatan berkendara
            - Menjelaskan istilah teknis dengan bahasa sederhana
            
            Gunakan bahasa Indonesia yang santai tapi profesional.
            """.trimIndent()
        } else {
            """
            You are a friendly and knowledgeable Suzuki mechanic assistant specializing in Suzuki Ertiga Diesel vehicles.
            Help users with questions about their vehicles, technical issues, maintenance, and driving tips.
            
            Your characteristics:
            - Friendly and easy to understand
            - Provide practical and actionable answers
            - Focus on driving safety
            - Explain technical terms in simple language
            
            Use casual but professional English.
            """.trimIndent()
        }
    }
    
    private fun buildAnalysisPrompt(request: AiAnalysisRequest): String {
        val userInputsText = request.userInputs.entries.joinToString("\n") { (key, value) ->
            "$key: $value"
        }
        
        val pidDataText = request.pidData.joinToString("\n") { pid ->
            "${pid.name}: ${pid.currentValue}${pid.unit} (${pid.category})"
        }
        
        return if (request.language == "id") {
            """
            Silakan analisis data kendaraan Suzuki Ertiga Diesel berikut:
            
            INFORMASI PENGGUNA:
            $userInputsText
            
            DATA OBD-II REAL-TIME:
            $pidDataText
            
            Berikan analisis lengkap dalam format JSON yang diminta.
            """.trimIndent()
        } else {
            """
            Please analyze the following Suzuki Ertiga Diesel vehicle data:
            
            USER INFORMATION:
            $userInputsText
            
            REAL-TIME OBD-II DATA:
            $pidDataText
            
            Provide a complete analysis in the requested JSON format.
            """.trimIndent()
        }
    }
    
    private fun parseAnalysisResponse(content: String): AiAnalysisResponse {
        return try {
            // Try to extract JSON from the response
            val jsonStart = content.indexOf("{")
            val jsonEnd = content.lastIndexOf("}") + 1
            
            if (jsonStart != -1 && jsonEnd > jsonStart) {
                val jsonContent = content.substring(jsonStart, jsonEnd)
                gson.fromJson(jsonContent, AiAnalysisResponse::class.java)
            } else {
                // Fallback parsing if JSON is not properly formatted
                AiAnalysisResponse(
                    summary = content.take(200),
                    issuesFound = listOf("Unable to parse detailed analysis"),
                    recommendations = listOf("Please check the response format"),
                    healthScore = 75
                )
            }
        } catch (e: Exception) {
            AiAnalysisResponse(
                summary = "Analysis completed but response parsing failed",
                issuesFound = listOf("Response parsing error: ${e.message}"),
                recommendations = listOf("Please try again or check API configuration"),
                healthScore = 50
            )
        }
    }
}
