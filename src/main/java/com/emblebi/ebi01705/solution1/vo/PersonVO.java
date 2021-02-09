package com.emblebi.ebi01705.solution1.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import com.emblebi.ebi01705.solution1.vo.json.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@Component
public class PersonVO extends RepresentationModel<PersonVO> {

	@JsonIgnore
	private Long id;
	
    @JsonView(
    	Views.Basic.class
    )
	@JsonProperty(
		value = "first_name"
	)
	@NotBlank(
		message = "first_name field is required"
	)
	private String firstName;
	
    @JsonView(
    	Views.Basic.class
    )
	@JsonProperty(
		value = "last_name"
	)
	@NotBlank(
		message = "last_name field is required"
	)
	private String lastName;
	
    @JsonView(
    	Views.Basic.class
    )
	@JsonProperty(
		value = "age"
	)
	@NotNull(
		message = "age field is required"
	)
	private Integer age;
	
    @JsonView(
    	Views.Basic.class
    )
	@JsonProperty(
		value = "favourite_colour"
	)
	@NotNull
	@NotEmpty(
		message = "favourite_colour field is required"
	)
	private String favouriteColour;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getFavouriteColour() {
		return favouriteColour;
	}

	public void setFavouriteColour(String favouriteColour) {
		this.favouriteColour = favouriteColour;
	}

	public PersonVO withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public PersonVO withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public PersonVO withAge(Integer age) {
		this.age = age;
		return this;
	}

	public PersonVO withFavouriteColour(String favouriteColour) {
		this.favouriteColour = favouriteColour;
		return this;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((favouriteColour == null) ? 0 : favouriteColour.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonVO other = (PersonVO) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (favouriteColour == null) {
			if (other.favouriteColour != null)
				return false;
		} else if (!favouriteColour.equals(other.favouriteColour))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
}
