package hello;

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
	
	public void linkDocuments(long existingDocumentID, long newDocumentID){
		IdentificationDocument existingID = idRepository.findOne(existingDocumentID);
		IdentificationDocument newID = idRepository.findOne(newDocumentID);
		
		existingID.setNextLinkedIdentificationDocumentID(newID.getIdentificationDocumentID());
		
		idRepository.save(existingID);
	}
}
