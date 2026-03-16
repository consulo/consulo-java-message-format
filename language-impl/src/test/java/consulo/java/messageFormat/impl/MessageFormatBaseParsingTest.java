/*
 * Copyright 2013-2026 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    public MessageFormatBaseParsingTest(String dataPath, String extension) {
        super(dataPath, extension);
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
