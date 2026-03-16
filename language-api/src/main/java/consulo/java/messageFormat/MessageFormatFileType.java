package consulo.java.messageFormat;

import consulo.java.messageFormat.localize.MessageFormatLocalize;
import consulo.language.file.LanguageFileType;
import consulo.localize.LocalizeValue;
import consulo.platform.base.icon.PlatformIconGroup;
import consulo.ui.image.Image;
import jakarta.annotation.Nonnull;

public class MessageFormatFileType extends LanguageFileType {
    public static final MessageFormatFileType INSTANCE = new MessageFormatFileType();

    private MessageFormatFileType() {
        super(MessageFormatLanguage.INSTANCE);
    }

    @Nonnull
    @Override
    public String getId() {
        return "MessageFormat";
    }

    @Nonnull
    @Override
    public LocalizeValue getDescription() {
        return MessageFormatLocalize.filetypeMessageformatDescription();
    }

    @Nonnull
    @Override
    public String getDefaultExtension() {
        return "msgfmt";
    }

    @Nonnull
    @Override
    public Image getIcon() {
        return PlatformIconGroup.filetypesText();
    }
}
