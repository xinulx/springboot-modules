package com.springboot.solr.demo.vo;

import org.apache.solr.client.solrj.beans.Field;
 
/**
 * @author wangshibao
 * 先写模型类代码，这个里面引入了一个Fieid类，在每个参数定义前注解
 * 如果不注解，存储时就会提示存储异常，字段对应不上，相当于做了类似序列化之类的工作
 * 最复杂的应该是查询部分，设置参数时，名称需要看SolrQuery的API
 */
public class Items {
	/** 编号 */
	@Field
	private Integer id;
	/** 名称 */
	@Field
	private String name;
	/** 价格 */
	@Field
	private double price;
	/** 图片 */
	@Field
	private String display_picture;
	/** 时间 */
	@Field
	private Integer release_time;
	/** 状态 */
	@Field
	private Integer release_state;
	/** 交易量 */
	@Field
	private Integer deals;
	/** 浏览量 */
	@Field
	private Integer hits;
 
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("编号:").append(id);
		sb.append(", 名称:").append(name);
		sb.append(", 价格:").append(price);
		sb.append(", 交易量:").append(deals);
		sb.append(", 浏览量:").append(hits);
		sb.append(", 时间:").append(release_time);
		return sb.toString();
	}
 
	/**
	 * 获取id 的值
	 * 
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
 
	/**
	 * 设置id 的值
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
 
	/**
	 * 获取name 的值
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
 
	/**
	 * 设置name 的值
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
 
	/**
	 * 获取price 的值
	 * 
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
 
	/**
	 * 设置price 的值
	 * 
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
 
	/**
	 * 获取deals 的值
	 * 
	 * @return the deals
	 */
	public Integer getDeals() {
		return deals;
	}
 
	/**
	 * 设置deals 的值
	 * 
	 * @param deals
	 *            the deals to set
	 */
	public void setDeals(Integer deals) {
		this.deals = deals;
	}
 
	/**
	 * 获取hits 的值
	 * 
	 * @return the hits
	 */
	public Integer getHits() {
		return hits;
	}
 
	/**
	 * 设置hits 的值
	 * 
	 * @param hits
	 *            the hits to set
	 */
	public void setHits(Integer hits) {
		this.hits = hits;
	}
 
	/**
	 * 获取display_picture 的值
	 * 
	 * @return the display_picture
	 */
	public String getDisplay_picture() {
		return display_picture;
	}
 
	/**
	 * 设置display_picture 的值
	 * 
	 * @param display_picture
	 *            the display_picture to set
	 */
	public void setDisplay_picture(String display_picture) {
		this.display_picture = display_picture;
	}
 
	/**
	 * 获取release_time 的值
	 * 
	 * @return the release_time
	 */
	public Integer getRelease_time() {
		return release_time;
	}
 
	/**
	 * 设置release_time 的值
	 * 
	 * @param release_time
	 *            the release_time to set
	 */
	public void setRelease_time(Integer release_time) {
		this.release_time = release_time;
	}
 
	/**
	 * 获取release_state 的值
	 * 
	 * @return the release_state
	 */
	public Integer getRelease_state() {
		return release_state;
	}
 
	/**
	 * 设置release_state 的值
	 * 
	 * @param release_state
	 *            the release_state to set
	 */
	public void setRelease_state(Integer release_state) {
		this.release_state = release_state;
	}
}
