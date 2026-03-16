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

package consulo.java.messageFormat.java.inject;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.java.messageFormat.MessageFormatLanguage;
import consulo.java.messageFormat.impl.version.IcuMessageFormatVersion;
import consulo.java.messageFormat.impl.version.JdkMessageFormatVersion;
import consulo.language.inject.MultiHostInjector;
import consulo.language.inject.MultiHostRegistrar;
import consulo.language.psi.ElementManipulators;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiLanguageInjectionHost;
import consulo.language.version.LanguageVersion;
import com.intellij.java.language.psi.*;
import jakarta.annotation.Nonnull;

@ExtensionImpl
public class MessageFormatLanguageInjector implements MultiHostInjector {
    private static final String JDK_MESSAGE_FORMAT = "java.text.MessageFormat";
    private static final String ICU_MESSAGE_FORMAT = "com.ibm.icu.text.MessageFormat";

    @Nonnull
    @Override
    public Class<? extends PsiElement> getElementClass() {
        return PsiLiteralExpression.class;
    }

    @RequiredReadAction
    @Override
    public void injectLanguages(@Nonnull MultiHostRegistrar registrar, @Nonnull PsiElement context) {
        if (!(context instanceof PsiLiteralExpression literal)) {
            return;
        }

        if (!(literal.getValue() instanceof String)) {
            return;
        }

        PsiElement parent = literal.getParent();
        if (parent == null) {
            return;
        }

        LanguageVersion version = resolveVersion(literal, parent);
        if (version == null) {
            return;
        }

        registrar.startInjecting(version)
            .addPlace(null, null, (PsiLanguageInjectionHost) literal, ElementManipulators.getValueTextRange(literal))
            .doneInjecting();
    }

    @RequiredReadAction
    private LanguageVersion resolveVersion(PsiLiteralExpression literal, PsiElement parent) {
        // Check if literal is an argument in a method call or constructor
        if (parent instanceof PsiExpressionList expressionList) {
            PsiElement grandParent = expressionList.getParent();

            if (grandParent instanceof PsiMethodCallExpression methodCall) {
                return resolveFromMethodCall(methodCall, literal, expressionList);
            }
            else if (grandParent instanceof PsiNewExpression newExpr) {
                return resolveFromConstructor(newExpr, literal, expressionList);
            }
        }

        return null;
    }

    @RequiredReadAction
    private LanguageVersion resolveFromMethodCall(PsiMethodCallExpression methodCall,
                                                   PsiLiteralExpression literal,
                                                   PsiExpressionList expressionList) {
        // Only inject into the first (pattern) argument
        PsiExpression[] args = expressionList.getExpressions();
        if (args.length == 0 || args[0] != literal) {
            return null;
        }

        PsiMethod method = methodCall.resolveMethod();
        if (method == null) {
            return null;
        }

        String methodName = method.getName();
        if (!"format".equals(methodName)) {
            return null;
        }

        PsiClass containingClass = method.getContainingClass();
        if (containingClass == null) {
            return null;
        }

        String qualifiedName = containingClass.getQualifiedName();
        return resolveVersionByClassName(qualifiedName);
    }

    @RequiredReadAction
    private LanguageVersion resolveFromConstructor(PsiNewExpression newExpr,
                                                    PsiLiteralExpression literal,
                                                    PsiExpressionList expressionList) {
        // Only inject into the first (pattern) argument
        PsiExpression[] args = expressionList.getExpressions();
        if (args.length == 0 || args[0] != literal) {
            return null;
        }

        PsiJavaCodeReferenceElement classRef = newExpr.getClassReference();
        if (classRef == null) {
            return null;
        }

        PsiElement resolved = classRef.resolve();
        if (!(resolved instanceof PsiClass psiClass)) {
            return null;
        }

        return resolveVersionByClassName(psiClass.getQualifiedName());
    }

    private LanguageVersion resolveVersionByClassName(String qualifiedName) {
        if (JDK_MESSAGE_FORMAT.equals(qualifiedName)) {
            return MessageFormatLanguage.INSTANCE.findVersionByClass(JdkMessageFormatVersion.class);
        }
        else if (ICU_MESSAGE_FORMAT.equals(qualifiedName)) {
            return MessageFormatLanguage.INSTANCE.findVersionByClass(IcuMessageFormatVersion.class);
        }
        return null;
    }
}
