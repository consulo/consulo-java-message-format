package consulo.java.messageFormat.impl.psi;

import consulo.java.messageFormat.MessageFormatFileType;
import consulo.java.messageFormat.MessageFormatLanguage;
import consulo.java.messageFormat.psi.MessageFormatFile;
import consulo.language.file.FileViewProvider;
import consulo.language.impl.psi.PsiFileBase;
import consulo.virtualFileSystem.fileType.FileType;
import jakarta.annotation.Nonnull;

public class MessageFormatFileImpl extends PsiFileBase implements MessageFormatFile {
    public MessageFormatFileImpl(@Nonnull FileViewProvider viewProvider) {
        super(viewProvider, MessageFormatLanguage.INSTANCE);
    }

    @Nonnull
    @Override
    public FileType getFileType() {
        return MessageFormatFileType.INSTANCE;
    }
}
