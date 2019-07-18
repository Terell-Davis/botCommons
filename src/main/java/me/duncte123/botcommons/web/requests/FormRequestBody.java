/*
 *    Copyright 2019 Duncan "duncte123" Sterken
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package me.duncte123.botcommons.web.requests;

import me.duncte123.botcommons.StringUtils;
import me.duncte123.botcommons.web.ContentType;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static me.duncte123.botcommons.web.WebUtils.urlEncodeString;

public class FormRequestBody implements IRequestBody {

    private final Map<String, String> params = new HashMap<>();

    public FormRequestBody append(@NotNull String key, @NotNull String value) {
        params.put(key, value);

        return this;
    }

    @Override
    public @NotNull String getContentType() {
        return ContentType.URLENCODED.getType();
    }

    @Override
    public @NotNull RequestBody toRequestBody() {
        final StringBuilder postParams = new StringBuilder();

        for (final Map.Entry<String, String> entry : params.entrySet()) {
            postParams
                .append(urlEncodeString(entry.getKey()))
                .append('=')
                .append(urlEncodeString(entry.getValue()))
                .append('&');

        }

        final String postString = StringUtils.replaceLast(postParams.toString(), "\\&", "");

        return RequestBody.create(postString.getBytes());
    }
}
