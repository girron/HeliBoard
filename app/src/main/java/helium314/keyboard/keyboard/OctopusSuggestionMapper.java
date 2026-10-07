// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.keyboard;

import android.util.SparseArray;
import android.util.SparseBooleanArray;

import androidx.annotation.Nullable;

import helium314.keyboard.latin.SuggestedWords;
import helium314.keyboard.latin.SuggestedWords.SuggestedWordInfo;

/**
 * Maps HeliBoard's ranked suggestions to the key that represents the next character,
 * matching the behavior of the original Octopus Keyboard.
 */
public final class OctopusSuggestionMapper {
    private final SparseArray<SuggestedWordInfo> mSuggestionsByKey = new SparseArray<>();
    private final SparseBooleanArray mCorrectionsByKey = new SparseBooleanArray();

    public void update(@Nullable final SuggestedWords suggestedWords) {
        mSuggestionsByKey.clear();
        mCorrectionsByKey.clear();
        if (suggestedWords == null || suggestedWords.isEmpty()
                || suggestedWords.isPunctuationSuggestions()) {
            return;
        }

        final SuggestedWordInfo typedWordInfo = suggestedWords.mTypedWordInfo;
        final String typedWord = typedWordInfo == null ? "" : typedWordInfo.mWord;
        final int typedCodePointCount = typedWord.codePointCount(0, typedWord.length());

        // SuggestedWords is already ranked. The first suggestion encountered for a
        // particular next-character key wins, just like Octopus' suggestionIndexForKey:.
        for (int i = 0; i < suggestedWords.size(); i++) {
            final SuggestedWordInfo info = suggestedWords.getInfo(i);
            if (info == null || info.isKindOf(SuggestedWordInfo.KIND_TYPED)) {
                continue;
            }

            final String word = info.mWord;
            if (word == null || word.isEmpty() || containsWhitespace(word)) {
                continue;
            }

            final int wordCodePointCount = word.codePointCount(0, word.length());
            if (wordCodePointCount <= typedCodePointCount) {
                continue;
            }

            final int nextCharIndex = word.offsetByCodePoints(0, typedCodePointCount);
            final int nextCodePoint = normalizeKeyCode(word.codePointAt(nextCharIndex));
            if (Character.isWhitespace(nextCodePoint)
                    || mSuggestionsByKey.get(nextCodePoint) != null) {
                continue;
            }

            mSuggestionsByKey.put(nextCodePoint, info);

            // The original Octopus colored a candidate red when it did not share
            // the text already typed as its prefix. That is a correction rather
            // than a normal completion.
            final boolean isCorrection = !typedWord.isEmpty()
                    && !startsWithIgnoreCase(word, typedWord);
            mCorrectionsByKey.put(nextCodePoint, isCorrection);
        }
    }

    @Nullable
    public SuggestedWordInfo getSuggestion(final int keyCode) {
        return mSuggestionsByKey.get(normalizeKeyCode(keyCode));
    }

    public boolean hasSuggestion(final int keyCode) {
        return getSuggestion(keyCode) != null;
    }

    public boolean isCorrection(final int keyCode) {
        return mCorrectionsByKey.get(normalizeKeyCode(keyCode), false);
    }

    private static boolean startsWithIgnoreCase(final String word, final String prefix) {
        return word.length() >= prefix.length()
                && word.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    private static boolean containsWhitespace(final String text) {
        for (int i = 0; i < text.length(); ) {
            final int codePoint = text.codePointAt(i);
            if (Character.isWhitespace(codePoint)) {
                return true;
            }
            i += Character.charCount(codePoint);
        }
        return false;
    }

    private static int normalizeKeyCode(final int keyCode) {
        return Character.toLowerCase(keyCode);
    }
}
