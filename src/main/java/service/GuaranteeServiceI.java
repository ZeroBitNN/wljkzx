package service;

import java.util.List;

import pageModel.DataGrid;
import pageModel.Guarantee;
import pageModel.GuaranteeNotice;

public interface GuaranteeServiceI {

	DataGrid<Guarantee> getGuaranteeDg(Guarantee guarantee);

	List<Guarantee> getDataList(Guarantee guarantee);

	Guarantee getGuarantee(String id);

	Guarantee save(Guarantee guarantee);

	void delete(String id);

	Guarantee edit(Guarantee guarantee);

	void updateStatus(String id);

	DataGrid<GuaranteeNotice> getNoticeByRelated(GuaranteeNotice guaranteeNotice);

	void saveNotice(GuaranteeNotice guaranteeNotice);

	void delNotice(String string);

	GuaranteeNotice editNotice(GuaranteeNotice guaranteeNotice);

	List<GuaranteeNotice> getDataForExcel(GuaranteeNotice guaranteeNotice);

}
