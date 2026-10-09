// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.settings.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import helium314.keyboard.latin.personalization.OctopusVocabularyManager
import helium314.keyboard.settings.SearchScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

/** Learned typing history is intentionally separate from Android's Personal Dictionary. */
@Composable
fun ManageVocabularyScreen(onClickBack: () -> Unit) {
    val locales = remember { getSortedDictionaryLocales().toList() }
    var selectedLocale by remember { mutableStateOf<Locale?>(null) }

    if (selectedLocale == null) {
        SearchScreen(
            onClickBack = onClickBack,
            title = { Text("Manage Vocabulary") },
            filteredItems = { term ->
                locales.filter { it.displayName.contains(term, ignoreCase = true) }
            },
            itemContent = { locale ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .clickable { selectedLocale = locale }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(locale.displayName, style = MaterialTheme.typography.bodyLarge)
                }
            },
            content = {
                Text(
                    "Choose a language to view learned typing history. Personal Dictionary entries are managed separately.",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                locales.forEach { locale ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable { selectedLocale = locale }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(locale.displayName, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        )
    } else {
        LearnedVocabularyLanguageScreen(selectedLocale!!, onClickBack = { selectedLocale = null })
    }
}

@Composable
private fun VocabularyWordRow(word: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(word, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun LearnedVocabularyLanguageScreen(locale: Locale, onClickBack: () -> Unit) {
    val context = LocalContext.current.applicationContext
    val scope = rememberCoroutineScope()
    var refresh by remember(locale) { mutableIntStateOf(0) }
    var loading by remember(locale) { mutableStateOf(true) }
    var learned by remember(locale) { mutableStateOf<List<String>>(emptyList()) }
    var forgotten by remember(locale) { mutableStateOf<List<String>>(emptyList()) }
    var showForgotten by remember(locale) { mutableStateOf(false) }
    var selectedWord by remember(locale) { mutableStateOf<String?>(null) }
    var confirmClear by remember(locale) { mutableStateOf(false) }

    LaunchedEffect(locale, refresh) {
        loading = true
        val results = withContext(Dispatchers.IO) {
            OctopusVocabularyManager.learnedWords(context, locale) to
                OctopusVocabularyManager.forgottenWords(context, locale)
        }
        learned = results.first
        forgotten = results.second
        loading = false
    }

    val visibleWords = if (showForgotten) forgotten else learned
    val emptyMessage = if (showForgotten) "No forgotten words for this language."
        else "No learned words found. If you have typed recently, try Refresh."

    SearchScreen(
        onClickBack = onClickBack,
        title = { Text(if (showForgotten) "Forgotten: ${locale.displayName}" else "Learned: ${locale.displayName}") },
        menu = listOf(
            (if (showForgotten) "Show learned words" else "Show forgotten words") to {
                showForgotten = !showForgotten
            },
            "Refresh" to { refresh++ },
            "Clear learned history" to { confirmClear = true }
        ),
        filteredItems = { term -> visibleWords.filter { it.contains(term, ignoreCase = true) } },
        itemContent = { word -> VocabularyWordRow(word) { selectedWord = word } },
        content = {
            Text(
                if (showForgotten) "Tap a word to allow it to be learned again."
                else "Tap a word to forget it. Forgotten words are suppressed from predictions and won't automatically be relearned.",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else if (visibleWords.isEmpty()) {
                Text(emptyMessage, modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn {
                    items(visibleWords, key = { it }) { word ->
                        VocabularyWordRow(word) { selectedWord = word }
                    }
                }
            }
        }
    )

    selectedWord?.let { word ->
        AlertDialog(
            onDismissRequest = { selectedWord = null },
            title = { Text(if (showForgotten) "Allow word again?" else "Forget word?") },
            text = {
                Text(
                    if (showForgotten) "Allow “$word” to be learned and suggested again?"
                    else "Remove “$word” from learned typing history and prevent it from being relearned automatically?"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    selectedWord = null
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            if (showForgotten) OctopusVocabularyManager.allowWord(context, locale, word)
                            else OctopusVocabularyManager.forgetWord(context, locale, word)
                        }
                        refresh++
                    }
                }) { Text(if (showForgotten) "Allow" else "Forget") }
            },
            dismissButton = {
                TextButton(onClick = { selectedWord = null }) { Text("Cancel") }
            }
        )
    }

    if (confirmClear) {
        AlertDialog(
            onDismissRequest = { confirmClear = false },
            title = { Text("Clear learned history?") },
            text = {
                Text("Delete learned typing history for ${locale.displayName}? Manually added Personal Dictionary words and the forgotten-word list will be preserved.")
            },
            confirmButton = {
                TextButton(onClick = {
                    confirmClear = false
                    learned = emptyList()
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            OctopusVocabularyManager.clearLearnedHistory(context, locale)
                        }
                    }
                }) { Text("Clear history") }
            },
            dismissButton = {
                TextButton(onClick = { confirmClear = false }) { Text("Cancel") }
            }
        )
    }
}
