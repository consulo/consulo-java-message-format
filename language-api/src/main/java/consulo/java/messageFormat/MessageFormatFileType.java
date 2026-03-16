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

package consulo.java.messageFormat;

import consulo.java.messageFormat.localize.MessageFormatLocalize;
import consulo.language.file.LanguageFileType;
import consulo.localize.LocalizeValue;
import consulo.platform.base.icon.PlatformIconGroup;
import consulo.ui.image.Image;
import jakarta.annotation.Nonnull;

public class MessageFormatFileType extends LanguageFileType {
    public static final MessageFormatFileType INSTANCE = new MessageFormatFileType();

    public static final String JDK_EXTENSION = "jmsgfmt";
    public static final String ICU_EXTENSION = "imsgfmt";

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
        return JDK_EXTENSION;
    }

    @Nonnull
    @Override
    public Image getIcon() {
        return PlatformIconGroup.filetypesText();
    }
}
