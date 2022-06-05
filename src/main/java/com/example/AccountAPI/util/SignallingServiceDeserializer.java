package com.example.AccountAPI.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SignallingServiceDeserializer extends StdDeserializer<String> {


    public SignallingServiceDeserializer(Class<?> vc) {
        super(vc);
    }


    public SignallingServiceDeserializer() {
        this(null);
    }

    @Override
    public String deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
        return jsonparser.readValueAsTree().toString();
    }

}
