package cn.ssm.crm.dep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ssm.crm.dep.pojo.Department;
import cn.ssm.crm.dep.service.DepService;
import cn.ssm.crm.exception.MyIllException;

@Controller
@RequestMapping("/dep")
public class DepController {
   
	@Autowired
	public DepService depService;
	
	
	
	@RequestMapping(value = "/test")
	public String test() {
		return "test";
	}
	@RequestMapping(value="/insertDep")
	public ModelAndView insertDep(Department department) {
		try {
			depService.insertDep(department);
		}catch(Exception exception) {
			if(exception instanceof MyIllException) {
				
			}
			exception.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/pages/department/listDepartment");
		return modelAndView;
	}
	
	@RequestMapping("/dep/selectAllDep") 
	public String selectAllDep(Model model) {
		try {
			List<Department> listDep = depService.selectAllDep();
		    model.addAttribute("listDep", listDep);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/pages/department/addOrEditDepartment";
	}
	
}
