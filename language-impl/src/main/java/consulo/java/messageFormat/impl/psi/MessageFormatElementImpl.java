package consulo.java.messageFormat.impl.psi;

import consulo.java.messageFormat.psi.MessageFormatElement;
import consulo.language.ast.ASTNode;
import consulo.language.impl.psi.ASTWrapperPsiElement;
import jakarta.annotation.Nonnull;

public class MessageFormatElementImpl extends ASTWrapperPsiElement implements MessageFormatElement {
    public MessageFormatElementImpl(@Nonnull ASTNode node) {
        super(node);
    }
}
