package service;

import pageModel.DataGrid;
import pageModel.NetManage;

public interface NetManageServiceI {
	
	public NetManage save(NetManage netManage);
	public DataGrid<NetManage> getAll(NetManage netManage);
	public void delete(String id);
	public NetManage edit(NetManage netManage);

}
