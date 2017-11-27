package dao.service;

import model.LibraryMember;

public interface MemberService {
	public void saveMember(LibraryMember member) throws Exception;
	public LibraryMember searchMember(String id) throws Exception;
	public void editMember(LibraryMember member) throws Exception;
} 
