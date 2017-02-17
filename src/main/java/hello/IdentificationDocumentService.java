package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentificationDocumentService {

	@Autowired
	IdentificationDocumentRepository idRepository;
	
	public long addDocument(IdentificationDocument identificationDocument){
		IdentificationDocument identificationDocumentSaved = idRepository.save(identificationDocument);
		return identificationDocumentSaved.getIdentificationDocumentID();
	}
	
	public void linkDocuments(long existingDocumentID, long newDocumentID) throws Exception{
		IdentificationDocument existingID = idRepository.findOne(existingDocumentID);
		IdentificationDocument newID = idRepository.findOne(newDocumentID);
		
		if(existingID.getLinkedDocuments().stream().anyMatch(ld -> ld.equals(newDocumentID))){
			throw new Exception();
		}
		
		existingID.linkDocument(newDocumentID);
		newID.linkDocument(existingDocumentID);
		
		idRepository.save(existingID);
		idRepository.save(newID);
	}

	public List<IdentificationDocument> getLinkedDocuments(long existingDocumentID) {
		return null;
		// TODO Auto-generated method stub
		
	}
}
