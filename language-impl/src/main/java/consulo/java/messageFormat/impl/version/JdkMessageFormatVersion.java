package consulo.java.messageFormat.impl.version;

import consulo.annotation.component.ExtensionImpl;
import consulo.java.messageFormat.MessageFormatLanguage;
import consulo.java.messageFormat.MessageFormatTokenSets;
import consulo.java.messageFormat.impl.lexer.JdkMessageFormatLexer;
import consulo.java.messageFormat.impl.parser.JdkMessageFormatParser;
import consulo.language.ast.TokenSet;
import consulo.language.lexer.Lexer;
import consulo.language.parser.PsiParser;
import consulo.language.version.LanguageVersion;
import consulo.language.version.LanguageVersionWithParsing;
import jakarta.annotation.Nonnull;

@ExtensionImpl
public class JdkMessageFormatVersion extends LanguageVersion implements LanguageVersionWithParsing {
    public JdkMessageFormatVersion() {
        super("JDK", "JDK MessageFormat", MessageFormatLanguage.INSTANCE);
    }

    @Nonnull
    @Override
    public Lexer createLexer() {
        return new JdkMessageFormatLexer();
    }

    @Nonnull
    @Override
    public PsiParser createParser() {
        return new JdkMessageFormatParser();
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
