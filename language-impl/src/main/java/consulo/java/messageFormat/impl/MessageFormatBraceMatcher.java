package consulo.java.messageFormat.impl;

import consulo.annotation.component.ExtensionImpl;
import consulo.java.messageFormat.MessageFormatLanguage;
import consulo.java.messageFormat.MessageFormatTokenTypes;
import consulo.language.BracePair;
import consulo.language.Language;
import consulo.language.PairedBraceMatcher;
import consulo.language.ast.IElementType;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

@ExtensionImpl
public class MessageFormatBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] PAIRS = new BracePair[]{
        new BracePair(MessageFormatTokenTypes.LBRACE, MessageFormatTokenTypes.RBRACE, true),
    };

    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@Nonnull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }

    @Nonnull
    @Override
    public Language getLanguage() {
        return MessageFormatLanguage.INSTANCE;
    }
}
