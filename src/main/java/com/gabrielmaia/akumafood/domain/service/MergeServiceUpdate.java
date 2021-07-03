package com.gabrielmaia.akumafood.domain.service;

import java.lang.reflect.Field;
//import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

//import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MergeServiceUpdate {

	/**public void objects(Map<String, Object> valueOrigin, Object destiny) {
		Object originClass = new ObjectMapper().convertValue(valueOrigin, destiny.getClass());

		valueOrigin.forEach((key, value) -> {
			Field field = ReflectionUtils.findField(destiny.getClass(), key);
			field.setAccessible(true);

			Object newValue = ReflectionUtils.getField(field, originClass);

			ReflectionUtils.setField(field, destiny, newValue);
		});
	}**/

	public void objects(final Object source, final Object target) {
		ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				field.setAccessible(true);

				if (field.get(source) != null)
					field.set(target, field.get(source));
			}
		}, ReflectionUtils.COPYABLE_FIELDS);
	}
}
