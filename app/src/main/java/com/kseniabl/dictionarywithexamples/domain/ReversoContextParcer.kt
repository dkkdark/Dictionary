import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

data class WordUsageExample(val text: String, val highlighted: List<Pair<Int, Int>>)

data class Translation(
    val sourceWord: String,
    val translation: String,
    val frequency: String,
    val inflectedForms: List<InflectedForm>
)

data class InflectedForm(val translation: String, val frequency: String)

class ReversoContextAPI(
    private var sourceText: String = "wilt",
    private var targetText: String = "",
    private var sourceLang: String = "en",
    private var targetLang: String = "ru"
) {
    private val client = OkHttpClient()
    private var pageList: JsonObject? = null

    fun getTranslations(): List<Translation> {
        updateData()
        val response = postData("https://context.reverso.net/bst-query-service")
        val translations = response.get("dictionary_entry_list").asJsonArray
        Log.e("qqq", "translations $translations")
        return translations.map {
            Translation(
                sourceText,
                it.asJsonObject.get("term").asString ?: "",
                it.asJsonObject.get("alignFreq").asString ?: "",
                it.asJsonObject.get("inflectedForms")?.asJsonArray?.map {

                    InflectedForm(
                        it.asJsonObject.get("term").asString ?: "",
                        it.asJsonObject.get("alignFreq").asString ?: ""
                    )
                } ?: emptyList()
            )
        }
    }

    fun getExamples(): List<Pair<WordUsageExample, WordUsageExample>> {
        updateData()
        val response = postData("https://context.reverso.net/bst-query-service")
        val examples = response.get("list").asJsonArray
        return examples.map { word ->
            val source = word.asJsonObject.get("s_text").asString ?: ""
            val target = word.asJsonObject.get("t_text").asString ?: ""
            val sourceHighlighted = findHighlightIndexes(source)
            val targetHighlighted = findHighlightIndexes(target)
            Pair(
                WordUsageExample(source, sourceHighlighted),
                WordUsageExample(target, targetHighlighted)
            )
        }
    }

    private fun updateData() {
        pageList = postData("https://context.reverso.net/bst-query-service")
    }

    private fun postData(url: String): JsonObject {
        val data = mapOf(
            "source_text" to sourceText,
            "target_text" to targetText,
            "source_lang" to sourceLang,
            "target_lang" to targetLang
        )
        val json = Gson().toJson(data)
        val request = Request.Builder()
            .url(url)
            .post(json.toRequestBody(JSON_MEDIA_TYPE))
            .addHeader("User-Agent", "Mozilla/5.0")
            .addHeader("Content-Type", "application/json; charset=UTF-8")
            .build()
        val response = client.newCall(request).execute()
        val res = response.body?.string()
        val jsonObject = JsonParser.parseString(res).asJsonObject
        return jsonObject
    }

    private fun findHighlightIndexes(text: String): List<Pair<Int, Int>> {
        val tag = "em"
        val highlightedIndexes = mutableListOf<Pair<Int, Int>>()
        var currentIndex = 0
        var currentIndexTagless = 0

        while (currentIndex < text.length) {
            val tagStart = text.indexOf("<$tag>", currentIndex)
            val tagEnd = text.indexOf("</$tag>", currentIndex)

            if (tagStart == -1 || tagEnd == -1) {
                break
            }

            highlightedIndexes.add(
                Pair(
                    currentIndexTagless + tagStart,
                    currentIndexTagless + tagEnd - tagStart - 2
                )
            )

            currentIndex = tagEnd + tag.length + 3
            currentIndexTagless = currentIndexTagless + tagEnd - tagStart - 2
        }

        return highlightedIndexes
    }

    companion object {
        private val JSON_MEDIA_TYPE = "application/json; charset=UTF-8".toMediaType()
    }
}
