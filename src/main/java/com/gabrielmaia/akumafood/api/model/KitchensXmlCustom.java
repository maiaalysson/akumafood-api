package com.gabrielmaia.akumafood.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.gabrielmaia.akumafood.domain.model.Kitchen;

import lombok.Data;
import lombok.NonNull;

@JacksonXmlRootElement(localName = "kitchens")
@Data
public class KitchensXmlCustom {
	
	@JsonProperty("kitchen")
	@JacksonXmlElementWrapper(useWrapping = false)
	@NonNull
	List<Kitchen> kitchens;
		
}