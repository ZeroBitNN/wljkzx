package action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.Contacts;
import pageModel.Json;
import service.ContactsServiceI;

@Namespace("/")
@Action(value = "contactsAction")
public class ContactsAction extends BaseAction implements ModelDriven<Contacts> {
	private Contacts contacts = new Contacts();

	@Override
	public Contacts getModel() {
		return contacts;
	}

	private ContactsServiceI contactsService;

	public ContactsServiceI getContactsService() {
		return contactsService;
	}

	@Autowired
	public void setContactsService(ContactsServiceI contactsService) {
		this.contactsService = contactsService;
	}

	public void doNotNeedSecurity_getDatagrid() {
		List<Contacts> l = contactsService.getAll(contacts);
		super.writeJson(l);
	}

	public void add() {
		Json j = new Json();
		try {
			contactsService.save(contacts);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void edit() {
		Json j = new Json();
		try {
			contactsService.edit(contacts);
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void delete() {
		Json j = new Json();
		try {
			contactsService.delete(contacts);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
}
