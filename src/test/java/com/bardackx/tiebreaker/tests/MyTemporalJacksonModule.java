package com.bardackx.tiebreaker.tests;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.ReferenceType;

public class MyTemporalJacksonModule extends Module implements Deserializers {

	private JsonDeserializer<LocalDate> myLocalDateJsonDeserializer;
	private JsonDeserializer<ZonedDateTime> myZonedDateTimeJsonDeserializer;

	public MyTemporalJacksonModule() {

		myLocalDateJsonDeserializer = new JsonDeserializer<LocalDate>() {

			@Override
			public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				return LocalDate.parse(p.getText());
			}
		};

		myZonedDateTimeJsonDeserializer = new JsonDeserializer<ZonedDateTime>() {

			@Override
			public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				return ZonedDateTime.parse(p.getText());
			}
		};
	}

	@Override
	public String getModuleName() {
		return getClass().getCanonicalName();
	}

	@Override
	public Version version() {
		return Version.unknownVersion();
	}

	@Override
	public void setupModule(SetupContext context) {
		context.addDeserializers(this);
	}

	@Override
	public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config,
			BeanDescription beanDesc) throws JsonMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonDeserializer<?> findTreeNodeDeserializer(Class<? extends JsonNode> nodeType,
			DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config,
			BeanDescription beanDesc) throws JsonMappingException {
		if (type.getRawClass() == LocalDate.class)
			return myLocalDateJsonDeserializer;
		if (type.getRawClass() == ZonedDateTime.class)
			return myZonedDateTimeJsonDeserializer;
		return null;
	}

	@Override
	public JsonDeserializer<?> findReferenceDeserializer(ReferenceType refType, DeserializationConfig config,
			BeanDescription beanDesc, TypeDeserializer contentTypeDeserializer, JsonDeserializer<?> contentDeserializer)
			throws JsonMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonDeserializer<?> findArrayDeserializer(ArrayType type, DeserializationConfig config,
			BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer)
			throws JsonMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonDeserializer<?> findCollectionDeserializer(CollectionType type, DeserializationConfig config,
			BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer)
			throws JsonMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonDeserializer<?> findCollectionLikeDeserializer(CollectionLikeType type, DeserializationConfig config,
			BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer)
			throws JsonMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonDeserializer<?> findMapDeserializer(MapType type, DeserializationConfig config, BeanDescription beanDesc,
			KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer,
			JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonDeserializer<?> findMapLikeDeserializer(MapLikeType type, DeserializationConfig config,
			BeanDescription beanDesc, KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer,
			JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
		// TODO Auto-generated method stub
		return null;
	}

}
