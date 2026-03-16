package consulo.java.messageFormat.impl.version;

import consulo.annotation.component.ExtensionImpl;
import consulo.java.messageFormat.MessageFormatLanguage;
import consulo.java.messageFormat.MessageFormatTokenSets;
import consulo.java.messageFormat.impl.lexer.IcuMessageFormatLexer;
import consulo.java.messageFormat.impl.parser.IcuMessageFormatParser;
import consulo.language.ast.TokenSet;
import consulo.language.lexer.Lexer;
import consulo.language.parser.PsiParser;
import consulo.language.version.LanguageVersion;
import consulo.language.version.LanguageVersionWithParsing;
import jakarta.annotation.Nonnull;

@ExtensionImpl
public class IcuMessageFormatVersion extends LanguageVersion implements LanguageVersionWithParsing {
    public IcuMessageFormatVersion() {
        super("ICU4J", "ICU4J MessageFormat", MessageFormatLanguage.INSTANCE);
    }

    @Nonnull
    @Override
    public Lexer createLexer() {
        return new IcuMessageFormatLexer();
    }

    @Nonnull
    @Override
    public PsiParser createParser() {
        return new IcuMessageFormatParser();
    }

    @Nonnull
    @Override
    public TokenSet getWhitespaceTokens() {
        return MessageFormatTokenSets.WHITE_SPACES;
    }

    @Nonnull
    @Override
    public TokenSet getCommentTokens() {
        return MessageFormatTokenSets.COMMENTS;
    }

    @Nonnull
    @Override
    public TokenSet getStringLiteralElements() {
        return MessageFormatTokenSets.STRING_LITERALS;
    }
}
