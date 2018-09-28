package com.lwj.flow.login.entity.vo;

import java.util.List;

import com.lwj.flow.login.entity.Resources;

/**
 * 父子资源类
 * @author luwja
 *
 */
public class ResourceVO {
	
	private int id;
	
	private String name;
	
	
	/**
	 * 子资源节点
	 */
	private List<ResourceVO>  childResources;
	
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ResourceVO> getChildResources() {
		return childResources;
	}
	public void setChildResources(List<ResourceVO> childResources) {
		this.childResources = childResources;
	}
	
	

	
	
	
	

}
