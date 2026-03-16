/*
 * Copyright 2013-2026 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package consulo.java.messageFormat.impl.highlighting;

import consulo.annotation.component.ExtensionImpl;
import consulo.codeEditor.HighlighterColors;
import consulo.colorScheme.TextAttributesKey;
import consulo.java.messageFormat.MessageFormatLanguage;
import consulo.java.messageFormat.MessageFormatTokenTypes;
import consulo.java.messageFormat.impl.lexer.IcuMessageFormatLexer;
import consulo.java.messageFormat.impl.lexer.JdkMessageFormatLexer;
import consulo.java.messageFormat.impl.version.JdkMessageFormatVersion;
import consulo.language.Language;
import consulo.language.ast.IElementType;
import consulo.language.ast.TokenType;
import consulo.language.editor.highlight.LanguageVersionableSyntaxHighlighterFactory;
import consulo.language.editor.highlight.SyntaxHighlighter;
import consulo.language.editor.highlight.SyntaxHighlighterBase;
import consulo.language.lexer.Lexer;
import consulo.language.version.LanguageVersion;
import jakarta.annotation.Nonnull;

import java.util.HashMap;
import java.util.Map;

import static consulo.codeEditor.DefaultLanguageHighlighterColors.*;

@ExtensionImpl
public class MessageFormatSyntaxHighlighterFactory extends LanguageVersionableSyntaxHighlighterFactory {
    @Nonnull
    @Override
    public Language getLanguage() {
        return MessageFormatLanguage.INSTANCE;
    }

    @Override
    public SyntaxHighlighter getSyntaxHighlighter(LanguageVersion languageVersion) {
        if (languageVersion instanceof JdkMessageFormatVersion) {
            return new MessageFormatSyntaxHighlighter(new JdkMessageFormatLexer());
        }
        return new MessageFormatSyntaxHighlighter(new IcuMessageFormatLexer());
    }

    private static class MessageFormatSyntaxHighlighter extends SyntaxHighlighterBase {
        private static final Map<IElementType, TextAttributesKey> ourAttributes = new HashMap<>();

        static {
            fillMap(ourAttributes, BRACES, MessageFormatTokenTypes.LBRACE, MessageFormatTokenTypes.RBRACE);
            fillMap(ourAttributes, COMMA, MessageFormatTokenTypes.COMMA);
            fillMap(ourAttributes, NUMBER, MessageFormatTokenTypes.NUMBER, MessageFormatTokenTypes.HASH);
            fillMap(ourAttributes, KEYWORD, MessageFormatTokenTypes.IDENTIFIER);
            fillMap(ourAttributes, STRING, MessageFormatTokenTypes.TEXT);
            fillMap(ourAttributes, VALID_STRING_ESCAPE,
                MessageFormatTokenTypes.QUOTED_STRING,
                MessageFormatTokenTypes.SINGLE_QUOTE);
            fillMap(ourAttributes, OPERATION_SIGN,
                MessageFormatTokenTypes.PIPE,
                MessageFormatTokenTypes.LESS,
                MessageFormatTokenTypes.LESS_OR_EQUAL,
                MessageFormatTokenTypes.GREATER_OR_EQUAL);
            fillMap(ourAttributes, HighlighterColors.BAD_CHARACTER, TokenType.BAD_CHARACTER);
        }

        private final Lexer myLexer;

        private MessageFormatSyntaxHighlighter(Lexer lexer) {
            myLexer = lexer;
        }

        @Nonnull
        @Override
        public Lexer getHighlightingLexer() {
            return myLexer;
        }

        @Nonnull
        @Override
        public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
            return pack(ourAttributes.get(tokenType));
        }
    }
}
