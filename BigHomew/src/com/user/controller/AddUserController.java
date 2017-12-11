package com.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.LoginUser;
import com.entity.Order;
import com.user.service.AddUserService;
import com.user.service.AdminLoginService;

@Controller
@RequestMapping("/adduser")
public class AddUserController {
@Resource
private AddUserService addUserService;
@Resource
private AdminLoginService adminloginservice;
//注册新用户
@RequestMapping("/add")
	public String adduser(LoginUser loginUser,HttpSession session){
		System.out.println(111111);
		session.setAttribute("name",loginUser.getName());
		addUserService.adduser(loginUser);
		return "index2";
	}
//用户登陆
	//验证用户名
@RequestMapping("/loginname")
@ResponseBody
	public String loginnmae(@RequestParam(value="q") String str,HttpSession session){
	//	String name = loginUser.getName();
		List<LoginUser> list=addUserService.findName(str);
		System.out.println("2111212121");
		System.out.println("用户姓名"+str);
		if(list.isEmpty()){
			return "noexist";
		}else{
				System.out.print("用户名存在");
				session.setAttribute("name",str);
				System.out.println(session.getAttribute("name"));
				return list.get(0).getPassword();		
		}
	}
	//验证密码
@RequestMapping("/loginpassword")
@ResponseBody
	public String loginpassword(@RequestParam(value="q") String str,HttpSession session){
	//	String name = loginUser.getName();
		List<LoginUser> list=addUserService.findName((String) session.getAttribute("name"));
		
		System.out.println("用户姓名"+session.getAttribute("name"));
		System.out.println("密码"+list.get(0).getPassword());
		if(str.equals(list.get(0).getPassword())){
			return "";
		}else{
			return "noexist";		
		}
	}
	//登陆跳转页面
@RequestMapping("/login")
	public String login(){
		return "index2";
	}
//修改个人信息
	@RequestMapping("/usermessage")
	public String message(Model model,HttpSession session){
		List<LoginUser> list1=addUserService.findName((String) session.getAttribute("name"));
		System.out.println("000000000000"+(String) session.getAttribute("name"));
		System.out.println("000000000000"+list1.get(0).getName());
		LoginUser user=new LoginUser();
		user.setAddress(list1.get(0).getAddress());
		user.setEmail(list1.get(0).getEmail());
		user.setId(list1.get(0).getId());
		user.setName(list1.get(0).getName());
		user.setPassword(list1.get(0).getPassword());
		user.setPhone(list1.get(0).getPhone());
		model.addAttribute("password0",list1.get(0).getPassword());
		model.addAttribute("user",user);
		List<Order> list2= adminloginservice.findorder((String) session.getAttribute("name"));
		model.addAttribute("list2",list2);
		return "personal";
	}
//使用ajax验证用户名
	@RequestMapping(value="/check",method=RequestMethod.GET)
	@ResponseBody
	public String checkuser(@RequestParam(value="q") String str){
		 List<LoginUser> list= addUserService.findalluser(); 
		 System.out.println(list.size()+"00000000000");
		 for(int i=0;i<list.size();i++) {
			 System.out.println(list.get(i).getName()+"tttttttttttt");
			 if(list.get(i).getName().equals(str)){
				 return "yes";
			 }
		 }
		 return "ww";
	}
}
