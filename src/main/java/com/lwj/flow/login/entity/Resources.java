package com.lwj.flow.login.entity;

public class Resources {
    /**
     * 
     *
     * 
     */
    private Integer id;

    /**
     * 资源名称
     *
     * 
     */
    private String name;

    /**
     * 资源url
     *
     * 
     */
    private String resurl;

    /**
     * 资源类型   1:菜单    2：按钮
     *
     * 
     */
    private Integer type;

    /**
     * 父资源
     *
     * 
     */
    private Integer parentid;

    /**
     * 排序
     *
     * 
     */
    private Integer sort;

    /**
     *
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * 
     */
    public String getName() {
        return name;
    }

    /**
     *
     * 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     *
     * 
     */
    public String getResurl() {
        return resurl;
    }

    /**
     *
     * 
     */
    public void setResurl(String resurl) {
        this.resurl = resurl == null ? null : resurl.trim();
    }

    /**
     *
     * 
     */
    public Integer getType() {
        return type;
    }

    /**
     *
     * 
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     *
     * 
     */
    public Integer getParentid() {
        return parentid;
    }

    /**
     *
     * 
     */
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    /**
     *
     * 
     */
    public Integer getSort() {
        return sort;
    }

    /**
     *
     * 
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}