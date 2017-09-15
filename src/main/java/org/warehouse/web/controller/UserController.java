package org.warehouse.web.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.warehouse.util.*;
import org.warehouse.web.dao.*;
import org.warehouse.web.dao.user.UserMapper;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserMapper mapper;
	
	@RequestMapping("list")
	String list(String name, Page page, Model model) {
		List<Map<String, Object>> list = mapper.queryList(name, 0, page);
		int count = mapper.queryCount(name, 0);
		page.setCount(count);
		
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		return "user/list";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	String add() {
		return "user/add";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	String add(String name, String password, Model model) {
		if(mapper.queryUser(name, null) != null) {
			model.addAttribute("msg", "帐号已存在！");
			return "/user/add";
		}
		int result = mapper.addUser(name, password);
		if(result == 0) {
			model.addAttribute("msg", "保存失败！");
			return "/user/add";
		}
		return "redirect:/user/list.html";
	}
}