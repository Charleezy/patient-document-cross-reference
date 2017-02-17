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
		mockIdentificationDocument.setIdentificationDocumentID(1L);
		Mockito.when(identificationDocumentRepository.save(mockIdentificationDocument)).thenReturn(mockIdentificationDocument);
		identificationDocumentService.addDocument(mockIdentificationDocument);
		Mockito.verify(identificationDocumentRepository).save(mockIdentificationDocument);
	}
	
	@Test
	public void shouldLinkDocuments() throws Exception{
		IdentificationDocument mockIdentificationDocument1 = new IdentificationDocument("Government of Canada", "QAZXC123");
		mockIdentificationDocument1.setIdentificationDocumentID(1L);
		IdentificationDocument mockIdentificationDocument2 = new IdentificationDocument("Government of Canada", "QAZXC123");
		mockIdentificationDocument2.setIdentificationDocumentID(2L);
		Mockito.when(identificationDocumentRepository.findOne(1L)).thenReturn(mockIdentificationDocument1);
		Mockito.when(identificationDocumentRepository.findOne(2L)).thenReturn(mockIdentificationDocument2);
		mockIdentificationDocument1.setHeadIdentificationDocumentID(1);
		identificationDocumentService.linkDocuments(1, 2);
		
		Mockito.verify(identificationDocumentRepository).save(mockIdentificationDocument1);
	}
	
	@Test(expected=Exception.class)
	public void shouldNotLinkDocumentsIfAlreadyLinked() throws Exception{
		IdentificationDocument mockIdentificationDocument1 = new IdentificationDocument("Government of Canada", "QAZXC123");
		mockIdentificationDocument1.setIdentificationDocumentID(1L);
		IdentificationDocument mockIdentificationDocument2 = new IdentificationDocument("Government of Canada", "QAZXC123");
		mockIdentificationDocument2.setIdentificationDocumentID(2L);
		Mockito.when(identificationDocumentRepository.findOne(1L)).thenReturn(mockIdentificationDocument1);
		Mockito.when(identificationDocumentRepository.findOne(2L)).thenReturn(mockIdentificationDocument2);
		identificationDocumentService.linkDocuments(1, 2);
		
		identificationDocumentService.linkDocuments(2, 1);
	}
	
	@Test(expected=Exception.class)
	public void linkListShouldAlwaysAddToEndOfList() throws Exception{
		IdentificationDocument mockIdentificationDocument1 = new IdentificationDocument("Government of Canada", "QAZXC123");
		mockIdentificationDocument1.setIdentificationDocumentID(1L);
		IdentificationDocument mockIdentificationDocument2 = new IdentificationDocument("Government of Canada", "QAZXC123");
		mockIdentificationDocument2.setIdentificationDocumentID(2L);
		IdentificationDocument mockIdentificationDocument3 = new IdentificationDocument("Government of Canada", "QAZXC123");
		mockIdentificationDocument3.setIdentificationDocumentID(3L);
		Mockito.when(identificationDocumentRepository.findOne(1L)).thenReturn(mockIdentificationDocument1);
		Mockito.when(identificationDocumentRepository.findOne(2L)).thenReturn(mockIdentificationDocument2);
		Mockito.when(identificationDocumentRepository.findOne(3L)).thenReturn(mockIdentificationDocument3);
		identificationDocumentService.linkDocuments(1, 2);
		identificationDocumentService.linkDocuments(1, 3);
		identificationDocumentService.linkDocuments(1, 2);
	}
	
	//TODO: test for linking multiple lists of patient documents
	//TODO: test for linking 3 or more documents
	
	@Test
	public void isAlreadyLinkedFalseIfNotLinked(){
		IdentificationDocument mockIdentificationDocument1 = new IdentificationDocument("Government of Canada", "QAZXC123");
		mockIdentificationDocument1.setIdentificationDocumentID(1L);
		IdentificationDocument mockIdentificationDocument2 = new IdentificationDocument("Government of Canada", "QAZXC123");
		mockIdentificationDocument2.setIdentificationDocumentID(2L);
		Mockito.when(identificationDocumentRepository.findOne(1L)).thenReturn(mockIdentificationDocument1);
		Mockito.when(identificationDocumentRepository.findOne(2L)).thenReturn(mockIdentificationDocument2);
		
		mockIdentificationDocument1.setHeadIdentificationDocumentID(1);
		
		Assert.assertFalse(identificationDocumentService.isAlreadyLinked(mockIdentificationDocument1, mockIdentificationDocument2));
	}
	
	@Test
	public void isAlreadyLinkedTrueIfLinked(){
		IdentificationDocument mockIdentificationDocument1 = new IdentificationDocument("Government of Canada", "QAZXC123");
		mockIdentificationDocument1.setIdentificationDocumentID(1L);
		IdentificationDocument mockIdentificationDocument2 = new IdentificationDocument("Government of Canada", "QAZXC123");
		mockIdentificationDocument2.setIdentificationDocumentID(2L);
		Mockito.when(identificationDocumentRepository.findOne(1L)).thenReturn(mockIdentificationDocument1);
		Mockito.when(identificationDocumentRepository.findOne(2L)).thenReturn(mockIdentificationDocument2);
		
		mockIdentificationDocument1.setHeadIdentificationDocumentID(1);
		mockIdentificationDocument2.setHeadIdentificationDocumentID(1);
		mockIdentificationDocument1.setNextLinkedIdentificationDocumentID(2);
		
		Assert.assertTrue(identificationDocumentService.isAlreadyLinked(mockIdentificationDocument1, mockIdentificationDocument2));
	}
	
	@Test
	public void shouldGetLinkedDocuments() throws Exception{
		IdentificationDocument mockIdentificationDocument1 = new IdentificationDocument("Government of Canada", "QAZXC123");
		mockIdentificationDocument1.setIdentificationDocumentID(1L);
		IdentificationDocument mockIdentificationDocument2 = new IdentificationDocument("Government of Canada", "QAZXC123");
		mockIdentificationDocument2.setIdentificationDocumentID(2L);
		Mockito.when(identificationDocumentRepository.findOne(1L)).thenReturn(mockIdentificationDocument1);
		Mockito.when(identificationDocumentRepository.findOne(2L)).thenReturn(mockIdentificationDocument2);
		
		mockIdentificationDocument1.setHeadIdentificationDocumentID(1);
		mockIdentificationDocument2.setHeadIdentificationDocumentID(1);
		mockIdentificationDocument1.setNextLinkedIdentificationDocumentID(2);
		
		identificationDocumentService.getLinkedDocuments(1L);
		
		Mockito.verify(identificationDocumentRepository).findOne(1L);
		Mockito.verify(identificationDocumentRepository).findOne(2L);
	}
}
