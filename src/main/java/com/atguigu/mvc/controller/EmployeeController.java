package com.atguigu.mvc.controller;

import com.atguigu.mvc.dao.EmployeeDao;
import com.atguigu.mvc.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author 李聪燕
 * @date 2022/1/17 16:55
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeDao employeeDao ;

    @GetMapping("/")
    public String getEmployeeList(Model model) {
        Collection<Employee> employeeList = employeeDao.getAll();
        model.addAttribute("employeeList",employeeList);
        return "employee_list";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeDao.delete(id);
        return "redirect:/employee/";
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Integer id,Model model) {
        Employee employee = employeeDao.get(id);
        model.addAttribute("employee",employee);
        return "employee_update";
    }

    @PutMapping("/")
    public String updateSave(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/employee/";
    }
    @PostMapping("/")
    public String add(Employee employee) {
        System.out.println(employee.getId());
        System.out.println(employee.getEmail());
        System.out.println(employee.getGender());
        System.out.println(employee.getLastName());
        employeeDao.save(employee);
        return "redirect:/employee/";
    }
}
