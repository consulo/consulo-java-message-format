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

package consulo.java.messageFormat.impl.completion;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.application.dumb.DumbAware;
import consulo.java.messageFormat.MessageFormatElementTypes;
import consulo.java.messageFormat.MessageFormatLanguage;
import consulo.java.messageFormat.MessageFormatTokenTypes;
import consulo.java.messageFormat.impl.version.JdkMessageFormatVersion;
import consulo.language.Language;
import consulo.language.editor.completion.*;
import consulo.language.editor.completion.lookup.LookupElementBuilder;
import consulo.language.psi.PsiElement;
import consulo.language.psi.util.PsiTreeUtil;
import consulo.language.util.ProcessingContext;
import consulo.language.version.LanguageVersion;
import consulo.language.version.LanguageVersionUtil;
import jakarta.annotation.Nonnull;

import java.util.List;

import static consulo.language.pattern.PlatformPatterns.psiElement;

@ExtensionImpl
public class MessageFormatCompletionContributor extends CompletionContributor implements DumbAware {
    private static final List<String> JDK_FORMAT_TYPES = List.of("number", "date", "time", "choice");
    private static final List<String> ICU_FORMAT_TYPES = List.of(
        "number", "date", "time", "choice",
        "plural", "select", "selectordinal",
        "spellout", "ordinal", "duration"
    );
    private static final List<String> DATE_TIME_STYLES = List.of("short", "medium", "long", "full");
    private static final List<String> NUMBER_STYLES = List.of("integer", "currency", "percent");
    private static final List<String> PLURAL_KEYWORDS = List.of("zero", "one", "two", "few", "many", "other");

    public MessageFormatCompletionContributor() {
        // Complete format type (after first comma): number, date, time, choice, plural, select...
        extend(CompletionType.BASIC,
            psiElement(MessageFormatTokenTypes.IDENTIFIER)
                .withParent(psiElement().withElementType(MessageFormatElementTypes.FORMAT_TYPE)),
            new FormatTypeCompletionProvider());

        // Complete format style (after second comma): short, medium, long, full, integer, currency, percent...
        extend(CompletionType.BASIC,
            psiElement(MessageFormatTokenTypes.IDENTIFIER)
                .withParent(psiElement().withElementType(MessageFormatElementTypes.FORMAT_STYLE)),
            new FormatStyleCompletionProvider());
    }

    @Nonnull
    @Override
    public Language getLanguage() {
        return MessageFormatLanguage.INSTANCE;
    }

    private static class FormatTypeCompletionProvider implements CompletionProvider {
        @RequiredReadAction
        @Override
        public void addCompletions(@Nonnull CompletionParameters parameters,
                                   @Nonnull ProcessingContext context,
                                   @Nonnull CompletionResultSet result) {
            PsiElement position = parameters.getPosition();
            List<String> types = isJdkVersion(position) ? JDK_FORMAT_TYPES : ICU_FORMAT_TYPES;
            for (String type : types) {
                result.addElement(LookupElementBuilder.create(type).withBoldness(true));
            }
        }
    }

    private static class FormatStyleCompletionProvider implements CompletionProvider {
        @RequiredReadAction
        @Override
        public void addCompletions(@Nonnull CompletionParameters parameters,
                                   @Nonnull ProcessingContext context,
                                   @Nonnull CompletionResultSet result) {
            PsiElement position = parameters.getPosition();
            String formatType = findFormatType(position);
            if (formatType == null) {
                return;
            }

            switch (formatType) {
                case "date", "time" -> {
                    for (String style : DATE_TIME_STYLES) {
                        result.addElement(LookupElementBuilder.create(style));
                    }
                }
                case "number" -> {
                    for (String style : NUMBER_STYLES) {
                        result.addElement(LookupElementBuilder.create(style));
                    }
                }
                case "plural", "selectordinal" -> {
                    for (String keyword : PLURAL_KEYWORDS) {
                        result.addElement(LookupElementBuilder.create(keyword));
                    }
                }
            }
        }

        @RequiredReadAction
        private String findFormatType(PsiElement position) {
            // Walk up to FORMAT_ELEMENT and find the FORMAT_TYPE child
            PsiElement formatElement = PsiTreeUtil.findFirstParent(position,
                e -> e.getNode().getElementType() == MessageFormatElementTypes.FORMAT_ELEMENT);
            if (formatElement == null) {
                return null;
            }

            for (PsiElement child = formatElement.getFirstChild(); child != null; child = child.getNextSibling()) {
                if (child.getNode().getElementType() == MessageFormatElementTypes.FORMAT_TYPE) {
                    return child.getText().trim();
                }
            }
            return null;
        }
    }

    @RequiredReadAction
    private static boolean isJdkVersion(PsiElement element) {
        LanguageVersion version = LanguageVersionUtil.findLanguageVersion(
            MessageFormatLanguage.INSTANCE, element);
        return version instanceof JdkMessageFormatVersion;
    }
}
