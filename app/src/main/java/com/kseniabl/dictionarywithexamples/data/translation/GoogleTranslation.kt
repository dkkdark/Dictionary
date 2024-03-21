package com.kseniabl.dictionarywithexamples.data.translation

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.kseniabl.dictionarywithexamples.domain.model.ResultModel
import com.kseniabl.dictionarywithexamples.domain.model.TranslationEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class GoogleTranslation {

    private fun getTranslator(): Translator {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.RUSSIAN)
            .build()
        return Translation.getClient(options)
    }

    fun downloadModelAndTranslate(text: String) = callbackFlow<ResultModel<TranslationEntity>> {
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        val translator = getTranslator()

        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                Log.e("qqq", "downloadModelAndTranslate addOnSuccessListener")
                val translateFlow = translator.translateText(text)
                launch {
                    translateFlow.collect { result ->
                        trySend(result)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("qqq", "GoogleTranslation downloadModel ${exception.message}")
                trySend(ResultModel.Error("${exception.message}"))
            }
        awaitClose { translator.close() }
    }

    private fun Translator.translateText(text: String) = callbackFlow<ResultModel<TranslationEntity>> {
        val onSuccessListener = OnSuccessListener<String> { translatedText ->
            val translation = TranslationEntity()
            translation.text = translatedText
            trySend(ResultModel.Success(translation))
        }

        val onFailureListener = OnFailureListener { exception ->
            Log.e("qqq", "GoogleTranslation translateText $exception")
            trySend(ResultModel.Error("${exception.message}"))
        }

        translate(text)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)

        awaitClose { this.close() }
    }
}