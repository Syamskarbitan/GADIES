package com.gadies.suzuki.data.model

data class AiAnalysisRequest(
    val userInputs: Map<String, String>,
    val pidData: List<PidData>,
    val language: String = "id"
)

data class AiAnalysisResponse(
    val summary: String,
    val issuesFound: List<String>,
    val recommendations: List<String>,
    val healthScore: Int, // 0-100
    val nextServiceKm: Int? = null
)

data class AiChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val content: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

data class AiQuestion(
    val id: String,
    val question: String,
    val type: String, // "number", "text", "boolean"
    val unit: String? = null,
    val required: Boolean = false,
    val options: List<String>? = null
)

enum class AiModel(val displayName: String, val modelId: String) {
    DEEPSEEK_R1("Deepseek R1", "deepseek/deepseek-r1"),
    CHATGPT_4O("ChatGPT 4o", "openai/gpt-4o"),
    GEMINI_PRO("Gemini Pro", "google/gemini-pro"),
    CLAUDE_SONNET("Claude Sonnet", "anthropic/claude-3-sonnet")
}

data class OpenRouterRequest(
    val model: String,
    val messages: List<OpenRouterMessage>,
    val temperature: Double = 0.7,
    val max_tokens: Int = 2000
)

data class OpenRouterMessage(
    val role: String, // "system", "user", "assistant"
    val content: String
)

data class OpenRouterResponse(
    val choices: List<OpenRouterChoice>
)

data class OpenRouterChoice(
    val message: OpenRouterMessage
)
