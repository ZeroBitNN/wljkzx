package service;

import java.util.List;

import model.TOwogImages;
import owogModel.OwogImages;
import pageModel.DataGrid;

public interface OwogImagesServiceI {

	int getDataNum(OwogImages owogImages);

	void addFileToDB(OwogImages owogImages);

	DataGrid<OwogImages> getAllDg(OwogImages owogImages);

	void delete(String id, String currentUser, String savePath) throws Exception;

	List<TOwogImages> getListForShow();

}
