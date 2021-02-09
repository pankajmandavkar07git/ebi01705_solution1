package com.emblebi.ebi01705.solution1;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.emblebi.ebi01705.solution1.service.PersonService;
import com.emblebi.ebi01705.solution1.util.JsonUtils;
import com.emblebi.ebi01705.solution1.vo.PersonVO;

@ExtendWith({
	SpringExtension.class
	, RestDocumentationExtension.class
})
@AutoConfigureRestDocs(
	outputDir = "build/generated-snippets"
)
@SpringBootTest(
	webEnvironment = WebEnvironment.RANDOM_PORT
)
class Solution1ApplicationTests {

	private static PersonVO person1;
	
	private static PersonVO person2;
	
	static {
		person1 = new PersonVO();
        person1.setAge(29);
        person1.setFirstName("John");
        person1.setLastName("Keynes");
        person1.setFavouriteColour("red");

        person2 = new PersonVO();
        person2.setAge(54);
        person2.setFirstName("Sarah");
        person2.setLastName("Robinson");
        person2.setFavouriteColour("blue");
	}
	
	
	private MockMvc mockMvc;
	
    @Autowired
    private PersonService personService;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private RestDocumentationContextProvider restDocumentation;
        
	@BeforeEach
    void setup() {
		this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }
	
	@WithMockUser(
		"username"
	)
	@Test
	void findAll() throws Exception {
		
		person1 = this.personService.save(person1);
        person2 = this.personService.save(person2);
        
		this.mockMvc.perform(
			get("/person")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath("$.[*].first_name").isNotEmpty())
		    .andDo(document("{methodName}",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())));
		
        this.personService.deleteById(person1.getId());
        this.personService.deleteById(person2.getId());
	}

	@WithMockUser(
		"username"
	)
	@Test
	void findById() throws Exception { 

		person1 = this.personService.save(person1);
        
		this.mockMvc.perform(
		      get("/person/{id}", person1.getId())
		      .contentType(MediaType.APPLICATION_JSON))
		      .andExpect(status().isOk())
		      .andExpect(MockMvcResultMatchers.jsonPath("$.first_name").value(person1.getFirstName()))
		      .andDo(document("{methodName}",
	                    preprocessRequest(prettyPrint()),
	                    preprocessResponse(prettyPrint())));
		
        this.personService.deleteById(person1.getId());
	}
	
	@WithMockUser(
		"username"
	)
	@Test
	void addPerson() throws Exception {
		
		this.mockMvc.perform(
	      post("/person")
	      .content(JsonUtils.asJsonString(
	    		  new PersonVO()
					.withFirstName(person1.getFirstName())
					.withLastName(person1.getLastName())
					.withAge(person1.getAge())
					.withFavouriteColour(person1.getFavouriteColour())))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isCreated())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.first_name").exists())
	      .andDo(document("{methodName}",
                  preprocessRequest(prettyPrint()),
                  preprocessResponse(prettyPrint())));
	}

	@WithMockUser(
		"username"
	)
	@Test
	void update() throws Exception {
		
		person1 = this.personService.save(person1);
        
		this.mockMvc.perform( 
	      put("/person/{id}", person1.getId())
	      .content(JsonUtils.asJsonString(new PersonVO().withFirstName("Pankaj").withLastName("Mandavkar").withAge(30).withFavouriteColour("red")))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isAccepted())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.first_name").value("Pankaj"))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.last_name").value("Mandavkar"))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.favourite_colour").value("red"))
	      .andDo(document("{methodName}",
                  preprocessRequest(prettyPrint()),
                  preprocessResponse(prettyPrint())));
		
		this.personService.deleteById(person1.getId());
	}
	
	@WithMockUser(
		"username"
	)
	@Test
	void deleteById() throws Exception {
		
		person2 = this.personService.save(person2);
		
		mockMvc.perform( 
			delete("/person/{id}", person2.getId()))
	        .andExpect(status().isAccepted())
	        .andDo(document("{methodName}",
	                  preprocessRequest(prettyPrint()),
	                  preprocessResponse(prettyPrint())));
	}
}
