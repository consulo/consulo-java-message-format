package consulo.java.messageFormat.impl;

import consulo.annotation.component.ExtensionImpl;
import consulo.java.messageFormat.MessageFormatFileElementTypes;
import consulo.java.messageFormat.MessageFormatLanguage;
import consulo.java.messageFormat.impl.psi.MessageFormatElementImpl;
import consulo.java.messageFormat.impl.psi.MessageFormatFileImpl;
import consulo.language.Language;
import consulo.language.ast.ASTNode;
import consulo.language.ast.IFileElementType;
import consulo.language.file.FileViewProvider;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFile;
import consulo.language.version.LanguageVersionableParserDefinition;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

@ExtensionImpl
public class MessageFormatParserDefinition extends LanguageVersionableParserDefinition {
    @Nonnull
    @Override
    public Language getLanguage() {
        return MessageFormatLanguage.INSTANCE;
    }

    @Nonnull
    @Override
    public IFileElementType getFileNodeType() {
        return MessageFormatFileElementTypes.FILE;
    }

    @Nonnull
    @Override
    public PsiElement createElement(@Nonnull ASTNode node) {
        return new MessageFormatElementImpl(node);
    }

    @Nonnull
    @Override
    public PsiFile createFile(@Nonnull FileViewProvider viewProvider) {
        return new MessageFormatFileImpl(viewProvider);
    }

    @Nonnull
    @Override
    public SpaceRequirements spaceExistenceTypeBetweenTokens(@Nullable ASTNode left, @Nullable ASTNode right) {
        return SpaceRequirements.MAY;
    }
}
