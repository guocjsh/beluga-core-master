/**
 * Copyright (c) 2021-2028, iron.guo 郭成杰 (jiedreams@sina.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.beluga.notice.sms;

import com.aliyuncs.exceptions.ClientException;

import java.util.Map;

public interface SMS {

    /**
     * 发送短信接口
     * @param params
     * @return
     * @throws ClientException
     */
    public boolean sendSMS(SMSEntity params) throws ClientException;
}
