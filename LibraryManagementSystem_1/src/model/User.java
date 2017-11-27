package model;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 5147265048973262104L;

	private String name;
	
	private String password;
	private Auth authorization;
	public User(String name, String pass, Auth  auth) {
		this.name = name;
		this.password = pass;
		this.authorization = auth;
	}
	
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public Auth getAuthorization() {
		return authorization;
	}
	@Override
	public String toString() {
		return "[" + name + ":" + password + ", " + authorization.toString() + "]";
	}
	
}
