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
		
		if(!isAlreadyLinked(existingID, newID)){
			existingID.setNextLinkedIdentificationDocumentID(newID.getIdentificationDocumentID());
			existingID.setHeadIdentificationDocumentID(existingDocumentID);
			newID.setHeadIdentificationDocumentID(existingDocumentID);
			
			idRepository.save(existingID);
		}else{
			throw new Exception("document " + existingDocumentID + " already linked to document " + newDocumentID);
		}
	}

	public boolean isAlreadyLinked(IdentificationDocument existingID, IdentificationDocument newID) {
		if(existingID.getHeadIdentificationDocumentID() == 0){
			return false;
		}else if(existingID.getHeadIdentificationDocumentID() == newID.getHeadIdentificationDocumentID()){
			return true;
		}else{
			return false;
		}
	}

	public List<IdentificationDocument> getLinkedDocuments(long existingDocumentID) {
		return null;
	}
}
