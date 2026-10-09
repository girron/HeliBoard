// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.latin.personalization

import android.content.Context
import helium314.keyboard.latin.makedict.WordProperty
import java.util.Locale

/**
 * Octopus-owned vocabulary controls. The Android personal dictionary and the keyboard's
 * learned typing history are separate stores; this manages the latter.
 *
 * A forgotten word is suppressed even if another dictionary still offers it. The explicit
 * allowWord action is required before normal typing is allowed to learn it again.
 */
object OctopusVocabularyManager {
    private const val PREFS_NAME = "octopus_vocabulary"
    private const val FORGOTTEN_PREFIX = "forgotten_"
    private val editLock = Any()

    private fun key(locale: Locale) = FORGOTTEN_PREFIX + locale.toLanguageTag()

    private fun canonical(word: String, locale: Locale) = word.lowercase(locale)

    fun isForgotten(context: Context, locale: Locale, word: String): Boolean {
        if (word.isBlank()) return false
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getStringSet(key(locale), emptySet())
            ?.contains(canonical(word, locale)) == true
    }

    fun forgottenWords(context: Context, locale: Locale): List<String> =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getStringSet(key(locale), emptySet())
            .orEmpty().sortedWith(String.CASE_INSENSITIVE_ORDER)

    /** Enumerates the existing native user-history dictionary without copying it to Android's
     * system personal dictionary. Work should be dispatched off the UI thread. */
    fun learnedWords(context: Context, locale: Locale): List<String> {
        val history = PersonalizationHelper.getUserHistoryDictionary(context, locale)
        return history.wordPropertiesForSyncing.orEmpty()
            .map(WordProperty::mWord)
            .filter { it.isNotBlank() && !isForgotten(context, locale, it) }
            .distinct()
            .sortedWith(String.CASE_INSENSITIVE_ORDER)
    }

    fun forgetWord(context: Context, locale: Locale, word: String) {
        if (word.isBlank()) return
        val appContext = context.applicationContext
        synchronized(editLock) {
            val prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val words = prefs.getStringSet(key(locale), emptySet()).orEmpty().toMutableSet()
            words.add(canonical(word, locale))
            // Commit before asynchronously deleting the history entry, so even an in-flight
            // suggestion query won't reintroduce it.
            prefs.edit().putStringSet(key(locale), words).commit()
        }
        PersonalizationHelper.getUserHistoryDictionary(appContext, locale)
            .removeUnigramEntryDynamically(word)
    }

    fun allowWord(context: Context, locale: Locale, word: String) {
        synchronized(editLock) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val words = prefs.getStringSet(key(locale), emptySet()).orEmpty().toMutableSet()
            words.remove(canonical(word, locale))
            prefs.edit().putStringSet(key(locale), words).commit()
        }
    }

    /** Clears only the selected language's learned history; manual personal words are preserved. */
    fun clearLearnedHistory(context: Context, locale: Locale) {
        PersonalizationHelper.getUserHistoryDictionary(context.applicationContext, locale).clear()
    }
}
