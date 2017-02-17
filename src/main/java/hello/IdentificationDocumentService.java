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
		
		isAlreadyLinked(existingID, newID);
		
		existingID.setNextLinkedIdentificationDocumentID(newID.getIdentificationDocumentID());
		existingID.setHeadIdentificationDocumentID(existingDocumentID);
		newID.setHeadIdentificationDocumentID(existingDocumentID);
		
		idRepository.save(existingID);
	}

	public boolean isAlreadyLinked(IdentificationDocument existingID, IdentificationDocument newID) {
		IdentificationDocument currentID = idRepository.findOne(existingID.getHeadIdentificationDocumentID());
		while(currentID.getNextLinkedIdentificationDocumentID() != 0){
			if(currentID.getIdentificationDocumentID() == newID.getIdentificationDocumentID()){
				return true;
			}else{
				currentID = idRepository.findOne(currentID.getNextLinkedIdentificationDocumentID());
			}
		}
		return false;
	}

	public List<IdentificationDocument> getLinkedDocuments(long existingDocumentID) {
		return null;
		// TODO Auto-generated method stub
		
	}
}
