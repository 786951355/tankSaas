/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tanker.basemodule.http.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.tanker.basemodule.http.api.HttpResult;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;
    private static boolean tokenInvalid = false;
    private final Gson gson;


    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
//      ((RealResponseBody) value..headers.get("ifUpdateAppClient")
            HttpResult apiModel = (HttpResult) adapter.fromJson(value.charStream());
//      if (apiModel.getCode() == 500) {
//        throw new ServerException(apiModel.getCode(), apiModel.getMessage());
//      }
            //if (tokenInvalid){
            //    apiModel.setCode(500);
            //    apiModel.setMessage("token失效");
            //    tokenInvalid=false;
            //}
            //switch (apiModel.getCode()){
            //    case 0: //成功
            //        tokenInvalid=true;
            //        return (T) apiModel;
            //    default:  //其他服务器错误
            //        throw new ServerException(apiModel.getCode(),apiModel.getMessage());
            //}
            return (T) apiModel;
        } finally {
            value.close();
        }
    }
}
