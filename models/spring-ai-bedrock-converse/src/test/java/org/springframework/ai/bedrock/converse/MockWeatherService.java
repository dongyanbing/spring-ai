/*
 * Copyright 2024-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ai.bedrock.converse;

import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Christian Tzolov
 */
public class MockWeatherService implements Function<MockWeatherService.Request, MockWeatherService.Response> {

	@Override
	public Response apply(Request request) {

		double temperature = 0;
		if (request.location().contains("Paris")) {
			temperature = 15;
		}
		else if (request.location().contains("Tokyo")) {
			temperature = 10;
		}
		else if (request.location().contains("San Francisco")) {
			temperature = 30;
		}

		return new Response(temperature, Unit.C);
	}

	/**
	 * Temperature units.
	 */
	public enum Unit {

		/**
		 * Celsius.
		 */
		C("metric"),
		/**
		 * Fahrenheit.
		 */
		F("imperial");

		/**
		 * Human readable unit name.
		 */
		public final String unitName;

		private Unit(String text) {
			this.unitName = text;
		}

	}

	/**
	 * Weather Function request.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonClassDescription("Weather API request")
	public record Request(@JsonProperty(required = true,
			value = "location") @JsonPropertyDescription("The city and state e.g. San Francisco, CA") String location,
			@JsonProperty(required = true, value = "unit") @JsonPropertyDescription("Temperature unit") Unit unit) {

	}

	/**
	 * Weather Function response.
	 */
	public record Response(double temp, Unit unit) {

	}

}
