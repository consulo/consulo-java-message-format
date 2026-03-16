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

package consulo.java.messageFormat.impl.version;

import consulo.annotation.component.ExtensionImpl;
import consulo.java.messageFormat.MessageFormatFileType;
import consulo.java.messageFormat.MessageFormatLanguage;
import consulo.java.messageFormat.MessageFormatTokenSets;
import consulo.java.messageFormat.impl.lexer.IcuMessageFormatLexer;
import consulo.java.messageFormat.impl.parser.IcuMessageFormatParser;
import consulo.language.ast.TokenSet;
import consulo.language.lexer.Lexer;
import consulo.language.parser.PsiParser;
import consulo.language.psi.PsiElement;
import consulo.language.version.LanguageVersion;
import consulo.language.version.LanguageVersionWithDefinition;
import consulo.language.version.LanguageVersionWithParsing;
import consulo.project.Project;
import consulo.virtualFileSystem.VirtualFile;
import jakarta.annotation.Nonnull;
import org.jspecify.annotations.Nullable;

@ExtensionImpl
public class IcuMessageFormatVersion extends LanguageVersion implements LanguageVersionWithParsing, LanguageVersionWithDefinition {
    public IcuMessageFormatVersion() {
        super("ICU4J", "ICU4J MessageFormat", MessageFormatLanguage.INSTANCE);
    }

    @Override
    public boolean isMyElement(@Nullable PsiElement element) {
        if (element == null) {
            return false;
        }
        PsiElement file = element.getContainingFile();
        if (file == null) {
            return false;
        }
        VirtualFile virtualFile = file.getContainingFile().getVirtualFile();
        return virtualFile != null && MessageFormatFileType.ICU_EXTENSION.equals(virtualFile.getExtension());
    }

    @Override
    public boolean isMyFile(@Nullable Project project, @Nullable VirtualFile virtualFile) {
        return virtualFile != null && MessageFormatFileType.ICU_EXTENSION.equals(virtualFile.getExtension());
    }

    @Nonnull
    @Override
    public Lexer createLexer() {
        return new IcuMessageFormatLexer();
    }

    @Nonnull
    @Override
    public PsiParser createParser() {
        return new IcuMessageFormatParser();
    }

    @Nonnull
    @Override
    public TokenSet getWhitespaceTokens() {
        return MessageFormatTokenSets.WHITE_SPACES;
    }

    @Nonnull
    @Override
    public TokenSet getCommentTokens() {
        return MessageFormatTokenSets.COMMENTS;
    }

    @Nonnull
    @Override
    public TokenSet getStringLiteralElements() {
        return MessageFormatTokenSets.STRING_LITERALS;
    }
}
