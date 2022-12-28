package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Service {
	
	public void start(HttpServletRequest request, HttpServletResponse response);
	
}
