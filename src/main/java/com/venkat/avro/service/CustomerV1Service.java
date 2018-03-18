package com.venkat.avro.service;

import com.venkat.avro.config.KafkaConfig;
import com.venkat.avro.consumer.CustomerV1Consumer;
import com.venkat.avro.producer.CustomerV1Producer;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerV1Service {

    @Autowired
    private KafkaConfig kafkaConfig;

    public void producer(){
        CustomerV1Producer customerV1Producer = new CustomerV1Producer();
        CustomerV1 customerV1 = CustomerV1.newBuilder()
                .setFirstName("Venkatram")
                .setLastName("Veerareddy")
                .setAge(51)
                .setHeight(162.2f)
                .setWeight(72.4f)
                .build();

        customerV1Producer.produce(customerV1.getFirstName(), customerV1);
    }

    public void consumer(){
        CustomerV1Consumer customerV1Consumer = new CustomerV1Consumer();
        customerV1Consumer.consume();
    }

    public static void main(String[] args) {
        CustomerV1Service customerV1Service = new CustomerV1Service();
        customerV1Service.producer();
        customerV1Service.consumer();
    }
}
