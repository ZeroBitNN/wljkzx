package action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import com.opensymphony.xwork2.ModelDriven;

import pageModel.FileModel;

@Namespace("/")
@Action(value = "searchFileAction")
public class SearchFileAction extends BaseAction implements ModelDriven<FileModel> {

	private FileModel fileModel = new FileModel();

	@Override
	public FileModel getModel() {
		return fileModel;
	}

	public void searchFile(){
		//List<FileModel> hitsList = createIndexService.getTopDoc(fileModel.getKeyWords(),indexPath,10);
		//super.writeJson(hitsList);
	}
}
