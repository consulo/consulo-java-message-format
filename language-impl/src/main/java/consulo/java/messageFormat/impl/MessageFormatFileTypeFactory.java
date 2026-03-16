package consulo.java.messageFormat.impl;

import consulo.annotation.component.ExtensionImpl;
import consulo.java.messageFormat.MessageFormatFileType;
import consulo.virtualFileSystem.fileType.FileTypeConsumer;
import consulo.virtualFileSystem.fileType.FileTypeFactory;
import jakarta.annotation.Nonnull;

@ExtensionImpl
public class MessageFormatFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@Nonnull FileTypeConsumer consumer) {
        consumer.consume(MessageFormatFileType.INSTANCE, MessageFormatFileType.JDK_EXTENSION + ";" + MessageFormatFileType.ICU_EXTENSION);
    }
}
