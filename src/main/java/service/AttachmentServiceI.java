package service;

import pageModel.Attachment;
import pageModel.DataGrid;

public interface AttachmentServiceI {

	void addFileToDB(Attachment attachment);

	DataGrid<Attachment> getDatagrid(Attachment attachment);

	void delete(String ids) throws Exception;

	void delByRelatedid(String relatedid) throws Exception;

}
