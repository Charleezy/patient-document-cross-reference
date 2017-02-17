package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DocumentController {
	
	@Autowired
	IdentificationDocumentService idService;
	
	@RequestMapping("/addDocument")
    public ResponseEntity addDocument(@RequestParam(value="issuer", required=true) String issuer, @RequestParam(value="id", required=true) String id) {
        long documentID = idService.addDocument(new IdentificationDocument(issuer, id));
		ResponseEntity response = ResponseEntity.status(HttpStatus.CREATED).header("Location", "/getDocument/" + documentID).build();
		return response;
    }
	
	@RequestMapping("/linkDocument")
    public ResponseEntity linkDocument(@RequestParam(value="existingDocumentID", required=true) long existingDocumentID, @RequestParam(value="newDocumentID", required=true) long newDocumentID) {
		ResponseEntity response;
		try {
			idService.linkDocuments(existingDocumentID, newDocumentID);
			response = ResponseEntity.status(HttpStatus.OK).body("document " + existingDocumentID + " linked to document " + newDocumentID);
		} catch (Exception e) {
			response = ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("document " + existingDocumentID + " already linked to document " + newDocumentID);
		}
		return response;
    }
}
