/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hello;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

//    @Autowired
    private MockMvc mockMvc;
    
    @Mock
    private IdentificationDocumentService idService;
    
    @InjectMocks
    private DocumentController documentController;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(documentController).build();
    }
    
    @Test
    public void addDocumentWithIdentification() throws Exception{
    	mockMvc.perform(post("/addDocument").param("issuer", "Government of Canada").param("id", "Qazxc123")).andExpect(status().isCreated());
    }
    
    @Test
	public void shouldCreateEntity() throws Exception {

		mockMvc.perform(post("/addDocument").param("issuer", "Government of Canada").param("id", "Qazxc123")).andExpect(
						status().isCreated()).andExpect(
								header().string("Location", containsString("getDocument/")));
	}
    
    @Test
	public void shouldLinkDocuments() throws Exception {

		mockMvc.perform(post("/linkDocument").param("existingDocumentID", "1").param("newDocumentID", "2")).andExpect(
						status().isOk()).andExpect(
								content().string(containsString("document 1 linked to document 2")));
		Mockito.verify(idService).linkDocuments(1, 2);
	}
    
    @Test
	public void shouldRetrieveDocuments() throws Exception {
    	mockMvc.perform(get("/getPatientDocuments").param("identificationDocumentID", "1")).andExpect(
						status().isOk()).andExpect(content().string(containsString("the following documents are linked: ")));

		Mockito.verify(idService).getLinkedDocuments(1);
    }
}
