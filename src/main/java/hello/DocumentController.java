package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DocumentController {
	
	@RequestMapping("/addDocument")
    public String addDocument(@RequestParam(value="identificationDocument", required=true) String identificationDocumentID, Model model) {
        model.addAttribute("name", identificationDocumentID);
        return "greeting";
    }
}
