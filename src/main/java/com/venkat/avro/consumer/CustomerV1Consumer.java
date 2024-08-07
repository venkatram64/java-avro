package com.venkat.avro.consumer;

import com.venkat.avro.service.CustomerV1;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class CustomerV1Consumer {

    private String topicName;

    private KafkaConsumer<String, CustomerV1> kafkaConsumer;

    public CustomerV1Consumer(){

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-wsl:9092"); //"192.168.99.100:9092"
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        properties.put("schema.registry.url", "http://kafka-wsl:8081");
        properties.put("group.id","my-avro-groupid");
        properties.put("enable.auto.commit", "false");
        properties.put("auto.offset.reset","earliest"); //from beginning
        properties.put("specific.avro.reader", "true");

        this.topicName = "customer-avro";
        this.kafkaConsumer = new KafkaConsumer(properties);
        this.kafkaConsumer.subscribe(Collections.singleton(this.topicName));
        System.out.println("Waiting for data!!!");
    }

    public void consume(){

        while(true){
            ConsumerRecords<String, CustomerV1> consumerRecords = kafkaConsumer.poll(500);
            //System.out.println("records size " + consumerRecords.count());
            for(ConsumerRecord<String, CustomerV1> consumerRec : consumerRecords){
                CustomerV1 v1 = consumerRec.value();
                System.out.println("***************** Reading message ***************** " + v1.toString());
            }
            //kafkaConsumer.commitAsync();
            kafkaConsumer.commitSync();
        }
    }
}
