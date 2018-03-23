package com.example.docker.dockerdemo.config;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/***
 *  ES 配置
 */

@Configuration
public class ElasticsearchConfiguration implements FactoryBean<TransportClient>,InitializingBean,DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfiguration.class);
    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes ;

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;

    private TransportClient client;

    @Override
    public void destroy() throws Exception {
        try {
            logger.info("Closing elasticSearch client");
            if (client != null) {
                client.close();
            }
        } catch (final Exception e) {
            logger.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public TransportClient getObject() throws Exception {
        return client;
    }

    @Override
    public Class<TransportClient> getObjectType() {
        return TransportClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildClient();
    }

    protected void buildClient()  {
        try {
            PreBuiltTransportClient  preBuiltTransportClient = new PreBuiltTransportClient(settings());
            if (!"".equals(clusterNodes)){
                for (String nodes:clusterNodes.split(",")) {
                    String InetSocket [] = nodes.split(":");
                    String  Address = InetSocket[0];
                    Integer  port = Integer.valueOf(InetSocket[1]);
                    preBuiltTransportClient.addTransportAddress(new
                            InetSocketTransportAddress(InetAddress.getByName(Address),port ));
                }
                client = preBuiltTransportClient;
            }
        } catch (UnknownHostException e) {
            logger.error(e.getMessage());
        }
    }
    /**
     * 初始化默认的client
     */
    private Settings settings(){
        Settings settings = Settings.builder()
                .put("cluster.name",clusterName)
                .put("client.transport.sniff",true).build();
        //设置"client.transport.sniff" 为true 是客户端去嗅探整个集群的状态，把集群中其他机器ip地址加到客户端中，这样做的好处是一般我们不需要手动设置集群里所有集群的IP到连接客户端她会自动帮你添加，并且自动发现新加入集群的机器
        client = new PreBuiltTransportClient(settings);
        return settings;
    }
}
