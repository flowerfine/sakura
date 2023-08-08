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

package cn.sliew.sakura.catalog.store.impl;

import cn.sliew.sakura.catalog.store.AbstractCatalogStore;
import cn.sliew.sakura.catalog.store.CatalogDescriptor;
import org.apache.flink.table.catalog.exceptions.CatalogException;

import java.util.Optional;
import java.util.Set;

public class JdbcCatalogStore extends AbstractCatalogStore {



    @Override
    public void storeCatalog(String catalogName, CatalogDescriptor catalog) throws CatalogException {

    }

    @Override
    public void removeCatalog(String catalogName, boolean ignoreIfNotExists) throws CatalogException {

    }

    @Override
    public Optional<CatalogDescriptor> getCatalog(String catalogName) throws CatalogException {
        return Optional.empty();
    }

    @Override
    public Set<String> listCatalogs() throws CatalogException {
        return null;
    }

    @Override
    public boolean contains(String catalogName) throws CatalogException {
        return false;
    }
}
