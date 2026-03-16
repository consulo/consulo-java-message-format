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

module consulo.java.messageFormat.language.impl {
    requires consulo.language.api;
    requires consulo.language.impl;
    requires consulo.language.editor.api;

    requires consulo.java.messageFormat.language.api;

    exports consulo.java.messageFormat.impl;
    exports consulo.java.messageFormat.impl.lexer;
    exports consulo.java.messageFormat.impl.parser;
    exports consulo.java.messageFormat.impl.version;
    exports consulo.java.messageFormat.impl.highlighting;
    exports consulo.java.messageFormat.impl.annotator;
    exports consulo.java.messageFormat.impl.completion;
    exports consulo.java.messageFormat.impl.localize;
    exports consulo.java.messageFormat.impl.psi;
}
