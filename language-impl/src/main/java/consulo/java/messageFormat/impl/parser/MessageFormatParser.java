package consulo.java.messageFormat.impl.parser;

import consulo.java.messageFormat.MessageFormatElementTypes;
import consulo.java.messageFormat.MessageFormatTokenTypes;
import consulo.language.ast.ASTNode;
import consulo.language.ast.IElementType;
import consulo.language.parser.PsiBuilder;
import consulo.language.parser.PsiParser;
import consulo.language.version.LanguageVersion;
import jakarta.annotation.Nonnull;

public class MessageFormatParser implements PsiParser {
    @Nonnull
    @Override
    public ASTNode parse(@Nonnull IElementType root, @Nonnull PsiBuilder builder, @Nonnull LanguageVersion languageVersion) {
        PsiBuilder.Marker rootMarker = builder.mark();
        parseContent(builder);
        rootMarker.done(root);
        return builder.getTreeBuilt();
    }

    protected void parseContent(PsiBuilder builder) {
        while (!builder.eof()) {
            IElementType token = builder.getTokenType();
            if (token == MessageFormatTokenTypes.LBRACE) {
                parseFormatElement(builder);
            }
            else if (token == MessageFormatTokenTypes.QUOTED_STRING || token == MessageFormatTokenTypes.SINGLE_QUOTE) {
                parseQuotedText(builder);
            }
            else if (token == MessageFormatTokenTypes.TEXT) {
                builder.advanceLexer();
            }
            else if (token == MessageFormatTokenTypes.HASH) {
                builder.advanceLexer();
            }
            else {
                builder.advanceLexer();
            }
        }
    }

    protected void parseFormatElement(PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        // consume '{'
        builder.advanceLexer();

        skipWhitespace(builder);

        // argument index
        if (builder.getTokenType() == MessageFormatTokenTypes.NUMBER ||
            builder.getTokenType() == MessageFormatTokenTypes.IDENTIFIER) {
            PsiBuilder.Marker argMarker = builder.mark();
            builder.advanceLexer();
            argMarker.done(MessageFormatElementTypes.ARGUMENT_INDEX);
        }

        skipWhitespace(builder);

        // optional: ',' formatType
        if (builder.getTokenType() == MessageFormatTokenTypes.COMMA) {
            builder.advanceLexer();
            skipWhitespace(builder);

            if (builder.getTokenType() == MessageFormatTokenTypes.IDENTIFIER) {
                PsiBuilder.Marker typeMarker = builder.mark();
                String formatType = builder.getTokenText();
                builder.advanceLexer();
                typeMarker.done(MessageFormatElementTypes.FORMAT_TYPE);

                skipWhitespace(builder);

                // optional: ',' formatStyle
                if (builder.getTokenType() == MessageFormatTokenTypes.COMMA) {
                    builder.advanceLexer();
                    skipWhitespace(builder);
                    parseFormatStyle(builder, formatType);
                }
            }
        }

        skipWhitespace(builder);

        // consume '}'
        if (builder.getTokenType() == MessageFormatTokenTypes.RBRACE) {
            builder.advanceLexer();
        }

        marker.done(MessageFormatElementTypes.FORMAT_ELEMENT);
    }

    protected void parseFormatStyle(PsiBuilder builder, String formatType) {
        PsiBuilder.Marker styleMarker = builder.mark();
        parseStyleContent(builder);
        styleMarker.done(MessageFormatElementTypes.FORMAT_STYLE);
    }

    protected void parseStyleContent(PsiBuilder builder) {
        while (!builder.eof()) {
            IElementType token = builder.getTokenType();
            if (token == MessageFormatTokenTypes.RBRACE) {
                break;
            }
            else if (token == MessageFormatTokenTypes.LBRACE) {
                parseFormatElement(builder);
            }
            else {
                builder.advanceLexer();
            }
        }
    }

    protected void parseQuotedText(PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        builder.advanceLexer();
        marker.done(MessageFormatElementTypes.QUOTED_TEXT);
    }

    protected void skipWhitespace(PsiBuilder builder) {
        while (builder.getTokenType() == MessageFormatTokenTypes.WHITE_SPACE) {
            builder.advanceLexer();
        }
    }
}
