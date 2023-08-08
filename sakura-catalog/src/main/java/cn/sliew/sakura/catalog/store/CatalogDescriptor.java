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

package cn.sliew.sakura.catalog.store;

import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.catalog.Catalog;

/**
 * Describes a {@link Catalog} with the catalog name and configuration.
 *
 * <p>A {@link CatalogDescriptor} is a template for creating a {@link Catalog} instance. It closely
 * resembles the "CREATE CATALOG" SQL DDL statement, containing catalog name and catalog
 * configuration. A {@link CatalogDescriptor} could be stored to {@link CatalogStore}.
 *
 * <p>This can be used to register a catalog in the Table API
 */
@PublicEvolving
public class CatalogDescriptor {

    /* Catalog name */
    private final String catalogName;

    /* The configuration used to discover and construct the catalog. */
    private final Configuration configuration;

    public String getCatalogName() {
        return catalogName;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    private CatalogDescriptor(String catalogName, Configuration configuration) {
        this.catalogName = catalogName;
        this.configuration = configuration;
    }

    /**
     * Creates an instance of this interface.
     *
     * @param catalogName   the name of the catalog
     * @param configuration the configuration of the catalog
     */
    public static CatalogDescriptor of(String catalogName, Configuration configuration) {
        return new CatalogDescriptor(catalogName, configuration);
    }
}
