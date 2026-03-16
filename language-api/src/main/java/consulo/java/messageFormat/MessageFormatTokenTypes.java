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
