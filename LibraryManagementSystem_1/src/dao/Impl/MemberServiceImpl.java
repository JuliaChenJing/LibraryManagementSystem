package dao.Impl;

import java.util.HashMap;

import dao.Impl.DataAccessFacade.StorageType;
import dao.service.DataAccess;
import dao.service.MemberService;
import model.LibraryMember;

public class MemberServiceImpl implements MemberService{
	
	DataAccess dataAccess = new DataAccessFacade();
	@Override
	public void saveMember(LibraryMember member) throws Exception {
		
		HashMap<String, LibraryMember> memberMap = dataAccess.readMemberMap();
		String memberId = member.getMemberId();
		memberMap.put(memberId, member);
		DataAccessFacade.saveToStorage(StorageType.MEMBERS, memberMap);
		System.out.println(DataAccessFacade.readFromStorage(StorageType.MEMBERS));
	}

	@Override
	public LibraryMember searchMember(String id) throws Exception {
		HashMap<String, LibraryMember> libMemberMap = dataAccess.readMemberMap();
		LibraryMember libMember = libMemberMap.get(id);
		return libMember;
	}

	@Override
	public void editMember(LibraryMember member) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
