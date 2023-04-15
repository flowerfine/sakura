package cn.sliew.sakura.catalog.model;

import org.apache.flink.table.api.Schema;
import org.apache.flink.table.catalog.DefaultCatalogView;

import javax.annotation.Nullable;
import java.util.Map;

public class CustomCatalogView extends DefaultCatalogView {

    public CustomCatalogView(Schema schema, @Nullable String comment, String originalQuery, String expandedQuery, Map<String, String> options) {
        super(schema, comment, originalQuery, expandedQuery, options);
    }
}
