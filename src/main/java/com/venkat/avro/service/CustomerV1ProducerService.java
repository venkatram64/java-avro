package com.venkat.avro.service;

import com.venkat.avro.config.KafkaConfig;
import com.venkat.avro.consumer.CustomerV1Consumer;
import com.venkat.avro.producer.CustomerV1Producer;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerV1ProducerService {

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

    public static void main(String[] args) {
        CustomerV1ProducerService customerV1Service = new CustomerV1ProducerService();
        customerV1Service.producer();
    }
}
