package cn.sliew.sakura.catalog.model;

import org.apache.flink.table.catalog.CatalogFunctionImpl;
import org.apache.flink.table.catalog.FunctionLanguage;
import org.apache.flink.table.resource.ResourceUri;

import java.util.List;

public class CustomCatalogFunction extends CatalogFunctionImpl {

    public CustomCatalogFunction(String className) {
        super(className);
    }

    public CustomCatalogFunction(String className, FunctionLanguage functionLanguage) {
        super(className, functionLanguage);
    }

    public CustomCatalogFunction(String className, FunctionLanguage functionLanguage, List<ResourceUri> resourceUris) {
        super(className, functionLanguage, resourceUris);
    }
}
