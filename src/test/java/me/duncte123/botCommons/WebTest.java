/*
 *    Copyright 2018 Duncan "duncte123" Sterken
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

package me.duncte123.botCommons;

import me.duncte123.botCommons.web.WebUtils;
import org.junit.Test;

public class WebTest {

    @Test
    public void testWebRequest() {
        WebUtils.ins.getJSONObject("https://bot.duncte123.me/api/llama/json")
                .async(json -> System.out.println(json.getString("file")));
    }

}
