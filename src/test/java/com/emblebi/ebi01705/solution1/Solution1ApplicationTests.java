package com.emblebi.ebi01705.solution1;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
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
	
	@WithAnonymousUser
	@Test
	void findAllByAnonymousUser() throws Exception {
		        
		this.mockMvc.perform(
			get("/person"))
			.andExpect(status().isUnauthorized());		
	}

	@WithMockUser(
		username = "username"
	)
	@Test
	void findAll() throws Exception {
		
		person1 = this.personService.save(person1);
        person2 = this.personService.save(person2);
        
		this.mockMvc.perform(
			get("/person")
			.header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ="))
			.andExpect(status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath("$.person.[*].first_name").isNotEmpty())
			.andDo(document("{methodName}", 
					preprocessRequest(prettyPrint())
					, preprocessResponse(prettyPrint())
					, requestParameters(
						parameterWithName("page").description("No page to retrieve").optional()
			            , parameterWithName("size").description("No of person within a single page").optional()
			        )
					, requestHeaders(headerWithName("Authorization").description("Basic auth credentials"))
					, responseFields(
			            fieldWithPath("person.[].first_name").description("First Name")
			            , fieldWithPath("person.[].last_name").description("Last Name")
			            , fieldWithPath("person.[].age").description("Age")
			            , fieldWithPath("person.[].favourite_colour").description("Favourite Colour")
			            , fieldWithPath("person.[]._links.self.href").description("Hyper links")
			            , fieldWithPath("_links.self.href").description("Self Hyper Link")
			            , fieldWithPath("_links.self.templated").ignored()
			        )
			    )
			);
		
        this.personService.deleteById(person1.getId());
        this.personService.deleteById(person2.getId());
	}

	@WithMockUser(
		username = "username"
	)
	@Test
	void findAllByPagination() throws Exception {
		
		person2 = this.personService.save(person2);
        
		this.mockMvc.perform(
			get("/person?page=0&size=1")
			.header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ="))
			.andExpect(status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath("$.person.[*].first_name").isNotEmpty());
		    
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
		      .header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ="))
		      .andExpect(status().isOk())
		      .andExpect(MockMvcResultMatchers.jsonPath("$.first_name").value(person1.getFirstName()))
		      .andDo(document("{methodName}", 
					preprocessRequest(prettyPrint())
					, preprocessResponse(prettyPrint())
					, pathParameters(
						parameterWithName("id").description("Person Id")
			        )
					, requestHeaders(headerWithName("Authorization").description("Basic auth credentials"))
					, responseFields(
						fieldWithPath("first_name").description("First Name")
			            , fieldWithPath("last_name").description("Last Name")
			            , fieldWithPath("age").description("Age")
			            , fieldWithPath("favourite_colour").description("Favourite Colour")
			            , fieldWithPath("_links.self.href").description("Self Hyper Link")
			        )
			    )
			);
		
        this.personService.deleteById(person1.getId());
	}
	
	@WithMockUser(
		"username"
	)
	@Test
	void addPerson() throws Exception {
		
		this.mockMvc.perform(
	      post("/person")
	      .header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ=")
	      .content(JsonUtils.asJsonString(
	    		  new PersonVO()
					.withFirstName(person1.getFirstName())
					.withLastName(person1.getLastName())
					.withAge(person1.getAge())
					.withFavouriteColour(person1.getFavouriteColour())))
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isCreated())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.first_name").exists())
	      .andDo(document("{methodName}", 
				preprocessRequest(prettyPrint())
				, preprocessResponse(prettyPrint())
				, requestHeaders(headerWithName("Authorization").description("Basic auth credentials"))
				, responseFields(
					fieldWithPath("first_name").description("First Name")
		            , fieldWithPath("last_name").description("Last Name")
		            , fieldWithPath("age").description("Age")
		            , fieldWithPath("favourite_colour").description("Favourite Colour")
		            , fieldWithPath("_links.self.href").description("Self Hyper Link")
		        )
		    )
		);
	}

	@WithMockUser(
		"username"
	)
	@Test
	void update() throws Exception {
		
		person1 = this.personService.save(person1);
        
		this.mockMvc.perform( 
	      put("/person/{id}", person1.getId())
	      .header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ=")
	      .content(JsonUtils.asJsonString(new PersonVO().withFirstName("Pankaj").withLastName("Mandavkar").withAge(30).withFavouriteColour("red")))
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isAccepted())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.first_name").value("Pankaj"))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.last_name").value("Mandavkar"))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.favourite_colour").value("red"))
	      .andDo(document("{methodName}", 
				preprocessRequest(prettyPrint())
				, preprocessResponse(prettyPrint())
				, pathParameters(
					parameterWithName("id").description("Person Id")
		        )
				, requestHeaders(headerWithName("Authorization").description("Basic auth credentials"))
				, responseFields(
		            fieldWithPath("first_name").description("First Name")
		            , fieldWithPath("last_name").description("Last Name")
		            , fieldWithPath("age").description("Age")
		            , fieldWithPath("favourite_colour").description("Favourite Colour")
		            , fieldWithPath("_links.self.href").description("Self Hyper Link")
		        )
		    )
		);
		this.personService.deleteById(person1.getId());
	}
	
	@WithMockUser(
		"username"
	)
	@Test
	void deleteById() throws Exception {
		
		person2 = this.personService.save(person2);
		
		mockMvc.perform( 
			delete("/person/{id}", person2.getId())
			.header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ="))
	        .andExpect(status().isAccepted())
	        .andDo(document("{methodName}", 
				preprocessRequest(prettyPrint())
				, preprocessResponse(prettyPrint())
				, pathParameters(
					parameterWithName("id").description("Person Id")
		        )
				, requestHeaders(headerWithName("Authorization").description("Basic auth credentials"))
		    )
		);
	}
}