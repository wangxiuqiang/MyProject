package cn.ssm.crm.dep.controller;

import java.awt.Dialog.ModalExclusionType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@RequestMapping(value = "/addDep/{depId}/{depName}")
	public String addDep(Model model, @PathVariable String depId, @PathVariable String depName) {
		/*
		 * String name = null; model.addAttribute("name", name);
		 */

		if (depId != null) {
			Department dep = new Department();
			dep.setDepId(depId);
			dep.setDepName(depName);
			model.addAttribute("dep", dep);
		}
		return "/pages/department/addOrEditDepartment";
	}

	/*
	 * @RequestMapping(value="/editDep/{name}") public String editDep(Model model
	 * , @PathVariable String name) { model.addAttribute("name",name); return
	 * "/pages/department/addOrEditDepartment"; }
	 */

	/*
	 * @RequestMapping(value="/updateDep/{before}") public String updateDep(Model
	 * model , @PathVariable String before ,Department department) {
	 * depService.updateDep(department); return "/pages/department/listDepartment";
	 * }
	 */

	@RequestMapping(value = "/insertDep")
	public ModelAndView insertDep(Department department) {
		try {
			if (department.getDepId() == null) {
				depService.insertDep(department);
			} else {
				depService.updateDep(department);
			}
		} catch (Exception exception) {
			if (exception instanceof MyIllException) {

			}
			exception.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView();
		List<Department> listDep = depService.selectAllDep();
		modelAndView.addObject("listDep", listDep);
		modelAndView.setViewName("/pages/department/listDepartment");
		return modelAndView;
	}

	@RequestMapping("/selectAllDepJson")
	@ResponseBody
	public List<Department> selectAllDepJson() {
		List<Department> listDep = depService.selectAllDep();
        return listDep;
	}
	
	@RequestMapping("/selectAllDep")
	
	public String selectAllDep(Model model) {

		List<Department> listDep = depService.selectAllDep();
		model.addAttribute("listDep", listDep);
		return "/pages/department/listDepartment";
	}

	@RequestMapping("/deleteThis/{depId}")
	public String deleteThis(@PathVariable String depId, Model model) {
		depService.deleteThis(depId);
		return "redirect:/dep/selectAllDep";
	}
}
