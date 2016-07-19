/*
 * Copyright 2014-2016 Message4U Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.messagemedia.restapi.client.v1.internal.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeZoneDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeZoneSerializer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper methods for mapping objects to JSON and vice versa.
 * <p/>
 * This class provides access to an ObjectMapper that correctly handles dates represented using ISO8601.
 * To improve detection of invalid or ambiguous input, years containing fewer than four digits will be rejected.
 */
public class JsonUtilities {

    private static final Logger LOGGER = Logger.getLogger(JsonUtilities.class.getName());
    private static final Pattern YEAR_PATTERN = Pattern.compile("\\d{4}.*");

    private static class DateSerializer extends JsonSerializer<DateTime> {
        @Override
        public void serialize(DateTime value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
            jsonGenerator.writeString(ISODateTimeFormat.dateTimeNoMillis().withZone(DateTimeZone.UTC).print(new DateTime(value, DateTimeZone.UTC)));
        }
    }

    private static class DateDeserializer extends JsonDeserializer<DateTime> {
        @Override
        public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Matcher matcher = YEAR_PATTERN.matcher(jsonParser.getValueAsString());
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Year must contain 4 digits.");
            }
            return ISODateTimeFormat.dateTimeParser().withZone(DateTimeZone.UTC).parseDateTime(jsonParser.getValueAsString());
        }
    }

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        // Set up a module that correctly handles ISO8601 dates
        SimpleModule module = new SimpleModule();

        module.addSerializer(DateTime.class, new DateSerializer());
        module.addDeserializer(DateTime.class, new DateDeserializer());

        module.addSerializer(DateTimeZone.class, new DateTimeZoneSerializer());
        module.addDeserializer(DateTimeZone.class, new DateTimeZoneDeserializer());

        MAPPER.registerModule(module);

        MAPPER.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        MAPPER.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        MAPPER.setSerializationInclusion(Include.NON_NULL);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        MAPPER.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(Visibility.ANY).withSetterVisibility(Visibility.ANY));
        MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public static <T> T bytesToObject(byte[] data, Class<T> clazz) throws IOException {
        return MAPPER.readValue(data, clazz);
    }

    public static <T> byte[] objectToBytes(T object) throws JsonProcessingException {
        try {
            return MAPPER.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.SEVERE, "Error converting object to bytes", e);
            throw e;
        }
    }

}
