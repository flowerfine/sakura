package cn.sliew.sakura.catalog.hive;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.RetryingMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.MetaException;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        test(init(getConfig()));
    }

    private static Configuration getConfig() {
        Configuration conf = new Configuration();
        conf.set("hive.metastore.uris", "thrift://localhost:9083");
//        conf.set("hive.metastore.warehouse.dir", "");
//        conf.set("hive.metastore.sasl.enabled", "false");
//        conf.set("hive.metastore.kerberos.keytab.file", "");
//        conf.set("hive.metastore.kerberos.principal", "");
//        conf.set("hive.metastore.client.socket.timeout", "30000");
        return conf;
    }

    private static IMetaStoreClient init(Configuration conf) throws MetaException {
        try {
            return RetryingMetaStoreClient.getProxy(conf, false);
        } catch (MetaException e) {
            log.error("hms连接失败", e);
            throw e;
        }
    }

    private static void test(IMetaStoreClient client) throws Exception {
        System.out.println("----------------------------获取所有catalogs-------------------------------------");
        client.getCatalogs().forEach(System.out::println);

        System.out.println("------------------------获取catalog为hive的描述信息--------------------------------");
        System.out.println(client.getCatalog("hive").toString());

        System.out.println("--------------------获取catalog为hive的所有database-------------------------------");
        client.getAllDatabases("hive").forEach(System.out::println);

        System.out.println("---------------获取catalog为hive，database为hive的描述信息--------------------------");
        System.out.println(client.getDatabase("hive", "hive_storage"));

        System.out.println("-----------获取catalog为hive，database名为hive_storage下的所有表--------------------");
        client.getTables("hive", "hive_storage", "*").forEach(System.out::println);

        System.out.println("------获取catalog为hive，database名为hive_storage，表名为sample_table_1的描述信息-----");
        System.out.println(client.getTable("hive", "hive_storage", "sample_table_1").toString());
    }
}
