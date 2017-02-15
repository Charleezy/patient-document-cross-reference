package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentificationDocumentService {

	@Autowired
	IdentificationDocumentRepository idRepository;
	
	public void addDocument(){
//		idRepository.save(new IdentificationDocument())
	}
}
