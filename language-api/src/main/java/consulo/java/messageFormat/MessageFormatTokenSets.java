package consulo.java.messageFormat;

import consulo.language.ast.TokenSet;

public interface MessageFormatTokenSets {
    TokenSet WHITE_SPACES = TokenSet.create(MessageFormatTokenTypes.WHITE_SPACE);

    TokenSet COMMENTS = TokenSet.EMPTY;

    TokenSet STRING_LITERALS = TokenSet.create(
        MessageFormatTokenTypes.TEXT,
        MessageFormatTokenTypes.QUOTED_STRING
    );
}
