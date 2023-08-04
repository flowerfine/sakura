/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.sakura.catalog.service.convert;

import cn.sliew.sakura.catalog.service.dto.CatalogDatabaseDTO;
import cn.sliew.sakura.common.util.CodecUtil;
import cn.sliew.sakura.common.util.JacksonUtil;
import cn.sliew.sakura.dao.entity.CatalogDatabase;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public enum CatalogDatabaseConvert implements BaseConvert<CatalogDatabase, CatalogDatabaseDTO> {
    INSTANCE;

    @Override
    public CatalogDatabase toDo(CatalogDatabaseDTO dto) {
        CatalogDatabase entity = JacksonUtil.deepCopy(dto, CatalogDatabase.class);
        entity.setProperties(CodecUtil.encrypt(JacksonUtil.toJsonString(dto.getProperties())));
        return entity;
    }

    @Override
    public CatalogDatabaseDTO toDto(CatalogDatabase entity) {
        CatalogDatabaseDTO dto = JacksonUtil.deepCopy(entity, CatalogDatabaseDTO.class);
        Map<String, String> properties = JacksonUtil.parseJsonString(CodecUtil.decrypt(entity.getProperties()), new TypeReference<Map<String, String>>() {
        });
        dto.setProperties(properties);
        return dto;
    }
}