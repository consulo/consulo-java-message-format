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

package consulo.java.messageFormat.devkit.inject;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.java.messageFormat.MessageFormatLanguage;
import consulo.java.messageFormat.impl.version.IcuMessageFormatVersion;
import consulo.language.inject.MultiHostInjector;
import consulo.language.inject.MultiHostRegistrar;
import consulo.language.psi.ElementManipulators;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiLanguageInjectionHost;
import consulo.language.version.LanguageVersion;
import consulo.virtualFileSystem.VirtualFile;
import jakarta.annotation.Nonnull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;
import org.jetbrains.yaml.psi.YAMLScalar;

@ExtensionImpl
public class LocalizeMessageFormatInjector implements MultiHostInjector {
    private static final String TEXT_KEY = "text";
    private static final String LOCALIZE_LIB_DIR = "LOCALIZE-LIB";

    @Nonnull
    @Override
    public Class<? extends PsiElement> getElementClass() {
        return YAMLScalar.class;
    }

    @RequiredReadAction
    @Override
    public void injectLanguages(@Nonnull MultiHostRegistrar registrar, @Nonnull PsiElement context) {
        if (!(context instanceof YAMLScalar scalar)) {
            return;
        }

        if (!isLocalizeTextValue(scalar)) {
            return;
        }

        LanguageVersion version = MessageFormatLanguage.INSTANCE.findVersionByClass(IcuMessageFormatVersion.class);
        if (version == null) {
            return;
        }

        registrar.startInjecting(version)
            .addPlace(null, null, (PsiLanguageInjectionHost) scalar, ElementManipulators.getValueTextRange(scalar))
            .doneInjecting();
    }

    @RequiredReadAction
    private boolean isLocalizeTextValue(YAMLScalar scalar) {
        // Check: scalar is value of a "text" key
        PsiElement parent = scalar.getParent();
        if (!(parent instanceof YAMLKeyValue keyValue)) {
            return false;
        }

        if (!TEXT_KEY.equals(keyValue.getKeyText())) {
            return false;
        }

        // Check: parent mapping is inside a top-level key-value
        PsiElement mapping = keyValue.getParent();
        if (!(mapping instanceof YAMLMapping)) {
            return false;
        }

        // Check: file is inside LOCALIZE-LIB directory
        VirtualFile virtualFile = scalar.getContainingFile().getVirtualFile();
        if (virtualFile == null) {
            return false;
        }

        return isInLocalizeLib(virtualFile);
    }

    private boolean isInLocalizeLib(VirtualFile file) {
        // Walk up parents to find LOCALIZE-LIB directory
        VirtualFile current = file.getParent();
        while (current != null) {
            if (LOCALIZE_LIB_DIR.equals(current.getName())) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }
}
