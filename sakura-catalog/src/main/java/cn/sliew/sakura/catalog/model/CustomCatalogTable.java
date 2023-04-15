package cn.sliew.sakura.catalog.model;

import org.apache.flink.table.api.Schema;
import org.apache.flink.table.catalog.DefaultCatalogTable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class CustomCatalogTable extends DefaultCatalogTable {

    public CustomCatalogTable(Schema schema, @Nullable String comment, List<String> partitionKeys, Map<String, String> options) {
        super(schema, comment, partitionKeys, options);
    }
}
