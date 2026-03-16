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

package consulo.java.messageFormat.impl.parser;

public class JdkMessageFormatParser extends MessageFormatParser {
    // JDK parser uses the base parser behavior.
    // Choice format style is handled by the base parseStyleContent() which
    // already handles nested braces and all tokens within the style section.
    // The lexer handles the choice-specific tokens (PIPE, LESS, etc.) in the
    // AFTER_SECOND_COMMA state.
}
