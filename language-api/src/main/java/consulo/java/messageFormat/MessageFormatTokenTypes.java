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

package consulo.java.messageFormat;

import consulo.language.ast.IElementType;
import consulo.language.ast.TokenType;

public interface MessageFormatTokenTypes {
    // Structural
    IElementType LBRACE = new IElementType("LBRACE", MessageFormatLanguage.INSTANCE);
    IElementType RBRACE = new IElementType("RBRACE", MessageFormatLanguage.INSTANCE);
    IElementType COMMA = new IElementType("COMMA", MessageFormatLanguage.INSTANCE);

    // Literals
    IElementType NUMBER = new IElementType("NUMBER", MessageFormatLanguage.INSTANCE);
    IElementType IDENTIFIER = new IElementType("IDENTIFIER", MessageFormatLanguage.INSTANCE);
    IElementType TEXT = new IElementType("TEXT", MessageFormatLanguage.INSTANCE);
    IElementType QUOTED_STRING = new IElementType("QUOTED_STRING", MessageFormatLanguage.INSTANCE);
    IElementType SINGLE_QUOTE = new IElementType("SINGLE_QUOTE", MessageFormatLanguage.INSTANCE);

    // ICU4J specific
    IElementType HASH = new IElementType("HASH", MessageFormatLanguage.INSTANCE);

    // JDK choice specific
    IElementType PIPE = new IElementType("PIPE", MessageFormatLanguage.INSTANCE);
    IElementType LESS = new IElementType("LESS", MessageFormatLanguage.INSTANCE);
    IElementType LESS_OR_EQUAL = new IElementType("LESS_OR_EQUAL", MessageFormatLanguage.INSTANCE);
    IElementType GREATER_OR_EQUAL = new IElementType("GREATER_OR_EQUAL", MessageFormatLanguage.INSTANCE);

    // Common
    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
}
