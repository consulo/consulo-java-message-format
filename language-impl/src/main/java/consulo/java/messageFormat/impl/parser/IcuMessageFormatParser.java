package consulo.java.messageFormat.impl.parser;

import consulo.java.messageFormat.MessageFormatTokenTypes;
import consulo.language.ast.IElementType;
import consulo.language.parser.PsiBuilder;

public class IcuMessageFormatParser extends MessageFormatParser {
    @Override
    protected void parseContent(PsiBuilder builder) {
        while (!builder.eof()) {
            IElementType token = builder.getTokenType();
            if (token == MessageFormatTokenTypes.LBRACE) {
                parseFormatElement(builder);
            }
            else if (token == MessageFormatTokenTypes.QUOTED_STRING || token == MessageFormatTokenTypes.SINGLE_QUOTE) {
                parseQuotedText(builder);
            }
            else if (token == MessageFormatTokenTypes.HASH) {
                // # is a number placeholder in plural/select sub-messages
                builder.advanceLexer();
            }
            else if (token == MessageFormatTokenTypes.TEXT) {
                builder.advanceLexer();
            }
            else if (token == MessageFormatTokenTypes.RBRACE) {
                // End of sub-message — don't consume, let the caller handle it
                break;
            }
            else {
                builder.advanceLexer();
            }
        }
    }

    @Override
    protected void parseFormatElement(PsiBuilder builder) {
        // ICU4J supports named arguments (IDENTIFIER) in addition to numeric
        super.parseFormatElement(builder);
    }

    @Override
    protected void parseStyleContent(PsiBuilder builder) {
        // In ICU4J, the style section for plural/select/selectordinal contains
        // keyword{sub-message} pairs
        while (!builder.eof()) {
            IElementType token = builder.getTokenType();
            if (token == MessageFormatTokenTypes.RBRACE) {
                break;
            }
            else if (token == MessageFormatTokenTypes.LBRACE) {
                // Sub-message: parse nested content until matching '}'
                builder.advanceLexer(); // consume '{'
                parseContent(builder);
                if (builder.getTokenType() == MessageFormatTokenTypes.RBRACE) {
                    builder.advanceLexer(); // consume '}'
                }
            }
            else {
                builder.advanceLexer();
            }
        }
    }
}
