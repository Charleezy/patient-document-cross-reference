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
}
