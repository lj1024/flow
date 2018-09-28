package com.lwj.flow.login.entity;

public class Role {
    /**
     * 
     *
     * 
     */
    private Integer id;

    /**
     * 
     *
     * 
     */
    private String roledesc;

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
    public String getRoledesc() {
        return roledesc;
    }

    /**
     *
     * 
     */
    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc == null ? null : roledesc.trim();
    }

	@Override
	public String toString() {
		return "Role [id=" + id + ", roledesc=" + roledesc + "]";
	}
    
    
}