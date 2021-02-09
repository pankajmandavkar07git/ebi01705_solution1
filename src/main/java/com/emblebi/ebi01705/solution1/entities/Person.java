package com.emblebi.ebi01705.solution1.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {
				"first_name"
				, "last_name"
			}
			, name = "user_name_uk"
		)
	}
)
public class Person {

	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE
	)
	private Long id;
	
	@Column(
		name = "first_name"
	)
	private String firstName;
	
	@Column(
		name = "last_name"
	)
	private String lastName;
	
	@Column(
		nullable = false
	)
	private Integer age;
	
	@OneToOne
	@JoinColumn(
		name = "favourite_color_id"
		, nullable = false
	)
	private Colour favouriteColour;

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

	public Colour getFavouriteColour() {
		return favouriteColour;
	}

	public void setFavouriteColour(Colour favouriteColour) {
		this.favouriteColour = favouriteColour;
	}
}
