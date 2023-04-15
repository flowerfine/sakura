package cn.sliew.sakura.catalog.model;

import org.apache.flink.table.catalog.CatalogDatabaseImpl;

import javax.annotation.Nullable;
import java.util.Map;

public class CustomCatalogDatabase extends CatalogDatabaseImpl {

    public CustomCatalogDatabase(Map<String, String> properties, @Nullable String comment) {
        super(properties, comment);
    }
}
