package be.shoktan.ingressCompagnion.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller in charge for base actions
 * @author wisthler
 *
 */
@Controller
@RequestMapping("/")
public class HomeController {
	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}
	
	@RequestMapping(value="stat", method = RequestMethod.GET)
	public String stat(){
		return "stat";
	}
	
	@RequestMapping("properties")
	@ResponseBody
	public java.util.Properties properties() {
		return System.getProperties();
	}
}
