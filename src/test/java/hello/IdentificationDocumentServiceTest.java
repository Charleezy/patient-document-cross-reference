package hello;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class IdentificationDocumentServiceTest {
	@Mock
	IdentificationDocumentRepository identificationDocumentRepository;
	
	@InjectMocks
	IdentificationDocumentService identificationDocumentService;
	
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void shouldAddDocument(){
		IdentificationDocument mockIdentificationDocument = new IdentificationDocument("Government of Canada", "QAZXC123");
		Mockito.when(identificationDocumentRepository.save(mockIdentificationDocument)).thenReturn(mockIdentificationDocument);
		identificationDocumentService.addDocument(mockIdentificationDocument);
		Mockito.verify(identificationDocumentRepository).save(mockIdentificationDocument);
	}
	
	@Test
	public void shouldLinkDocuments(){
		IdentificationDocument mockIdentificationDocument1 = new IdentificationDocument("Government of Canada", "QAZXC123");
		IdentificationDocument mockIdentificationDocument2 = new IdentificationDocument("Government of Canada", "QAZXC123");
		Mockito.when(identificationDocumentRepository.findOne(1L)).thenReturn(mockIdentificationDocument1);
		Mockito.when(identificationDocumentRepository.findOne(2L)).thenReturn(mockIdentificationDocument2);
		identificationDocumentService.linkDocuments(1, 2);
		
		Mockito.verify(identificationDocumentRepository).save(mockIdentificationDocument1);
		Mockito.verify(identificationDocumentRepository).save(mockIdentificationDocument2);
	}
	
    //Should not link if documents already in linked list
	//TODO
//	@Ignore
	@Test(expected=Exception.class)
	public void shouldNotLinkDocumentsIfAlreadyLinked(){
		IdentificationDocument mockIdentificationDocument1 = new IdentificationDocument("Government of Canada", "QAZXC123");
		IdentificationDocument mockIdentificationDocument2 = new IdentificationDocument("Government of Canada", "QAZXC123");
		Mockito.when(identificationDocumentRepository.findOne(1L)).thenReturn(mockIdentificationDocument1);
		Mockito.when(identificationDocumentRepository.findOne(2L)).thenReturn(mockIdentificationDocument2);
		identificationDocumentService.linkDocuments(1, 2);
		
//		mockIdentificationDocument1.setNextLinkedIdentificationDocumentID(mockIdentificationDocument2.getIdentificationDocumentID());
		
		identificationDocumentService.linkDocuments(2, 1);
	}
}
