package consulo.java.messageFormat.impl.annotator;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.java.messageFormat.MessageFormatElementTypes;
import consulo.java.messageFormat.MessageFormatLanguage;
import consulo.java.messageFormat.impl.localize.MessageFormatImplLocalize;
import consulo.java.messageFormat.impl.version.JdkMessageFormatVersion;
import consulo.language.Language;
import consulo.language.editor.annotation.AnnotationHolder;
import consulo.language.editor.annotation.Annotator;
import consulo.language.editor.annotation.AnnotatorFactory;
import consulo.language.editor.annotation.HighlightSeverity;
import consulo.language.psi.PsiElement;
import consulo.language.version.LanguageVersion;
import consulo.language.version.LanguageVersionUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Set;

@ExtensionImpl
public class MessageFormatAnnotatorFactory implements AnnotatorFactory {
    private static final Set<String> JDK_FORMAT_TYPES = Set.of("number", "date", "time", "choice");
    private static final Set<String> ICU_FORMAT_TYPES = Set.of(
        "number", "date", "time", "choice",
        "plural", "select", "selectordinal",
        "spellout", "ordinal", "duration"
    );

    @Nonnull
    @Override
    public Language getLanguage() {
        return MessageFormatLanguage.INSTANCE;
    }

    @Nullable
    @Override
    public Annotator createAnnotator() {
        return new MessageFormatAnnotator();
    }

    private static class MessageFormatAnnotator implements Annotator {
        @RequiredReadAction
        @Override
        public void annotate(@Nonnull PsiElement element, @Nonnull AnnotationHolder holder) {
            if (element.getNode().getElementType() == MessageFormatElementTypes.FORMAT_TYPE) {
                String text = element.getText().trim();
                Set<String> validTypes = getValidFormatTypes(element);
                if (!validTypes.contains(text)) {
                    holder.newAnnotation(HighlightSeverity.ERROR, MessageFormatImplLocalize.errorUnknownFormatType(text).get())
                        .range(element)
                        .create();
                }
            }
        }

        @RequiredReadAction
        private Set<String> getValidFormatTypes(PsiElement element) {
            LanguageVersion version = LanguageVersionUtil.findLanguageVersion(MessageFormatLanguage.INSTANCE, element.getContainingFile());
            if (version instanceof JdkMessageFormatVersion) {
                return JDK_FORMAT_TYPES;
            }
            return ICU_FORMAT_TYPES;
        }
    }
}
