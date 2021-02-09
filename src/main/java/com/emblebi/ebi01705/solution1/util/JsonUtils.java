package com.emblebi.ebi01705.solution1.util;

import com.emblebi.ebi01705.solution1.vo.PersonVO;
import com.emblebi.ebi01705.solution1.vo.json.Views;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface JsonUtils {
	
	public static String asJsonString(final PersonVO person) {
	    try {
	         return new ObjectMapper()
	        		 .setSerializationInclusion(Include.NON_EMPTY)
	        		 .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
		  	         .writerWithView(Views.Basic.class)
		  	         .writeValueAsString(person);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
