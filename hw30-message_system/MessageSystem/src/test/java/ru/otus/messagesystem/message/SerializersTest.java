package ru.otus.messagesystem.message;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SerializersTest {

    @Test
    void serializeDeSerialize() {
        TestData testData = new TestData(1, "str", 2);

        byte[] data = Serializers.serialize(testData);

        TestData object = (TestData) Serializers.deserialize(data);
        assertThat(object).isEqualTo(testData);
    }
}
