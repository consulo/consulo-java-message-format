package consulo.java.messageFormat.impl;

import consulo.annotation.access.RequiredReadAction;
import consulo.java.messageFormat.MessageFormatFileType;
import consulo.java.messageFormat.impl.version.IcuMessageFormatVersion;
import consulo.java.messageFormat.impl.version.JdkMessageFormatVersion;
import consulo.language.file.LanguageFileType;
import consulo.language.version.LanguageVersion;
import consulo.test.junit.impl.language.SimpleParsingTest;
import consulo.virtualFileSystem.fileType.FileType;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.lang.reflect.Method;

public abstract class MessageFormatBaseParsingTest extends SimpleParsingTest<Object> {
    public MessageFormatBaseParsingTest(String dataPath) {
        super(dataPath, "msgfmt");
    }

    @Nonnull
    @Override
    protected LanguageFileType getFileType(@Nonnull Context context, @Nullable Object testContext) {
        return MessageFormatFileType.INSTANCE;
    }

    @Override
    protected boolean checkAllPsiRoots() {
        return false;
    }

    @RequiredReadAction
    @Override
    protected LanguageVersion resolveLanguageVersion(Context context, @Nullable Object testContext, FileType fileType) {
        Method method = context.testInfo().getTestMethod().get();
        SetMessageFormatVersion annotation = method.getAnnotation(SetMessageFormatVersion.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Missing @SetMessageFormatVersion annotation");
        }
        return switch (annotation.value()) {
            case JDK -> findVersion(JdkMessageFormatVersion.class);
            case ICU4J -> findVersion(IcuMessageFormatVersion.class);
        };
    }

    private <T extends LanguageVersion> LanguageVersion findVersion(Class<T> versionClass) {
        LanguageVersion version = MessageFormatFileType.INSTANCE.getLanguage().findVersionByClass(versionClass);
        if (version == null) {
            throw new IllegalStateException("Language version not found: " + versionClass.getSimpleName());
        }
        return version;
    }
}
