package com.venkat.avro.producer;

import com.venkat.avro.config.KafkaConfig;
import com.venkat.avro.service.CustomerV1;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class CustomerV1Producer {

    private KafkaProducer<String,CustomerV1> kafkaProducer;
    private String topicName;

    public CustomerV1Producer(){

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        properties.put("acks","1");
        properties.put("retries", "10");
        properties.put("schema.registry.url", "http://192.168.99.100:8081");
        this.topicName = "customer-avro";

        kafkaProducer = new KafkaProducer<String, CustomerV1>(properties);
    }

    public CustomerV1Producer(KafkaConfig kafkaConfig){

        Properties properties = new Properties();
        properties.put("zookeeper.connect", kafkaConfig.getZookeeperUrl());
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServiceConfig());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        properties.put("acks",kafkaConfig.getAcks());
        properties.put("retries", kafkaConfig.getRetries());
        properties.put("schema.registry.url", kafkaConfig.getSchemaRegistryUrl());
        this.topicName = kafkaConfig.getTopicName();

        kafkaProducer = new KafkaProducer<String, CustomerV1>(properties);
    }

    public void produce(String key, CustomerV1 customerV1){
        kafkaProducer.send(new ProducerRecord<>(this.topicName, /*key, */customerV1), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e == null) {
                    System.out.println("Sent record successfully!!!");
                    System.out.println(recordMetadata.toString());
                }else{
                    System.out.println("Failed ");
                    e.printStackTrace();
                }
            }
        });
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
