package hello;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.junit.Assert;

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
	public void shouldLinkDocuments() throws Exception{
		IdentificationDocument mockIdentificationDocument1 = new IdentificationDocument("Government of Canada", "QAZXC123");
		IdentificationDocument mockIdentificationDocument2 = new IdentificationDocument("Government of Canada", "QAZXC123");
		Mockito.when(identificationDocumentRepository.findOne(1L)).thenReturn(mockIdentificationDocument1);
		Mockito.when(identificationDocumentRepository.findOne(2L)).thenReturn(mockIdentificationDocument2);
		mockIdentificationDocument1.setHeadIdentificationDocumentID(1);
		identificationDocumentService.linkDocuments(1, 2);
		
		Mockito.verify(identificationDocumentRepository).save(mockIdentificationDocument1);
	}
	
	@Test(expected=Exception.class)
	public void shouldNotLinkDocumentsIfAlreadyLinked() throws Exception{
		IdentificationDocument mockIdentificationDocument1 = new IdentificationDocument("Government of Canada", "QAZXC123");
		IdentificationDocument mockIdentificationDocument2 = new IdentificationDocument("Government of Canada", "QAZXC123");
		Mockito.when(identificationDocumentRepository.findOne(1L)).thenReturn(mockIdentificationDocument1);
		Mockito.when(identificationDocumentRepository.findOne(2L)).thenReturn(mockIdentificationDocument2);
		identificationDocumentService.linkDocuments(1, 2);
		
		identificationDocumentService.linkDocuments(2, 1);
	}
	
	@Test(expected=Exception.class)
	public void linkListShouldAlwaysAddToEndOfList() throws Exception{
		IdentificationDocument mockIdentificationDocument1 = new IdentificationDocument("Government of Canada", "QAZXC123");
		IdentificationDocument mockIdentificationDocument2 = new IdentificationDocument("Government of Canada", "QAZXC123");
		IdentificationDocument mockIdentificationDocument3 = new IdentificationDocument("Government of Canada", "QAZXC123");
		Mockito.when(identificationDocumentRepository.findOne(1L)).thenReturn(mockIdentificationDocument1);
		Mockito.when(identificationDocumentRepository.findOne(2L)).thenReturn(mockIdentificationDocument2);
		Mockito.when(identificationDocumentRepository.findOne(3L)).thenReturn(mockIdentificationDocument3);
		identificationDocumentService.linkDocuments(1, 2);
		identificationDocumentService.linkDocuments(1, 3);
		identificationDocumentService.linkDocuments(1, 2);
	}
	
	@Test
	public void isAlreadyLinkedFalseIfNotLinked(){
		IdentificationDocument mockIdentificationDocument1 = new IdentificationDocument("Government of Canada", "QAZXC123");
		IdentificationDocument mockIdentificationDocument2 = new IdentificationDocument("Government of Canada", "QAZXC123");
		Mockito.when(identificationDocumentRepository.findOne(1L)).thenReturn(mockIdentificationDocument1);
		Mockito.when(identificationDocumentRepository.findOne(2L)).thenReturn(mockIdentificationDocument2);
		
		mockIdentificationDocument1.setHeadIdentificationDocumentID(1);
		
		Assert.assertFalse(identificationDocumentService.isAlreadyLinked(mockIdentificationDocument1, mockIdentificationDocument2));
	}
	
	@Test
	public void isAlreadyLinkedTrueIfLinked(){
		IdentificationDocument mockIdentificationDocument1 = new IdentificationDocument("Government of Canada", "QAZXC123");
		IdentificationDocument mockIdentificationDocument2 = new IdentificationDocument("Government of Canada", "QAZXC123");
		Mockito.when(identificationDocumentRepository.findOne(1L)).thenReturn(mockIdentificationDocument1);
		Mockito.when(identificationDocumentRepository.findOne(2L)).thenReturn(mockIdentificationDocument2);
		
		mockIdentificationDocument1.setHeadIdentificationDocumentID(1);
		mockIdentificationDocument2.setHeadIdentificationDocumentID(1);
		mockIdentificationDocument1.setNextLinkedIdentificationDocumentID(2);
		
		Assert.assertTrue(identificationDocumentService.isAlreadyLinked(mockIdentificationDocument1, mockIdentificationDocument2));
	}
}
