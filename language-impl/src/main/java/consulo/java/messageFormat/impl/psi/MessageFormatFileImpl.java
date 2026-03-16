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
