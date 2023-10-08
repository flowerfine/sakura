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

import cn.sliew.sakura.catalog.service.dto.CatalogStoreDTO;
import cn.sliew.sakura.common.exception.Rethrower;
import cn.sliew.sakura.common.util.CodecUtil;
import cn.sliew.sakura.common.util.JacksonUtil;
import cn.sliew.sakura.dao.entity.CatalogStore;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public enum CatalogStoreConvert implements BaseConvert<CatalogStore, CatalogStoreDTO> {
    INSTANCE;

    @Override
    public CatalogStore toDo(CatalogStoreDTO dto) {
        try {
            CatalogStore entity = new CatalogStore();
            Util.copyProperties(dto, entity);
            entity.setCatalogName(dto.getCatalogName());
            if (dto.getConfiguration() != null) {
                entity.setConfiguration(CodecUtil.decrypt(JacksonUtil.toJsonString(dto.getConfiguration())));
            }
            return entity;
        } catch (Exception e) {
            Rethrower.throwAs(e);
            return null;
        }
    }

    @Override
    public CatalogStoreDTO toDto(CatalogStore entity) {
        try {
            CatalogStoreDTO dto = new CatalogStoreDTO();
            Util.copyProperties(entity, dto);
            dto.setCatalogName(entity.getCatalogName());
            if (entity != null && StringUtils.isNotBlank(entity.getConfiguration())) {
                Map<String, String> configuration = JacksonUtil.parseJsonString(CodecUtil.decrypt(entity.getConfiguration()), new TypeReference<Map<String, String>>() {
                });
                dto.setConfiguration(Configuration.fromMap(configuration));
            }
            return dto;
        } catch (Exception e) {
            Rethrower.throwAs(e);
            return null;
        }
    }
}
