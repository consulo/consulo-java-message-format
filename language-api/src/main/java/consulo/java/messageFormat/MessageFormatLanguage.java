package consulo.java.messageFormat;

import consulo.language.InjectableLanguage;
import consulo.language.Language;

public class MessageFormatLanguage extends Language implements InjectableLanguage {
    public static final MessageFormatLanguage INSTANCE = new MessageFormatLanguage();

    private MessageFormatLanguage() {
        super("MessageFormat");
    }

    @Override
    public boolean isCaseSensitive() {
        return true;
    }
}
